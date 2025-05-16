package com.example.chapter08codingchallenge;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class QuizStartActivity extends AppCompatActivity implements QuizFragment.OnQuizAnsweredListener {

    private List<Quiz> quizList;
    private int currentQuizIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        initializeQuizzes();
        showQuizFragment(currentQuizIndex);
    }

    private void initializeQuizzes() {
        quizList = new ArrayList<>();

        // 문제 1
        quizList.add(new Quiz(
                "안드로이드 프로그래밍에 가장 많이 사용되는 언어는?",
                new String[]{"C언어", "Python", "C++언어", "자바 언어"},
                3
        ));

        // 문제 2
        quizList.add(new Quiz(
                "안드로이드의 4가지 컴포넌트가 아닌 것은?",
                new String[]{"서비스", "컨텐트 제공자", "액티비티", "프로세스"},
                3
        ));

        // 문제 3
        quizList.add(new Quiz(
                "안드로이드 앱의 레이아웃 파일 확장자는?",
                new String[]{"java", "xml", "html", "kt"},
                1
        ));

        // 문제 4
        quizList.add(new Quiz(
                "안드로이드 스튜디오의 기본 빌드 시스템은?",
                new String[]{"Maven", "Ant", "Gradle", "npm"},
                2
        ));

        // 문제 5
        quizList.add(new Quiz(
                "안드로이드에서 UI 스레드를 다른 말로 무엇이라고 하는가?",
                new String[]{"Worker 스레드", "Background 스레드", "Main 스레드", "Service 스레드"},
                2
        ));
    }

    private void showQuizFragment(int quizIndex) {
        if (quizIndex < quizList.size()) {
            QuizFragment quizFragment = QuizFragment.newInstance(quizList.get(quizIndex), quizIndex);
            replaceFragment(quizFragment);
        } else {
            // 모든 퀴즈가 끝났을 때 결과 화면으로 이동
            ResultFragment resultFragment = ResultFragment.newInstance(quizList);
            replaceFragment(resultFragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onNextQuiz(int currentIndex, int selectedOptionIndex) {
        // 사용자가 선택한 답변 저장
        quizList.get(currentIndex).setUserSelectedIndex(selectedOptionIndex);

        // 다음 문제로 이동
        currentQuizIndex = currentIndex + 1;
        showQuizFragment(currentQuizIndex);
    }
}
