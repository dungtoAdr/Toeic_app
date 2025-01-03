package com.example.toeicapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.txt_word.setText(MessageFormat.format("{0}{1}", word.substring(0, 1).toUpperCase(), word.substring(1)));
        holder.txt_pronunciation.setText(vocabulary.getPronunciation().trim());
        holder.txt_meaning.setText(vocabulary.getMeaning().trim());
        holder.audio_path.setOnClickListener(v -> {
            holder.audio_path.setEnabled(false);
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse(vocabulary.getAudioPath()));
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> holder.audio_path.setEnabled(true));
        });
    }

    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_word, txt_pronunciation, txt_meaning;
        private final ImageView audio_path;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_word = itemView.findViewById(R.id.txt_word);
            txt_pronunciation = itemView.findViewById(R.id.txt_pronunciation);
            audio_path = itemView.findViewById(R.id.img_sound);
            txt_meaning = itemView.findViewById(R.id.txt_meaning);
        }
    }
}