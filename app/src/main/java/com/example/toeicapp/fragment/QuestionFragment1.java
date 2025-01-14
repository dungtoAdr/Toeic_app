package com.example.toeicapp.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.QuestionAdapter;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.model.Questions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionFragment1 extends Fragment {
    private static final String ARG_QUESTION = "questions";
    private MediaPlayer mediaPlayer;
    private ImageButton btnPlayPause, btnRewind, btnForward;
    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalTime;
    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private RecyclerView recyclerView;

    public static QuestionFragment1 newInstance(Questions questions) {
        QuestionFragment1 fragment = new QuestionFragment1();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupFragment();

    }

    private void setupFragment() {
        if (getArguments() != null) {
            Questions questions = (Questions) getArguments().getSerializable(ARG_QUESTION);
            if (questions != null) {
                List<Question> questionList = questions.getQuestions();
                QuestionAdapter questionAdapter = new QuestionAdapter(getContext(), questionList);
                recyclerView.setAdapter(questionAdapter);
                if (questions.getParagraph_path() != null) {
                    ScrollView scrollView =getView().findViewById(R.id.scroll_paragraph);
                    TextView paragraph_text = getView().findViewById(R.id.paragraph_text);
                    scrollView.setVisibility(View.VISIBLE);
                    paragraph_text.setText(questions.getParagraph_path());
                }
                // Cấu hình MediaPlayer với audio_path
                if (questions.getAudio_path() != null) {
                    LinearLayout line1 = getView().findViewById(R.id.line1);
                    line1.setVisibility(View.VISIBLE);
                    initializeAudioPlayer(questions.getAudio_path());
                } else {
                    btnPlayPause.setEnabled(false);
                    seekBar.setEnabled(false);
                }
            }
        }
    }

    private void initView(View view) {
        btnPlayPause = view.findViewById(R.id.btn_play_pause);
        btnRewind = view.findViewById(R.id.btn_rewind);
        btnForward = view.findViewById(R.id.btn_forward);
        seekBar = view.findViewById(R.id.seekBar);
        tvCurrentTime = view.findViewById(R.id.tv_current_time);
        tvTotalTime = view.findViewById(R.id.tv_total_time);
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void initializeAudioPlayer(String audioPath) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            tvTotalTime.setText(formatTime(mediaPlayer.getDuration()));

            // Sự kiện khi nhấn nút Play/Pause
            btnPlayPause.setOnClickListener(v -> {
                if (isPlaying) {
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.pause);
                    handler.post(updateSeekBar); // Bắt đầu cập nhật SeekBar khi phát
                }
                isPlaying = !isPlaying;
            });

            // Sự kiện tua lại
            btnRewind.setOnClickListener(v -> {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(Math.max(currentPosition - 5000, 0)); // Tua lại 5 giây
            });

            // Sự kiện tua tới
            btnForward.setOnClickListener(v -> {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(Math.min(currentPosition + 5000, mediaPlayer.getDuration())); // Tua tới 5 giây
            });

            // Cập nhật tiến trình SeekBar
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.setOnCompletionListener(mp -> {
                btnPlayPause.setImageResource(R.drawable.play);
                isPlaying = false;
                seekBar.setProgress(0); // Reset SeekBar về 0 khi phát xong
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        tvCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                tvCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this, 1000); // Tiếp tục cập nhật mỗi giây
            }
        }
    };

    private String formatTime(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateSeekBar); // Dừng cập nhật khi Fragment bị hủy
    }
}
