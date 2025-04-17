package com.example.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class TimetableAlarmManager {
    private final Context context;
    private final AlarmManager alarmManager;

    public TimetableAlarmManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(Timetable timetable) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("com.example.todolist.ALARM");
        intent.putExtra("timetable_id", timetable.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context,
            timetable.getId().intValue(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, timetable.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, getHourFromTime(timetable.getStartTime()));
        calendar.set(Calendar.MINUTE, getMinuteFromTime(timetable.getStartTime()));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            pendingIntent
        );
    }

    public void cancelAlarm(Timetable timetable) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("com.example.todolist.ALARM");
        intent.putExtra("timetable_id", timetable.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context,
            timetable.getId().intValue(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.cancel(pendingIntent);
    }

    private int getHourFromTime(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]);
    }

    private int getMinuteFromTime(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[1]);
    }
} 