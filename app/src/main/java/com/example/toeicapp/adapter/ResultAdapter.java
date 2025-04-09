package com.example.toeicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.Interface.ItemClickListener;
import com.example.toeicapp.R;
import com.example.toeicapp.activty.TranscriptActivity;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.utils.Utils;
import com.google.gson.Gson;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private Context context;
    private List<Question> questions;

    public ResultAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = questions.get(position);
        if(question.getSelectedAnswerId()!=null){
            if (!question.getCorrect_option().equals(question.getSelectedAnswerId())) {
                holder.answer.setText("Đáp án đúng: " + question.getCorrect_option() + " ❌");
                holder.number_question.setText("Câu hỏi " + (position + 1));
            } else {
                holder.answer.setText("Đáp án đúng: " + question.getCorrect_option() + " ✅");
                holder.number_question.setText("Câu hỏi " + (position + 1));
            }
        } else if(question.getUser_answer()!=null){
            if (!question.getCorrect_option().equals(question.getUser_answer())) {
                holder.answer.setText("Đáp án đúng: " + question.getCorrect_option() + " ❌");
                holder.number_question.setText("Câu hỏi " + (position + 1));
            } else {
                holder.answer.setText("Đáp án đúng: " + question.getCorrect_option() + " ✅");
                holder.number_question.setText("Câu hỏi " + (position + 1));
            }
        } else {
            holder.answer.setText("Đáp án đúng: " + question.getCorrect_option() + " ❌");
            holder.number_question.setText("Câu hỏi " + (position + 1));
        }

        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent intent=new Intent(context, TranscriptActivity.class);
            intent.putExtra("id",question.getId());
            if(question.getSelectedAnswerId()!=null){
                intent.putExtra("user_answer",question.getSelectedAnswerId());
            } else if(question.getUser_answer()!=null){
                intent.putExtra("user_answer",question.getUser_answer());
            } else {
                intent.putExtra("user_answer", (String) null);
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView answer, number_question;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.answer);
            number_question = itemView.findViewById(R.id.number_question);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
