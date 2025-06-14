// TravelListAdapter.java
package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(TravelItem item);
    }

    private List<TravelItem> travelItems;
    private OnItemClickListener listener;

    public TravelListAdapter(List<TravelItem> travelItems) {
        this.travelItems = travelItems;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(travelItems.get(pos));
                }
            });
        }
    }

    @NonNull
    @Override
    public TravelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelListAdapter.ViewHolder holder, int position) {
        TravelItem item = travelItems.get(position);
        holder.textViewTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return travelItems.size();
    }
}
