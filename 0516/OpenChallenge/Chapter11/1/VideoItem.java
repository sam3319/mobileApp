package com.example.chapter11codingchallenge1;

public class VideoItem {
    private String title;
    private String path;
    private long size;
    private String duration;

    public VideoItem(String title, String path, long size, String duration) {
        this.title = title;
        this.path = path;
        this.size = size;
        this.duration = duration;
    }

    // Getter 메서드들
    public String getTitle() { return title; }
    public String getPath() { return path; }
    public long getSize() { return size; }
    public String getDuration() { return duration; }
}
