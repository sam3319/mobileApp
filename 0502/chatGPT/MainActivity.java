package com.example.chatgpt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        // 데이터 준비
        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        HashMap<String, String> item1 = new HashMap<>();
        item1.put("title", "제목 1");
        item1.put("subtitle", "부제목 1");

        HashMap<String, String> item2 = new HashMap<>();
        item2.put("title", "제목 2");
        item2.put("subtitle", "부제목 2");

        data.add(item1);
        data.add(item2);

        // 어댑터 생성
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        // ListView에 어댑터 설정
        listView.setAdapter(adapter);
    }
}
