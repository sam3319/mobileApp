package com.example.chapter08codingchallenge;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {

    private static final String ARG_QUIZ = "quiz";
    private static final String ARG_INDEX = "index";

    private Quiz quiz;
    private int quizIndex;
    private int selectedOptionIndex = -1;
    private OnQuizAnsweredListener listener;

    public interface OnQuizAnsweredListener {
        void onNextQuiz(int currentIndex, int selectedOptionIndex);
    }

    public static QuizFragment newInstance(Quiz quiz, int index) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ, quiz);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quiz = (Quiz) getArguments().getSerializable(ARG_QUIZ);
            quizIndex = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView questionTextView = view.findViewById(R.id.questionTextView);
        RadioGroup optionsRadioGroup = view.findViewById(R.id.optionsRadioGroup);
        Button nextButton = view.findViewById(R.id.nextButton);

        // 문제 번호와 질문 설정
        questionTextView.setText("문제 " + (quizIndex + 1) + ": " + quiz.getQuestion());

        // 라디오 버튼에 선택지 설정
        for (int i = 0; i < quiz.getOptions().length; i++) {
            RadioButton radioButton = (RadioButton) optionsRadioGroup.getChildAt(i);
            radioButton.setText(quiz.getOptions()[i]);
        }

        // 라디오 그룹 리스너 설정
        // QuizFragment.java의 onCheckedChanged 메서드 수정
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // if-else 방식으로 변경
                if (checkedId == R.id.option1) {
                    selectedOptionIndex = 0;
                } else if (checkedId == R.id.option2) {
                    selectedOptionIndex = 1;
                } else if (checkedId == R.id.option3) {
                    selectedOptionIndex = 2;
                } else if (checkedId == R.id.option4) {
                    selectedOptionIndex = 3;
                }
            }
        });


        // 다음 버튼 클릭 리스너
        nextButton.setText(quizIndex == 4 ? "결과 보기" : "다음 문제");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionIndex != -1) {
                    listener.onNextQuiz(quizIndex, selectedOptionIndex);
                } else {
                    // 선택하지 않았을 경우 메시지 표시
                    // 실제 앱에서는 Toast 메시지 등으로 알림
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnQuizAnsweredListener) {
            listener = (OnQuizAnsweredListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnQuizAnsweredListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
