package com.example.toeicapp.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.toeicapp.R;
import com.example.toeicapp.model.Question;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class QuestionFragment extends Fragment {
    private static final String ARG_QUESTION = "question";
    private MediaPlayer mediaPlayer;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question); // Ensure Question implements Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView questionText = view.findViewById(R.id.txt_quest);
        ImageView questionImage = view.findViewById(R.id.img_question);
        Button playAudioButton = view.findViewById(R.id.btn_play_audio);

        if (getArguments() != null) {
            Question question = (Question) getArguments().getSerializable(ARG_QUESTION);
            if (question != null) {
                questionText.setText(question.getQuestion_text());
                if (question.getImage_path() != null) {
                    Picasso.get().load(question.getImage_path()).into(questionImage);
                }

                // Cấu hình MediaPlayer với audio_path
                if (question.getAudio_path() != null) {
                    playAudioButton.setOnClickListener(v -> playAudio(question.getAudio_path()));
                } else {
                    playAudioButton.setEnabled(false);
                }
            }
        }
    }

    private void playAudio(String audioPath) {
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Giải phóng MediaPlayer nếu đã sử dụng trước đó
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
