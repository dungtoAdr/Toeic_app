package com.example.toeicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.activty.GrammarActivity;
import com.example.toeicapp.activty.ListenningActivity;
import com.example.toeicapp.activty.ReadingActivity;
import com.example.toeicapp.activty.SlideActivity;
import com.example.toeicapp.activty.TopicActivity;
import com.example.toeicapp.adapter.HistoryAdapter;
import com.example.toeicapp.model.PracticeSession;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentHome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }


    private CardView item_reading, item_listening, item_vocabulary, item_grammar;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private ApiToeic apiToeic;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        onClickItemReading();
        onClickItemListening();
        onCLickItemVocabulary();
        onCLickItemGrammar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        compositeDisposable.add(apiToeic.getPracticeSessions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        apiResponse -> {
                            if (apiResponse.isSuccess()) {
                                List<PracticeSession> practiceSessions = apiResponse.getData();
                                Utils.practiceSessions_all = practiceSessions;
                                List<PracticeSession> practiceSessions_user = new ArrayList<>();
                                for (int i = 0; i < practiceSessions.size(); i++) {
                                    if (practiceSessions.get(i).getUser_id().equals(Utils.current_user_id)) {
                                        practiceSessions_user.add(practiceSessions.get(i));
                                    }
                                }
                                Utils.practiceSessions = practiceSessions_user;
                                if (practiceSessions_user.size() > 4) {
                                    practiceSessions_user = practiceSessions_user.subList(0, 4);
                                }
                                historyAdapter = new HistoryAdapter(getContext(), practiceSessions_user);
                                recyclerView.setAdapter(historyAdapter);
                                historyAdapter.notifyDataSetChanged();
                            } else {
                                Log.d("TAG_history", "Không nhận được dữ liệu");
                            }
                        }, throwable -> {
                            Log.d("TAG_history", throwable.getMessage());
                        }
                )
        );
    }


    private void onCLickItemVocabulary() {
        item_vocabulary.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TopicActivity.class);
            startActivity(intent);
        });
    }

    private void onCLickItemGrammar() {
        item_grammar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GrammarActivity.class);
            startActivity(intent);
        });
    }

    private void onClickItemListening() {
        item_listening.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Listening", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ListenningActivity.class);
            startActivity(intent);
        });
    }

    private void onClickItemReading() {
        item_reading.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reading", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ReadingActivity.class);
            startActivity(intent);
        });
    }

    private void initView(View view) {
        item_listening = view.findViewById(R.id.item_listening);
        item_reading = view.findViewById(R.id.item_reading);
        item_vocabulary = view.findViewById(R.id.item_vocabulary);
        item_grammar = view.findViewById(R.id.item_grammar);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}
