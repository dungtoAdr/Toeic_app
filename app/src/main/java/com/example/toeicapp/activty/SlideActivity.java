package com.example.toeicapp.activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toeicapp.R;
import com.example.toeicapp.fragment.QuestionFragment;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SlideActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiToeic apiToeic;
    private Toolbar toolbar;
    private Button bt_submit;
    private TextView number_question;
    private ProgressDialog progressDialog;
    private final Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        initView();
        getData();
    }

    private void getData() {
        showLoading(); // Hiển thị vòng xoay
        int part = getIntent().getIntExtra("part", 1);
        Log.d("TAG_SLIDE", part + "");
        if (part == 1) {
            compositeDisposable.add(apiToeic.getPart1()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionModel -> {
                                if (questionModel.isSuccess()) {
                                    List<Question> questions = questionModel.getQuestions();
                                    Utils.questions_answer = questionModel.getQuestions();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                    viewPager.setOffscreenPageLimit(questions.size());
                                    Log.d("TAG_part1", questions.get(0).getOption_a());
                                    handler.postDelayed(this::hideLoading, 500);
                                }
                            },
                            throwable -> {
                                Log.d("API_ERROR", Objects.requireNonNull(throwable.getMessage()));
                                hideLoading();

                            }                    )
            );
        } else if (part == 2) {
            compositeDisposable.add(apiToeic.getPart2()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionModel -> {
                                if (questionModel.isSuccess()) {
                                    List<Question> questions = questionModel.getQuestions();
                                    Utils.questions_answer = questionModel.getQuestions();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                    viewPager.setOffscreenPageLimit(questions.size());
                                    handler.postDelayed(this::hideLoading, 500);
                                }
                            },
                            throwable -> {
                                Log.d("API_ERROR", Objects.requireNonNull(throwable.getMessage()));
                                hideLoading();

                            }                    )
            );
        } else if (part == 5) {
            compositeDisposable.add(apiToeic.getPart5()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            questionModel -> {
                                if (questionModel.isSuccess()) {
                                    List<Question> questions = questionModel.getQuestions();
                                    Utils.questions_answer = questionModel.getQuestions();
                                    pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                    viewPager.setAdapter(pagerAdapter);
                                    viewPager.setOffscreenPageLimit(questions.size());
                                    handler.postDelayed(this::hideLoading, 500);
                                }
                            },
                            throwable -> {
                                Log.d("API_ERROR", Objects.requireNonNull(throwable.getMessage()));
                                hideLoading();

                            }
                    )
            );
        }
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải câu hỏi...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void initView() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.tool_bar);
        bt_submit = findViewById(R.id.bt_submit);
        number_question = findViewById(R.id.number_question);
        number_question.setText("Câu 1");

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                number_question.setText("Câu " + (position + 1));
            }
        });

        bt_submit.setOnClickListener(view -> {
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final List<Question> questions;

        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Question> questions) {
            super(fa);
            this.questions = questions;
        }

        @NonNull
        @Override
        public QuestionFragment createFragment(int position) {
            return QuestionFragment.newInstance(questions.get(position));
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }
    }
}
