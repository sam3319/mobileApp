package com.example.chapter12codingchallenge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextYear, editTextDirector, editTextRating, editTextCountry;
    private Button btnSave, btnUpdate, btnDelete;
    private DatabaseHelper dbHelper;
    private int movieId = -1;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        dbHelper = new DatabaseHelper(this);

        // UI 요소 초기화
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextYear = findViewById(R.id.editTextYear);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextRating = findViewById(R.id.editTextRating);
        editTextCountry = findViewById(R.id.editTextCountry);

        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // 인텐트에서 영화 ID 가져오기 (수정/삭제 모드인 경우)
        if (getIntent().hasExtra("movie_id")) {
            movieId = getIntent().getIntExtra("movie_id", -1);
            loadMovieData(movieId);

            // 수정/삭제 모드
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            // 추가 모드
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovie();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMovie();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMovie();
            }
        });
    }

    private void loadMovieData(int movieId) {
        currentMovie = dbHelper.getMovie(movieId);

        editTextTitle.setText(currentMovie.getTitle());
        editTextYear.setText(currentMovie.getYear());
        editTextDirector.setText(currentMovie.getDirector());
        editTextRating.setText(currentMovie.getRating());
        editTextCountry.setText(currentMovie.getCountry());
    }

    private void saveMovie() {
        String title = editTextTitle.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String rating = editTextRating.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();

        if (title.isEmpty() || year.isEmpty() || director.isEmpty() || rating.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        Movie movie = new Movie(title, year, director, rating, country);
        long result = dbHelper.addMovie(movie);

        if (result != -1) {
            Toast.makeText(this, "영화가 성공적으로 저장되었습니다", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "영화 저장에 실패했습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMovie() {
        String title = editTextTitle.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String rating = editTextRating.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();

        if (title.isEmpty() || year.isEmpty() || director.isEmpty() || rating.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        currentMovie.setTitle(title);
        currentMovie.setYear(year);
        currentMovie.setDirector(director);
        currentMovie.setRating(rating);
        currentMovie.setCountry(country);

        int result = dbHelper.updateMovie(currentMovie);

        if (result > 0) {
            Toast.makeText(this, "영화가 성공적으로 수정되었습니다", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "영화 수정에 실패했습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMovie() {
        dbHelper.deleteMovie(currentMovie);
        Toast.makeText(this, "영화가 삭제되었습니다", Toast.LENGTH_SHORT).show();
        finish();
    }
}
