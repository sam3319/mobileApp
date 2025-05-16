package com.example.chapter06codingchallenge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar seekBarDifficulty, seekBarPlayers;
    private TextView tvDifficultyValue, tvPlayersValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 액션바에 뒤로가기 버튼 활성화
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        // 뷰 초기화
        seekBarDifficulty = findViewById(R.id.seekBarDifficulty);
        seekBarPlayers = findViewById(R.id.seekBarPlayers);
        tvDifficultyValue = findViewById(R.id.tvDifficultyValue);
        tvPlayersValue = findViewById(R.id.tvPlayersValue);

        // Difficulty SeekBar 리스너 설정
        seekBarDifficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDifficultyValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Players SeekBar 리스너 설정
        seekBarPlayers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 최소 1명의 플레이어는 있어야 함
                int playerCount = Math.max(1, progress);
                tvPlayersValue.setText(String.valueOf(playerCount));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // 뒤로가기 버튼 클릭 시 메인 액티비티로 돌아가기
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
