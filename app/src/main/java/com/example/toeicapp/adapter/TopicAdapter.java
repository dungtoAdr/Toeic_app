package com.example.toeicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.Interface.ItemClickListener;
import com.example.toeicapp.R;
import com.example.toeicapp.activty.VocabularyActivity;
import com.example.toeicapp.model.Topic;

import java.text.MessageFormat;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> {
    private final Context context;
    private final List<Topic> topics;

    public TopicAdapter(Context context, List<Topic> topics) {
        this.context = context;
        this.topics = topics;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Topic topic=topics.get(position);
        holder.txt_id.setText(MessageFormat.format("{0}", topic.getId()));
        holder.txt_name.setText(topic.getName());
        holder.setItemClickListener((view, position1, isLongClick) -> {
            if (!isLongClick){
                Intent intent=new Intent(context, VocabularyActivity.class);
                intent.putExtra("topic_id",topic.getId());
                intent.putExtra("topic_name",topic.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txt_id;
        private final TextView txt_name;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id=itemView.findViewById(R.id.txt_id);
            txt_name=itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}
