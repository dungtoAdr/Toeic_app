package com.example.toeicapp.adapter;

import android.app.Activity;
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
import com.example.toeicapp.activty.DetailHistoryActivity;
import com.example.toeicapp.model.PracticeAnswer;
import com.example.toeicapp.model.PracticeSession;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoryFullAdapter extends RecyclerView.Adapter<HistoryFullAdapter.MyViewHolder> {
    private Activity activity;  // Đổi từ Context sang Activity
    private List<PracticeSession> practiceSessions;
    private ApiToeic apiToeic;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HistoryFullAdapter(Activity activity, List<PracticeSession> practiceSessions) {
        this.activity = activity;
        this.practiceSessions = practiceSessions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_full_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PracticeSession practiceSession = practiceSessions.get(position);
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        String tv_part_a = "";

        switch (practiceSession.getPart()) {
            case 1:
                tv_part_a = "Mô tả hình ảnh";
                break;
            case 2:
                tv_part_a = "Hỏi và đáp";
                break;
            case 3:
                tv_part_a = "Đoạn hội thoại";
                break;
            case 4:
                tv_part_a = "Bài nói chuyện ngắn";
                break;
            case 5:
                tv_part_a = "Điền vào câu";
                break;
            case 6:
                tv_part_a = "Điền vào đoạn văn";
                break;
            case 7:
                tv_part_a = "Đọc hiểu văn bản";
                break;
            case 8:
                tv_part_a = "Thi toàn bộ";
                break;
        }

        holder.tv_part.setText(tv_part_a);
        int avg = (int) ((float) practiceSession.getCorrect_answers() / practiceSession.getTotal_questions() * 100);
        holder.tv_avg_result.setText(avg + "%");
        holder.tv_number_question.setText("Số câu hỏi: " + practiceSession.getTotal_questions());
        holder.tv_date.setText(practiceSession.getCompleted_at());

        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent intent = new Intent(activity, DetailHistoryActivity.class);
            intent.putExtra("session_id", practiceSession.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return practiceSessions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_part, tv_avg_result, tv_number_question, tv_date;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_part = itemView.findViewById(R.id.tv_part);
            tv_avg_result = itemView.findViewById(R.id.tv_avg_result);
            tv_number_question = itemView.findViewById(R.id.tv_number_question);
            tv_date = itemView.findViewById(R.id.tv_date);
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
