package com.example.imageviewtest5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.img);
    }

    public void ScaleChange(View v){
        ImageView.ScaleType[] scaleTypes = {
                ImageView.ScaleType.CENTER,
                ImageView.ScaleType.CENTER_CROP,
                ImageView.ScaleType.CENTER_INSIDE,
                ImageView.ScaleType.FIT_CENTER,
                ImageView.ScaleType.FIT_XY
        };

        image.setScaleType(scaleTypes[state]);
        state = (state + 1) % scaleTypes.length;
    }

    public void RotationChange(View v){
        image.setRotation(image.getRotation() + 45);
    }

    public void AlphaChange(View v){
        float alpha = image.getAlpha();
        alpha = (alpha == 1.0f) ? 0.5f : 1.0f;
        image.setAlpha(alpha);
    }
}