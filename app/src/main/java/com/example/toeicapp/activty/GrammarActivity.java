package com.example.toeicapp.activty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

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

public class GrammarActivity extends AppCompatActivity {
    private final List<Grammar> grammars = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        initView();
        initData();
        ActionToolBar();

    }

    private void initData() {
        Grammar topic = new Grammar(1, "Đại từ", "https://langmaster.edu.vn/dai-tu-la-gi-tron-bo-kien-thuc-ve-dai-tu-trong-tieng-anh");
        Grammar topic2 = new Grammar(2, "Danh từ", "https://langmaster.edu.vn/danh-tu-trong-tieng-anh");
        Grammar topic3 = new Grammar(3, "Tính từ", "https://langmaster.edu.vn/500-tinh-tu-tieng-anh-thong-dung-b8i352.html");
        Grammar topic4 = new Grammar(4, "Trạng từ", "https://langmaster.edu.vn/100-trang-tu-tieng-anh-thong-dung-nhat-a70i1495.html");
        Grammar topic5 = new Grammar(5, "Thì hiện tại tiếp diễn", "https://langmaster.edu.vn/100-trang-tu-tieng-anh-thong-dung-nhat-a70i1495.html");
        grammars.add(topic);
        grammars.add(topic2);
        grammars.add(topic3);
        grammars.add(topic4);
        grammars.add(topic5);
        RecycleAdapter adapter = new RecycleAdapter(this, grammars);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.tool_bar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Ngữ Pháp");
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