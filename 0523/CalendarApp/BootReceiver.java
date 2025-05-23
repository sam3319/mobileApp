package com.example.calendarapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) ||
                Intent.ACTION_MY_PACKAGE_REPLACED.equals(intent.getAction())) {

            // 부팅 완료 후 모든 알람 재설정
            restoreAlarms(context);
        }
    }

    private void restoreAlarms(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<CalendarEvent> events = databaseHelper.getAllEvents();

        AlarmHelper alarmHelper = new AlarmHelper(context);
        for (CalendarEvent event : events) {
            // 현재 시간보다 미래의 일정만 다시 설정
            if (event.getTimestamp() > System.currentTimeMillis()) {
                alarmHelper.setAlarm(event);
            }
        }
    }
}
