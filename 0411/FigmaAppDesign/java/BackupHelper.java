package com.example.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BackupHelper {
    private static final String PREFS_NAME = "timetable_backup";
    private static final String KEY_BACKUP = "timetable_data";
    private static final String BACKUP_FILENAME = "timetable_backup.json";
    private final SharedPreferences prefs;
    private final Gson gson;

    public BackupHelper(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void backupTimetables(List<Timetable> timetables) {
        if (timetables == null) {
            return;
        }
        String json = gson.toJson(timetables);
        prefs.edit().putString(KEY_BACKUP, json).apply();
    }

    public List<Timetable> restoreTimetables() {
        String json = prefs.getString(KEY_BACKUP, null);
        if (json == null) {
            return new ArrayList<>();
        }
        try {
            Type type = new TypeToken<List<Timetable>>(){}.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e("BackupHelper", "Failed to restore timetables", e);
            return new ArrayList<>();
        }
    }

    public boolean exportToFile(Context context, List<Timetable> timetables) {
        if (timetables == null) {
            return false;
        }
        String json = gson.toJson(timetables);
        try (FileOutputStream fos = context.openFileOutput(BACKUP_FILENAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
            return true;
        } catch (IOException e) {
            Log.e("BackupHelper", "Failed to export timetable", e);
            return false;
        }
    }

    public List<Timetable> importFromFile(Context context) {
        try (FileInputStream fis = context.openFileInput(BACKUP_FILENAME)) {
            byte[] buffer = new byte[fis.available()];
            int bytesRead = fis.read(buffer);
            if (bytesRead <= 0) {
                return new ArrayList<>();
            }
            String json = new String(buffer, 0, bytesRead);
            Type type = new TypeToken<List<Timetable>>(){}.getType();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            Log.e("BackupHelper", "Failed to import timetable", e);
            return new ArrayList<>();
        }
    }
} 