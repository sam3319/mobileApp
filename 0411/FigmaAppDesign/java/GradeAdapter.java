package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    private List<Grade> grades;
    private final OnGradeLongClickListener onGradeLongClickListener;

    public interface OnGradeLongClickListener {
        void onGradeLongClick(Grade grade);
    }

    public GradeAdapter(List<Grade> grades, OnGradeLongClickListener listener) {
        this.grades = grades;
        this.onGradeLongClickListener = listener;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade grade = grades.get(position);
        holder.bind(grade);
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    class GradeViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSubject;
        private final TextView tvScore;
        private final TextView tvGrade;

        GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvGrade = itemView.findViewById(R.id.tvGrade);

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onGradeLongClickListener.onGradeLongClick(grades.get(position));
                    return true;
                }
                return false;
            });
        }

        void bind(Grade grade) {
            tvSubject.setText(grade.getSubject());
            tvScore.setText(String.valueOf(grade.getScore()));
            tvGrade.setText(grade.getGrade());
        }
    }
} 