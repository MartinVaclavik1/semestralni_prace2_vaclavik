package com.mycompany.semestralni.prace.vaclavik.data;

import java.io.Serializable;

//přesunout atributy, které jsou jen pro jednu třídu do ní - jednolitesklo do bryly atd
public abstract class PotapecskaPotreba implements Serializable {

    private String znacka;
    private String carovyKod;
    private final String typPotomka;


    public PotapecskaPotreba(String typPotomka, String znacka, String carovyKod) {
        this.znacka = znacka;
        this.carovyKod = carovyKod;
        this.typPotomka = typPotomka;
    }

    public PotapecskaPotreba(String typPotomka) {
        this.typPotomka = typPotomka;
    }


    public String getZnacka() {
        return znacka;
    }

    public String getCarovyKod() {
        return carovyKod;
    }

    public String getTypPotomka() {
        return typPotomka;
    }

    public void setZnacka(String znacka) {
        this.znacka = znacka;
    }

    public void setCarovyKod(String carovyKod) {
        this.carovyKod = carovyKod;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PotapecskaPotreba{");
        sb.append("znacka='").append(znacka).append('\'');
        sb.append(", carovyKod='").append(carovyKod).append('\'');
        sb.append(", typPotomka='").append(typPotomka).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
