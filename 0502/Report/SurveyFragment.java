package com.example.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SurveyFragment extends Fragment {
    private static final String ARG_INDEX = "index";
    private static final String ARG_QUESTION = "question";
    private static final String ARG_CHOICES = "choices";

    private int questionIndex;
    private String questionText;
    private String[] choiceArray;

    public interface OnSurveyAnswerListener {
        void onAnswerSelected(int questionIndex, String answer);
    }

    public SurveyFragment() {}

    public static SurveyFragment newInstance(int index, String question, String[] choices) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        args.putString(ARG_QUESTION, question);
        args.putStringArray(ARG_CHOICES, choices);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionIndex = getArguments().getInt(ARG_INDEX);
            questionText = getArguments().getString(ARG_QUESTION);
            choiceArray = getArguments().getStringArray(ARG_CHOICES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        TextView questionView = view.findViewById(R.id.question_text);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        Button nextBtn = view.findViewById(R.id.next_button);

        questionView.setText(questionText);
        radioGroup.removeAllViews();

        for (String choice : choiceArray) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(choice);
            radioGroup.addView(rb);
        }

        nextBtn.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadio = radioGroup.findViewById(selectedId);
                String answer = selectedRadio.getText().toString();
                if (getActivity() instanceof OnSurveyAnswerListener) {
                    ((OnSurveyAnswerListener) getActivity()).onAnswerSelected(questionIndex, answer);
                }
            } else {
                Toast.makeText(getContext(), "답변을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

