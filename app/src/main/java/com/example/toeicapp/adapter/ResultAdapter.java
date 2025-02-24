package com.example.toeicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.model.Question;

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
        View view= LayoutInflater.from(context).inflate(R.layout.item_result,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question=questions.get(position);
        if(!question.getCorrect_option().equals(question.getSelectedAnswerId())){
            holder.answer.setText(question.getCorrect_option()+"❌");
            holder.number_question.setText("Câu " + (position + 1));
        }else {
            holder.answer.setText(question.getCorrect_option()+"✅");
            holder.number_question.setText("Câu " + (position + 1));
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView answer,number_question;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.answer);
            number_question = itemView.findViewById(R.id.number_question);
        }
    }
}
