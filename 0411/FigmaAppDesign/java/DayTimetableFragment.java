package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DayTimetableFragment extends Fragment {
    private static final String ARG_DAY_OF_WEEK = "day_of_week";
    private int dayOfWeek;
    private List<Timetable> timetableList = new ArrayList<>();
    private DayTimetableAdapter adapter;

    public static DayTimetableFragment newInstance(int dayOfWeek) {
        DayTimetableFragment fragment = new DayTimetableFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_OF_WEEK, dayOfWeek);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayOfWeek = getArguments().getInt(ARG_DAY_OF_WEEK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_day_timetable);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        adapter = new DayTimetableAdapter(timetableList);
        recyclerView.setAdapter(adapter);
    }

    public void updateTimetables(List<Timetable> timetables) {
        timetableList.clear();
        for (Timetable timetable : timetables) {
            if (timetable.getDayOfWeek() == dayOfWeek) {
                timetableList.add(timetable);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
} 