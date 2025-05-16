package com.example.chapter06codingchallenge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 액션바에 뒤로가기 버튼 활성화
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Start");
        }
    }

    // 뒤로가기 버튼 클릭 시 메인 액티비티로 돌아가기
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
