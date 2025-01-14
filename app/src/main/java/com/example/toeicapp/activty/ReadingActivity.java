package com.example.toeicapp.activty;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.RecycleAdapter;
import com.example.toeicapp.model.Grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initView();
        initData();
        ActionToolBar();
    }

    private void initData() {
        List<Grammar> grammars = new ArrayList<>();
        Grammar grammar1 = new Grammar(1, "Điền Vào Câu");
        Grammar grammar2 = new Grammar(2, "Điền Vào Đoạn Văn");
        Grammar grammar3 = new Grammar(3, "Đọc Hiểu Văn Bản");
        grammars.add(grammar1);
        grammars.add(grammar2);
        grammars.add(grammar3);
        RecycleAdapter recycleAdapter = new RecycleAdapter(this, grammars);
        recyclerView.setAdapter(recycleAdapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.tool_bar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}