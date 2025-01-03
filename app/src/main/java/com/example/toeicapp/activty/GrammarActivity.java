package com.example.toeicapp.activty;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.example.toeicapp.adapter.TopicAdapter;
import com.example.toeicapp.model.Grammar;
import com.example.toeicapp.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class GrammarActivity extends AppCompatActivity {
    private GrammarAdapter adapter;
    private RecyclerView recyclerView;
    private List<Grammar> grammars=new ArrayList<>();
    private Toolbar toolbar;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        initView();
        ActionToolBar();

    }

    private void initView() {
        recyclerView=findViewById(R.id.recycler_view);
        toolbar=findViewById(R.id.tool_bar);
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText("Ngữ Pháp");
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Grammar topic=new Grammar(1,"Đại từ","https://langmaster.edu.vn/dai-tu-la-gi-tron-bo-kien-thuc-ve-dai-tu-trong-tieng-anh");
        Grammar topic2=new Grammar(2,"Danh từ","https://langmaster.edu.vn/danh-tu-trong-tieng-anh");
        Grammar topic3=new Grammar(3,"Tính từ","https://langmaster.edu.vn/500-tinh-tu-tieng-anh-thong-dung-b8i352.html");
        Grammar topic4=new Grammar(4,"Trạng từ","https://langmaster.edu.vn/100-trang-tu-tieng-anh-thong-dung-nhat-a70i1495.html");
        grammars.add(topic);
        grammars.add(topic2);
        grammars.add(topic3);
        grammars.add(topic4);
        adapter=new GrammarAdapter(this,grammars);
        recyclerView.setAdapter(adapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}