package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimetableDao {
    @Query("SELECT * FROM timetables ORDER BY dayOfWeek, startTime")
    LiveData<List<Timetable>> getAllTimetables();

    @Query("SELECT * FROM timetables WHERE dayOfWeek = :dayOfWeek ORDER BY startTime")
    LiveData<List<Timetable>> getTimetablesByDay(int dayOfWeek);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Timetable timetable);

    @Update
    void update(Timetable timetable);

    @Delete
    void delete(Timetable timetable);

    @Query("SELECT * FROM timetables WHERE dayOfWeek = :dayOfWeek AND " +
           "((startTime <= :endTime AND endTime >= :startTime) OR " +
           "(startTime >= :startTime AND startTime < :endTime))")
    List<Timetable> getConflictingTimetables(int dayOfWeek, String startTime, String endTime);
} 