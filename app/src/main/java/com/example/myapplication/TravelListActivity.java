package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TravelListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_TRAVEL = 1;
    private RecyclerView recyclerView;
    private TravelListAdapter adapter;
    private List<TravelItem> travelItems; // 전체 목록
    private List<TravelItem> filteredItems; // 필터 적용된 목록

    private Spinner spinnerCountryFilter;
    private Spinner spinnerPeriodFilter;

    private ImageButton btnAddTravel;
    private ImageButton btnBack;
    private ImageButton btnEditDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);

        recyclerView = findViewById(R.id.recyclerViewTravelList);
        spinnerCountryFilter = findViewById(R.id.spinnerCountryFilter);
        spinnerPeriodFilter = findViewById(R.id.spinnerPeriodFilter);
        btnAddTravel = findViewById(R.id.btnAddTravel);
        btnBack = findViewById(R.id.btnBack);
        btnEditDelete = findViewById(R.id.btnEditDelete);

        // 스피너 어댑터 세팅
        String[] countries = {"전체", "대한민국", "일본", "미국", "프랑스"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryFilter.setAdapter(countryAdapter);

        String[] periods = {"전체", "1일", "2~3일", "4~7일", "7일 이상"};
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, periods);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodFilter.setAdapter(periodAdapter);

        // 예시 여행 데이터 초기화
        travelItems = new ArrayList<>();
        travelItems.add(new TravelItem("제주 여행", "대한민국", "2025-07-01", "2025-07-05"));
        travelItems.add(new TravelItem("도쿄 여행", "일본", "2025-08-10", "2025-08-15"));
        travelItems.add(new TravelItem("뉴욕 출장", "미국", "2025-09-01", "2025-09-03"));

        filteredItems = new ArrayList<>(travelItems);

        adapter = new TravelListAdapter(filteredItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnEditDelete.setOnClickListener(v -> {
            // TODO: 여행 수정/삭제 기능 구현 예정
        });

        btnAddTravel.setOnClickListener(v -> {
            Intent intent = new Intent(TravelListActivity.this, PlanAddActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TRAVEL);
        });

        // 스피너 선택 리스너 (필터 적용)
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
            startCal.set(Integer.parseInt(startParts[0]), Integer.parseInt(startParts[1]) - 1, Integer.parseInt(startParts[2]), 0,0,0);
            startCal.set(java.util.Calendar.MILLISECOND, 0);

            java.util.Calendar endCal = java.util.Calendar.getInstance();
            endCal.set(Integer.parseInt(endParts[0]), Integer.parseInt(endParts[1]) - 1, Integer.parseInt(endParts[2]), 0,0,0);
            endCal.set(java.util.Calendar.MILLISECOND, 0);

            long diffMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
            if(diffMillis < 0) return 0; // 종료일이 시작일 이전인 경우 0 처리

            return (int) (diffMillis / (24 * 60 * 60 * 1000)) + 1; // 날짜 포함 일수 계산

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
