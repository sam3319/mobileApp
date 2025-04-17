package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class TimetablePagerAdapter extends FragmentStateAdapter {
    private final List<DayTimetableFragment> fragments;

    public TimetablePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<DayTimetableFragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
} 