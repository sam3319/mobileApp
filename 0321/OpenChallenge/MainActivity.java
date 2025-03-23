package com.example.dicroller;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int []n1 = {0, 0, 0, 0, 0, 0};
    int []n2 = {0, 0, 0, 0, 0, 0};
    TextView text;
    TextView result1;
    TextView result2;
    ImageView img1;
    ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);
        result1 = (TextView)findViewById(R.id.result1);
        result2 = (TextView)findViewById(R.id.result2);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
    }

    public void roll(View v) {
        int num1 = (int) (Math.random() * 6) + 1;
        int num2 = (int) (Math.random() * 6) + 1;

        text.setText("첫 번째 주사위 : " + num1 + "\n두 번째 주사위 : " + num2);

        switch (num1){
            case 1:
                img1.setImageResource(R.drawable.dice_1);
                n1[0] += 1;
                break;
            case 2:
                img1.setImageResource(R.drawable.dice_2);
                n1[1] += 1;
                break;
            case 3:
                img1.setImageResource(R.drawable.dice_3);
                n1[2] += 1;
                break;
            case 4:
                img1.setImageResource(R.drawable.dice_4);
                n1[3] += 1;
                break;
            case 5:
                img1.setImageResource(R.drawable.dice_5);
                n1[4] += 1;
                break;
            case 6:
                img1.setImageResource(R.drawable.dice_6);
                n1[5] += 1;
                break;
        }
        switch (num2){
            case 1:
                img2.setImageResource(R.drawable.dice_1);
                n2[0] += 1;
                break;
            case 2:
                img2.setImageResource(R.drawable.dice_2);
                n2[1] += 1;
                break;
            case 3:
                img2.setImageResource(R.drawable.dice_3);
                n2[2] += 1;
                break;
            case 4:
                img2.setImageResource(R.drawable.dice_4);
                n2[3] += 1;
                break;
            case 5:
                img2.setImageResource(R.drawable.dice_5);
                n2[4] += 1;
                break;
            case 6:
                img2.setImageResource(R.drawable.dice_6);
                n2[5] += 1;
                break;
        }

        result1.setText("첫 번째 주사위\n1: " + n1[0] + "\n2: " + n1[1] + "\n3: " + n1[2] + "\n4: " + n1[3] + "\n5: " + n1[4] + "\n6: " + n1[5]);
        result2.setText("두 번째 주사위\n1: " + n2[0] + "\n2: " + n2[1] + "\n3: " + n2[2] + "\n4: " + n2[3] + "\n5: " + n2[4] + "\n6: " + n2[5]);
    }
}