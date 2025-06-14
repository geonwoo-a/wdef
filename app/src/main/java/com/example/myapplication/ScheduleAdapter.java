package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<String> scheduleList;

    public ScheduleAdapter(List<String> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView scheduleTextView;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            scheduleTextView = itemView.findViewById(R.id.scheduleTextView); // 일정 텍스트를 표시할 TextView
        }
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        String schedule = scheduleList.get(position);
        holder.scheduleTextView.setText(schedule);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
