package com.example.survey;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SurveyFragment.OnSurveyAnswerListener {
    private int currentQuestion = 0;
    private static final int TOTAL_QUESTIONS = 10;
    private ArrayList<String> answers = new ArrayList<>();

    private String[] questions = {
            "부산에서 계속 거주할 의향이 있습니까?",
            "부산을 떠나고 싶은 주요 이유는 무엇입니까?",
            "현재 직업에 만족하십니까?",
            "임금(급여)에 만족하십니까?",
            "고용 안정성과 소득 중 어느 쪽이 더 중요합니까?",
            "현재 직업과 전공이 일치합니까?",
            "1년 내 이직 의사가 있습니까?",
            "부산에서 일하고 싶은 기업 유형은?",
            "부산 청년 정책에 대해 알고 계십니까?",
            "부산에 남기 위한 가장 필요한 정책은 무엇이라고 생각하십니까?"
    };

    private String[][] choices = {
            {"예", "아니오"},
            {"일자리", "임금", "주거", "문화", "기타"},
            {"매우 만족", "만족", "보통", "불만족", "매우 불만족"},
            {"매우 만족", "만족", "보통", "불만족", "매우 불만족"},
            {"고용 안정성", "소득"},
            {"예", "아니오"},
            {"예", "아니오"},
            {"공공기관", "대기업", "창업", "중소기업"},
            {"예", "아니오"},
            {"일자리 확대", "주거 지원", "임금 인상", "문화 인프라", "기타"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 첫 번째 설문 프래그먼트 표시
        showSurveyFragment();
    }

    private void showSurveyFragment() {
        if (currentQuestion < TOTAL_QUESTIONS) {
            SurveyFragment fragment = SurveyFragment.newInstance(
                    currentQuestion,
                    questions[currentQuestion],
                    choices[currentQuestion]
            );
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            // 결과 프래그먼트로 이동
            ResultFragment resultFragment = ResultFragment.newInstance(answers, questions);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, resultFragment)
                    .commit();
        }
    }

    @Override
    public void onAnswerSelected(int questionIndex, String answer) {
        if (answers.size() > questionIndex) {
            answers.set(questionIndex, answer);
        } else {
            answers.add(answer);
        }
        currentQuestion++;
        showSurveyFragment();
    }
}
