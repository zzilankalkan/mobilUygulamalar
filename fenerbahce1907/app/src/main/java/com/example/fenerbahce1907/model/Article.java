package com.example.fenerbahce1907.model;

public class Article {
    private String title;
    private String description;
    private String urlToImage;
    private String publishedAt;
    private String url; // ✅ Bu satırı ekledik

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrlToImage() { return urlToImage; }
    public String getPublishedAt() { return publishedAt; }
    public String getUrl() { return url; } // ✅ Getter'ını da ekledik
}
