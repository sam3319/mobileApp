package com.example.todolist;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class ShareHelper {
    private final Context context;

    public ShareHelper(Context context) {
        this.context = context;
    }

    public void shareTimetable(List<Timetable> timetables) {
        StringBuilder shareText = new StringBuilder();
        shareText.append("📚 내 시간표\n\n");

        for (int day = 1; day <= 5; day++) {
            String dayName = getDayName(day);
            shareText.append(dayName).append("\n");
            
            for (Timetable timetable : timetables) {
                if (timetable.getDayOfWeek() == day) {
                    shareText.append(String.format("- %s (%s)\n", 
                        timetable.getSubject(), 
                        timetable.getLocation()));
                    shareText.append(String.format("  %d교시 - %d교시\n", 
                        timetable.getStartTime(), 
                        timetable.getEndTime()));
                    shareText.append(String.format("  %s 교수님\n\n", 
                        timetable.getProfessor()));
                }
            }
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
        context.startActivity(Intent.createChooser(shareIntent, "시간표 공유하기"));
    }

    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return "월요일";
            case 2: return "화요일";
            case 3: return "수요일";
            case 4: return "목요일";
            case 5: return "금요일";
            default: return "알 수 없는 요일";
        }
    }
} 