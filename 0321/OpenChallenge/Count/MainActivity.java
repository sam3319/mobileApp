package com.example.count;

import android.graphics.text.TextRunShaper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView count;
    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        count = (TextView)findViewById(R.id.count);
        Button plusBtn = (Button)findViewById(R.id.plus);
        Button minusBtn = (Button)findViewById(R.id.minus);
    }

    public void Increase(View v){
        count.setText("카운터: " + ++num);
    }

    public void Decrease(View v) {
        count.setText("카운터: " + --num);
    }

    public void Reset(View v){
        num = 0;
        count.setText("카운터: " + num);
    }
}