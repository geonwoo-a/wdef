package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TravelListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_TRAVEL = 1;
    private static final int REQUEST_CODE_EDIT_TRAVEL = 2;

    private RecyclerView recyclerView;
    private TravelListAdapter adapter;
    private List<TravelItem> travelItems;
    private List<TravelItem> filteredItems;
    private Spinner spinnerCountryFilter;
    private Spinner spinnerPeriodFilter;

    private ImageButton btnAddTravel;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);

        recyclerView = findViewById(R.id.recyclerViewTravelList);
        spinnerCountryFilter = findViewById(R.id.spinnerCountryFilter);
        spinnerPeriodFilter = findViewById(R.id.spinnerPeriodFilter);
        btnAddTravel = findViewById(R.id.btnAddTravel);
        btnBack = findViewById(R.id.btnBack);

        String[] countries = {"전체", "대한민국", "일본", "미국", "프랑스"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryFilter.setAdapter(countryAdapter);

        String[] periods = {"전체", "1일", "2~3일", "4~7일", "7일 이상"};
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, periods);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodFilter.setAdapter(periodAdapter);

        travelItems = new ArrayList<>();
        travelItems.add(new TravelItem("제주 여행", "대한민국", "2025-07-01", "2025-07-05"));
        travelItems.add(new TravelItem("도쿄 여행", "일본", "2025-08-10", "2025-08-15"));
        travelItems.add(new TravelItem("뉴욕 출장", "미국", "2025-09-01", "2025-09-03"));

        filteredItems = new ArrayList<>(travelItems);

        adapter = new TravelListAdapter(filteredItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnAddTravel.setOnClickListener(v -> {
            Intent intent = new Intent(TravelListActivity.this, PlanAddActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TRAVEL);
        });

        adapter.setOnItemClickListener(new TravelListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                TravelItem item = filteredItems.get(position);
                Intent intent = new Intent(TravelListActivity.this, PlanAddActivity.class);
                intent.putExtra("travelItem", item);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_TRAVEL);
            }

            @Override
            public void onDeleteClick(int position) {
                TravelItem itemToRemove = filteredItems.get(position);
                travelItems.remove(itemToRemove);
                filteredItems.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterList();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spinnerCountryFilter.setOnItemSelectedListener(filterListener);
        spinnerPeriodFilter.setOnItemSelectedListener(filterListener);
    }

    private void filterList() {
        String selectedCountry = spinnerCountryFilter.getSelectedItem().toString();
        String selectedPeriod = spinnerPeriodFilter.getSelectedItem().toString();

        filteredItems.clear();

        for (TravelItem item : travelItems) {
            boolean countryMatches = selectedCountry.equals("전체") || item.getCountry().equals(selectedCountry);

            boolean periodMatches = false;
            if ("전체".equals(selectedPeriod)) {
                periodMatches = true;
            } else {
                int days = calculateDaysBetween(item.getStartDate(), item.getEndDate());
                switch (selectedPeriod) {
                    case "1일":
                        periodMatches = (days == 1);
                        break;
                    case "2~3일":
                        periodMatches = (days >= 2 && days <= 3);
                        break;
                    case "4~7일":
                        periodMatches = (days >= 4 && days <= 7);
                        break;
                    case "7일 이상":
                        periodMatches = (days > 7);
                        break;
                }
            }

            if (countryMatches && periodMatches) {
                filteredItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private int calculateDaysBetween(String startDate, String endDate) {
        try {
            String[] startParts = startDate.split("-");
            String[] endParts = endDate.split("-");

            java.util.Calendar startCal = java.util.Calendar.getInstance();
            startCal.set(Integer.parseInt(startParts[0]), Integer.parseInt(startParts[1]) - 1, Integer.parseInt(startParts[2]), 0, 0, 0);
            startCal.set(java.util.Calendar.MILLISECOND, 0);

            java.util.Calendar endCal = java.util.Calendar.getInstance();
            endCal.set(Integer.parseInt(endParts[0]), Integer.parseInt(endParts[1]) - 1, Integer.parseInt(endParts[2]), 0, 0, 0);
            endCal.set(java.util.Calendar.MILLISECOND, 0);

            long diffMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
            if (diffMillis < 0) return 0;

            return (int) (diffMillis / (24 * 60 * 60 * 1000)) + 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            TravelItem updatedItem = (TravelItem) data.getSerializableExtra("travelItem");
            int position = data.getIntExtra("position", -1);

            if (requestCode == REQUEST_CODE_ADD_TRAVEL) {
                travelItems.add(updatedItem);
                filterList();
            } else if (requestCode == REQUEST_CODE_EDIT_TRAVEL && position != -1) {
                TravelItem originalItem = filteredItems.get(position);
                int originalIndex = travelItems.indexOf(originalItem);
                if (originalIndex != -1) {
                    travelItems.set(originalIndex, updatedItem);
                    filterList();
                }
            }
        }
    }

    public static class TravelItem implements Serializable {
        private String title;
        private String country;
        private String startDate;
        private String endDate;

        public TravelItem(String title, String country, String startDate, String endDate) {
            this.title = title;
            this.country = country;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getTitle() { return title; }
        public String getCountry() { return country; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }

        public void setTitle(String title) { this.title = title; }
        public void setCountry(String country) { this.country = country; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
    }

    public static class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

        private List<TravelItem> travelItems;
        private OnItemClickListener listener;

        public interface OnItemClickListener {
            void onEditClick(int position);
            void onDeleteClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public TravelListAdapter(List<TravelItem> travelItems) {
            this.travelItems = travelItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_travel, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TravelItem item = travelItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return travelItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textTitle, textCountry, textPeriod;
            Spinner spinnerCountryFilter, spinnerPeriodFilter;
            ImageButton btnEdit, btnDelete;

            public ViewHolder(View itemView) {
                super(itemView);
                textTitle = itemView.findViewById(R.id.Title);
                spinnerCountryFilter = itemView.findViewById(R.id.spinnerCountryFilter);
                spinnerPeriodFilter = itemView.findViewById(R.id.spinnerPeriodFilter);
                btnEdit = itemView.findViewById(R.id.btnBack);
                btnDelete = itemView.findViewById(R.id.btnEditDelete);

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

            void bind(TravelItem item) {
                textTitle.setText(item.getTitle());
                textCountry.setText(item.getCountry());
                textPeriod.setText(item.getStartDate() + " ~ " + item.getEndDate());
            }
        }
    }
}
