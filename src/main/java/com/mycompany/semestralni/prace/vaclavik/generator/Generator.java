package com.mycompany.semestralni.prace.vaclavik.generator;

import com.mycompany.semestralni.prace.vaclavik.data.Bryle;
import com.mycompany.semestralni.prace.vaclavik.data.Neopren;
import com.mycompany.semestralni.prace.vaclavik.data.PotapecskaPotreba;
import com.mycompany.semestralni.prace.vaclavik.data.Snorchl;
import com.mycompany.semestralni.prace.vaclavik.kolekce.KolekceException;

import java.util.Locale;
import java.util.Random;

public class Generator {

    public static PotapecskaPotreba vygeneruj() {
        final int MINIMALNI_HODNOTA = 1;
        final int POCET_POTREB = 3;
        final int MIN_HLOUBKA_PONORU = 101;
        final int MAX_HLOUBKA_PONORU = 501 - MIN_HLOUBKA_PONORU;
        final int MINIMALNI_DELKA_ZNACKY = 4;
        final int MAX_DELKA_ZNACKY = 14 - MINIMALNI_DELKA_ZNACKY;
        final int MAXIMALNI_TLOUSTKA = 6 - MINIMALNI_HODNOTA;

        Random nahoda = new Random();
        boolean jednoliteSklo;
        int maxHloubkaPonoru;
        boolean vlnolam;
        boolean vymenitelnyNahubek;
        double tloustka;
        boolean hlavniZip;
        String znacka;
        String carovyKod;

        int typPotreby = (MINIMALNI_HODNOTA + nahoda.nextInt(POCET_POTREB));
        switch (typPotreby) {
            case 1:
                jednoliteSklo = nahoda.nextBoolean();
                maxHloubkaPonoru = (MIN_HLOUBKA_PONORU + nahoda.nextInt(MAX_HLOUBKA_PONORU));
                znacka = generatorZnacky(MINIMALNI_DELKA_ZNACKY + nahoda.nextInt(MAX_DELKA_ZNACKY));
                carovyKod = String.valueOf(nahoda.nextDouble());
                try {
                    return new Bryle(jednoliteSklo, maxHloubkaPonoru, znacka, carovyKod);
                } catch (KolekceException e) {
                    throw new RuntimeException(e);
                }
            case 2:
                vlnolam = nahoda.nextBoolean();
                vymenitelnyNahubek = nahoda.nextBoolean();
                znacka = generatorZnacky(MINIMALNI_DELKA_ZNACKY + nahoda.nextInt(MAX_DELKA_ZNACKY));
                carovyKod = String.valueOf(Math.random());
                return new Snorchl(vlnolam, vymenitelnyNahubek, znacka, carovyKod);
            case 3:
                tloustka = MINIMALNI_HODNOTA + nahoda.nextDouble(MAXIMALNI_TLOUSTKA);
                tloustka = Double.parseDouble(String.format(Locale.US, "%.2f", tloustka));
                hlavniZip = nahoda.nextBoolean();
                znacka = generatorZnacky(MINIMALNI_DELKA_ZNACKY + nahoda.nextInt(MAX_DELKA_ZNACKY));
                carovyKod = String.valueOf(Math.random());
                return new Neopren(hlavniZip, tloustka, znacka, carovyKod);

        }
        return null;
    }

    private static String generatorZnacky(int pocetPismen) {
        char ZACATEK = 'a';
        char POCET_PISMEN = 26;

        StringBuilder znacka = new StringBuilder();
        Random nahoda = new Random();
        for (int i = 0; i < pocetPismen; i++) {
            znacka.append((char) (ZACATEK + nahoda.nextInt(POCET_PISMEN) ));

        }
        return String.valueOf(znacka);
    }
}
