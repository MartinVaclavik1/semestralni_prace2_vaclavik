package com.mycompany.semestralni.prace.vaclavik.gui;

import com.mycompany.semestralni.prace.vaclavik.data.Bryle;
import com.mycompany.semestralni.prace.vaclavik.data.Neopren;
import com.mycompany.semestralni.prace.vaclavik.data.PotapecskaPotreba;
import com.mycompany.semestralni.prace.vaclavik.data.Snorchl;
import com.mycompany.semestralni.prace.vaclavik.spravce.Spravce;
import com.mycompany.semestralni.prace.vaclavik.spravce.SpravceRozhrani;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class Gui extends Application {
    private SpravceRozhrani spravce = new Spravce();
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(observableList);
    private ComboBox comboBoxNovy, comboBoxNajdi, comboBoxFiltr;
    private Comparator<String> comparatorTypPtomka = Comparator.comparing(String::strip);
    private Comparator<PotapecskaPotreba> comparatorToString = Comparator.comparing(PotapecskaPotreba::toString);
    private Predicate<PotapecskaPotreba> filtr = Objects::nonNull;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        listView.setMouseTransparent(true);
        listView.setFocusTraversable(false);
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        root.setCenter(listView);
        root.setRight(vBox);
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setSpacing(5);

        vBox.getChildren().add(new Label("Procházení"));
        vBox.getChildren().add(newVButton("první", nastavPrvni()));
        vBox.getChildren().add(newVButton("další", nastavDalsi()));
        vBox.getChildren().add(newVButton("předchozí", nastavPredchozi()));
        vBox.getChildren().addAll(newVButton("poslední", nastavPosledni()), new Label("Příkazy"));
        vBox.getChildren().add(newVButton("edituj", edituj()));
        vBox.getChildren().add(newVButton("vyjmi", vyjmi()));

        HBox hBox = new HBox();
        root.setBottom(hBox);
        hBox.setPadding(new Insets(10, 0, 10, 10));
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(newHButton("generuj", generuj()));
        hBox.getChildren().add(newHButton("uloz", uloz()));
        hBox.getChildren().add(newHButton("nacti", nacti()));
        hBox.getChildren().add(new Label("nový:"));

        comboBoxNovy = newComboBox(novy());
        comboBoxNovy.getItems().addAll("nový", "brýle", "šnorchl", "neopren");
        comboBoxNovy.getSelectionModel().selectFirst();
        hBox.getChildren().add(comboBoxNovy);

        comboBoxFiltr = newComboBox(filtruj());
        comboBoxFiltr.getItems().addAll("filtr", "brýle", "šnorchl", "neopren", "resetovat");
        comboBoxFiltr.getSelectionModel().selectFirst();
        hBox.getChildren().add(comboBoxFiltr);

        comboBoxNajdi = newComboBox(najdi());
        comboBoxNajdi.getItems().addAll("najdi", "brýle", "šnorchl", "neopren", "resetovat");
        comboBoxNajdi.getSelectionModel().selectFirst();
        hBox.getChildren().add(comboBoxNajdi);

        hBox.getChildren().add(newHButton("zálohuj", zalohuj()));
        hBox.getChildren().add(newHButton("obnov", obnov()));
        hBox.getChildren().add(newHButton("zruš", zrus()));

        Scene scene = new Scene(root);
        stage.setTitle("Václavík - potápěčské potřeby");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }

    private EventHandler<ActionEvent> filtruj() {
        return EventHandler -> {
            try {
                observableList.clear();

                switch (comboBoxFiltr.getSelectionModel().getSelectedIndex()) {
                    case 1://bryle
                        filtr = x -> comparatorTypPtomka.compare(x.getTypPotomka(), "bryle") == 0;

                        break;
                    case 2://snorchl
                        filtr = x -> comparatorTypPtomka.compare(x.getTypPotomka(), "snorchl") == 0;

                        break;
                    case 3://neopren
                        filtr = x -> comparatorTypPtomka.compare(x.getTypPotomka(), "neopren") == 0;

                        break;
                    default:
                        filtr = Objects::nonNull;
                        comboBoxFiltr.getSelectionModel().selectFirst();
                        break;
                }
                vlozDoListView();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> najdi() {
        return EventHandler -> {
            try {
                switch (comboBoxNajdi.getSelectionModel().getSelectedIndex()) {
                    case 1://bryle
                        try {
                            PotapecskaPotreba potreba = dialogBryle(new Bryle(), false);
                            if (potreba != null && spravce.najdi(potreba) != null)
                                filtr = obj -> comparatorToString.compare(obj, potreba) == 0;
                        } catch (NoSuchElementException x) {
                            alert("prvek nenalezen");
                            comboBoxNajdi.getSelectionModel().selectFirst();
                        }

                        break;
                    case 2://snorchl
                        try {
                            PotapecskaPotreba potreba = dialogSnorchl(new Snorchl(), false);
                            if (potreba != null && spravce.najdi(potreba) != null)
                                filtr = obj -> comparatorToString.compare(obj, potreba) == 0;
                        } catch (NoSuchElementException x) {
                            alert("prvek nenalezen");
                            comboBoxNajdi.getSelectionModel().selectFirst();
                        }

                        break;
                    case 3://neopren
                        try {
                            PotapecskaPotreba potreba = dialogNeopren(new Neopren(), false);
                            if (potreba != null && spravce.najdi(potreba) != null)
                                filtr = obj -> comparatorToString.compare(obj, potreba) == 0;
                        } catch (NoSuchElementException x) {
                            alert("prvek nenalezen");
                            comboBoxNajdi.getSelectionModel().selectFirst();
                        }

                        break;
                    default:
                        filtr = Objects::nonNull;
                        comboBoxNajdi.getSelectionModel().selectFirst();
                        break;
                }
                vlozDoListView();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> novy() {
        return EventHandler -> {
            try {
                switch (comboBoxNovy.getSelectionModel().getSelectedIndex()) {
                    case 1://brýle
                        Bryle bryle = dialogBryle(new Bryle(), false);
                        if (bryle != null)
                            spravce.novy(bryle);
                        break;
                    case 2://šnorchl
                        Snorchl snorchl = dialogSnorchl(new Snorchl(), false);
                        if (snorchl != null)
                            spravce.novy(snorchl);
                        break;
                    case 3://neopren
                        Neopren neopren = dialogNeopren(new Neopren(), false);
                        if (neopren != null)
                            spravce.novy(neopren);
                        break;
                }
                comboBoxNovy.getSelectionModel().selectFirst();
                vlozDoListView();
                listView.getSelectionModel().select(spravce.dejAktualni().toString());
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> zrus() {
        return ActionEvent -> {
            try {
                spravce.zrus();
                vlozDoListView();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> obnov() {
        return ActionEvent -> {
            try {
                spravce.obnov();
                vlozDoListView();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> zalohuj() {
        return ActionEvent -> {
            try {
                spravce.zalohuj();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> nacti() {
        return actionEvent -> {
            try {
                spravce.nactitext();
                vlozDoListView();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> uloz() {
        return actionEvent -> {
            try {
                spravce.uloztext();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> generuj() {
        return ActionEvent -> {
            try {
                spravce.generuj();
                vlozDoListView();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> vyjmi() {
        return actionEvent -> {
            try {
                spravce.odeberAktualni();
                observableList.remove(listView.getSelectionModel().getSelectedIndex());
                listView.getSelectionModel().clearSelection();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };

    }

    private EventHandler<ActionEvent> edituj() {
        return actionEvent -> {
            try {
                PotapecskaPotreba potapecskaPotreba = (PotapecskaPotreba) spravce.dejAktualni();
                if (potapecskaPotreba != null) {
                    switch (potapecskaPotreba.getTypPotomka()) {
                        case "bryle":
                            dialogBryle((Bryle) potapecskaPotreba, true);
                            break;
                        case "snorchl":
                            dialogSnorchl((Snorchl) potapecskaPotreba, true);
                            break;
                        case "neopren":
                            dialogNeopren((Neopren) potapecskaPotreba, true);
                            break;
                        default:
                            alert("Interní chyba v přiřazování typu prvku");
                            break;
                    }
                    vlozDoListView();
                    listView.getSelectionModel().select(String.valueOf(potapecskaPotreba));
                }
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private Neopren dialogNeopren(Neopren neopren, Boolean editace) {
        Dialog<String> dialog = new Dialog<>();
        GridPane grid = gridPaneZaklad();

        ComboBox zip = new ComboBox<>();
        zip.getItems().addAll("Ano", "Ne");
        zip.getSelectionModel().selectFirst();

        grid.add(new Label("Je hlavní zip vzadu?"), 0, 0);
        grid.add(zip, 1, 0);

        TextField txtTloustka = new TextField();
        grid.add(new Label("Tloušťka: "), 0, 1);
        grid.add(txtTloustka, 1, 1);

        TextField txtZnacka = new TextField();
        grid.add(txtZnacka, 1, 2);

        TextField txtCarovyKod = new TextField();
        grid.add(txtCarovyKod, 1, 3);


        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        if (editace) {

            if (neopren.isHlavniZipVzadu()) {
                zip.getSelectionModel().selectFirst();
            } else {
                zip.getSelectionModel().selectLast();
            }
            txtTloustka.setText(String.valueOf(neopren.getTloustka()));
            txtZnacka.setText(String.valueOf(neopren.getZnacka()));
            txtCarovyKod.setText(String.valueOf(neopren.getCarovyKod()));
        }

        dialog.setResultConverter(new Callback<>() {
            @Override
            public String call(ButtonType buttonType) {
                if (buttonType == ButtonType.OK) {
                    String hodnoty = "neopren";

                    if (jsouNeprazdneHodnoty(new String[]{txtTloustka.getText(),
                            txtZnacka.getText(), txtCarovyKod.getText()})) {
                        try {
                            neopren.setHlavniZipVzadu(zip.getSelectionModel().isSelected(0));
                            neopren.setTloustka(Double.parseDouble(txtTloustka.getText()));
                            neopren.setZnacka(txtZnacka.getText());
                            neopren.setCarovyKod(txtCarovyKod.getText());
                        } catch (Exception x) {
                            alert("Chyba v zadávání hodnot");
                        }
                        return hodnoty;
                    } else {
                        alert("Špatně zadány vstupní hodnoty");
                        dialogNeopren(neopren, editace);
                        return null;
                    }
                }
                return null;
            }
        });


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return neopren;
        } else {
            return null;
        }
    }

    private Snorchl dialogSnorchl(Snorchl snorchl, Boolean editace) {
        Dialog<String> dialog = new Dialog<>();
        GridPane grid = gridPaneZaklad();

        ComboBox jeVlnolam = new ComboBox<>();
        jeVlnolam.getItems().addAll("Ano", "Ne");
        jeVlnolam.getSelectionModel().selectFirst();

        grid.add(new Label("Má vlnolam?"), 0, 0);
        grid.add(jeVlnolam, 1, 0);

        ComboBox jeVymenitelnyNahubek = new ComboBox<>();
        jeVymenitelnyNahubek.getItems().addAll("Ano", "Ne");
        jeVymenitelnyNahubek.getSelectionModel().selectFirst();

        grid.add(new Label("Je vyměnitelný náhubek?"), 0, 1);
        grid.add(jeVymenitelnyNahubek, 1, 1);

        TextField txtZnacka = new TextField();
        grid.add(txtZnacka, 1, 2);

        TextField txtCarovyKod = new TextField();
        grid.add(txtCarovyKod, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        if (editace) {
            if (snorchl.isVlnolam()) {
                jeVlnolam.getSelectionModel().selectFirst();
            } else {
                jeVlnolam.getSelectionModel().selectLast();
            }

            if (snorchl.isVymenitelnyNahubek()) {
                jeVymenitelnyNahubek.getSelectionModel().selectFirst();
            } else {
                jeVymenitelnyNahubek.getSelectionModel().selectLast();
            }

            txtZnacka.setText(String.valueOf(snorchl.getZnacka()));
            txtCarovyKod.setText(String.valueOf(snorchl.getCarovyKod()));
        }

        dialog.setResultConverter(new Callback<>() {
            @Override
            public String call(ButtonType buttonType) {
                if (buttonType == ButtonType.OK) {
                    String hodnoty = "Snorchl";


                    if (jsouNeprazdneHodnoty(new String[]{txtZnacka.getText(), txtCarovyKod.getText()})) {
                        try {
                            snorchl.setVlnolam(jeVlnolam.getSelectionModel().isSelected(0));
                            snorchl.setVymenitelnyNahubek(jeVymenitelnyNahubek.getSelectionModel().isSelected(0));
                            snorchl.setZnacka(txtZnacka.getText());
                            snorchl.setCarovyKod(txtCarovyKod.getText());
                            return hodnoty;

                        } catch (Exception x) {
                            alert("Chyba v zadávání hodnot");
                        }
                    } else {
                        alert("Špatně zadány vstupní hodnoty");
                        dialogSnorchl(snorchl, editace);
                        return null;
                    }
                }
                return null;

            }
        });


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return snorchl;
        } else {
            return null;
        }
    }

    private Bryle dialogBryle(Bryle bryle, Boolean editace) {
        Dialog<String> dialog = new Dialog<>();
        GridPane grid = gridPaneZaklad();
        ComboBox jeJednolite = new ComboBox<>();
        jeJednolite.getItems().addAll("Ano", "Ne");
        jeJednolite.getSelectionModel().selectFirst();

        grid.add(new Label("Má jednolité sklo?"), 0, 0);
        grid.add(jeJednolite, 1, 0);

        TextField txtMaxHloubka = new TextField();
        grid.add(new Label("Maximální hloubka ponoru: "), 0, 1);
        grid.add(txtMaxHloubka, 1, 1);

        TextField txtZnacka = new TextField();
        grid.add(txtZnacka, 1, 2);

        TextField txtCarovyKod = new TextField();
        grid.add(txtCarovyKod, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        if (editace) {
            if (bryle.isJednoliteSklo()) {
                jeJednolite.getSelectionModel().selectFirst();
            } else {
                jeJednolite.getSelectionModel().selectLast();
            }

            txtMaxHloubka.setText(String.valueOf(bryle.getMaxHloubkaPonoru()));
            txtZnacka.setText(String.valueOf(bryle.getZnacka()));
            txtCarovyKod.setText(String.valueOf(bryle.getCarovyKod()));
        }

        dialog.setResultConverter(new Callback<>() {
            @Override
            public String call(ButtonType buttonType) {
                if (buttonType == ButtonType.OK) {
                    String hodnoty = "bryle";

                    if (jsouNeprazdneHodnoty(new String[]{txtMaxHloubka.getText(),
                            txtZnacka.getText(), txtCarovyKod.getText()})) {
                        try {
                            bryle.setJednoliteSklo(jeJednolite.getSelectionModel().isSelected(0));
                            bryle.setMaxHloubkaPonoru(Integer.parseInt(txtMaxHloubka.getText()));
                            bryle.setZnacka(txtZnacka.getText());
                            bryle.setCarovyKod(txtCarovyKod.getText());
                        } catch (Exception x) {
                            alert("Chyba v zadávání hodnot");
                        }
                        return hodnoty;
                    } else {
                        alert("Špatně zadány vstupní hodonty");
                        dialogBryle(bryle, editace);
                        return null;
                    }
                }
                return null;
            }
        });


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return bryle;
        } else {
            return null;
        }
    }

    private boolean jsouNeprazdneHodnoty(String[] hodnoty) {
        for (int i = 0; i < hodnoty.length; i++) {
            if (hodnoty[i] == null || hodnoty[i].isBlank()) {
                return false;
            }
        }
        return true;
    }

    private GridPane gridPaneZaklad() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.add(new Label("Značka:"), 0, 2);
        gridPane.add(new Label("Čárový kód:"), 0, 3);

        return gridPane;
    }

    private EventHandler<ActionEvent> nastavPosledni() {
        return actionEvent -> {
            try {
                spravce.posledni();
                listView.getSelectionModel().selectLast();
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> nastavPredchozi() {
        return actionEvent -> {
            try {
                if (!spravce.dejAktualni().equals(spravce.dejPrvni())) {
                    spravce.predAktualnim(spravce.dejAktualni());
                    int vybrany = listView.getSelectionModel().getSelectedIndex();
                    if (vybrany > 0)
                        listView.getSelectionModel().select(vybrany - 1);
                } else {
                    alert("Není předchozí prvek (již je nasatven první)");
                }
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> nastavDalsi() {
        return actionEvent -> {
            try {
                if (!spravce.dejAktualni().equals(spravce.dejPosledni())) {
                    spravce.nastavDalsi();
                    listView.getSelectionModel().selectNext();
                } else {
                    alert("Není další prvek (již je nastaven poslední)");
                }
            } catch (RuntimeException x) {
                alert(x.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> nastavPrvni() {
        return actionEvent -> {
            try {
                spravce.nastavPrvni();
                listView.getSelectionModel().selectFirst();
            } catch (RuntimeException ex) {
                alert(ex.getMessage());
            }

        };
    }

    private void vlozDoListView() {
        observableList.clear();
        if (spravce.pocet() == 1) {
            observableList.add(spravce.dejPrvni().toString());
            return;
        }
        spravce.getData()
                .stream()
                .filter(filtr)
                .forEach(x -> observableList.add(x.toString()));

        //TODO přidat .filter(filtr);
//        Iterator iterator = seznam.iterator();
//        if (!seznam.jePrazdny()) {
//            do {
//                observableList.add(iterator.next().toString());
//            } while (iterator.hasNext());
//        }
    }

    private Button newVButton(
            String text,
            EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        button.setPrefWidth(70);
        return button;
    }

    private Button newHButton(
            String text,
            EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        return button;
    }

    private ComboBox newComboBox(EventHandler<ActionEvent> handler) {
        ComboBox comboBox = new ComboBox<>();
        comboBox.setOnAction(handler);
        return comboBox;
    }

    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText(text);
        alert.setTitle("Chyba");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

}
