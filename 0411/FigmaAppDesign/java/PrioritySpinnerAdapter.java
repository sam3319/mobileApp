package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PrioritySpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] priorities;
    private int[] colors;

    public PrioritySpinnerAdapter(@NonNull Context context, int resource, String[] priorities, int[] colors) {
        super(context, resource, priorities);
        this.context = context;
        this.priorities = priorities;
        this.colors = colors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(priorities[position]);
        textView.setTextColor(colors[position]);

        return convertView;
    }
} 