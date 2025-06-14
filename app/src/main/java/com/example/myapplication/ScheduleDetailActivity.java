package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailActivity extends AppCompatActivity {

    private ImageButton btnBack, btnEditDelete;
    private TextView titleTextView, selectedDateTextView;
    private CalendarView calendarView;
    private RecyclerView recyclerViewSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail); // XML 레이아웃을 참조

        // UI 요소 초기화
        btnBack = findViewById(R.id.btnBack);
        btnEditDelete = findViewById(R.id.btnEditDelete);
        titleTextView = findViewById(R.id.Title);
        selectedDateTextView = findViewById(R.id.SelectedDate);
        calendarView = findViewById(R.id.calendarView);
        recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule);

        // 리사이클러뷰 설정
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(this));

        // 예시 데이터 생성
        List<String> scheduleList = new ArrayList<>();
        scheduleList.add("2023-06-14 일정 1");
        scheduleList.add("2023-06-15 일정 2");
        scheduleList.add("2023-06-16 일정 3");

        // ScheduleAdapter에 데이터를 넘겨줌
        ScheduleAdapter adapter = new ScheduleAdapter(scheduleList);
        recyclerViewSchedule.setAdapter(adapter);

        // 툴바 뒤로가기 버튼 클릭 이벤트 처리
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 수정/삭제 버튼 클릭 이벤트 처리
        btnEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수정/삭제 기능 구현 (여기서 추가로 로직을 작성할 수 있습니다)
            }
        });

        // 캘린더 뷰 날짜 선택 이벤트 처리
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 날짜 표시
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // 월은 0부터 시작하므로 +1 필요
                selectedDateTextView.setText("선택된 날짜: " + selectedDate);
            }
        });
    }
}
