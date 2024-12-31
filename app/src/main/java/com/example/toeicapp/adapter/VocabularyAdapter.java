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

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.MyViewHolder> {
    private Context context;
    private List<Vocabulary> vocabularies;

    public VocabularyAdapter(Context context, List<Vocabulary> vocabularies) {
        this.context = context;
        this.vocabularies = vocabularies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_vocabulary,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vocabulary vocabulary=vocabularies.get(position);
        holder.txt_word.setText(vocabulary.getWord().toString().trim());
        holder.txt_pronunciation.setText(vocabulary.getPronunciation().toString().trim());
        holder.txt_meaning.setText(vocabulary.getMeaning().toString().trim());
        holder.audio_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer=MediaPlayer.create(context, Uri.parse(vocabulary.getAudioPath().toString()));
                mediaPlayer.start();
            }
        });
    }
    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_word, txt_pronunciation,txt_meaning;
        private ImageView audio_path;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_word = itemView.findViewById(R.id.txt_word);
            txt_pronunciation = itemView.findViewById(R.id.txt_pronunciation);
            audio_path = itemView.findViewById(R.id.img_sound);
            txt_meaning = itemView.findViewById(R.id.txt_meaning);
        }
    }
}