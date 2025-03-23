package com.example.exercises10;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText text;
    TextView result;
    int random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        text = (EditText)findViewById(R.id.text);
        result = (TextView)findViewById(R.id.result);
        random = (int)(Math.random() * 100) + 1;
    }

    public void submit(View v){
        int input = Integer.parseInt(text.getText().toString());

        if(input == random){result.setText("정답입니다.");}
        else if(input > random){result.setText("Low!!");}
        else if(input < random){result.setText("High!!");}
    }
}