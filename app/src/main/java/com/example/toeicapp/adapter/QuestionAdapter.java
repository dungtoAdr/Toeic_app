package com.example.toeicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.model.Question;

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
        View view= LayoutInflater.from(context).inflate(R.layout.item_question,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question=questions.get(position);
        holder.question_text.setText(question.getQuestion_text());
        holder.radio_a.setText("A. "+question.getOption_a().trim());
        holder.radio_b.setText("B. "+question.getOption_b().trim());
        holder.radio_c.setText("C. "+question.getOption_c().trim());
        holder.radio_d.setText("D. "+question.getOption_d().trim());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView question_text;
        private RadioButton radio_a,radio_b,radio_c,radio_d;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question_text=itemView.findViewById(R.id.question_text);
            radio_a=itemView.findViewById(R.id.radio_a);
            radio_b=itemView.findViewById(R.id.radio_b);
            radio_c=itemView.findViewById(R.id.radio_c);
            radio_d=itemView.findViewById(R.id.radio_d);
        }
    }
}