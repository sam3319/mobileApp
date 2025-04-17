package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TimetableFragment extends Fragment {
    private TimetableViewModel viewModel;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private List<DayTimetableFragment> dayFragments = new ArrayList<>();
    private TimetableAdapter adapter;
    private List<Timetable> timetableList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_days);

        // 요일별 프래그먼트 초기화
        for (int i = 1; i <= 5; i++) {
            dayFragments.add(DayTimetableFragment.newInstance(i));
        }

        viewPager.setAdapter(new TimetablePagerAdapter(requireActivity(), dayFragments));
        new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> tab.setText(getDayName(position + 1))
        ).attach();

        // FAB 설정
        FloatingActionButton fab = view.findViewById(R.id.fab_add_timetable);
        fab.setOnClickListener(v -> showAddDialog());

        // 시간표 데이터 관찰
        viewModel = new ViewModelProvider(this).get(TimetableViewModel.class);
        viewModel.getAllTimetables().observe(getViewLifecycleOwner(), timetables -> {
            for (DayTimetableFragment fragment : dayFragments) {
                fragment.updateTimetables(timetables);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        timetableList = new ArrayList<>();
        adapter = new TimetableAdapter(timetableList);
        viewModel.getAllTimetables().observe(getViewLifecycleOwner(), timetables -> {
            timetableList.clear();
            timetableList.addAll(timetables);
            adapter.notifyDataSetChanged();
        });
    }

    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return "월요일";
            case 2: return "화요일";
            case 3: return "수요일";
            case 4: return "목요일";
            case 5: return "금요일";
            default: return "";
        }
    }

    private void showAddDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_timetable, null);
        TextInputEditText editSubject = dialogView.findViewById(R.id.edit_subject);
        TextInputEditText editProfessor = dialogView.findViewById(R.id.edit_professor);
        TextInputEditText editDayOfWeek = dialogView.findViewById(R.id.edit_day_of_week);
        TextInputEditText editStartTime = dialogView.findViewById(R.id.edit_start_time);
        TextInputEditText editEndTime = dialogView.findViewById(R.id.edit_end_time);
        TextInputEditText editLocation = dialogView.findViewById(R.id.edit_location);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.add_timetable)
                .setView(dialogView)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String subject = editSubject.getText().toString();
                    String professor = editProfessor.getText().toString();
                    String dayOfWeekStr = editDayOfWeek.getText().toString();
                    String startTimeStr = editStartTime.getText().toString();
                    String endTimeStr = editEndTime.getText().toString();
                    String location = editLocation.getText().toString();

                    if (subject.isEmpty() || professor.isEmpty() || dayOfWeekStr.isEmpty() || 
                        startTimeStr.isEmpty() || endTimeStr.isEmpty() || location.isEmpty()) {
                        Toast.makeText(requireContext(), "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int dayOfWeek = Integer.parseInt(dayOfWeekStr);
                        String startTime = startTimeStr;
                        String endTime = endTimeStr;

                        if (dayOfWeek < 1 || dayOfWeek > 5) {
                            Toast.makeText(requireContext(), "요일은 1~5 사이의 숫자여야 합니다", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Timetable timetable = new Timetable(subject, professor, location, dayOfWeek, startTime, endTime);
                        viewModel.insert(timetable);
                    } catch (NumberFormatException e) {
                        Toast.makeText(requireContext(), "숫자를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
} 