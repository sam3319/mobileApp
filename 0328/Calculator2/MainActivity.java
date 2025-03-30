package com.example.calculator2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private String input = "";
    private double x;
    private double y;
    private String op;
    private boolean decimalState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
    }

    public void setNumber(View v) {
        if (v instanceof Button) {
            Button btn = (Button) v;
            input += btn.getText().toString();
        }
        text.setText(input);
    }

    public void reset(View v) {
        text.setText("");
        input = "";
        decimalState = false;
    }

    public void op(View v) {
        if (v instanceof Button) {
            Button btn = (Button) v;
            if (!input.isEmpty()) {
                x = Double.parseDouble(input);
                input = "";
                op = btn.getText().toString();
                decimalState = false;
            }
        }
    }

    public void result(View v) {
        if (!input.isEmpty()) {
            y = Double.parseDouble(input);
            input = "";

            double result = 0;
            switch (op) {
                case "+":
                    result = x + y;
                    break;
                case "-":
                    result = x - y;
                    break;
                case "*":
                    result = x * y;
                    break;
                case "/":
                    if (y == 0) {
                        text.setText("0으로 나눌 수 없습니다.");
                        return;
                    }
                    result = x / y;
                    break;
            }

            if (result % 1 == 0) {
                text.setText(String.valueOf((int) result));
            } else {
                text.setText(String.valueOf(result));
            }

            op = "";
            decimalState = false;
        }
    }

    public void delete(View v) {
        if (input.isEmpty()) {
            text.setText("숫자가 없습니다.");
        } else {
            if (input.endsWith(".")) {
                decimalState = false;
            }
            input = input.substring(0, input.length() - 1);
            text.setText(input);
        }
    }

    public void decimal(View v) {
        if (!decimalState) {
            if (input.isEmpty()) {
                input = "0.";
            } else {
                input += ".";
            }
            text.setText(input);
            decimalState = true;
        }
    }
}
