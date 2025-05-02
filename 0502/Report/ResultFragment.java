package com.example.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import android.graphics.Color;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
    private static final String ARG_ANSWERS = "answers";
    private static final String ARG_QUESTIONS = "questions";
    private ArrayList<String> answers;
    private String[] questions;

    public ResultFragment() {}

    public static ResultFragment newInstance(ArrayList<String> answers, String[] questions) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_ANSWERS, answers);
        args.putStringArray(ARG_QUESTIONS, questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answers = getArguments().getStringArrayList(ARG_ANSWERS);
            questions = getArguments().getStringArray(ARG_QUESTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        TextView resultText = view.findViewById(R.id.result_text);
        BarChart barChart = view.findViewById(R.id.bar_chart);

        // 결과 요약 텍스트
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < questions.length; i++) {
            sb.append((i+1) + ". " + questions[i] + "\n");
            sb.append("   → " + answers.get(i) + "\n\n");
        }
        resultText.setText(sb.toString());

        // 예시: 1번 문항(부산 거주 의향) 결과 그래프
        int yesCount = 0, noCount = 0;
        if (answers.get(0).equals("예")) yesCount++;
        else noCount++;

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, yesCount));
        entries.add(new BarEntry(1, noCount));

        BarDataSet dataSet = new BarDataSet(entries, "부산 거주 의향");
        dataSet.setColors(Color.BLUE, Color.RED);
        BarData data = new BarData(dataSet);

        barChart.setData(data);
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if ((int) value == 0) return "예";
                else if ((int) value == 1) return "아니오";
                else return "";
            }
        });

        barChart.invalidate(); // 그래프 갱신

        return view;
    }

}
