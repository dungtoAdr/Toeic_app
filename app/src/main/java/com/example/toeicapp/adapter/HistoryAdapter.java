package com.example.toeicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.model.PracticeSession;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context context;
    private List<PracticeSession> practiceSessions;

    public HistoryAdapter(Context context, List<PracticeSession> practiceSessions) {
        this.context = context;
        this.practiceSessions = practiceSessions;
    }

    public void setPracticeSessions(List<PracticeSession> practiceSessions) {
        this.practiceSessions = practiceSessions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PracticeSession practiceSession = practiceSessions.get(position);
        String tv_part_a = "";
        switch (practiceSession.getPart()) {
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
        holder.tv_part.setText(tv_part_a);
        int avg = (int) ((float)practiceSession.getCorrect_answers()/practiceSession.getTotal_questions()*100);
        holder.tv_avg_result.setText(avg+"%");
    }

    @Override
    public int getItemCount() {
        return practiceSessions.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_part, tv_avg_result;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_part = itemView.findViewById(R.id.tv_part);
            tv_avg_result = itemView.findViewById(R.id.tv_avg_result);
        }
    }
}
