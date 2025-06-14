package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<String> scheduleList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public ScheduleAdapter(List<String> scheduleList, OnItemClickListener listener) {
        this.scheduleList = scheduleList;
        this.listener = listener;
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewScheduleTitle;
        public TextView textViewScheduleTime;
        public ImageButton btnEdit, btnDelete;

        public ScheduleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewScheduleTitle = itemView.findViewById(R.id.textViewScheduleTitle);
            textViewScheduleTime = itemView.findViewById(R.id.textViewScheduleTime);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onEditClick(pos);
                    }
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(pos);
                    }
                }
            });
        }
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        String schedule = scheduleList.get(position);
        holder.textViewScheduleTitle.setText(schedule);
        holder.textViewScheduleTime.setText("시간 정보 없음"); // 필요 시 변경
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public void updateScheduleList(List<String> newList) {
        scheduleList = newList;
        notifyDataSetChanged();
    }
}
