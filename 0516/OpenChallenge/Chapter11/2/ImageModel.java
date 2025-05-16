package com.example.chapter11codingchallenge;

public class ImageModel {
    private String path;
    private String name;
    private String date;

    public ImageModel(String path, String name, String date) {
        this.path = path;
        this.name = name;
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
