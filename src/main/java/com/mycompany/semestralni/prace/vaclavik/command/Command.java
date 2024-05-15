package com.mycompany.semestralni.prace.vaclavik.command;

import com.mycompany.semestralni.prace.vaclavik.data.Bryle;
import com.mycompany.semestralni.prace.vaclavik.data.Neopren;
import com.mycompany.semestralni.prace.vaclavik.data.PotapecskaPotreba;
import com.mycompany.semestralni.prace.vaclavik.data.Snorchl;
import com.mycompany.semestralni.prace.vaclavik.spravce.Spravce;
import com.mycompany.semestralni.prace.vaclavik.spravce.SpravceRozhrani;

import java.util.Scanner;

public class Command {
    private static boolean zapnuto = true;
    private static Scanner vstup = new Scanner(System.in);
    private static SpravceRozhrani spravce = new Spravce();

    public static void main(String[] args) {

        while (zapnuto) {
            System.out.print("Zadej příkaz: ");
            try {

                String prikaz = vstup.next().toLowerCase();
                switch (prikaz) {

                    case "help", "h":
                        help();
                        break;

                    case "novy", "no":
                        spravce.novy(input());
                        break;

                    case "najdi", "na", "n":
//                        System.out.print("Zadej hledaný prvek: ");
//                        String hledanyPrvek = vstup.next().toLowerCase();
                        try {
                            System.out.println("Prvek nalezen: " + spravce.najdi(input()));
                        } catch (Exception x) {
                            System.out.println("Prvek nenalezen");
                        }

//                        if(nalezen != null)
//                            System.out.println("Nalezen prvek: " + nalezen);
                        break;

                    case "odeber", "od":
//                        System.out.print("Zadej hledaný prvek: ");
                        spravce.odeber(input());
                        break;

                    case "dej":
                        if (spravce.dejAktualni() != null)
                            System.out.println(spravce.dejAktualni());
                        break;

                    case "edituj", "edit":
                        spravce.edituj(inputEditace());
                        break;

                    case "vyjmi":
                        spravce.odeberAktualni();
                        break;

                    case "prvni", "pr":
                        spravce.nastavPrvni();
                        break;

                    case "dalsi", "da":
                        spravce.nastavDalsi();
                        break;

                    case "posledni", "po":
                        spravce.posledni();
                        break;

                    case "pocet":
                        System.out.println(spravce.pocet());
                        break;

                    case "obnov":
                        spravce.obnov();
                        break;

                    case "zalohuj":
                        spravce.zalohuj();
                        break;

                    case "vypis":
                        if (spravce.pocet() == 1) {
                            System.out.println(spravce.dejPrvni());
                        } else {
                            //při jednom prvku se nevypíše nic. proč?
                            spravce.getData().stream().forEach(System.out::println);
                        }
                        break;

                    case "nactitext", "nt":
                        spravce.nactitext();
                        break;

                    case "uloztext", "ut":
                        spravce.uloztext();
                        break;

                    case "generuj", "g":
                        spravce.generuj();
                        break;

                    case "zrus":
                        spravce.zrus();
                        break;

                    case "pred":
                        spravce.vlozPredAktualni(input());
                        break;

                    case "exit":
                        zapnuto = false;
                        System.out.println("Uzavírání programu!");
                        break;

                    default:
                        System.err.println("Chyba v zadávání příkazů");
                }

            } catch (RuntimeException x){
                System.err.println(x.getMessage());
            } catch (Exception x) {
                System.err.println("\nChyba v zadávání hodnot");
            }
        }
    }

    private static String[] inputEditace() {

        PotapecskaPotreba aktualni = (PotapecskaPotreba) spravce.dejAktualni();
        String[] data = new String[4];

        switch (aktualni.getTypPotomka()) {
            case "bryle":
                //boolean jednoliteSklo, int maxHloubkaPonoru, String znacka, String carovyKod
                Bryle bryle = (Bryle) aktualni;
                System.out.print("Je stávající sklo jednolité: " + bryle.isJednoliteSklo() + ", je nové sklo jednolité(true/false): ");
                boolean jednoliteSklo = vstup.nextBoolean();
                data[0] = String.valueOf(jednoliteSklo);

                System.out.print("Stávající maximální hloubka: " + bryle.getMaxHloubkaPonoru() + ", nová hloubka: ");
                int maxHloubkaPonoru = vstup.nextInt();
                data[1] = String.valueOf(maxHloubkaPonoru);

                break;
            case "snorchl":
                //boolean vlnolam, boolean vymenitelnyNahubek, String znacka, String carovyKod
                Snorchl snorchl = (Snorchl) aktualni;
                System.out.print("Má stávající vlnolam: " + snorchl.isVlnolam() + ", má nový vlnolam(true/false): ");
                boolean vlnolam = vstup.nextBoolean();
                data[0] = String.valueOf(vlnolam);

                System.out.print("Má stávající vyměnitelný náhubek: " + snorchl.isVymenitelnyNahubek() + ", má nový vyměnitelný náhubek(true/false): ");
                boolean vymenitelnyNahubek = vstup.nextBoolean();
                data[1] = String.valueOf(vymenitelnyNahubek);

                break;
            case "neopren":
                //boolean hlavniZip, double tloustka, String znacka, String carovyKod
                Neopren neopren = (Neopren) aktualni;
                System.out.print("Má stávající hlavní zip vzadu: " + neopren.isHlavniZipVzadu() + ", má nový hlavní zip vzadu(true/false): ");
                boolean hlavniZipVzadu = vstup.nextBoolean();
                data[0] = String.valueOf(hlavniZipVzadu);

                System.out.print("Stávající tloušťka hloubka: " + neopren.getTloustka() + ", nová tloušťka: ");
                Double tloustka = vstup.nextDouble();
                data[1] = String.valueOf(tloustka);

                break;
        }

        System.out.print("Stávající značka: " + aktualni.getZnacka() + ", nová značka: ");
        String znacka = vstup.next();
        data[2] = znacka;

        System.out.print("Stávající čárový kód: " + aktualni.getCarovyKod() + ", nový čárový kód: ");
        String carovyKod = vstup.next();
        data[3] = carovyKod;


        return data;
    }

    private static void help() {
        System.out.println("help, h       - výpis příkazů");
        System.out.println("novy,no       - vytvoř novou instanci a vlož data za aktuální prvek");
        System.out.println("najdi,na,n    - najdi v seznamu data podle hodnoty nějakém atributu");
        System.out.println("odeber,od     - odeber data ze seznamu podle nějaké hodnoty atributu");
        System.out.println("dej           - zobraz aktuální data v seznamu");
        System.out.println("edituj, edit  - edituj aktuální data v seznamu");
        System.out.println("vyjmi         - vyjmi aktuální data ze seznamu");
        System.out.println("prvni, pr     - nastav jako aktuální první data v seznamu");
        System.out.println("dalsi, da     - přejdi na další data");
        System.out.println("posledni, po  - přejdi na poslední data");
        System.out.println("pocet         - zobraz počet položek v seznamu");
        System.out.println("obnov         - obnov seznam data z binárního souboru");
        System.out.println("zalohuj       - zálohuj seznam dat do binárního souboru");
        System.out.println("vypis         - zobraz seznam dat");
        System.out.println("nactitext, nt - načti seznam data z textového souboru");
        System.out.println("uloztext, ut  - ulož seznam data do textového souboru");
        System.out.println("generuj, g    - generuj náhodně data pro testování");
        System.out.println("zrus          - zruš všechny data v seznamu");
        System.out.println("pred          - vloží instanci před aktuální prvek");
        System.out.println("exit          - ukončení programu");
    }

    public static PotapecskaPotreba input() {
        System.out.print("Zadej druh potřeby: ");
        String prikaz = null;
        prikaz = vstup.next().toLowerCase();

        switch (prikaz) {
            case "bryle", "brýle":
                try {
                    System.out.print("Zadej zda má jednolité sklo (true/false):");
                    boolean jednoliteSklo = vstup.nextBoolean();
                    System.out.print("Zadej maximální hloubku ponoru(celé číslo v metrech):");
                    int maxHloubkaPonoru = vstup.nextInt();
                    System.out.print("Zadej znacku:");
                    String znacka = vstup.next();
                    System.out.print("Zadej carovy kod:");
                    String carovyKod = vstup.next();

                    return new Bryle(jednoliteSklo, maxHloubkaPonoru, znacka, carovyKod);
                } catch (Exception x) {
                    System.err.println("Chyba v zadávání hodnot brýlí");
                }
                break;
            case "snorchl", "šnorchl":
                try {
                    System.out.print("Zadej, zda má vlnolam(true/false):");
                    boolean vlnolam = vstup.nextBoolean();
                    System.out.print("Zadej, zda je vyměnitelný náhubek(true/false):");
                    boolean vymenitelnyNahubek = vstup.nextBoolean();
                    System.out.print("Zadej znacku:");
                    String znacka = vstup.next();
                    System.out.print("Zadej carovy kod:");
                    String carovyKod = vstup.next();
                    return new Snorchl(vlnolam, vymenitelnyNahubek, znacka, carovyKod);
                } catch (Exception x) {
                    System.err.println("Chyba v zadávání hodnot šnorchlu");
                }
                break;
            case "neopren":
                try {
                    System.out.print("Zadej, zda je hlavní zip vzadu(true/false):");
                    boolean hlavniZipVzadu = vstup.nextBoolean();
                    System.out.print("Zadej tloušťku v mm:");
                    double tloustka = vstup.nextDouble();
                    System.out.print("Zadej znacku:");
                    String znacka = vstup.next();
                    System.out.print("Zadej carovy kod:");
                    String carovyKod = vstup.next();

                    return new Neopren(hlavniZipVzadu, tloustka, znacka, carovyKod);
                } catch (Exception x) {
                    System.err.println("Chyba v zadávání hodnot neoprenu");
                }
                break;
            default:
                System.err.println("Špatně zadaný název potřeb");
        }
        return null;
    }
}