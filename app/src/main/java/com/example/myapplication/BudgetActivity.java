package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BudgetActivity extends AppCompatActivity {

    private EditText editTextFlight, editTextAccommodation, editTextFood, editTextTransport, editTextEtc;
    private TextView textTotalBudget;
    private ImageButton btnBack, btnEditDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        // 뷰 초기화
        editTextFlight = findViewById(R.id.editTextFlight);
        editTextAccommodation = findViewById(R.id.editTextAccommodation);
        editTextFood = findViewById(R.id.editTextFood);
        editTextTransport = findViewById(R.id.editTextTransport);
        editTextEtc = findViewById(R.id.editTextEtc);
        textTotalBudget = findViewById(R.id.textTotalBudget);

        btnBack = findViewById(R.id.btnBack);
        btnEditDelete = findViewById(R.id.btnEditDelete);

        btnBack.setOnClickListener(v -> finish());

        // 수정/삭제 버튼 클릭 처리 (필요하면 구현)
        btnEditDelete.setOnClickListener(v -> {
            // TODO: 예산 수정/삭제 기능 구현 예정
        });

        // 입력 값 변화 감지 리스너 등록
        TextWatcher budgetTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                updateTotalBudget();
            }
        };

        editTextFlight.addTextChangedListener(budgetTextWatcher);
        editTextAccommodation.addTextChangedListener(budgetTextWatcher);
        editTextFood.addTextChangedListener(budgetTextWatcher);
        editTextTransport.addTextChangedListener(budgetTextWatcher);
        editTextEtc.addTextChangedListener(budgetTextWatcher);
    }

    private void updateTotalBudget() {
        int flight = parseInt(editTextFlight.getText().toString());
        int accommodation = parseInt(editTextAccommodation.getText().toString());
        int food = parseInt(editTextFood.getText().toString());
        int transport = parseInt(editTextTransport.getText().toString());
        int etc = parseInt(editTextEtc.getText().toString());

        int total = flight + accommodation + food + transport + etc;

        textTotalBudget.setText("총 예산 : " + total + "원");
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
