package com.example.toeicapp.activty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toeicapp.R;
import com.example.toeicapp.fragment.QuestionExamFragment;
import com.example.toeicapp.model.Exam;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.model.Questions;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SlideExamActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiToeic apiToeic;
    private Toolbar toolbar;
    private Button bt_submit;
    private TextView number_question;
    private List<Question> questionList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private final Handler handler = new Handler(); // Xử lý delay để tránh lỗi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        initView();
        getData();
    }

    private void getData() {
        showLoading(); // Hiển thị vòng xoay

        int id = getIntent().getIntExtra("id", 1);
        compositeDisposable.add(apiToeic.getExams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        examModel -> {
                            if (examModel.isSuccess()) {
                                List<Exam> exams = examModel.getData();
                                questionList = exams.get(0).getQuestions();
                                Utils.questions_answer = questionList;
                                Log.d("TAG_exam", "Số câu hỏi: " + questionList.size());

                                // Khởi tạo adapter và gán vào ViewPager2
                                pagerAdapter = new ScreenSlidePagerAdapter(this, questionList);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setOffscreenPageLimit(questionList.size());

                                // Delay để đảm bảo ViewPager2 hiển thị xong
                                handler.postDelayed(this::hideLoading, 500);
                            }
                        }, throwable -> {
                            hideLoading(); // Ẩn vòng xoay nếu có lỗi
                            Log.e("TAG_exam", "Lỗi khi tải câu hỏi: " + throwable.getMessage());
                        }
                )
        );
    }

    private void initView() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.tool_bar);
        bt_submit = findViewById(R.id.bt_submit);
        number_question = findViewById(R.id.number_question);
        number_question.setText("Câu 1");

        number_question.setOnClickListener(view -> showQuestionDialog());

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

    private void showQuestionDialog() {
        if (questionList.isEmpty()) return;

        String[] questionNumbers = new String[questionList.size()];

        for (int i = 0; i < questionNumbers.length; i++) {
            questionNumbers[i] = "Câu " + (i + 1);
            String selectedAnswer = Utils.questions_answer.get(i).getSelectedAnswerId();

            // Kiểm tra nếu đã chọn đáp án
            if (selectedAnswer != null && !selectedAnswer.trim().isEmpty()) {
                questionNumbers[i] += " ✔"; // Đánh dấu câu đã trả lời
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn câu hỏi");
        builder.setItems(questionNumbers, (dialog, which) -> viewPager.setCurrentItem(which, false));
        builder.create().show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final List<Question> questions;
        private final List<Fragment> fragmentList = new ArrayList<>();

        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Question> questions) {
            super(fa);
            this.questions = questions;

            for (Question question : questions) {
                fragmentList.add(QuestionExamFragment.newInstance(question));
            }
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }

        public void updateQuestions(List<Question> newQuestions) {
            questions.clear();
            questions.addAll(newQuestions);
            notifyDataSetChanged();
        }
    }
}
