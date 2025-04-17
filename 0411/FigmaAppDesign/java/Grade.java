package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "grades")
public class Grade implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String subject;
    private String grade;
    private String semester;
    private int credit;

    public Grade(String subject, String grade, String semester, int credit) {
        this.subject = subject;
        this.grade = grade;
        this.semester = semester;
        this.credit = credit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getGradePoint() {
        switch (grade) {
            case "A+": return 4.5;
            case "A": return 4.0;
            case "B+": return 3.5;
            case "B": return 3.0;
            case "C+": return 2.5;
            case "C": return 2.0;
            case "D+": return 1.5;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    public double getScore() {
        return getGradePoint() * credit;
    }
} 