package com.example.todolist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TimetableViewModel extends AndroidViewModel {
    private final TimetableRepository repository;
    private final LiveData<List<Timetable>> allTimetables;

    public TimetableViewModel(Application application) {
        super(application);
        repository = new TimetableRepository(application);
        allTimetables = repository.getAllTimetables();
    }

    public LiveData<List<Timetable>> getAllTimetables() {
        return allTimetables;
    }

    public LiveData<List<Timetable>> getTimetablesByDay(int dayOfWeek) {
        return repository.getTimetablesByDay(dayOfWeek);
    }

    public void insert(Timetable timetable) {
        repository.insert(timetable);
    }

    public void update(Timetable timetable) {
        repository.update(timetable);
    }

    public void delete(Timetable timetable) {
        repository.delete(timetable);
    }

    public List<Timetable> getConflictingTimetables(int dayOfWeek, String startTime, String endTime) {
        return repository.getConflictingTimetables(dayOfWeek, startTime, endTime);
    }
} 