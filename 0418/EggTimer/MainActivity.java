package com.example.eggtimernew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    String[] reqestPermission={ "android.permission.POST_NOTIFICATIONS" };
    public static int REQUEST_P0STNOTIFICATIONS = 10023;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit);
        createNotificationChannel();
        ActivityCompat.requestPermissions(this, reqestPermission,
                1000);

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.timer_sound);
    }

    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        // 알림이 클릭되면 이 인텐트가 보내진다.
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_IMMUTABLE);

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Egg Timer")
                .setContentText("계란 삶기가 완료되었습니다.")
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    private void playCompletionSound() {
        // If MediaPlayer is already playing, stop and reset it
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, R.raw.timer_sound);
        }

        // Play the sound
        mediaPlayer.start();
    }

    public void startTimer(View view) {
        String s = mEditText.getText().toString();
        int min = 0;
        int sec = 10; // Default to 10 seconds

        try {
            if (s.contains(":")) {
                min = Integer.parseInt(s.substring(0, 2));
                sec = Integer.parseInt(s.substring(3, 5));
            } else {
                // If input doesn't match the expected format, use default
                mEditText.setText("00:10");
            }
        } catch (Exception e) {
            // If there's any error parsing the input, use default
            mEditText.setText("00:10");
        }

        new CountDownTimer(min * 60 * 1000 + sec * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                seconds = seconds % 60;
                mEditText.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                mEditText.setText("완료!");
                sendNotification();
                playCompletionSound();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}