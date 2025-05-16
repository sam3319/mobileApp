package com.example.filenotepad;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private static final String FILENAME = "memo.txt";
    private EditText memoEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        memoEditText = findViewById(R.id.memoEditText);

        // 저장 버튼
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveMemo();
            }
        });

        // 불러오기 버튼
        Button loadButton = findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadMemo();
            }
        });

        // 삭제 버튼
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteMemo();
            }
        });

        // 새 메모 버튼
        Button newButton = findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newMemo();
            }
        });

        // 앱 시작 시 자동으로 메모 불러오기
        loadMemo();
    }

    // 메모 저장 기능
    private void saveMemo() {
        String memoText = memoEditText.getText().toString();

        // 빈 메모는 저장하지 않음
        if (memoText.trim().isEmpty()) {
            Toast.makeText(this, "메모를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // 현재 시간 포맷팅
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentTime = sdf.format(new Date());

            // 저장할 내용 (메모 + 시간)
            String saveContent = memoText + "\n\n마지막 저장 시간: " + currentTime;

            // 파일에 저장
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(saveContent.getBytes());
            fos.close();

            Toast.makeText(this, "메모가 저장되었습니다", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 메모 불러오기 기능
    private void loadMemo() {
        try {
            FileInputStream fis = openFileInput(FILENAME);

            // 파일이 존재하면 내용 읽기
            if (fis != null) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();

                // 읽은 내용을 EditText에 설정
                memoEditText.setText(new String(buffer));

                // 커서를 텍스트의 시작으로 이동
                memoEditText.setSelection(0);

                Toast.makeText(this, "메모를 불러왔습니다", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            // 파일이 없거나 읽기 오류 발생
            memoEditText.setText("");
            Toast.makeText(this, "저장된 메모가 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    // 메모 삭제 기능
    private void deleteMemo() {
        boolean deleted = deleteFile(FILENAME);

        if (deleted) {
            memoEditText.setText("");
            Toast.makeText(this, "메모가 삭제되었습니다", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "삭제할 메모가 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    // 새 메모 기능
    private void newMemo() {
        // 현재 내용이 있다면 저장 여부 확인 (실제 앱에서는 대화상자 사용)
        if (!memoEditText.getText().toString().trim().isEmpty()) {
            // 여기서는 간단히 토스트 메시지만 표시
            Toast.makeText(this, "이전 메모는 저장되지 않습니다", Toast.LENGTH_SHORT).show();
        }

        // 편집 영역 비우기
        memoEditText.setText("");
    }

    // 앱이 일시 중지될 때 자동 저장 (선택 사항)
    @Override
    protected void onPause() {
        super.onPause();

        // 내용이 있을 경우만 자동 저장
        if (!memoEditText.getText().toString().trim().isEmpty()) {
            saveMemo();
        }
    }
}
