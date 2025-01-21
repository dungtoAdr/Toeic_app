package com.example.toeicapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toeicapp.R;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.utils.Utils;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    private Context context;
    private List<Question> questions;

    public QuestionAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.question_text.setText(question.getQuestion_text());
        if (question.getImage_path() != null) {
            holder.image_path.setVisibility(View.VISIBLE);
            Glide.with(context).load(question.getImage_path()).into(holder.image_path);
        }
        holder.radio_a.setText("A. " + question.getOption_a().trim());
        holder.radio_b.setText("B. " + question.getOption_b().trim());
        holder.radio_c.setText("C. " + question.getOption_c().trim());
        if (question.getOption_d() != null) {
            holder.radio_d.setText("D. " + question.getOption_d().trim());
        } else {
            holder.radio_d.setVisibility(View.GONE);
        }

        // Xử lý sự kiện khi người dùng chọn đáp án
        holder.radio_answer.setOnCheckedChangeListener(null); // Loại bỏ listener cũ nếu có

        holder.radio_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_a) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn A");
                    question.setSelectedAnswerId("A");
                } else if (checkedId == R.id.radio_b) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn B");
                    question.setSelectedAnswerId("B");

                } else if (checkedId == R.id.radio_c) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn C");
                    question.setSelectedAnswerId("C");

                } else if (checkedId == R.id.radio_d) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn D");
                    question.setSelectedAnswerId("D");

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView question_text;
        private RadioButton radio_a, radio_b, radio_c, radio_d;
        private RadioGroup radio_answer;
        private ImageView image_path;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question_text = itemView.findViewById(R.id.question_text);
            radio_a = itemView.findViewById(R.id.radio_a);
            radio_b = itemView.findViewById(R.id.radio_b);
            radio_c = itemView.findViewById(R.id.radio_c);
            radio_d = itemView.findViewById(R.id.radio_d);
            radio_answer = itemView.findViewById(R.id.radio_answer);
            image_path = itemView.findViewById(R.id.image_path);
        }
    }
}