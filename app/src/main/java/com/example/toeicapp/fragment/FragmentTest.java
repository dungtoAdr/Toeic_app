package com.example.toeicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.activty.SlideExamActivity;
import com.example.toeicapp.adapter.ExamAdapter;
import com.example.toeicapp.model.Exam;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentTest extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment,container,false);
    }

    private RecyclerView recycler_reading, recycler_listening, recycler_full;
    private ApiToeic apiToeic;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ExamAdapter examAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        recycler_reading = view.findViewById(R.id.recycler_reading);
        recycler_listening = view.findViewById(R.id.recycler_listening);
        recycler_full = view.findViewById(R.id.recycler_full);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1, GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),1, GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),1, GridLayoutManager.HORIZONTAL,false);
        recycler_reading.setLayoutManager(layoutManager);
        recycler_listening.setLayoutManager(layoutManager1);
        recycler_full.setLayoutManager(layoutManager2);
        compositeDisposable.add(apiToeic.getTest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        examModel -> {
                            if (examModel.isSuccess()){
                                List<Exam> exams = examModel.getData();
                                for (int i=0;i<exams.size();i++){
                                    Exam exam = exams.get(i);
                                    if(exam.getDuration() == 120){
                                        List<Exam> list_full = new ArrayList<>();
                                        list_full.add(exam);
                                        examAdapter = new ExamAdapter(getContext(),list_full);
                                        recycler_full.setAdapter(examAdapter);
                                    } if(exam.getDuration() == 45){
                                        List<Exam> list_listen = new ArrayList<>();
                                        list_listen.add(exam);
                                        examAdapter = new ExamAdapter(getContext(),list_listen);
                                        recycler_listening.setAdapter(examAdapter);
                                    } if(exam.getDuration() == 75){
                                        List<Exam> list_read = new ArrayList<>();
                                        list_read.add(exam);
                                        examAdapter = new ExamAdapter(getContext(),list_read);
                                        recycler_reading.setAdapter(examAdapter);
                                    }
                                }
                            }
                        },throwable -> {
                            Log.d("TAG", throwable.getMessage());
                        }
                )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
