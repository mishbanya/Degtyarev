package com.example.fintech2024_spring;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable{
    private int kinopoiskId;
    private String imdbId;
    private String name;
    private String nameEn;
    private String nameOriginal;
    private List<String> countries;
    private List<String> genres;
    private Double ratingKinopoisk;
    private Double ratingImdb;
    private int year;
    private String type;
    private String posterUrl;
    private String posterUrlPreview;

    public Film() {
        this.name = "A film";
        this.year = 2000;
    }

    public Film(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(int kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameOriginal() {
        return nameOriginal;
    }

    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getRatingKinopoisk(){ return  this.ratingKinopoisk;}

    public void setRatingKinopoisk(Double ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
    }

    public Double getRatingImdb() {
        return ratingImdb;
    }

    public void setRatingImdb(Double ratingImdb) {
        this.ratingImdb = ratingImdb;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }
}