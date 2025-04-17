package com.example.todolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class AddEditGradeDialog extends DialogFragment {
    private static final String ARG_GRADE = "grade";
    private Grade grade;
    private OnGradeSaveListener listener;
    private TextInputEditText etSubject;
    private TextInputEditText etCredit;
    private TextInputEditText etSemester;
    private AutoCompleteTextView spinnerGrade;

    public interface OnGradeSaveListener {
        void onGradeSave(Grade grade);
    }

    public static AddEditGradeDialog newInstance(Grade grade) {
        AddEditGradeDialog dialog = new AddEditGradeDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GRADE, grade);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnGradeSaveListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnGradeSaveListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_grade, null);

        etSubject = view.findViewById(R.id.etSubject);
        etCredit = view.findViewById(R.id.etCredit);
        etSemester = view.findViewById(R.id.etSemester);
        spinnerGrade = view.findViewById(R.id.spinnerGrade);

        setupGradeSpinner();

        if (getArguments() != null) {
            grade = (Grade) getArguments().getSerializable(ARG_GRADE);
            if (grade != null) {
                etSubject.setText(grade.getSubject());
                etCredit.setText(String.valueOf(grade.getCredit()));
                etSemester.setText(grade.getSemester());
                spinnerGrade.setText(grade.getGrade(), false);
            }
        }

        builder.setView(view)
                .setTitle(grade == null ? R.string.add_grade : R.string.edit_grade)
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (validateInput()) {
                    saveGrade();
                    dialog.dismiss();
                }
            });
        });

        return dialog;
    }

    private void setupGradeSpinner() {
        String[] grades = getResources().getStringArray(R.array.grades);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                grades
        );
        spinnerGrade.setAdapter(adapter);
    }

    private boolean validateInput() {
        String subject = etSubject.getText().toString().trim();
        String credit = etCredit.getText().toString().trim();
        String semester = etSemester.getText().toString().trim();
        String grade = spinnerGrade.getText().toString().trim();

        if (subject.isEmpty()) {
            etSubject.setError(getString(R.string.error_subject_required));
            return false;
        }

        if (credit.isEmpty()) {
            etCredit.setError(getString(R.string.error_credit_required));
            return false;
        }

        if (semester.isEmpty()) {
            etSemester.setError(getString(R.string.error_semester_required));
            return false;
        }

        if (grade.isEmpty()) {
            spinnerGrade.setError(getString(R.string.error_grade_required));
            return false;
        }

        return true;
    }

    private void saveGrade() {
        String subject = etSubject.getText().toString().trim();
        int credit = Integer.parseInt(etCredit.getText().toString().trim());
        String semester = etSemester.getText().toString().trim();
        String grade = spinnerGrade.getText().toString().trim();

        if (this.grade == null) {
            this.grade = new Grade(subject, grade, semester, credit);
        } else {
            this.grade.setSubject(subject);
            this.grade.setGrade(grade);
            this.grade.setSemester(semester);
            this.grade.setCredit(credit);
        }

        listener.onGradeSave(this.grade);
    }
} 