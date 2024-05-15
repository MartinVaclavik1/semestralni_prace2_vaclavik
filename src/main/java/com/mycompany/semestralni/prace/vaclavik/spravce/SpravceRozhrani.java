package com.mycompany.semestralni.prace.vaclavik.spravce;

import com.mycompany.semestralni.prace.vaclavik.data.PotapecskaPotreba;
import com.mycompany.semestralni.prace.vaclavik.kolekce.Seznam;

import java.util.List;

public interface SpravceRozhrani<E> {
    /*
     * vrátí nový prvek zadaný uživatelem
     */
    void novy(PotapecskaPotreba prvek);

    /*
     * najde a vrátí prvek v seznamu
     */
    PotapecskaPotreba najdi(PotapecskaPotreba hledanyPrvek);

    /*
     * odebere prvek ze seznamu
     */
    void odeber(PotapecskaPotreba hledanyPrvek);

    /*
     * vrátí aktuální prvek, na kterém je nastaven ukazatel
     * když není nastaven ukazatel, nebo z jiného důvodu vyskočí chybová hláška, tak vrátí null a vypíše chybu
     */
    E dejAktualni();

    /*
     * začne upravovat prvek, na kterém je nastaven ukazatel
     * vypíše atribut prvku a poté se zeptá na doplnění nového
     */
    void edituj(String[] data);

    /*
     * odebere prvek, na kterém je nastaven ukazatel
     */
    void odeberAktualni();

    /*
     * nastaví ukazatel na první prvek v seznamu
     */
    void nastavPrvni();

    /*
     * nastaví ukazatel na dalsí prvek v seznamu
     */
    void nastavDalsi();

    /*
     * nastaví ukazatel na poslední prvek v seznamu
     */
    void posledni();

    /*
     * vypíše počet prvků v seznamu
     */
    int pocet();

    /*
     * obnoví data z bitového souboru
     */
    void obnov();

    /*
     * zálohuje data do bitového souboru
     */
    void zalohuj();

    /*
     * načte data z textového souboru
     */
    void nactitext();

    /*
     * uloží data do textového souboru
     */
    void uloztext();

    /*
     * vygeneruje a vloží prvky do seznamu
     * počet prvků určuje uživatel
     */
    void generuj();

    /*
     * smaže všechny prvky v seznmu
     */
    void zrus();

    /*
     * vloží prvek před aktuálně vybraný
     */
    void vlozPredAktualni(E command);

    /*
     * nastaví pointer před aktuální prvek
     * @return vrací boolean zda se povedlo najít prvek před aktuálním
     */
    boolean predAktualnim(E aktualni);

    /*
     * vrátí první objekt v seznamu
     */
    E dejPrvni();

    /*
     * vrátí poslední objekt v seznamu
     */
    E dejPosledni();

    /*
     * vrátí list se všemi potřebami
     */
    List<PotapecskaPotreba> getData();

    /*
     * vyhodí chybovou hlášku
     */
    void error(String zprava);
}
