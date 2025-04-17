package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class GradeViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Grade>> grades = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Grade> selectedGrade = new MutableLiveData<>();
    private final MutableLiveData<Double> averageGrade = new MutableLiveData<>(0.0);

    public GradeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Grade>> getGrades() {
        return grades;
    }

    public LiveData<Grade> getSelectedGrade() {
        return selectedGrade;
    }

    public LiveData<Double> getAverageGrade() {
        return averageGrade;
    }

    public void addGrade(Grade grade) {
        List<Grade> currentGrades = grades.getValue();
        if (currentGrades != null) {
            currentGrades.add(grade);
            grades.setValue(currentGrades);
            updateAverageGrade();
        }
    }

    public void updateGrade(Grade grade) {
        List<Grade> currentGrades = grades.getValue();
        if (currentGrades != null) {
            for (int i = 0; i < currentGrades.size(); i++) {
                if (currentGrades.get(i).getId().equals(grade.getId())) {
                    currentGrades.set(i, grade);
                    break;
                }
            }
            grades.setValue(currentGrades);
            updateAverageGrade();
        }
    }

    public void deleteGrade(Grade grade) {
        List<Grade> currentGrades = grades.getValue();
        if (currentGrades != null) {
            currentGrades.removeIf(g -> g.getId().equals(grade.getId()));
            grades.setValue(currentGrades);
            updateAverageGrade();
        }
    }

    public void selectGrade(Grade grade) {
        selectedGrade.setValue(grade);
    }

    public void clearSelectedGrade() {
        selectedGrade.setValue(null);
    }

    private void updateAverageGrade() {
        List<Grade> currentGrades = grades.getValue();
        if (currentGrades != null && !currentGrades.isEmpty()) {
            double totalGradePoints = 0;
            int totalCredits = 0;

            for (Grade grade : currentGrades) {
                totalGradePoints += grade.getGradePoint() * grade.getCredit();
                totalCredits += grade.getCredit();
            }

            if (totalCredits > 0) {
                averageGrade.setValue(totalGradePoints / totalCredits);
            } else {
                averageGrade.setValue(0.0);
            }
        } else {
            averageGrade.setValue(0.0);
        }
    }
} 