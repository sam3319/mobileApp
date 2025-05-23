package com.example.calendarapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.app.NotificationManagerCompat;

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int eventId = intent.getIntExtra("event_id", 0);

        if ("ACTION_COMPLETE".equals(action)) {
            // 알림 완료 처리
            NotificationManagerCompat.from(context).cancel(eventId);
            Toast.makeText(context, "일정 완료!", Toast.LENGTH_SHORT).show();

        } else if ("ACTION_SNOOZE".equals(action)) {
            // 5분 후 다시 알림 설정
            String eventTitle = intent.getStringExtra("event_title");
            String eventDescription = intent.getStringExtra("event_description");

            // 현재 알림 취소
            NotificationManagerCompat.from(context).cancel(eventId);

            // 5분 후 알림 재설정
            scheduleSnoozeAlarm(context, eventId, eventTitle, eventDescription);
            Toast.makeText(context, "5분 후 다시 알림됩니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleSnoozeAlarm(Context context, int eventId, String title, String description) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("event_id", eventId);
        intent.putExtra("event_title", title);
        intent.putExtra("event_description", description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                eventId + 10000, // 다른 ID 사용
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long snoozeTime = System.currentTimeMillis() + (5 * 60 * 1000); // 5분 후
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, snoozeTime, pendingIntent);
        }
    }
}
