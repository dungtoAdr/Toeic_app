package com.example.toeicapp.activty;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.ResultAdapter;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.model.Questions;
import com.example.toeicapp.utils.Utils;

import java.util.List;

public class ResultActivity1 extends AppCompatActivity {
    private RecyclerView recycler_answer;
    private TextView result_answer;
    private ResultAdapter resultAdapter;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result_answer = findViewById(R.id.result_answer);
        recycler_answer = findViewById(R.id.recycler_answer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_answer.setLayoutManager(layoutManager);
        checkAnswers_1(Utils.questions_model_answer);
        resultAdapter = new ResultAdapter(this, Utils.questions_answer);
        recycler_answer.setAdapter(resultAdapter);
        result_answer.setText("Kết quả: " + score + "/" + Utils.questions_answer.size());
    }

    public void checkAnswers_1(List<Questions> questionss) {
        score = 0;
        Utils.questions_answer.clear();

        for (Questions questions : questionss) {
            List<Question> questionList = questions.getQuestions();
            Utils.questions_answer.addAll(questionList);

            for (Question question : questionList) {
                if (question.getSelectedAnswerId() != null && question.getSelectedAnswerId().equals(question.getCorrect_option())) {
                    score++;
                }
            }
        }
    }

}