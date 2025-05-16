package com.example.chapter08codingchallenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ResultFragment extends Fragment {

    private static final String ARG_QUIZ_LIST = "quiz_list";
    private List<Quiz> quizList;

    public static ResultFragment newInstance(List<Quiz> quizList) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ_LIST, (java.io.Serializable) quizList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizList = (List<Quiz>) getArguments().getSerializable(ARG_QUIZ_LIST);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView resultTextView = view.findViewById(R.id.resultTextView);
        Button finishButton = view.findViewById(R.id.finishButton);

        // 결과 계산
        int correctCount = 0;
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("퀴즈 결과\n\n");

        for (int i = 0; i < quizList.size(); i++) {
            Quiz quiz = quizList.get(i);
            boolean isCorrect = quiz.isCorrect();

            if (isCorrect) {
                correctCount++;
            }

            resultBuilder.append("문제 ").append(i + 1).append(": ");
            resultBuilder.append(isCorrect ? "정답" : "오답");
            resultBuilder.append("\n");
            resultBuilder.append("- 질문: ").append(quiz.getQuestion()).append("\n");
            resultBuilder.append("- 선택한 답: ").append(quiz.getOptions()[quiz.getUserSelectedIndex()]).append("\n");
            resultBuilder.append("- 정답: ").append(quiz.getOptions()[quiz.getCorrectAnswerIndex()]).append("\n\n");
        }

        resultBuilder.append("총 ").append(quizList.size()).append("문제 중 ").append(correctCount).append("개 맞았습니다.");
        resultTextView.setText(resultBuilder.toString());

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
    }
}
