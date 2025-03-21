package com.example.randomnumber;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textViewRandomNumber = findViewById(R.id.textViewRandomNumber);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }
    public void generateRandomNumber(View v){
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        textViewRandomNumber.setText("난수 : " + randomNumber);

    }
}