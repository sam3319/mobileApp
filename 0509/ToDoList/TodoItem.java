package com.example.todolist;

public class TodoItem {
    private String task;
    private boolean isCompleted;
    private long id;

    public TodoItem(String task) {
        this.task = task;
        this.isCompleted = false;
        this.id = System.currentTimeMillis();
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
}
