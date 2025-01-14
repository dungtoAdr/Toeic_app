package com.example.toeicapp.activty;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toeicapp.R;
import com.example.toeicapp.fragment.QuestionFragment1;
import com.example.toeicapp.model.Questions;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SlideActivity1 extends FragmentActivity {
    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiToeic apiToeic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        initView();
        getData();
    }

    private void getData() {
        int part = getIntent().getIntExtra("part", 1);
        if (part == 3) {
            compositeDisposable.add(apiToeic.getPart3()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionsModel -> {
                                if (questionsModel.isSuccess()) {
                                    List<Questions> questions = questionsModel.getResult();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                }
                            }, throwable -> {
                                Log.d("TAG_Part_3", throwable.getMessage());
                            }
                    ));
        } else if (part == 4) {
            compositeDisposable.add(apiToeic.getPart4()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionsModel -> {
                                if (questionsModel.isSuccess()) {
                                    List<Questions> questions = questionsModel.getResult();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                }
                            }, throwable -> {
                                Log.d("TAG_Part_4", throwable.getMessage());
                            }
                    ));
        } else if (part == 6) {
            compositeDisposable.add(apiToeic.getPart6()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionsModel -> {
                                if (questionsModel.isSuccess()) {
                                    List<Questions> questions = questionsModel.getResult();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                }
                            }, throwable -> {
                                Log.d("TAG_Part_6", throwable.getMessage());
                            }
                    ));
        } else if (part == 7) {
            compositeDisposable.add(apiToeic.getPart7()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionsModel -> {
                                if (questionsModel.isSuccess()) {
                                    List<Questions> questions = questionsModel.getResult();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                }
                            }, throwable -> {
                                Log.d("TAG_Part_7", throwable.getMessage());
                            }
                    ));
        }

    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final List<Questions> questions;

        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Questions> questions) {
            super(fa);
            this.questions = questions;
        }

        @NonNull
        @Override
        public QuestionFragment1 createFragment(int position) {
            return QuestionFragment1.newInstance(questions.get(position));
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }
    }
}
