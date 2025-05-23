package com.example.calendarapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "calendar_notifications";
    private static final String TAG = "AlarmReceiver";
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "AlarmReceiver onReceive called");

        int eventId = intent.getIntExtra("event_id", 0);
        String eventTitle = intent.getStringExtra("event_title");
        String eventDescription = intent.getStringExtra("event_description");

        Log.d(TAG, "Event ID: " + eventId + ", Title: " + eventTitle);

        createNotificationChannel(context);
        showNotification(context, eventId, eventTitle, eventDescription);
    }

    private void createNotificationChannel(Context context) {
        // Android 8.0 (API 26) 이상에서만 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "캘린더 알림";
            String description = "일정 알림을 위한 채널";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setShowBadge(true);

            // 시스템에 채널 등록
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                Log.d(TAG, "Notification channel created");
            }
        }
    }

    private void showNotification(Context context, int eventId, String eventTitle, String eventDescription) {
        // 알림 클릭 시 MainActivity로 이동하는 Intent 생성
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // 알림 내용 설정
        String contentText = (eventDescription != null && !eventDescription.isEmpty()) ?
                eventDescription : "일정 시간입니다!";

        // NotificationCompat.Builder를 사용하여 알림 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("📅 " + eventTitle)
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(contentText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVibrate(new long[]{0, 500, 200, 500});

        // 알림 액션 버튼 추가 (완료, 5분 후 다시 알림)
        addNotificationActions(context, builder, eventId, eventTitle, eventDescription);

        // 알림 표시
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.notify(eventId, builder.build());
            Log.d(TAG, "Notification sent successfully");
        } catch (SecurityException e) {
            Log.e(TAG, "Failed to send notification: " + e.getMessage());
        }
    }

    private void addNotificationActions(Context context, NotificationCompat.Builder builder,
                                        int eventId, String eventTitle, String eventDescription) {
        // "완료" 액션
        Intent completeIntent = new Intent(context, NotificationActionReceiver.class);
        completeIntent.setAction("ACTION_COMPLETE");
        completeIntent.putExtra("event_id", eventId);

        PendingIntent completePendingIntent = PendingIntent.getBroadcast(
                context,
                eventId * 10 + 1,
                completeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // "5분 후 다시 알림" 액션
        Intent snoozeIntent = new Intent(context, NotificationActionReceiver.class);
        snoozeIntent.setAction("ACTION_SNOOZE");
        snoozeIntent.putExtra("event_id", eventId);
        snoozeIntent.putExtra("event_title", eventTitle);
        snoozeIntent.putExtra("event_description", eventDescription);

        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(
                context,
                eventId * 10 + 2,
                snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "완료", completePendingIntent)
                .addAction(android.R.drawable.ic_menu_recent_history, "5분 후", snoozePendingIntent);
    }
}
