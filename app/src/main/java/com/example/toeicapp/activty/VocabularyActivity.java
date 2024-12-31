package com.example.toeicapp.activty;

import android.os.Bundle;
import android.util.Log;
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
import com.example.toeicapp.adapter.VocabularyAdapter;
import com.example.toeicapp.ritrofit.ApiToeic;
import com.example.toeicapp.ritrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VocabularyActivity extends AppCompatActivity {
    private ApiToeic apiToeic;
    private VocabularyAdapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        initView();
        ActionToolBar();
        getData();
    }

    private void getData() {
        int topic_id = getIntent().getIntExtra("topic_id", 1);
        compositeDisposable.add(apiToeic.getVoca(topic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        vocabularies -> {
                            if(vocabularies.isSuccess()){
                                adapter=new VocabularyAdapter(getApplicationContext(),vocabularies.getData());
                                recyclerView.setAdapter(adapter);
                            }
                        },throwable -> {
                            Log.d("TAG_vocabulary", throwable.getMessage());
                        }
                ));
    }

    private void initView() {
        apiToeic= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        recyclerView=findViewById(R.id.recycler_view);
        toolbar=findViewById(R.id.tool_bar);
        toolbar_title=findViewById(R.id.toolbar_title);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        String topic_name=getIntent().getStringExtra("topic_name");
        toolbar_title.setText(topic_name);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}