package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todos;
    private OnTodoClickListener listener;
    private Context context;

    public interface OnTodoClickListener {
        void onTodoClick(Todo todo);
        void onTodoDelete(Todo todo);
    }

    public TodoAdapter(Context context, OnTodoClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.todos = new ArrayList<>();
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public void addTodo(Todo todo) {
        todos.add(todo);
        notifyItemInserted(todos.size() - 1);
    }

    public void updateTodo(Todo todo) {
        int position = findTodoPosition(todo.getId());
        if (position != -1) {
            todos.set(position, todo);
            notifyItemChanged(position);
        }
    }

    public void deleteTodo(Todo todo) {
        int position = findTodoPosition(todo.getId());
        if (position != -1) {
            todos.remove(position);
            notifyItemRemoved(position);
        }
    }

    private int findTodoPosition(String id) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDueDate;
        private TextView tvPriority;
        private ImageButton btnDelete;

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(Todo todo) {
            tvTitle.setText(todo.getTitle());
            tvDescription.setText(todo.getDescription());
            tvDueDate.setText(todo.getDueDate());
            tvPriority.setText(todo.getPriority());

            int priorityColor = getPriorityColor(todo.getPriority());
            tvPriority.setBackgroundColor(priorityColor);

            itemView.setOnClickListener(v -> listener.onTodoClick(todo));
            btnDelete.setOnClickListener(v -> listener.onTodoDelete(todo));
        }

        private int getPriorityColor(String priority) {
            if (priority.equals(context.getString(R.string.priority_high))) {
                return ContextCompat.getColor(context, R.color.priority_high);
            } else if (priority.equals(context.getString(R.string.priority_medium))) {
                return ContextCompat.getColor(context, R.color.priority_medium);
            } else {
                return ContextCompat.getColor(context, R.color.priority_low);
            }
        }
    }
} 