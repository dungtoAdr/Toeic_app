package com.example.toeicapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.model.Vocabulary;

import java.text.MessageFormat;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.MyViewHolder> {
    private final Context context;
    private final List<Vocabulary> vocabularies;

    public VocabularyAdapter(Context context, List<Vocabulary> vocabularies) {
        this.context = context;
        this.vocabularies = vocabularies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vocabulary, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vocabulary vocabulary = vocabularies.get(position);
        String word = vocabulary.getWord().trim();

        // Hiển thị mặt trước
        holder.txt_word.setText(MessageFormat.format("{0}{1}", word.substring(0, 1).toUpperCase(), word.substring(1)));
        holder.txt_pronunciation.setText(vocabulary.getPronunciation().trim());

        // Lắng nghe sự kiện click để lật mặt của item
        holder.itemView.setOnClickListener(v -> {
            // Lật mặt trước và mặt sau
            if (holder.frontLayout.getVisibility() == View.VISIBLE) {
                holder.frontLayout.setVisibility(View.GONE);
                holder.backLayout.setVisibility(View.VISIBLE);
                MediaPlayer mediaPlayer=MediaPlayer.create(context,Uri.parse(vocabulary.getAudioPath()));
                mediaPlayer.start();
            } else {
                holder.frontLayout.setVisibility(View.VISIBLE);
                holder.backLayout.setVisibility(View.GONE);
            }
        });

        // Hiển thị nghĩa từ khi mặt sau được lật
        holder.txt_meaning.setText(vocabulary.getMeaning().trim());
    }


    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_word, txt_pronunciation, txt_meaning;
        private final ImageView audio_path;
        private final LinearLayout frontLayout, backLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_word = itemView.findViewById(R.id.txt_word);
            txt_pronunciation = itemView.findViewById(R.id.txt_pronunciation);
            audio_path = itemView.findViewById(R.id.img_sound);
            txt_meaning = itemView.findViewById(R.id.txt_meaning);

            frontLayout = itemView.findViewById(R.id.frontLayout);
            backLayout = itemView.findViewById(R.id.backLayout);
        }
    }

}