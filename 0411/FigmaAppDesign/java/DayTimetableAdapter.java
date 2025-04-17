package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class DayTimetableAdapter extends RecyclerView.Adapter<DayTimetableAdapter.DayTimetableViewHolder> {
    private final List<Timetable> timetableList;

    public DayTimetableAdapter(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    @NonNull
    @Override
    public DayTimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_timetable, parent, false);
        return new DayTimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayTimetableViewHolder holder, int position) {
        Timetable timetable = timetableList.get(position);
        holder.bind(timetable);
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    static class DayTimetableViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final TextView textSubject;
        private final TextView textTime;
        private final TextView textLocation;
        private final TextView textProfessor;

        public DayTimetableViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            textSubject = itemView.findViewById(R.id.text_subject);
            textTime = itemView.findViewById(R.id.text_time);
            textLocation = itemView.findViewById(R.id.text_location);
            textProfessor = itemView.findViewById(R.id.text_professor);
        }

        public void bind(Timetable timetable) {
            textSubject.setText(timetable.getSubject());
            textTime.setText(String.format("%d교시 - %d교시", timetable.getStartTime(), timetable.getEndTime()));
            textLocation.setText(timetable.getLocation());
            textProfessor.setText(timetable.getProfessor());
        }
    }
} 