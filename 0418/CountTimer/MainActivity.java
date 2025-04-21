package com.example.claude;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 메인 레이아웃 참조 가져오기
        mainLayout = findViewById(R.id.main_layout);

        // 레이아웃에 컨텍스트 메뉴 등록
        registerForContextMenu(mainLayout);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // 컨텍스트 메뉴 항목 추가
        menu.setHeaderTitle("배경색 선택");
        menu.add(0, 1, 0, "빨간색");
        menu.add(0, 2, 0, "초록색");
        menu.add(0, 3, 0, "파란색");
        menu.add(0, 4, 0, "노란색");
        menu.add(0, 5, 0, "기본색");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // 선택된 메뉴 항목에 따라 배경색 변경
        switch (item.getItemId()) {
            case 1:
                mainLayout.setBackgroundColor(Color.RED);
                showToast("빨간색으로 변경되었습니다.");
                return true;
            case 2:
                mainLayout.setBackgroundColor(Color.GREEN);
                showToast("초록색으로 변경되었습니다.");
                return true;
            case 3:
                mainLayout.setBackgroundColor(Color.BLUE);
                showToast("파란색으로 변경되었습니다.");
                return true;
            case 4:
                mainLayout.setBackgroundColor(Color.YELLOW);
                showToast("노란색으로 변경되었습니다.");
                return true;
            case 5:
                mainLayout.setBackgroundColor(Color.WHITE);
                showToast("기본색으로 변경되었습니다.");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}