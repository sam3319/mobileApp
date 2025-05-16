package com.example.chapter11codingchallenge1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private ListView listView;
    private ArrayList<String> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        videoList = new ArrayList<>();

        // 권한 확인 및 요청
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_VIDEO},
                        PERMISSION_REQUEST_CODE);
            } else {
                loadVideos();
            }
        } else {
            // Android 12 이하
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                loadVideos();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadVideos();
            } else {
                Toast.makeText(this, "권한이 거부되었습니다. 동영상 목록을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadVideos() {
        videoList.clear();
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        // 동영상 정보를 쿼리
        Cursor cursor = resolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                videoList.add(cursor.getString(index));
            } while (cursor.moveToNext());

            cursor.close();

            // 어댑터 설정
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    videoList);
            listView.setAdapter(adapter);

            // 아이템 클릭 리스너 설정
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Toast.makeText(MainActivity.this,
                        "선택된 동영상: " + videoList.get(position),
                        Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "장치에 동영상이 없거나 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
