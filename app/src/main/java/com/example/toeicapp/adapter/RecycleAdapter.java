package com.example.toeicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.Interface.ItemClickListener;
import com.example.toeicapp.R;
import com.example.toeicapp.activty.SlideActivity;
import com.example.toeicapp.activty.SlideActivity1;
import com.example.toeicapp.activty.WebActivity;
import com.example.toeicapp.model.Grammar;

import java.text.MessageFormat;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private final Context context;
    private final List<Grammar> grammars;

    public RecycleAdapter(Context context, List<Grammar> grammars) {
        this.context = context;
        this.grammars = grammars;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Grammar grammar = grammars.get(position);
        holder.txt_id.setText(MessageFormat.format("{0}", grammar.getId()));
        holder.txt_name.setText(grammar.getName());
        holder.setItemClickListener((view, position1, isLongClick) -> {
            if (grammar.getUrl() != null) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", grammar.getUrl());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } else if (grammar.isListenning()) {
                switch (grammar.getId()) {
                    case 1: {
                        Intent intent = new Intent(context, SlideActivity.class);
                        intent.putExtra("part", 1);
                        context.startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(context, SlideActivity.class);
                        intent.putExtra("part", 2);
                        context.startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(context, SlideActivity1.class);
                        intent.putExtra("part", 3);
                        context.startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(context, SlideActivity1.class);
                        intent.putExtra("part", 4);
                        context.startActivity(intent);
                        break;
                    }
                }
            } else {
                Toast.makeText(context, grammar.getName(), Toast.LENGTH_SHORT).show();
                if (grammar.getId() == 1) {
                    Intent intent = new Intent(context, SlideActivity.class);
                    intent.putExtra("part", 5);
                    Log.d("TAG_part5", 5 + "");
                    context.startActivity(intent);
                } else if (grammar.getId() == 2) {
                    Intent intent = new Intent(context, SlideActivity1.class);
                    intent.putExtra("part", 6);
                    Log.d("TAG_part6", 6 + "");
                    context.startActivity(intent);
                } else if (grammar.getId() == 3) {
                    Intent intent = new Intent(context, SlideActivity1.class);
                    intent.putExtra("part", 7);
                    Log.d("TAG_part6", 7 + "");
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return grammars.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txt_id;
        private final TextView txt_name;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_name = itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
