package com.example.studentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextMajor;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextName = findViewById(R.id.editTextName);
        editTextMajor = findViewById(R.id.editTextMajor);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // 프로필 저장 버튼 클릭 리스너
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String major = editTextMajor.getText().toString();

                // 간단한 저장 처리
                Toast.makeText(ProfileActivity.this, "프로필이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
