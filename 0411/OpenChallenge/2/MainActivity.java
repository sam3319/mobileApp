package com.example.todoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button addButton;
    LinearLayout checkboxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        checkboxContainer = findViewById(R.id.checkboxContainer);

        addButton.setOnClickListener(v -> {
            String inputText = editText.getText().toString().trim();

            if (!inputText.isEmpty()) {
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(inputText);
                checkboxContainer.addView(checkBox);

                editText.setText("");
            }
        });
    }
}
