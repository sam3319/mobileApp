package com.example.chapter09codingchallenge;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SnowSceneView snowSceneView;
    private SeekBar sizeSeekBar;
    private SeekBar countSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 커스텀 뷰 생성 및 추가
        snowSceneView = new SnowSceneView(this);
        FrameLayout container = findViewById(R.id.snow_scene_container);
        container.addView(snowSceneView);

        // 눈 크기 조절 SeekBar
        sizeSeekBar = findViewById(R.id.snow_size_seekbar);
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float size = 2 + progress;
                snowSceneView.setSnowflakeSize(size);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 눈 개수 조절 SeekBar
        countSeekBar = findViewById(R.id.snow_count_seekbar);
        countSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                snowSceneView.setSnowflakeCount(progress + 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        snowSceneView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        snowSceneView.resume();
    }
}
