package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {
    private List<Timetable> timetableList;

    public TimetableAdapter(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        Timetable timetable = timetableList.get(position);
        holder.subjectTextView.setText(timetable.getSubject());
        holder.timeTextView.setText(timetable.getStartTime() + " - " + timetable.getEndTime());
        holder.locationTextView.setText(timetable.getLocation());
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    static class TimetableViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;
        TextView timeTextView;
        TextView locationTextView;

        TimetableViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.text_subject);
            timeTextView = itemView.findViewById(R.id.text_time);
            locationTextView = itemView.findViewById(R.id.text_location);
        }
    }
} 