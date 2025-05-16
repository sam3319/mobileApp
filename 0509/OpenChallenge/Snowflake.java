package com.example.chapter09codingchallenge;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Snowflake {
    private float x;
    private float y;
    private float size;
    private float speed;
    private float alpha;
    private Paint paint;
    private Random random;
    private int screenWidth;
    private int screenHeight;

    public Snowflake(int screenWidth, int screenHeight, float size) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.size = size;
        random = new Random();

        // 랜덤 위치 초기화
        x = random.nextFloat() * screenWidth;
        y = random.nextFloat() * screenHeight - screenHeight; // 화면 위에서 시작

        // 랜덤 속도
        speed = 1 + random.nextFloat() * 5;

        // 투명도
        alpha = 50 + random.nextFloat() * 200;

        // 페인트 설정
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha((int) alpha);
        paint.setAntiAlias(true);
    }

    public void update() {
        // 눈송이 아래로 이동
        y += speed;

        // 좌우로 살짝 흔들림 효과
        x += (random.nextFloat() - 0.5f);

        // 화면 아래로 벗어나면 다시 위에서 시작
        if (y > screenHeight) {
            y = 0;
            x = random.nextFloat() * screenWidth;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, size, paint);
    }

    public void setSize(float size) {
        this.size = size;
    }
}
