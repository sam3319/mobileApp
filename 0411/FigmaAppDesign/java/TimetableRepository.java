package com.example.todolist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimetableRepository {
    private final TimetableDao timetableDao;
    private final ExecutorService executorService;

    public TimetableRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        timetableDao = db.timetableDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Timetable>> getAllTimetables() {
        return timetableDao.getAllTimetables();
    }

    public LiveData<List<Timetable>> getTimetablesByDay(int dayOfWeek) {
        return timetableDao.getTimetablesByDay(dayOfWeek);
    }

    public void insert(Timetable timetable) {
        executorService.execute(() -> timetableDao.insert(timetable));
    }

    public void update(Timetable timetable) {
        executorService.execute(() -> timetableDao.update(timetable));
    }

    public void delete(Timetable timetable) {
        executorService.execute(() -> timetableDao.delete(timetable));
    }

    public List<Timetable> getConflictingTimetables(int dayOfWeek, String startTime, String endTime) {
        return timetableDao.getConflictingTimetables(dayOfWeek, startTime, endTime);
    }
} 