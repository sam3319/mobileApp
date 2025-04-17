package com.example.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Application;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.example.todolist.ALARM")) {
            int timetableId = intent.getIntExtra("timetable_id", -1);
            if (timetableId != -1) {
                TimetableViewModel viewModel = new TimetableViewModel((Application) context.getApplicationContext());
                viewModel.getAllTimetables().observeForever(timetables -> {
                    for (Timetable timetable : timetables) {
                        if (timetable.getId() == timetableId) {
                            new NotificationHelper(context).showNotification(timetable);
                            break;
                        }
                    }
                });
            }
        }
    }
} 