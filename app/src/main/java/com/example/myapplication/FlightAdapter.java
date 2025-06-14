package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    private List<FlightItem> flightList;

    public FlightAdapter(List<FlightItem> flightList) {
        this.flightList = flightList;
    }

    public void setFlightList(List<FlightItem> flightList) {
        this.flightList = flightList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolder holder, int position) {
        FlightItem item = flightList.get(position);
        holder.textViewAirline.setText(item.getAirline());
        // 기타 바인딩 작업
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAirline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAirline = itemView.findViewById(R.id.textViewAirline);
        }
    }
}
