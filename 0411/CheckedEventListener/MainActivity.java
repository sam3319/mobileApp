package com.example.checkedeventlistener;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
    }

    public void onCheckBoxClicked(View v){
        boolean state = ((CheckBox)v).isChecked();

        int id = v.getId();
        if(id == R.id.cb1){
            if(state) img1.setImageResource(R.drawable.sand1);
            else img1.setImageResource(0);
        }
        else if(id == R.id.cb2){
            if(state) img2.setImageResource(R.drawable.sand2);
            else img2.setImageResource(0);
        }
    }
}