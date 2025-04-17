package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GradesFragment extends Fragment implements GradeAdapter.OnGradeLongClickListener, AddEditGradeDialog.OnGradeSaveListener {
    private GradeViewModel viewModel;
    private GradeAdapter adapter;
    private TextView tvAverageGrade;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GradeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        tvAverageGrade = view.findViewById(R.id.tvAverageGrade);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GradeAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showAddGradeDialog());

        viewModel.getGrades().observe(getViewLifecycleOwner(), grades -> {
            adapter.setGrades(grades);
        });

        viewModel.getAverageGrade().observe(getViewLifecycleOwner(), average -> {
            DecimalFormat df = new DecimalFormat("#.##");
            tvAverageGrade.setText(getString(R.string.average_grade_format, df.format(average)));
        });

        return view;
    }

    @Override
    public void onGradeLongClick(Grade grade) {
        viewModel.selectGrade(grade);
        showEditGradeDialog(grade);
    }

    private void showAddGradeDialog() {
        AddEditGradeDialog dialog = AddEditGradeDialog.newInstance(null);
        dialog.show(getChildFragmentManager(), "AddEditGradeDialog");
    }

    private void showEditGradeDialog(Grade grade) {
        AddEditGradeDialog dialog = AddEditGradeDialog.newInstance(grade);
        dialog.show(getChildFragmentManager(), "AddEditGradeDialog");
    }

    @Override
    public void onGradeSave(Grade grade) {
        if (grade.getId() == null) {
            viewModel.addGrade(grade);
        } else {
            viewModel.updateGrade(grade);
        }
        viewModel.clearSelectedGrade();
    }
} 