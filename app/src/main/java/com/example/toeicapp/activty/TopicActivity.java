package com.example.toeicapp.activty;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.TopicAdapter;
import com.example.toeicapp.ritrofit.ApiToeic;
import com.example.toeicapp.ritrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopicActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ApiToeic apiToeic;
    private TopicAdapter adapter;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        initView();
        getData();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ActionToolBar();
    }

    private void getData() {
        compositeDisposable.add(apiToeic.getTopics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        topics -> {
                            if(topics.isSuccess()){
                                adapter=new TopicAdapter(getApplicationContext(),topics.getData());
                                recyclerView.setAdapter(adapter);
                            }
                        },throwable -> {
                            Log.d("TAG_topic", throwable.getMessage());
                        }
                ));
    }

    private void initView() {
        apiToeic= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        toolbar=findViewById(R.id.tool_bar);
        recyclerView=findViewById(R.id.recycler_view);
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