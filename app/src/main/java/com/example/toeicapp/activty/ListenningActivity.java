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
import com.example.toeicapp.adapter.GrammarAdapter;
import com.example.toeicapp.model.Grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListenningActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listenning);
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.tool_bar);
        ActionToolBar();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        List<Grammar> grammars = new ArrayList<>();
        Grammar grammar1 = new Grammar(1, "Mô Tả Hình Ảnh",true);
        Grammar grammar2 = new Grammar(2, "Hỏi Và Đáp",true);
        Grammar grammar3 = new Grammar(3, "Đoạn Hội Thoại",true);
        Grammar grammar4 = new Grammar(4, "Bài Nói Chuyện Ngắn",true);
        grammars.add(grammar1);
        grammars.add(grammar2);
        grammars.add(grammar3);
        grammars.add(grammar4);
        GrammarAdapter grammarAdapter = new GrammarAdapter(this, grammars);
        recyclerView.setAdapter(grammarAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}