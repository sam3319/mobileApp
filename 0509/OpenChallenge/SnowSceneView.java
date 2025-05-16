package com.example.chapter09codingchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SnowSceneView extends View {
    private Paint backgroundPaint;
    private Bitmap backgroundImage;
    private List<Snowflake> snowflakes;
    private int snowflakeCount = 50;
    private float snowflakeSize = 5f;
    private boolean isRunning = true;
    private int screenWidth;
    private int screenHeight;

    public SnowSceneView(Context context) {
        super(context);
        init();
    }

    public SnowSceneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(25, 25, 112)); // 어두운 파란색 배경

        // 배경 이미지 로드 (res/drawable에 winter_background.jpg 파일 필요)
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.winter_background);

        snowflakes = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;

        // 배경 이미지 크기 조정
        if (backgroundImage != null) {
            backgroundImage = Bitmap.createScaledBitmap(backgroundImage, w, h, true);
        }

        // 눈송이 초기화
        createSnowflakes();
    }

    private void createSnowflakes() {
        snowflakes.clear();
        for (int i = 0; i < snowflakeCount; i++) {
            snowflakes.add(new Snowflake(screenWidth, screenHeight, snowflakeSize));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 배경 그리기
        if (backgroundImage != null) {
            canvas.drawBitmap(backgroundImage, 0, 0, null);
        } else {
            canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        }

        // 눈송이 그리기
        for (Snowflake snowflake : snowflakes) {
            snowflake.update();
            snowflake.draw(canvas);
        }

        // 애니메이션 지속
        if (isRunning) {
            invalidate();
        }
    }

    public void setSnowflakeCount(int count) {
        this.snowflakeCount = count;
        createSnowflakes();
    }

    public void setSnowflakeSize(float size) {
        this.snowflakeSize = size;
        for (Snowflake snowflake : snowflakes) {
            snowflake.setSize(size);
        }
    }

    public void pause() {
        isRunning = false;
    }

    public void resume() {
        isRunning = true;
        invalidate();
    }
}
