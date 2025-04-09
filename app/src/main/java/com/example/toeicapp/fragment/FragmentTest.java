package com.example.toeicapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
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

    private RecyclerView recycler_reading, recycler_listening, recycler_full;
    private ApiToeic apiToeic;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ExamAdapter adapterReading, adapterListening, adapterFull;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);

        recycler_reading = view.findViewById(R.id.recycler_reading);
        recycler_listening = view.findViewById(R.id.recycler_listening);
        recycler_full = view.findViewById(R.id.recycler_full);

        recycler_reading.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        recycler_listening.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        recycler_full.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));

        loadData();
    }

    private void loadData() {
        compositeDisposable.add(apiToeic.getTest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        examModel -> {
                            if (examModel.isSuccess()) {
                                List<Exam> exams = examModel.getData();

                                List<Exam> list_full = new ArrayList<>();
                                List<Exam> list_listen = new ArrayList<>();
                                List<Exam> list_read = new ArrayList<>();

                                for (Exam exam : exams) {
                                    if (exam.getDuration() == 120) {
                                        list_full.add(exam);
                                    } else if (exam.getDuration() == 45) {
                                        list_listen.add(exam);
                                    } else if (exam.getDuration() == 75) {
                                        list_read.add(exam);
                                    }
                                }
                                Log.d("List_exam", list_full.size()+"");
                                adapterFull = new ExamAdapter(getContext(), list_full);
                                recycler_full.setAdapter(adapterFull);

                                adapterListening = new ExamAdapter(getContext(), list_listen);
                                recycler_listening.setAdapter(adapterListening);

                                adapterReading = new ExamAdapter(getContext(), list_read);
                                recycler_reading.setAdapter(adapterReading);
                            }
                        },
                        throwable -> Log.e("API_ERROR", "loadData: " + throwable.getMessage())
                )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
