package com.example.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditTodoDialog extends DialogFragment {
    private static final String ARG_TODO = "todo";
    private Todo todo;
    private OnTodoSaveListener listener;
    private TextInputEditText etTitle;
    private TextInputEditText etDescription;
    private TextInputEditText etDueDate;
    private AutoCompleteTextView spinnerPriority;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    public interface OnTodoSaveListener {
        void onTodoSave(Todo todo);
    }

    public static AddEditTodoDialog newInstance(Todo todo) {
        AddEditTodoDialog dialog = new AddEditTodoDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO, todo);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnTodoSaveListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTodoSaveListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_todo, null);

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etDueDate = view.findViewById(R.id.etDueDate);
        spinnerPriority = view.findViewById(R.id.spinnerPriority);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        setupPrioritySpinner();
        setupDueDatePicker();

        if (getArguments() != null) {
            todo = (Todo) getArguments().getSerializable(ARG_TODO);
            if (todo != null) {
                etTitle.setText(todo.getTitle());
                etDescription.setText(todo.getDescription());
                etDueDate.setText(todo.getDueDate());
                spinnerPriority.setText(todo.getPriority(), false);
            }
        }

        builder.setView(view)
                .setTitle(todo == null ? R.string.add_todo : R.string.edit_todo)
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (validateInput()) {
                    saveTodo();
                    dialog.dismiss();
                }
            });
        });

        return dialog;
    }

    private void setupPrioritySpinner() {
        String[] priorities = getResources().getStringArray(R.array.priorities);
        int[] colors = {
                getResources().getColor(R.color.priority_high),
                getResources().getColor(R.color.priority_medium),
                getResources().getColor(R.color.priority_low)
        };

        PrioritySpinnerAdapter adapter = new PrioritySpinnerAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                priorities,
                colors
        );
        spinnerPriority.setAdapter(adapter);
    }

    private void setupDueDatePicker() {
        etDueDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        etDueDate.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
    }

    private boolean validateInput() {
        String title = etTitle.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();
        String priority = spinnerPriority.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError(getString(R.string.error_title_required));
            return false;
        }

        if (dueDate.isEmpty()) {
            etDueDate.setError(getString(R.string.error_due_date_required));
            return false;
        }

        if (priority.isEmpty()) {
            spinnerPriority.setError(getString(R.string.error_priority_required));
            return false;
        }

        return true;
    }

    private void saveTodo() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();
        String priority = spinnerPriority.getText().toString().trim();

        if (todo == null) {
            todo = new Todo(title, description, dueDate, priority);
        } else {
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setDueDate(dueDate);
            todo.setPriority(priority);
        }

        listener.onTodoSave(todo);
    }
} 