package com.example.toeicapp.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toeicapp.R;
import com.example.toeicapp.model.Vocabulary;
import com.google.gson.Gson;

import java.text.MessageFormat;
import java.util.ArrayList;
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
        String audio = vocabulary.getAudioPath().trim();

        holder.txt_word.setText(MessageFormat.format("{0}{1}", word.substring(0, 1).toUpperCase(), word.substring(1)));
        holder.txt_pronunciation.setText(vocabulary.getPronunciation().trim());
        holder.txt_meaning.setText(vocabulary.getMeaning().trim());

        // Load trạng thái yêu thích
        if (vocabulary.isFavorite()) {
            Glide.with(context).load(R.drawable.star_yellow).into(holder.image_star);
        } else {
            Glide.with(context).load(R.drawable.star).into(holder.image_star);
        }

        holder.audio_path.setOnClickListener(view -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse(audio));
            mediaPlayer.start();
        });

        holder.image_star.setOnClickListener(view -> {
            boolean newFavoriteState = !vocabulary.isFavorite();
            vocabulary.setFavorite(newFavoriteState);

            // Lưu trạng thái vào SharedPreferences
            saveFavoriteWords();

            // Cập nhật UI
            if (newFavoriteState) {
                Glide.with(context).load(R.drawable.star_yellow).into(holder.image_star);
            } else {
                Glide.with(context).load(R.drawable.star).into(holder.image_star);
            }
        });
    }

    // Lưu danh sách từ vựng yêu thích vào SharedPreferences
    private void saveFavoriteWords() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        // Lọc danh sách các từ vựng yêu thích
        List<Vocabulary> favoriteWords = new ArrayList<>();
        for (Vocabulary vocab : vocabularies) {
            if (vocab.isFavorite()) {
                favoriteWords.add(vocab);
            }
        }

        String json = gson.toJson(favoriteWords);
        editor.putString("favorite_words", json);
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_word, txt_pronunciation, txt_meaning;
        private final ImageView audio_path,image_star;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_word = itemView.findViewById(R.id.txt_word);
            txt_pronunciation = itemView.findViewById(R.id.txt_pronunciation);
            audio_path = itemView.findViewById(R.id.img_sound);
            txt_meaning = itemView.findViewById(R.id.txt_meaning);
            image_star = itemView.findViewById(R.id.image_star);
        }
    }

}