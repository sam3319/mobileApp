package com.example.chapter12codingchallenge;

public class Movie {
    private int id;
    private String title;
    private String year;
    private String director;
    private String rating;
    private String country;

    // 생성자
    public Movie() {
    }

    public Movie(int id, String title, String year, String director, String rating, String country) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.country = country;
    }

    // ID 없는 생성자 (새 영화 추가용)
    public Movie(String title, String year, String director, String rating, String country) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.country = country;
    }

    // Getter와 Setter 메소드
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
