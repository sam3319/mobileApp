package com.example.chapter12codingchallenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies_db";
    private static final String TABLE_MOVIES = "movies";

    // 테이블 컬럼
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_YEAR = "year";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_RATING = "rating";
    private static final String KEY_COUNTRY = "country";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_YEAR + " TEXT,"
                + KEY_DIRECTOR + " TEXT,"
                + KEY_RATING + " TEXT,"
                + KEY_COUNTRY + " TEXT" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // 영화 추가
    public long addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_DIRECTOR, movie.getDirector());
        values.put(KEY_RATING, movie.getRating());
        values.put(KEY_COUNTRY, movie.getCountry());

        long id = db.insert(TABLE_MOVIES, null, values);
        db.close();
        return id;
    }

    // 영화 조회
    public Movie getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[]{KEY_ID, KEY_TITLE, KEY_YEAR, KEY_DIRECTOR, KEY_RATING, KEY_COUNTRY},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Movie movie = new Movie(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));

        cursor.close();
        return movie;
    }

    // 모든 영화 조회
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(0));
                movie.setTitle(cursor.getString(1));
                movie.setYear(cursor.getString(2));
                movie.setDirector(cursor.getString(3));
                movie.setRating(cursor.getString(4));
                movie.setCountry(cursor.getString(5));

                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return movieList;
    }

    // 영화 업데이트
    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_DIRECTOR, movie.getDirector());
        values.put(KEY_RATING, movie.getRating());
        values.put(KEY_COUNTRY, movie.getCountry());

        return db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
    }

    // 영화 삭제
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
    }
}
