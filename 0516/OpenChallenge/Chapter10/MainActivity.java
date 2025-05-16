package com.example.chapter10openchallenge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup regionRadioGroup;
    private RadioButton radioKorea, radioUSA;
    private Button submitButton;
    private TextView foodTitleTextView, recipeTitleTextView, recipeTextView;
    private ImageView foodImageView;

    private String currentLanguage = "ko"; // 기본값은 한국어

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        regionRadioGroup = findViewById(R.id.regionRadioGroup);
        radioKorea = findViewById(R.id.radioKorea);
        radioUSA = findViewById(R.id.radioUSA);
        submitButton = findViewById(R.id.submitButton);
        foodTitleTextView = findViewById(R.id.foodTitleTextView);
        recipeTitleTextView = findViewById(R.id.recipeTitleTextView);
        recipeTextView = findViewById(R.id.recipeTextView);
        foodImageView = findViewById(R.id.foodImageView);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택된 라디오 버튼에 따라 언어 설정
                if (radioKorea.isChecked()) {
                    currentLanguage = "ko";
                } else {
                    currentLanguage = "en";
                }

                showFoodRecipe();
            }
        });
    }

    private void showFoodRecipe() {
        // UI 요소 표시
        foodTitleTextView.setVisibility(View.VISIBLE);
        foodImageView.setVisibility(View.VISIBLE);
        recipeTitleTextView.setVisibility(View.VISIBLE);
        recipeTextView.setVisibility(View.VISIBLE);

        // 텍스트 설정
        foodTitleTextView.setText(TextManager.getText("food_title", currentLanguage));
        recipeTitleTextView.setText(TextManager.getText("recipe_title", currentLanguage));

        // 레시피 텍스트 구성
        StringBuilder recipe = new StringBuilder();
        recipe.append(TextManager.getText("recipe_step1", currentLanguage)).append("\n\n");
        recipe.append(TextManager.getText("recipe_step2", currentLanguage)).append("\n\n");
        recipe.append(TextManager.getText("recipe_step3", currentLanguage)).append("\n\n");
        recipe.append(TextManager.getText("recipe_step4", currentLanguage)).append("\n\n");
        recipe.append(TextManager.getText("recipe_step5", currentLanguage));

        recipeTextView.setText(recipe.toString());

        // 이미지 설정
        if (currentLanguage.equals("ko")) {
            foodImageView.setImageResource(R.drawable.kimbap);
        } else {
            foodImageView.setImageResource(R.drawable.hamburger);
        }
    }
}
