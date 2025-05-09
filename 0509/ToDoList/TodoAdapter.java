package com.example.todolist;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<TodoItem> todoItems;
    private OnTodoItemClickListener listener;
    private Context context;
    private AlarmHelper alarmHelper;

    public interface OnTodoItemClickListener {
        void onDeleteClick(int position);
        void onCheckBoxClick(int position, boolean isChecked);
        void onAlarmSet(int position, long timeInMillis);
    }

    public TodoAdapter(Context context, List<TodoItem> todoItems, OnTodoItemClickListener listener) {
        this.context = context;
        this.todoItems = todoItems;
        this.listener = listener;
        this.alarmHelper = new AlarmHelper(context);
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

        // 알람 시간 표시
        if (currentItem.hasAlarm()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String formattedTime = sdf.format(new Date(currentItem.getAlarmTime()));
            holder.textViewAlarmTime.setText("알람: " + formattedTime);
            holder.textViewAlarmTime.setVisibility(View.VISIBLE);
        } else {
            holder.textViewAlarmTime.setVisibility(View.GONE);
        }

        // 리스너 다시 설정
        final int currentPosition = position;
        holder.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onCheckBoxClick(currentPosition, isChecked);
            }
        });

        // 알람 설정 버튼 리스너
        holder.buttonSetAlarm.setOnClickListener(v -> {
            showTimePickerDialog(currentPosition);
        });
    }

    private void showTimePickerDialog(int position) {
        Calendar calendar = Calendar.getInstance();

        // 이미 알람이 설정되어 있으면 해당 시간으로 초기화
        TodoItem item = todoItems.get(position);
        if (item.hasAlarm()) {
            calendar.setTimeInMillis(item.getAlarmTime());
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                (view, hourOfDay, selectedMinute) -> {
                    Calendar selectedTime = Calendar.getInstance();
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedTime.set(Calendar.MINUTE, selectedMinute);
                    selectedTime.set(Calendar.SECOND, 0);

                    // 현재 시간보다 이전이면 다음 날로 설정
                    if (selectedTime.getTimeInMillis() <= System.currentTimeMillis()) {
                        selectedTime.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    if (listener != null) {
                        listener.onAlarmSet(position, selectedTime.getTimeInMillis());
                    }
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        ImageButton buttonDelete;
        ImageButton buttonSetAlarm;
        TextView textViewAlarmTime;

        public TodoViewHolder(@NonNull View itemView, final OnTodoItemClickListener listener) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonSetAlarm = itemView.findViewById(R.id.buttonSetAlarm);
            textViewAlarmTime = itemView.findViewById(R.id.textViewAlarmTime);

            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onDeleteClick(position);
                }
            });
        }
    }
}
