package com.example.todolist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class TodoFragment extends Fragment implements TodoAdapter.OnTodoClickListener, AddEditTodoDialog.OnTodoSaveListener {
    private TodoViewModel viewModel;
    private TodoAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TodoAdapter(requireContext(), this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showAddTodoDialog());

        viewModel.getTodos().observe(getViewLifecycleOwner(), todos -> {
            adapter.setTodos(todos);
        });
    }

    private void showAddTodoDialog() {
        AddEditTodoDialog dialog = AddEditTodoDialog.newInstance(null);
        dialog.show(getChildFragmentManager(), "AddEditTodoDialog");
    }

    private void showEditTodoDialog(Todo todo) {
        AddEditTodoDialog dialog = AddEditTodoDialog.newInstance(todo);
        dialog.show(getChildFragmentManager(), "AddEditTodoDialog");
    }

    @Override
    public void onTodoClick(Todo todo) {
        viewModel.selectTodo(todo);
        showEditTodoDialog(todo);
    }

    @Override
    public void onTodoDelete(Todo todo) {
        viewModel.deleteTodo(todo);
    }

    @Override
    public void onTodoSave(Todo todo) {
        if (todo.getId() == null) {
            viewModel.addTodo(todo);
        } else {
            viewModel.updateTodo(todo);
        }
        viewModel.clearSelectedTodo();
    }

    private void showDatePicker(TextInputEditText etDueDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            requireContext(),
            (view, selectedYear, selectedMonth, selectedDay) -> {
                String formattedDate = String.format("%04d-%02d-%02d", 
                    selectedYear, selectedMonth + 1, selectedDay);
                etDueDate.setText(formattedDate);
            },
            year, month, day
        );
        datePickerDialog.show();
    }
} 