package com.example.calendarapp;

public class CalendarEvent {
    private int id;
    private String title;
    private String description;
    private String date;
    private String time;
    private long timestamp;

    public CalendarEvent() {}

    public CalendarEvent(String title, String description, String date, String time, long timestamp) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
