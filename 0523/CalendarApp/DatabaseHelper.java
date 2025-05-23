package com.example.calendarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT NOT NULL,"
                + COLUMN_TIME + " TEXT NOT NULL,"
                + COLUMN_TIMESTAMP + " LONG NOT NULL" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // Event를 CalendarEvent로 변경
    public long addEvent(CalendarEvent event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_DATE, event.getDate());
        values.put(COLUMN_TIME, event.getTime());
        values.put(COLUMN_TIMESTAMP, event.getTimestamp());

        long id = db.insert(TABLE_EVENTS, null, values);
        db.close();
        return id;
    }

    // Event를 CalendarEvent로 변경
    public List<CalendarEvent> getAllEvents() {
        List<CalendarEvent> eventList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " ORDER BY " + COLUMN_TIMESTAMP + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CalendarEvent event = new CalendarEvent(); // Event를 CalendarEvent로 변경
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setDate(cursor.getString(3));
                event.setTime(cursor.getString(4));
                event.setTimestamp(cursor.getLong(5));
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }

    public void deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 특정 이벤트 조회 메서드 추가
    public CalendarEvent getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        CalendarEvent event = null;
        if (cursor.moveToFirst()) {
            event = new CalendarEvent();
            event.setId(cursor.getInt(0));
            event.setTitle(cursor.getString(1));
            event.setDescription(cursor.getString(2));
            event.setDate(cursor.getString(3));
            event.setTime(cursor.getString(4));
            event.setTimestamp(cursor.getLong(5));
        }

        cursor.close();
        db.close();
        return event;
    }

}
