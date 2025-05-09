package com.example.todolist;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmHelper {
    private static final String CHANNEL_ID = "todo_alarm_channel";
    private static final String CHANNEL_NAME = "Todo Alarms";

    private Context context;
    private AlarmManager alarmManager;

    public AlarmHelper(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Android 8.0 이상에서는 알림 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("할 일 알림을 위한 채널");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setAlarm(TodoItem todoItem) {
        if (!todoItem.hasAlarm()) {
            return; // 알람 시간이 설정되지 않은 경우
        }

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("TASK_ID", todoItem.getId());
        intent.putExtra("TASK_TITLE", todoItem.getTask());

        // 동일한 ID에 대해 이전 알람을 업데이트하기 위해 ID를 사용
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) todoItem.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 알람 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    todoItem.getAlarmTime(),
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    todoItem.getAlarmTime(),
                    pendingIntent
            );
        }
    }

    public void cancelAlarm(TodoItem todoItem) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) todoItem.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 알람 취소
        alarmManager.cancel(pendingIntent);
    }
}
