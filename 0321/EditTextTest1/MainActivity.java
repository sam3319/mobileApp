package com.example.edittexttest1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText id;
    EditText pass;
    EditText phone;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        id = (EditText)findViewById(R.id.id);
        pass = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);

        result = (TextView)findViewById(R.id.result);
        Button btn = (Button)findViewById(R.id.btn);
    }
    public void Sign(View v){
        String idText = id.getText().toString();
        String passText = pass.getText().toString();
        String phoneText = phone.getText().toString();

        result.setText("아이디: "+ idText + "\n비밀번호: " + passText + "\n전화번호: " + phoneText);
    }
}