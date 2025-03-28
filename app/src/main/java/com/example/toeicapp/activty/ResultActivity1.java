package com.example.toeicapp.activty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.ResultAdapter;
import com.example.toeicapp.model.ApiResponse;
import com.example.toeicapp.model.PracticeAnswer;
import com.example.toeicapp.model.PracticeSession;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.model.Questions;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity1 extends AppCompatActivity {
    private final String TAG = "Result1";
    private RecyclerView recycler_answer;
    private TextView result_answer, tv_avg_result, tv_part, tv_avg_total;
    private ResultAdapter resultAdapter;
    private int score;
    private ApiToeic apiToeic;
    private Toolbar toolbar;
    private Button bt_continue;
    private int part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        checkAnswers_1(Utils.questions_model_answer);
        submitResult();
        setData();
        ActionToolBar();
        AvgTotal();
        String tv_part_a = "";
        switch (part) {
            case 1:
                tv_part_a = "Mô tả hình ảnh (Nghe Hiểu)";
                break;
            case 2:
                tv_part_a = "Hỏi và đáp (Nghe Hiểu)";
                break;
            case 3:
                tv_part_a = "Đoạn hội thoại (Nghe Hiểu)";
                break;
            case 4:
                tv_part_a = "Bài nói chuện ngắn (Nghe Hiểu)";
                break;
            case 5:
                tv_part_a = "Điền vào câu (Đọc Hiểu)";
                break;
            case 6:
                tv_part_a = "Điền vào đoạn văn (Đọc Hiểu)";
                break;
            case 7:
                tv_part_a = "Đọc hiểu văn bản (Đọc Hiểu)";
                break;
            case 8:
                tv_part_a = "Thi toàn bộ (Bài Thi)";
                break;
        }
        tv_part.setText(tv_part_a);
        bt_continue.setOnClickListener(v ->{
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        result_answer = findViewById(R.id.result_answer);
        recycler_answer = findViewById(R.id.recycler_answer);
        toolbar = findViewById(R.id.tool_bar);
        tv_avg_result = findViewById(R.id.tv_avg_result);
        bt_continue = findViewById(R.id.bt_continue);
        tv_part = findViewById(R.id.tv_part);
        tv_avg_total = findViewById(R.id.tv_avg_total);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_answer.setLayoutManager(layoutManager);

    }
    private void setData(){
        resultAdapter = new ResultAdapter(this, Utils.questions_answer);
        recycler_answer.setAdapter(resultAdapter);
        result_answer.setText("Kết quả: " + score + "/" + Utils.questions_answer.size());
        int avg = (int) ((float) score / Utils.questions_answer.size() * 100);
        tv_avg_result.setText(avg + "%");
    }
    public void AvgTotal(){
        int total = 0;
        int total_correct_answer = 0;
        for (int i=0;i<Utils.practiceSessions.size();i++){
            PracticeSession practiceSession = Utils.practiceSessions.get(i);
            if(practiceSession.getPart() == part){
                total += practiceSession.getTotal_questions();
                total_correct_answer += practiceSession.getCorrect_answers();
            }
        }
        int avg_total = (total > 0) ? Math.round(((float) total_correct_answer / total) * 100) : 0;
        tv_avg_total.setText(avg_total+"%");
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private boolean checker(String a, String b) {
        return Objects.equals(a, b);
    }


    private void submitResult() {
        String userId = Utils.current_user_id;
        List<Question> questions = Utils.questions_answer;
        List<PracticeAnswer> answers = new ArrayList<>();
        int data = getIntent().getIntExtra("part", 1);
        if (data > 7) {
            part = data;
        } else {
            part = questions.get(0).getCategory_id();
        }
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            Log.d(TAG, question.getId() + question.getSelectedAnswerId() + checker(question.getSelectedAnswerId(), question.getCorrect_option()));
            if (question.getSelectedAnswerId() != null) {
                answers.add(new PracticeAnswer(question.getId(), question.getSelectedAnswerId(), checker(question.getSelectedAnswerId(), question.getCorrect_option())));
            } else {
                answers.add(new PracticeAnswer(question.getId(), "null", false));
            }
        }

        // Tạo request
        PracticeSession request = new PracticeSession(userId, part, answers);

        // Gửi API
        Call<ApiResponse> call = apiToeic.submitPracticeResult(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Lưu kết quả thành công!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Kết quả: " + response.body().getMessage());
                } else {
                    Toast.makeText(getApplicationContext(), "Lỗi khi gửi dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.getMessage());
            }
        });
    }
    public void checkAnswers_1(List<Questions> questionss) {
        score = 0;
        Utils.questions_answer.clear();
        for (Questions questions : questionss) {
            List<Question> questionList = questions.getQuestions();
            Utils.questions_answer.addAll(questionList);

            for (Question question : questionList) {
                if (Objects.equals(question.getSelectedAnswerId(), question.getCorrect_option())) {
                    score++;
                }
            }
        }
    }


}