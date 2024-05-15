package com.mycompany.semestralni.prace.vaclavik.data;

public class Neopren extends PotapecskaPotreba {
    private boolean hlavniZipVzadu; //vepředu/vzadu
    private double tloustka; //v mm
    public Neopren(boolean hlavniZipVzadu, double tloustka, String znacka, String carovyKod) {
        super("neopren", znacka, carovyKod);
        this.hlavniZipVzadu = hlavniZipVzadu;
        this.tloustka = tloustka;
    }

    public Neopren(){
        super("neopren");
    }

    public boolean isHlavniZipVzadu() {
        return hlavniZipVzadu;
    }

    public double getTloustka() {
        return tloustka;
    }

    public void setHlavniZipVzadu(boolean hlavniZipVzadu) {
        this.hlavniZipVzadu = hlavniZipVzadu;
    }

    public void setTloustka(double tloustka) {
        this.tloustka = tloustka;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Neopren: ");
        sb.append("hlavniZipVzadu='").append(hlavniZipVzadu ? "ANO" : "NE").append('\'');
        sb.append(", tloustka=").append(tloustka);
        sb.append(", znacka='").append(super.getZnacka()).append('\'');
        sb.append(", carovyKod='").append(super.getCarovyKod()).append('\'');
        return sb.toString();
    }
}
