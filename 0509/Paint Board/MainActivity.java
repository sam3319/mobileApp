package kr.co.example.singletouch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SingleTouchView drawingView;
    private boolean eraserMode = false;
    private LinearLayout eraserSizeLayout;
    private SeekBar penSizeSeekBar, eraserSizeSeekBar;
    private TextView penSizeText, eraserSizeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawingView);
        eraserSizeLayout = findViewById(R.id.eraserSizeLayout);
        penSizeSeekBar = findViewById(R.id.penSizeSeekBar);
        eraserSizeSeekBar = findViewById(R.id.eraserSizeSeekBar);
        penSizeText = findViewById(R.id.penSizeText);
        eraserSizeText = findViewById(R.id.eraserSizeText);

        // 초기값 설정
        penSizeSeekBar.setProgress((int)drawingView.getPenSize());
        eraserSizeSeekBar.setProgress((int)drawingView.getEraserSize());
        penSizeText.setText(String.valueOf((int)drawingView.getPenSize()));
        eraserSizeText.setText(String.valueOf((int)drawingView.getEraserSize()));

        // 색상 버튼 설정
        findViewById(R.id.redButton).setOnClickListener(this);
        findViewById(R.id.blueButton).setOnClickListener(this);
        findViewById(R.id.greenButton).setOnClickListener(this);
        findViewById(R.id.blackButton).setOnClickListener(this);
        findViewById(R.id.yellowButton).setOnClickListener(this);

        // 도구 버튼 설정
        findViewById(R.id.eraserButton).setOnClickListener(this);
        findViewById(R.id.clearButton).setOnClickListener(this);
        findViewById(R.id.undoButton).setOnClickListener(this);
        findViewById(R.id.redoButton).setOnClickListener(this);
        findViewById(R.id.saveButton).setOnClickListener(this);

        // SeekBar 리스너 설정
        penSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) progress = 1; // 최소값 설정
                penSizeText.setText(String.valueOf(progress));
                drawingView.setPenSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        eraserSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 5) progress = 5; // 최소값 설정
                eraserSizeText.setText(String.valueOf(progress));
                drawingView.setEraserSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // 색상 변경 (색상 버튼 클릭 시 지우개 모드 해제)
        if (id == R.id.redButton) {
            setDrawingMode(false);
            drawingView.setColor(Color.RED);
        } else if (id == R.id.blueButton) {
            setDrawingMode(false);
            drawingView.setColor(Color.BLUE);
        } else if (id == R.id.greenButton) {
            setDrawingMode(false);
            drawingView.setColor(Color.GREEN);
        } else if (id == R.id.blackButton) {
            setDrawingMode(false);
            drawingView.setColor(Color.BLACK);
        } else if (id == R.id.yellowButton) {
            setDrawingMode(false);
            drawingView.setColor(Color.YELLOW);
        }
        // 지우개 모드
        else if (id == R.id.eraserButton) {
            eraserMode = !eraserMode; // 지우개 모드 토글
            setDrawingMode(eraserMode);
        }
        // 전체 지우기
        else if (id == R.id.clearButton) {
            drawingView.clearCanvas();
        }
        // 실행 취소
        else if (id == R.id.undoButton) {
            if (!drawingView.undo()) {
                Toast.makeText(this, "더 이상 실행 취소할 수 없습니다", Toast.LENGTH_SHORT).show();
            }
        }
        // 다시 실행
        else if (id == R.id.redoButton) {
            if (!drawingView.redo()) {
                Toast.makeText(this, "더 이상 다시 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
            }
        }
        // 저장
        else if (id == R.id.saveButton) {
            saveDrawing();
        }
    }

    // 그리기/지우개 모드 설정
    private void setDrawingMode(boolean isEraserMode) {
        eraserMode = isEraserMode;
        drawingView.setEraserMode(isEraserMode);

        // UI 업데이트
        Button eraserButton = findViewById(R.id.eraserButton);
        if (isEraserMode) {
            eraserButton.setText("펜으로");
            eraserSizeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, "지우개 모드", Toast.LENGTH_SHORT).show();
        } else {
            eraserButton.setText("지우개");
            eraserSizeLayout.setVisibility(View.GONE);
            Toast.makeText(this, "펜 모드", Toast.LENGTH_SHORT).show();
        }
    }

    // 그림 저장 메소드
    private void saveDrawing() {
        drawingView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(drawingView.getDrawingCache());
        drawingView.setDrawingCacheEnabled(false);

        // 파일 이름 생성 (현재 날짜와 시간 사용)
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "Drawing_" + timeStamp + ".png";

        // 비트맵을 갤러리에 저장
        try {
            // 임시 파일 생성
            File file = new File(getExternalFilesDir(null), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            // 갤러리에 추가
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, "Drawing");

            Toast.makeText(this, "그림이 저장되었습니다", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
