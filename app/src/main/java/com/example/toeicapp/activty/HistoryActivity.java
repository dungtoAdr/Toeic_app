package com.example.toeicapp.activty;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.HistoryFullAdapter;
import com.example.toeicapp.model.PracticeSession;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        ActionToolBar();

        showChart(Utils.practiceSessions);
    }

    private void initView() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.recycler_view);
        barChart = findViewById(R.id.barChart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        HistoryFullAdapter adapter = new HistoryFullAdapter(this, Utils.practiceSessions);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showChart(List<PracticeSession> sessions) {
        HashMap<Integer, int[]> partResults = new HashMap<>();

        // Tính tổng số câu đúng và tổng số câu theo từng part
        for (PracticeSession session : sessions) {
            int part = session.getPart();
            int correct = session.getCorrect_answers();
            int total = session.getTotal_questions();
            if (!partResults.containsKey(part)) {
                partResults.put(part, new int[]{0, 0});
            }
            partResults.get(part)[0] += correct;
            partResults.get(part)[1] += total;
        }

        // Tạo dữ liệu cho biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int index = 0;

        for (int part : partResults.keySet()) {
            int[] values = partResults.get(part);
            float percent = (values[1] == 0) ? 0 : (values[0] * 100f / values[1]);
            entries.add(new BarEntry(index++, percent));
            if(part == 8){
                labels.add("Test");
            }
            labels.add("Part " + part);
        }

        // Tạo BarDataSet và tùy chỉnh
        BarDataSet dataSet = new BarDataSet(entries, "Tỷ lệ đúng (%)");
        dataSet.setColor(getResources().getColor(R.color.tool_bar));
        dataSet.setValueTextSize(12f);
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.setFitBars(true);

        // Cài đặt trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());

        // Cài đặt trục Y
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);

        barChart.getAxisRight().setEnabled(false); // Tắt trục Y bên phải

        // Cập nhật biểu đồ
        barChart.invalidate();
        barChart.getDescription().setEnabled(false);
    }
}