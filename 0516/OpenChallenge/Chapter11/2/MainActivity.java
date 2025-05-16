package com.example.chapter11codingchallenge;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private GridView gridView;
    private ArrayList<ImageModel> imageList;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        imageList = new ArrayList<>();

        // 권한 요청
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        // 이미지 로드 및 표시
        loadImagesFromGallery();
    }

    private void loadImagesFromGallery() {
        // 올바른 OnItemClickListener 구현 방법
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 모든 이미지 경로를 ArrayList로 준비
                ArrayList<String> allImagePaths = new ArrayList<>();
                for (ImageModel model : imageList) {
                    allImagePaths.add(model.getPath());
                }

                // 상세 화면으로 이동
                Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                intent.putStringArrayListExtra("imagePaths", allImagePaths);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        try {
            String[] projection = new String[]{
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.DATA,
                    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.ImageColumns.DATE_TAKEN,
                    MediaStore.Images.ImageColumns.MIME_TYPE
            };

            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"); // 최신 이미지부터 정렬

            int pathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int nameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int dateColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "장치에 이미지가 없습니다!", Toast.LENGTH_LONG).show();
            } else {
                while (cursor.moveToNext()) {
                    String imagePath = cursor.getString(pathColumnIndex);
                    String imageName = cursor.getString(nameColumnIndex);
                    String imageDate = cursor.getString(dateColumnIndex);

                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        ImageModel model = new ImageModel(imagePath, imageName, imageDate);
                        imageList.add(model);
                    }
                }

                // 어댑터 설정
                adapter = new ImageAdapter(this, imageList);
                gridView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "이미지 로딩 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
