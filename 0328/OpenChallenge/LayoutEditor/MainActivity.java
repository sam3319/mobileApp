package com.example.layouteditor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout)findViewById(R.id.main);
    }

    public void onClick(View v){
        int[] color = {Color.RED, Color.BLUE, Color.GREEN};
        Random random = new Random();
        int index = random.nextInt(color.length);
        layout.setBackgroundColor(color[index]);
    }
}