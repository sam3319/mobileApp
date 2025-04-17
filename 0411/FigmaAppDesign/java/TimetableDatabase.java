package com.example.todolist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Timetable.class}, version = 1, exportSchema = false)
public abstract class TimetableDatabase extends RoomDatabase {
    private static volatile TimetableDatabase INSTANCE;

    public abstract TimetableDao timetableDao();

    public static TimetableDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TimetableDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TimetableDatabase.class,
                            "timetable_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
} 