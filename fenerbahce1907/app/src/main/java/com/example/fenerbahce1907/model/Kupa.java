package com.example.fenerbahce1907.model;

public class Kupa {
    private String kupaAdi;
    private int kupaSayi;
    private String ikonUrl;

    // Boş constructor
    public Kupa() {}

    // Constructor
    public Kupa(String kupaAdi, int kupaSayi, String ikonUrl) {
        this.kupaAdi = kupaAdi;
        this.kupaSayi = kupaSayi;
        this.ikonUrl = ikonUrl;
    }

    public String getKupaAdi() {
        return kupaAdi;
    }

    public void setKupaAdi(String kupaAdi) {
        this.kupaAdi = kupaAdi;
    }

    public int getKupaSayi() {
        return kupaSayi;
    }

    public void setKupaSayi(int kupaSayi) {
        this.kupaSayi = kupaSayi;
    }

    public String getIkonUrl() {
        return ikonUrl;
    }

    public void setIkonUrl(String ikonUrl) {
        this.ikonUrl = ikonUrl;
    }
}
