package com.mycompany.semestralni.prace.vaclavik.perzistence;

import com.mycompany.semestralni.prace.vaclavik.data.Bryle;
import com.mycompany.semestralni.prace.vaclavik.data.Neopren;
import com.mycompany.semestralni.prace.vaclavik.data.PotapecskaPotreba;
import com.mycompany.semestralni.prace.vaclavik.data.Snorchl;
import com.mycompany.semestralni.prace.vaclavik.kolekce.KolekceException;
import com.mycompany.semestralni.prace.vaclavik.kolekce.Seznam;
import com.mycompany.semestralni.prace.vaclavik.kolekce.SpojovySeznam;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;

public class InputOutput {
    private final static String nazevBinSouboru = "binZaloha.bin";
    private final static String nazevTxtSouboru = "txtZaloha.txt";

    public static Seznam nactiText() throws KolekceException {
        Seznam seznam = new SpojovySeznam();

        try (BufferedReader cteni = new BufferedReader(new FileReader(nazevTxtSouboru))) {
            String text = cteni.readLine();
            while (text != null) {
                String[] data = text.split(";");
                String typPotomka = data[0];
                switch (typPotomka) {
                    case "bryle":
                        seznam.vlozPosledni(new Bryle(Boolean.parseBoolean(data[1]), Integer.parseInt(data[2]), data[3], data[4]));
                        break;
                    case "snorchl":
                        seznam.vlozPosledni(new Snorchl(Boolean.parseBoolean(data[1]), Boolean.parseBoolean(data[2]), data[3], data[4]));
                        break;
                    case "neopren":
                        seznam.vlozPosledni(new Neopren(Boolean.parseBoolean(data[1]), Double.parseDouble(data[2]), data[3], data[4]));
                        break;
                }
                text = cteni.readLine();
            }
        } catch (FileNotFoundException x) {
            System.err.println("Soubor nenalezen");
        } catch (IOException x) {
            System.err.println("Chyba při čtení souboru");
        }
        return seznam;
    }

    public static void ulozText(Seznam seznam) throws KolekceException {
        seznam.nastavPrvni();

        PotapecskaPotreba aktualni = (PotapecskaPotreba) seznam.dejAktualni();

        try (BufferedWriter zapis = new BufferedWriter(new FileWriter(nazevTxtSouboru))) {
            for (int i = 0; i < seznam.size(); i++) {

                zapis.write(aktualni.getTypPotomka());
                switch (aktualni.getTypPotomka()) {
                    case "bryle":
                        Bryle bryle = (Bryle) aktualni;
                        zapis.write(";" + String.valueOf(bryle.isJednoliteSklo()));
                        zapis.write(";" + bryle.getMaxHloubkaPonoru());
                        break;
                    case "snorchl":
                        Snorchl snorchl = (Snorchl) aktualni;
                        zapis.write(";" + String.valueOf(snorchl.isVlnolam()));
                        zapis.write(";" + String.valueOf(snorchl.isVymenitelnyNahubek()));
                        break;
                    case "neopren":
                        Neopren neopren = (Neopren) aktualni;
                        zapis.write(";" + String.valueOf(neopren.isHlavniZipVzadu()));
                        zapis.write(";" + String.valueOf(neopren.getTloustka()));
                        break;
                }

                zapis.write(";" + aktualni.getZnacka());
                zapis.write(";" + aktualni.getCarovyKod());

                zapis.newLine();
                if (seznam.jeDalsi()) {
                    seznam.dalsi();
                    aktualni = (PotapecskaPotreba) seznam.dejAktualni();
                }
            }

        } catch (FileNotFoundException x) {
            System.err.println("Soubor nenalezen");
        } catch (IOException x) {
            System.err.println("Chyba v ukládání souboru");
        }
    }

    public static void zalohuj(Seznam seznam){
        //FileOutputStream fout = new FileOutputStream("zaloha.dat");
        //DataOutputStream dout = new DataOutputStream(fout);
        //InputStream inputStream = new FileInputStream(inputFile);
        //OutputStream outputStream = new FileOutputStream(outputFile);
//        seznam.nastavPrvni();
//
//        PotapecskaPotreba aktualni = (PotapecskaPotreba) seznam.dejAktualni();

//        try {
//            Objects.requireNonNull(seznam);
//
//            ObjectOutputStream vystup = new ObjectOutputStream(new FileOutputStream(nazevBinSouboru));
//
//            //vystup.reset();
//            byte[] text = aktualni.getCarovyKod().getBytes();
//            for(byte byt:text){
//                System.out.print(byt);
//            }
//            for (int i = 0; i < seznam.size(); i++) {
//
//                vystup.write(aktualni.getTypPotomka().getBytes());
//                switch (aktualni.getTypPotomka()) {
//                    case "bryle":
//                        vystup.write((";" + String.valueOf(aktualni.isJednoliteSklo((Bryle) aktualni))).getBytes());
//                        vystup.write((";" + aktualni.getMaxHloubkaPonoru((Bryle) aktualni)).getBytes());
//                        break;
//                    case "snorchl":
//                        vystup.write((";" + String.valueOf(aktualni.isVlnolam((Snorchl) aktualni))).getBytes());
//                        vystup.write((";" + String.valueOf(aktualni.isVymenitelnyNahubek((Snorchl) aktualni))).getBytes());
//                        break;
//                    case "neopren":
//                        vystup.write((";" + String.valueOf(aktualni.isHlavniZipVzadu((Neopren) aktualni))).getBytes());
//                        vystup.write((";" + String.valueOf(aktualni.getTloustka((Neopren) aktualni))).getBytes());
//                        break;
//                }
//
//                vystup.write((";" + aktualni.getZnacka()).getBytes());
//                vystup.write((";" + aktualni.getCarovyKod() + "\n").getBytes());
//
//                if (seznam.jeDalsi()) {
//                    seznam.dalsi();
//                    aktualni = (PotapecskaPotreba) seznam.dejAktualni();
//                }
//            }
//            vystup.close();
//        } catch (IOException ex) {
//            System.err.println("Chyba v zálohování dat");
//        }
        try {
            Objects.requireNonNull(seznam);

            ObjectOutputStream vystup =
                    new ObjectOutputStream(
                            new FileOutputStream(nazevBinSouboru));


            vystup.writeInt(seznam.size());


            Iterator<Seznam> it = seznam.iterator();
            while (it.hasNext()) {
                vystup.writeObject(it.next());
            }

            vystup.close();
        } catch (IOException ex) {
            System.err.println("Chyba v zálohování dat");
        }
    }

    public static Seznam obnov() {
        Seznam seznam = new SpojovySeznam();

//        try {
//            ObjectInputStream vstup = new ObjectInputStream(new FileInputStream(nazevBinSouboru));
//            String nacti = vstup.readLine();
//            while (nacti != null) {
//
//                String[] data = nacti.split(";");
//                String typPotomka = data[0];
//                switch (typPotomka) {
//                    case "bryle":
//                        seznam.vlozPosledni(new Bryle(Boolean.parseBoolean(data[1]), Integer.parseInt(data[2]), data[3], data[4]));
//                        break;
//                    case "snorchl":
//                        seznam.vlozPosledni(new Snorchl(Boolean.parseBoolean(data[1]), Boolean.parseBoolean(data[2]), data[3], data[4]));
//                        break;
//                    case "neopren":
//                        seznam.vlozPosledni(new Neopren(Boolean.parseBoolean(data[1]), Double.parseDouble(data[2]), data[3], data[4]));
//                        break;
//                }
//                nacti = vstup.readLine();
//            }
//            vstup.close();
//        } catch (IOException x) {
//            System.err.println("Chyba při obnovování dat");
//        }
//
//        return seznam;
        try {
            Objects.requireNonNull(seznam);
            ObjectInputStream vstup =
                    new ObjectInputStream(
                            new FileInputStream(nazevBinSouboru));
            seznam.zrus();

            int pocet = vstup.readInt();
            for (int i = 0; i < pocet; i++) {
                seznam.vlozPosledni(vstup.readObject());
            }
            vstup.close();

        } catch (IOException | KolekceException | ClassNotFoundException ex) {
            System.err.println("Chyba při obnovování dat");
        }
        return seznam;
    }
}


