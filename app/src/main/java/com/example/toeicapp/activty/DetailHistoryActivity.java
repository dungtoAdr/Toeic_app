package com.example.toeicapp.activty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.ResultAdapter;
import com.example.toeicapp.model.ApiResponse;
import com.example.toeicapp.model.PracticeAnswer;
import com.example.toeicapp.model.PracticeSession;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistoryActivity extends AppCompatActivity {
    private RecyclerView recycler_answer;
    private ResultAdapter resultAdapter;
    private Toolbar toolbar;
    private ApiToeic apiToeic;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        initView();
        ActionToolBar();
        initData();
    }

    private void initView() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        recycler_answer = findViewById(R.id.recycler_answer);
        toolbar = findViewById(R.id.tool_bar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_answer.setLayoutManager(layoutManager);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initData(){
        int session_id=getIntent().getIntExtra("session_id",1);
        compositeDisposable.add(apiToeic.getQuestionsBySession(session_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        apiResponse -> {
                            if(apiResponse.isSuccess()){
                                List<Question> questions=apiResponse.getData().getQuestions();
                                resultAdapter = new ResultAdapter(this, questions);
                                recycler_answer.setAdapter(resultAdapter);
                            }else {
                                Log.d("TAGggg_detail", "loi");
                            }
                        },throwable -> {
                            Log.d("TAGggg_detail", throwable.getMessage());
                        }
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}