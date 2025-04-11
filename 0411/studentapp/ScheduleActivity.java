package com.example.studentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScheduleActivity extends AppCompatActivity {

    private ListView listViewSchedule;
    private String[] schedules = {"월요일: 수학 10:00", "화요일: 물리학 12:00", "수요일: 컴퓨터 공학 14:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listViewSchedule = findViewById(R.id.listViewSchedule);

        // 일정 리스트 어댑터 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, schedules);
        listViewSchedule.setAdapter(adapter);
    }
}
