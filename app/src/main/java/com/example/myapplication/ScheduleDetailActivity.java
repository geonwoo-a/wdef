package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSchedule;
    private ScheduleAdapter adapter;
    private List<String> scheduleList;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule);
        btnBack = findViewById(R.id.btnBack);

        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(this));

        scheduleList = new ArrayList<>();
        scheduleList.add("2023-06-14 일정 1");
        scheduleList.add("2023-06-15 일정 2");
        scheduleList.add("2023-06-16 일정 3");

        adapter = new ScheduleAdapter(scheduleList, new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                showEditDialog(position);
            }

            @Override
            public void onDeleteClick(int position) {
                showDeleteDialog(position);
            }
        });

        recyclerViewSchedule.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());
    }

    private void showEditDialog(int position) {
        EditText editText = new EditText(this);
        editText.setText(scheduleList.get(position));

        new AlertDialog.Builder(this)
                .setTitle("일정 수정")
                .setView(editText)
                .setPositiveButton("수정", (dialog, which) -> {
                    String newText = editText.getText().toString().trim();
                    if (!newText.isEmpty()) {
                        scheduleList.set(position, newText);
                        adapter.updateScheduleList(scheduleList);
                        Toast.makeText(this, "일정이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("일정 삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    scheduleList.remove(position);
                    adapter.updateScheduleList(scheduleList);
                    Toast.makeText(this, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("취소", null)
                .show();
    }
}
