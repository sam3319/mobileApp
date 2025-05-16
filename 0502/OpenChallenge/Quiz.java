package com.example.chapter08codingchallenge;

import java.io.Serializable;

public class Quiz implements Serializable {  // Serializable 인터페이스 구현 추가
    private String question;
    private String[] options;
    private int correctAnswerIndex;
    private int userSelectedIndex = -1;

    public Quiz(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public int getUserSelectedIndex() {
        return userSelectedIndex;
    }

    public void setUserSelectedIndex(int userSelectedIndex) {
        this.userSelectedIndex = userSelectedIndex;
    }

    public boolean isCorrect() {
        return userSelectedIndex == correctAnswerIndex;
    }
}
