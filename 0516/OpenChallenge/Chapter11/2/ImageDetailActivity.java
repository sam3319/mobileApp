package com.example.chapter11codingchallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView fullImageView;
    private Button btnPrevious, btnNext;
    private ArrayList<String> imagePaths;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        fullImageView = findViewById(R.id.fullImageView);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        Intent intent = getIntent();
        imagePaths = intent.getStringArrayListExtra("imagePaths");
        currentPosition = intent.getIntExtra("position", 0);

        displayImage(currentPosition);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0) {
                    currentPosition--;
                    displayImage(currentPosition);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < imagePaths.size() - 1) {
                    currentPosition++;
                    displayImage(currentPosition);
                }
            }
        });
    }

    private void displayImage(int position) {
        // 버튼 활성화/비활성화 상태 업데이트
        btnPrevious.setEnabled(position > 0);
        btnNext.setEnabled(position < imagePaths.size() - 1);

        // 이미지 표시
        String imagePath = imagePaths.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        fullImageView.setImageBitmap(bitmap);
    }
}
