package com.mycompany.semestralni.prace.vaclavik.kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author admin
 */
public class SpojovySeznam<E> implements Seznam<E> {

    private int pocet = 0;
    private Prvek<E> prvni;
    private Prvek<E> posledni;
    private Prvek<E> ukazatel;

    public SpojovySeznam() {

    }

    private class Prvek<E> {
        private E prvek;
        private Prvek<E> dalsi;

        private Prvek(E prvek, Prvek<E> dalsi) {
            this.prvek = prvek;
            this.dalsi = dalsi;
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        ukazatel = prvni;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        ukazatel = posledni;
    }

    @Override
    public void dalsi() throws KolekceException {
        if (ukazatel == null || ukazatel.dalsi == null) { //chyba?
            throw new KolekceException();
        }
        ukazatel = ukazatel.dalsi;
    }

    @Override
    public boolean jeDalsi() {
        return (ukazatel != null && ukazatel.dalsi != null);
    }

    @Override
    public void vlozPrvni(E data) throws NullPointerException {

        if (data == null) {
            throw new NullPointerException();
        }

        Prvek<E> prv = prvni;
        Prvek<E> novyPrvek = new Prvek<>(data, prv);
        prvni = novyPrvek;

        //když je první null => seznam je prázdný a nastavý se nový prvek i jako poslední
        if (prv == null) {
            posledni = novyPrvek;
        }
        pocet++;
    }

    @Override
    public void vlozPosledni(E data) throws NullPointerException {

        if (data == null) {
            throw new NullPointerException();
        }

        Prvek<E> posl = posledni;
        Prvek<E> novyPrvek = new Prvek<>(data, null);
        posledni = novyPrvek;

        /*
         * když je předchozí poslední null => novyPrvek je jediný v seznamu
         * a nastaví se jako první prvek.
         * jinak se nastaví reference předchozího na nový poslední
         */
        if (posl == null) {
            prvni = novyPrvek;
        } else {
            posl.dalsi = novyPrvek;
        }
        pocet++;
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if (data == null) {
            throw new NullPointerException();
        } else if (ukazatel == null) {
            throw new KolekceException();
        }

        Prvek<E> aktualni = ukazatel;
        Prvek<E> dalsi = ukazatel.dalsi;

        Prvek<E> novy = new Prvek<>(data, dalsi);
        aktualni.dalsi = novy;

        if (aktualni == posledni) {
            posledni = novy;
        }

        pocet++;
    }

    @Override
    public boolean jePrazdny() {
        return (pocet == 0);
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        return prvni.prvek;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        return posledni.prvek;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        if (jePrazdny() || ukazatel == null) {
            throw new KolekceException();
        }
        return ukazatel.prvek;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        if (jePrazdny() || ukazatel == null || !jeDalsi()) {
            throw new KolekceException();
        }
        return ukazatel.dalsi.prvek;
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }

        if (ukazatel == prvni) {
            ukazatel = null;
        }

        Prvek<E> odebrany = prvni;
        Prvek<E> za = odebrany.dalsi;
        E odebranyPrvek = odebrany.prvek;

        odebrany.prvek = null;
        odebrany.dalsi = null;

        prvni = za;
        pocet--;

        //vynulování poslední hodnoty při nulové délce
        if (pocet == 0) {
            posledni = null;
        }
        return odebranyPrvek;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }

        //když ukazatel ukauje na poslední(odebíraný) prvek, tak se vynuluje
        if (ukazatel == posledni) {
            ukazatel = null;
        }

        Prvek<E> odebrany = posledni;
        E odebranyPrvek = odebrany.prvek;

        Prvek<E> novyPosledni = null;


        /*
         * když je poslední zároveň první, tak zůstane novyPoslední null
         * prvni se vynuluje a na konci programu se přiřadí novyPosledni(null)
         * do poslední, takže se taky vynuluje
         *
         * Když poslední není zároveň první, tak se nastaví předchozí hodnota jako poslední
         */
        if (odebrany != prvni) {
            novyPosledni = prvni;

            while (novyPosledni.dalsi != odebrany) {
                novyPosledni = novyPosledni.dalsi;
            }
        } else {
            prvni = null;
        }

        odebrany.prvek = null;
        odebrany.dalsi = null;

        posledni = novyPosledni;
        pocet--;
        if(pocet > 0)
            posledni.dalsi = null;
        return odebranyPrvek;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        if (ukazatel == null || jePrazdny()) {
            throw new KolekceException();
        }

        Prvek<E> pred = prvni;
        Prvek<E> odebrany = ukazatel;
        Prvek<E> za = ukazatel.dalsi;


        if (ukazatel == prvni) {
            return odeberPrvni();
        } else if (ukazatel == posledni) {
            return odeberPosledni();
        }

        //nastavení prvku před odebíraným
        while (pred.dalsi != odebrany) {
            pred = pred.dalsi;
        }

        //kontrola zda je následující prvek. popř. spojení s předchozím - teoreticky zbytečný if, protože je min. první a tím pádem nebude null
        if (pred != null) {
            pred.dalsi = za;
        }


        ukazatel = null;
        E odebranyPrvek = odebrany.prvek;

        //vynulování odebraného prvku
        odebrany.prvek = null;
        odebrany.dalsi = null;

        pocet--;

//        /*
//         *v případě, že je odebíraný prvek první/poslední a zbyde
//         * jen jeden prvek, tak se nastaví jako první/poslední
//         */
//        if (pocet == 1) {             //kdyby byl počet 1, tak by předchozí byl buď první, nebo poslední ==> řešeno nahoře
//            if (za != null) {
//                prvni = za;
//            } else if (pred != null) {
//                posledni = pred;
//            }
//        }

        //reset hodnot první/poslední při počtu 0 (pro jistotu) - řešeno skrz odeber prvni/posledni
//        if (pocet == 0) {
//            prvni = null;
//            posledni = null;
//        }

        return odebranyPrvek;
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        if (ukazatel == null || jePrazdny() || ukazatel.dalsi == null) {
            throw new KolekceException();
        }
        Prvek<E> pred = ukazatel;
        Prvek<E> odebrany = ukazatel.dalsi;
        Prvek<E> za = ukazatel.dalsi.dalsi;
        E odebranyPrvek = odebrany.prvek;

        if (pred != null) {
            pred.dalsi = za;
        }

        odebrany.prvek = null;
        odebrany.dalsi = null;

        pocet--;

        if (pocet == 1) {
            posledni = pred;
        }

        return odebranyPrvek;
    }

    @Override
    public int size() {
        return pocet;
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        ukazatel = null;
        pocet = 0;
    }

    @Override
    public Iterator<E> iterator() throws NoSuchElementException {
        return new Iterator<E>() {
            Prvek<E> index = prvni;
            boolean jePrvni = true;

            @Override
            public boolean hasNext() {
                return (pocet > 0) ? index.dalsi != null : false;
            }

            @Override
            public E next() {
                if (!hasNext() && (!jePrvni || prvni == null)) {
                    throw new NoSuchElementException("No more elements in the list");
                }
                if (jePrvni) {
                    jePrvni = false;
                    return index.prvek;
                }
                index = index.dalsi;
                return index.prvek;
            }
        };
    }

}
