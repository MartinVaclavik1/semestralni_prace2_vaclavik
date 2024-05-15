package com.mycompany.semestralni.prace.vaclavik.data;

public class Snorchl extends PotapecskaPotreba {
    private boolean vlnolam;
    private boolean vymenitelnyNahubek;

    public Snorchl(boolean vlnolam, boolean vymenitelnyNahubek, String znacka, String carovyKod) {
        super("snorchl", znacka, carovyKod);
        this.vlnolam = vlnolam;
        this.vymenitelnyNahubek = vymenitelnyNahubek;
    }

    public Snorchl(){
        super("snorchl");
    }

    public boolean isVlnolam() {
        return vlnolam;
    }

    public boolean isVymenitelnyNahubek() {
        return vymenitelnyNahubek;
    }


    public void setVlnolam(boolean vlnolam) {
        this.vlnolam = vlnolam;
    }

    public void setVymenitelnyNahubek(boolean vymenitelnyNahubek) {
        this.vymenitelnyNahubek = vymenitelnyNahubek;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Snorchl: ");
        sb.append("vlnolam=").append(vlnolam ? "ANO" : "NE");
        sb.append(", vymenitelnyNahubek=").append(vymenitelnyNahubek ? "ANO" : "NE");
        sb.append(", znacka='").append(super.getZnacka()).append('\'');
        sb.append(", carovyKod='").append(super.getCarovyKod()).append('\'');
        return sb.toString();
    }
}
