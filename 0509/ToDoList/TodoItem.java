package com.example.todolist;

public class TodoItem {
    private String task;
    private boolean isCompleted;
    private long id;
    private long alarmTime; // 알람 시간 추가 (밀리초 단위)

    public TodoItem(String task) {
        this.task = task;
        this.isCompleted = false;
        this.id = System.currentTimeMillis();
        this.alarmTime = 0; // 기본값은 알람 없음
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getId() {
        return id;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean hasAlarm() {
        return alarmTime > 0;
    }
}
