package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanAddActivity extends AppCompatActivity {

    private EditText etDeparture, etDestination, etDepartureDate, etReturnDate;
    private Button btnSearchFlights;
    private RecyclerView recyclerFlights;
    private TextView flightResults;
    private ImageButton btnBack;

    private FlightAdapter flightAdapter;
    private List<FlightItem> flightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        etDeparture = findViewById(R.id.etDeparture);
        etDestination = findViewById(R.id.etDestination);
        etDepartureDate = findViewById(R.id.etDepartureDate);
        etReturnDate = findViewById(R.id.etReturnDate);
        btnSearchFlights = findViewById(R.id.btnSearchFlights);
        recyclerFlights = findViewById(R.id.recycler_flights);
        flightResults = findViewById(R.id.FlightResults);
        btnBack = findViewById(R.id.btnBack);

        flightList = new ArrayList<>();
        flightAdapter = new FlightAdapter(flightList);

        recyclerFlights.setLayoutManager(new LinearLayoutManager(this));
        recyclerFlights.setAdapter(flightAdapter);

        btnBack.setOnClickListener(v -> finish());

        btnSearchFlights.setOnClickListener(v -> {
            flightList.clear();

            String departure = etDeparture.getText().toString().trim();
            String destination = etDestination.getText().toString().trim();
            String departDate = etDepartureDate.getText().toString().trim();

            flightList.add(new FlightItem("대한항공", departDate + " 10:00", departDate + " 14:00", 250000));
            flightList.add(new FlightItem("아시아나", departDate + " 15:00", departDate + " 19:00", 230000));

            flightAdapter.setFlightList(flightList);  // 어댑터에 변경된 데이터 전달
        });
    }
}
