package com.example.chap5_openchallenge1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
    }

    public void onRadioButtonClicked(View v) {
        img1.setImageResource(0);
        img2.setImageResource(0);
        img3.setImageResource(0);

        boolean checked = ((RadioButton) v).isChecked();

        int id = v.getId();
        if (checked) {
            if (id == R.id.btn1) {
                img1.setImageResource(R.drawable.image0);
            } else if (id == R.id.btn2) {
                img2.setImageResource(R.drawable.image1);
            } else if (id == R.id.btn3) {
                img3.setImageResource(R.drawable.image2);
            }
        }
    }
}
