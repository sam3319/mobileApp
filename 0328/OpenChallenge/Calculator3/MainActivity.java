package com.example.calculator3;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText num1;
    EditText num2;
    EditText num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        num1 = (EditText)findViewById(R.id.edit1);
        num2 = (EditText)findViewById(R.id.edit2);
        num3 = (EditText)findViewById(R.id.edit3);
    }

    public void cal_plus(View v){
        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());
        int result = n1 + n2;

        num3.setText("" + result);
    }

    public void cal_minus(View v){
        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());
        int result = n1 - n2;

        num3.setText("" + result);
    }

    public void cal_multply(View v){
        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());
        int result = n1 * n2;

        num3.setText("" + result);
    }

    public void cal_divide(View v) {

        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());

        if (n1 > 0 && n2 > 0) {
            double result = (double) n1 / n2;
            num3.setText("" + result);
        } else {
            num3.setText("0은 나눌 수 없습니다.");
        }
    }
}