package com.example.toeicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.Interface.ItemClickListener;
import com.example.toeicapp.R;
import com.example.toeicapp.activty.SlideExamActivity;
import com.example.toeicapp.model.Exam;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.MyViewHolder> {
    private Context context;
    private List<Exam> exams;

    public ExamAdapter(Context context, List<Exam> exams) {
        this.context = context;
        this.exams = exams;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exam exam = exams.get(position);
        holder.txt_name_test.setText(exam.getTitle());
        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent intent = new Intent(context, SlideExamActivity.class);
            intent.putExtra("id",exam.getId()-1);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_name_test;
        private ItemClickListener itemClickListener;
        private CardView cardView;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_test = itemView.findViewById(R.id.txt_name_test);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
