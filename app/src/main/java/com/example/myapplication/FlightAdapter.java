package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<FlightItem> flightList;

    // 생성자
    public FlightAdapter(List<FlightItem> flightList) {
        this.flightList = flightList;
    }

    // ViewHolder 클래스 (각 항목의 UI 요소)
    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAirline, textViewTime, textViewPrice;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAirline = itemView.findViewById(R.id.textViewAirline);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 각 항목의 레이아웃을 인플레이션
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        // 데이터 바인딩
        FlightItem flightItem = flightList.get(position);
        holder.textViewAirline.setText(flightItem.getAirline());
        holder.textViewTime.setText(flightItem.getDepartureTime() + " ~ " + flightItem.getArrivalTime());
        holder.textViewPrice.setText(flightItem.getPrice() + " 원");
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    // 데이터가 변경되었을 때 호출
    public void setFlightList(List<FlightItem> flightList) {
        this.flightList = flightList;
        notifyDataSetChanged();  // 데이터가 변경되었음을 RecyclerView에 알림
    }
}
