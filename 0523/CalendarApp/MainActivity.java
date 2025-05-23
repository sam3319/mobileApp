package com.example.calendarapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends Activity implements EventAdapter.OnEventDeleteListener {
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<CalendarEvent> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        Button btnAddEvent = findViewById(R.id.btnAddEvent);
        recyclerView = findViewById(R.id.recyclerViewEvents);

        setupRecyclerView();
        loadEvents();

        btnAddEvent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadEvents() {
        eventList = databaseHelper.getAllEvents();
        eventAdapter = new EventAdapter(eventList, this);
        recyclerView.setAdapter(eventAdapter);
    }

    // OnEventDeleteListener 인터페이스 구현
    @Override
    public void onEventDelete(int eventId) {
        deleteEvent(eventId);
    }

    // 알람 취소 메서드
    private void cancelAlarm(int eventId) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                eventId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    // 일정 삭제 메서드
    private void deleteEvent(int eventId) {
        // 1. 알람 취소
        cancelAlarm(eventId);

        // 2. 데이터베이스에서 삭제
        databaseHelper.deleteEvent(eventId);

        // 3. 목록 새로고침
        loadEvents();

        Toast.makeText(this, "일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
    }
}
