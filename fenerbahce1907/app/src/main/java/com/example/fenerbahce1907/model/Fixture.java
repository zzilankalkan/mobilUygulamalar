package com.example.fenerbahce1907.model;
public class Fixture {
    private int hafta;
    private String tarih;
    private String saat;
    private String ev;
    private String deplasman;
    private String skor;

    public Fixture() {} // Firestore için boş constructor

    public Fixture(int hafta, String tarih, String saat, String ev, String deplasman, String skor) {
        this.hafta = hafta;
        this.tarih = tarih;
        this.saat = saat;
        this.ev = ev;
        this.deplasman = deplasman;
        this.skor = skor;
    }

    public int getHafta() { return hafta; }
    public String getTarih() { return tarih; }
    public String getSaat() { return saat; }
    public String getEv() { return ev; }
    public String getDeplasman() { return deplasman; }
    public String getSkor() { return skor; }
}
