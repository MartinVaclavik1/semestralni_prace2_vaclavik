package com.mycompany.semestralni.prace.vaclavik.spravce;

import com.mycompany.semestralni.prace.vaclavik.data.Bryle;
import com.mycompany.semestralni.prace.vaclavik.data.Neopren;
import com.mycompany.semestralni.prace.vaclavik.data.PotapecskaPotreba;
import com.mycompany.semestralni.prace.vaclavik.data.Snorchl;
import com.mycompany.semestralni.prace.vaclavik.generator.Generator;
import com.mycompany.semestralni.prace.vaclavik.kolekce.KolekceException;
import com.mycompany.semestralni.prace.vaclavik.kolekce.Seznam;
import com.mycompany.semestralni.prace.vaclavik.kolekce.SpojovySeznam;
import com.mycompany.semestralni.prace.vaclavik.perzistence.InputOutput;

import java.util.*;


public class Spravce<E> implements SpravceRozhrani<E> {
    private final int POCET_GENEROVANYCH_OBJEKTU = 15;
    private Seznam<PotapecskaPotreba> seznam = new SpojovySeznam<>();
    private final Comparator<PotapecskaPotreba> comparator = Comparator.comparing(PotapecskaPotreba::toString);

    public void novy(PotapecskaPotreba prvek) {
        if (prvek != null) {
            if (pocet() == 0) {
                seznam.vlozPosledni(prvek);
            } else {
                try {
                    seznam.vlozZaAktualni(prvek);
                } catch (Exception x) {
                    error("Nenastaven pointer pro vložení");
                }
            }
        }else{
            error("Nelze vkládat null");
        }
    }

    @Override
    public PotapecskaPotreba najdi(PotapecskaPotreba hledanyPrvek) {
        if (hledanyPrvek != null) {
            return seznam.stream()
                    .filter(obj -> comparator.compare(obj, hledanyPrvek) == 0)
                    .findFirst()
                    .get();
        } else {
            error("špatné vstupní hodnoty");
            return null;
        }
    }

    @Override
    public void odeber(PotapecskaPotreba hledanyPrvek) {
        if (hledanyPrvek != null) {
            PotapecskaPotreba potreba = najdi(hledanyPrvek);

            nastavPrvni();
            for (int i = 0; i < pocet(); i++) {
                if (dejAktualni().equals(potreba)) {
                    odeberAktualni();
                    return;
                }
                if (seznam.jeDalsi()) {
                    nastavDalsi();
                }
            }

        } else {
            error("špatné vstupní hodnoty");
        }
    }

    @Override
    public E dejAktualni() {
        try {
            return (E) seznam.dejAktualni();
        } catch (KolekceException e) {
            error("Chyba při získávání aktuálního prvku (nejspíše nenastaven pointer)");
            return null;
        }
    }

    public void edituj(String[] data) {
        //input = nový prvek, před editací se zeptá na zadání nových dat
        // (nejdřív vypíše aktuální stav pomocí dej aktuální, který dá pole prvků). poté data zadá do vstupu.
        // zde se přiřadí data pomocí set.
        PotapecskaPotreba objekt = (PotapecskaPotreba) dejAktualni();

        switch (objekt.getTypPotomka()) {
            case "bryle":
                //boolean jednoliteSklo, int maxHloubkaPonoru, String znacka, String carovyKod
                Bryle bryle = (Bryle) objekt;

                bryle.setJednoliteSklo(Boolean.parseBoolean(data[0]));
                bryle.setMaxHloubkaPonoru(Integer.parseInt(data[1]));
                bryle.setZnacka(data[2]);
                bryle.setCarovyKod(data[3]);

                break;
            case "snorchl":
                //boolean vlnolam, boolean vymenitelnyNahubek, String znacka, String carovyKod
                Snorchl snorchl = (Snorchl) objekt;

                snorchl.setVlnolam(Boolean.parseBoolean(data[0]));
                snorchl.setVymenitelnyNahubek(Boolean.parseBoolean(data[1]));
                snorchl.setZnacka(data[2]);
                snorchl.setCarovyKod(data[3]);

                break;
            case "neopren":
                //boolean hlavniZip, double tloustka, String znacka, String carovyKod
                Neopren neopren = (Neopren) objekt;

                neopren.setHlavniZipVzadu(Boolean.parseBoolean(data[0]));
                neopren.setTloustka(Double.parseDouble(data[1]));
                neopren.setZnacka(data[2]);
                neopren.setCarovyKod(data[3]);

                break;
        }
    }

    @Override
    public void odeberAktualni() {

        try {
            seznam.odeberAktualni();
        } catch (KolekceException e) {
            error("Chyba při odebírání prvku");
        }
    }

    @Override
    public void nastavPrvni() {

        try {
            seznam.nastavPrvni();
        } catch (KolekceException e) {
            error("Chyba při nastavování prvního prvku");
        }
    }

    @Override
    public void nastavDalsi() {

        try {
            seznam.dalsi();
        } catch (KolekceException e) {
            error("Chyba při nastavování dalšího prvku");
        }
    }

    @Override
    public void posledni() {

        try {
            seznam.nastavPosledni();
        } catch (KolekceException e) {
            error("Chyba při nastavování posledního prvku");
        }
    }

    @Override
    public int pocet() {
        return seznam.size();
    }

    @Override
    public void obnov() {
        seznam = InputOutput.obnov();
    }

    @Override
    public void zalohuj() {
        InputOutput.zalohuj(seznam);
    }

    @Override
    public void nactitext() {
        try {
            seznam = InputOutput.nactiText();
        } catch (KolekceException e) {
            error("Chyba při načítání dat z textového souboru");
        }
    }

    @Override
    public void uloztext() {

        try {
            InputOutput.ulozText(seznam);
        } catch (KolekceException e) {
            error("Chyba při ukládání do textového souboru");
        }
    }

    @Override
    public void generuj() {
        zrus();
        int pocet = POCET_GENEROVANYCH_OBJEKTU;
        for (int i = 0; i < pocet; i++) {
            seznam.vlozPosledni(Generator.vygeneruj());
        }
    }

    @Override
    public void zrus() {

        try {
            seznam.zrus();
        } catch (KolekceException e) {
            error("Chyba při mazání seznamu");
        }
    }

    @Override
    public void vlozPredAktualni(E command) {

        E aktualni = null;
        E prvni = null;

        try {

            aktualni = dejAktualni();
            nastavPrvni();
            prvni = (E) seznam.dejPrvni();

        } catch (KolekceException e) {
            error("Chyba v získávání prvního prvku");
            return;
        }

        if (aktualni.equals(prvni)) {
            seznam.vlozPrvni((PotapecskaPotreba) command);

            nastavPrvni();
        } else {
            if (predAktualnim(aktualni)) {
                try {
                    seznam.vlozZaAktualni((PotapecskaPotreba) command);
                    seznam.dalsi();//posun na prvek před který se vložil prvek
                    seznam.dalsi();
                } catch (KolekceException e) {
                    throw new RuntimeException(e);
                }
            }
//            while (true) {
//                try {
//                    if (seznam.dejZaAktualnim().equals(aktualni)) {
//                        seznam.vlozZaAktualni(novy());
//                        return;
//                    }
//                } catch (KolekceException e) {
//                    System.err.println("Chyba při vkládání za aktuálním(pro usera před aktuálním)");
//                    return;
//                }
//                try {
//                    seznam.dalsi();
//                } catch (KolekceException e) {
//                    System.err.println("Chyba při nastavování dalšího prvku");
//                    return;
//                }
//            }
        }
    }

    public boolean predAktualnim(E aktualni) {

        nastavPrvni();
        E prvni = dejPrvni();

        if (!aktualni.equals(prvni)) {
            while (true) {
                try {
                    if (seznam.dejZaAktualnim().equals(aktualni)) {
                        return true;
                    }
                } catch (KolekceException e) {
                    error("Chyba posunu před aktuální");
                    return false;
                }
                nastavDalsi();
            }
        } else {
            return false;
        }
    }

    public E dejPrvni() {
        try {
            return (E) seznam.dejPrvni();
        } catch (KolekceException e) {
            throw new RuntimeException(e);
        }
    }

    public E dejPosledni() {
        try {
            return (E) seznam.dejPosledni();
        } catch (KolekceException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PotapecskaPotreba> getData() {
        return seznam.stream().toList();
    }

    public void error(String zprava) {
        //TODO dodělat implementaci k gui/cmd
        //jediný co mě napadá je udělat interface, kde bude error() a to se overridne v cmd/gui
        //System.err.println(zprava);

        //nebo tady throwovat chybu a v mainu to catchovat
        throw new RuntimeException(zprava);

    }
}
