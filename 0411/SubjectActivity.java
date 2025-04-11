package com.example.studentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubjectActivity extends AppCompatActivity {

    private ListView listViewSubjects;
    private String[] subjects = {"수학", "물리학", "컴퓨터 공학", "영문학", "경제학"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        listViewSubjects = findViewById(R.id.listViewSubjects);

        // 과목 리스트 어댑터 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjects);
        listViewSubjects.setAdapter(adapter);
    }
}
