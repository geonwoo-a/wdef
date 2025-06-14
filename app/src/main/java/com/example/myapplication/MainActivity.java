package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnTravelListLayout, btnAddPlanLayout, btnScheduleDetailLayout, btnBudgetLayout, btnMapLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTravelListLayout = findViewById(R.id.btnTravelListLayout);
        btnAddPlanLayout = findViewById(R.id.btnAddPlanLayout);
        btnScheduleDetailLayout = findViewById(R.id.btnScheduleDetailLayout);
        btnBudgetLayout = findViewById(R.id.btnBudgetLayout);
        btnMapLayout = findViewById(R.id.btnMapLayout);

        btnTravelListLayout.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, TravelListActivity.class)));

        btnAddPlanLayout.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, PlanAddActivity.class)));

        btnScheduleDetailLayout.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ScheduleDetailActivity.class)));

        btnBudgetLayout.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, BudgetActivity.class)));

        btnMapLayout.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MapActivity.class)));
    }
}