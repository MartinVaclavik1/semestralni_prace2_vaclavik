package com.mycompany.semestralni.prace.vaclavik.data;

import com.mycompany.semestralni.prace.vaclavik.kolekce.KolekceException;

public class Bryle extends PotapecskaPotreba {
    private boolean jednoliteSklo; //1, nebo 2
    private int maxHloubkaPonoru; //hloubka v metrech

    public Bryle(boolean jednoliteSklo, int maxHloubkaPonoru, String znacka, String carovyKod) throws KolekceException {
        super("bryle", znacka, carovyKod);
        this.jednoliteSklo = jednoliteSklo;
        this.maxHloubkaPonoru = maxHloubkaPonoru;
    }

    public Bryle(){
        super("bryle");
    }

    public boolean isJednoliteSklo() {
        return jednoliteSklo;
    }

    public int getMaxHloubkaPonoru() {
        return maxHloubkaPonoru;
    }

    public void setJednoliteSklo(boolean jednoliteSklo) {
        this.jednoliteSklo = jednoliteSklo;
    }

    public void setMaxHloubkaPonoru(int maxHloubkaPonoru) {
        this.maxHloubkaPonoru = maxHloubkaPonoru;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bryle: ");
        sb.append("  jednoliteSklo=").append(jednoliteSklo ? "ANO" : "NE");
        sb.append(", maxHloubkaPonoru=").append(maxHloubkaPonoru);
        sb.append(", znacka='").append(super.getZnacka()).append('\'');
        sb.append(", carovyKod='").append(super.getCarovyKod()).append('\'');
        return sb.toString();
    }
}
