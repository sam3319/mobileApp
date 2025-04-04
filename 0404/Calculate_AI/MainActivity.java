package com.example.calculate_ai;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private String input = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);

        setNumberButtonClickListeners();
        setOperatorButtonClickListeners();
    }

    private void setNumberButtonClickListeners() {
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        View.OnClickListener listener = v -> {
            Button btn = (Button) v;
            input += btn.getText().toString();
            tvResult.setText(input);
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonClickListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> handleOperator("/"));

        findViewById(R.id.btnEqual).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnAC).setOnClickListener(v -> clear());
    }

    private void handleOperator(String op) {
        if (!input.isEmpty()) {
            firstNumber = Double.parseDouble(input);
            operator = op;
            isOperatorPressed = true;
            input = "";
        }
    }

    private void calculateResult() {
        if (isOperatorPressed && !input.isEmpty()) {
            double secondNumber = Double.parseDouble(input);
            double result = 0;

            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        tvResult.setText("Error");
                        return;
                    }
                    break;
            }

            // 🔽 여기서 결과를 정수인지 확인해서 출력 형식 결정
            if (result == (int) result) {
                tvResult.setText(String.valueOf((int) result));  // 정수면 정수로 출력
            } else {
                tvResult.setText(String.valueOf(result));         // 실수면 그대로 출력
            }

            input = String.valueOf(result);
            isOperatorPressed = false;
        }
    }

    private void clear() {
        input = "";
        operator = "";
        firstNumber = 0;
        isOperatorPressed = false;
        tvResult.setText("0");
    }
}
