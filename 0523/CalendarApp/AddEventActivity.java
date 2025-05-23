package com.example.calendarapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends Activity {
    private EditText etTitle, etDescription;
    private TextView tvSelectedDate, tvSelectedTime;
    private Calendar selectedDateTime;
    private DatabaseHelper databaseHelper;

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // 알림 권한 확인 및 요청
        checkAndRequestNotificationPermission();

        databaseHelper = new DatabaseHelper(this);
        selectedDateTime = Calendar.getInstance();

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        Button btnSaveEvent = findViewById(R.id.btnSaveEvent);

        tvSelectedDate.setOnClickListener(v -> showDatePicker());
        tvSelectedTime.setOnClickListener(v -> showTimePicker());
        btnSaveEvent.setOnClickListener(v -> saveEvent());
    }

    private void checkAndRequestNotificationPermission() {
        // Android 13 (API 33) 이상에서 알림 권한 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                // 권한 요청
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "알림 권한이 허용되었습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "알림 권한이 거부되어 알림을 받을 수 없습니다", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateDisplay();
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    selectedDateTime.set(Calendar.SECOND, 0);
                    updateTimeDisplay();
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tvSelectedDate.setText(dateFormat.format(selectedDateTime.getTime()));
    }

    private void updateTimeDisplay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvSelectedTime.setText(timeFormat.format(selectedDateTime.getTime()));
    }

    private void saveEvent() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 정확한 알람 권한 확인 및 요청 (Android 12 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                showExactAlarmPermissionDialog();
                return;
            }
        }

        // 권한이 있거나 필요 없는 경우 바로 저장
        saveEventToDatabase();
    }

    private void showExactAlarmPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("알람 권한 필요")
                .setMessage("정확한 시간에 알림을 받으려면 '정확한 알람' 권한이 필요합니다.\n\n설정으로 이동하여 권한을 허용하시겠습니까?")
                .setPositiveButton("설정으로 이동", (dialog, which) -> {
                    try {
                        Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                        startActivity(intent);
                        Toast.makeText(this, "권한 설정 후 다시 일정을 저장해주세요", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "설정 화면을 열 수 없습니다", Toast.LENGTH_SHORT).show();
                        saveEventToDatabase(); // 설정 화면을 열 수 없으면 그냥 저장
                    }
                })
                .setNegativeButton("나중에", (dialog, which) -> {
                    // 권한 없이 저장 (근사치 알람 사용)
                    saveEventToDatabase();
                })
                .setCancelable(false)
                .show();
    }

    private void saveEventToDatabase() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        CalendarEvent calendarEvent = new CalendarEvent(
                title,
                description,
                dateFormat.format(selectedDateTime.getTime()),
                timeFormat.format(selectedDateTime.getTime()),
                selectedDateTime.getTimeInMillis()
        );

        long eventId = databaseHelper.addEvent(calendarEvent);

        if (eventId != -1) {
            scheduleNotification((int) eventId, title, description, selectedDateTime.getTimeInMillis());
            Toast.makeText(this, "일정이 저장되었습니다", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleNotification(int eventId, String title, String description, long timeInMillis) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("event_id", eventId);
        intent.putExtra("event_title", title);
        intent.putExtra("event_description", description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                eventId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (alarmManager.canScheduleExactAlarms()) {
                    // 정확한 알람 설정
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    PendingIntent showIntent = PendingIntent.getActivity(
                            this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager.AlarmClockInfo clockInfo =
                            new AlarmManager.AlarmClockInfo(timeInMillis, showIntent);
                    alarmManager.setAlarmClock(clockInfo, pendingIntent);
                } else {
                    // 근사치 알람 사용
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
                    Toast.makeText(this, "알림이 설정되었습니다 (시간이 약간 다를 수 있습니다)", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Android 6.0 미만에서는 바로 정확한 알람 설정
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            }
        }
    }

    // 테스트용 즉시 알림 메서드 (필요시 사용)
    private void testNotification() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("event_id", 999);
        intent.putExtra("event_title", "테스트 알림");
        intent.putExtra("event_description", "알림 테스트입니다");

        sendBroadcast(intent);
    }
}
