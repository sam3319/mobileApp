package com.example.calendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<CalendarEvent> eventList;
    private Context context;
    private OnEventDeleteListener deleteListener;

    // 삭제 리스너 인터페이스
    public interface OnEventDeleteListener {
        void onEventDelete(int eventId);
    }

    public EventAdapter(List<CalendarEvent> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        this.deleteListener = (OnEventDeleteListener) context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        CalendarEvent event = eventList.get(position);
        holder.tvTitle.setText(event.getTitle());
        holder.tvDescription.setText(event.getDescription());
        holder.tvDateTime.setText(event.getDate() + " " + event.getTime());

        // 삭제 버튼 클릭 리스너
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onEventDelete(event.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvDateTime;
        Button btnDelete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
