package com.example.todolist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<TodoItem> todoItems;
    private OnTodoItemClickListener listener;

    public interface OnTodoItemClickListener {
        void onDeleteClick(int position);
        void onCheckBoxClick(int position, boolean isChecked);
    }

    public TodoAdapter(List<TodoItem> todoItems, OnTodoItemClickListener listener) {
        this.todoItems = todoItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem currentItem = todoItems.get(position);

        // CheckBox 리스너를 일시적으로 제거 (중복 호출 방지)
        holder.checkBoxTask.setOnCheckedChangeListener(null);

        // 데이터 설정
        holder.checkBoxTask.setText(currentItem.getTask());
        holder.checkBoxTask.setChecked(currentItem.isCompleted());

        // 완료된 항목에 취소선 추가
        if (currentItem.isCompleted()) {
            holder.checkBoxTask.setPaintFlags(holder.checkBoxTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.checkBoxTask.setPaintFlags(holder.checkBoxTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // 리스너 다시 설정
        final int currentPosition = position;
        holder.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onCheckBoxClick(currentPosition, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        ImageButton buttonDelete;

        public TodoViewHolder(@NonNull View itemView, final OnTodoItemClickListener listener) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onDeleteClick(position);
                }
            });
        }
    }
}
