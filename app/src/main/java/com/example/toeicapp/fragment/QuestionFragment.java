package com.example.toeicapp.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.toeicapp.R;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionFragment extends Fragment {
    private static final String ARG_QUESTION = "question";
    private MediaPlayer mediaPlayer;
    private ImageButton btnPlayPause, btnRewind, btnForward;
    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalTime, text1, text2, text3, text4;
    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private TextView questionText;
    private ImageView questionImage;
    private RadioGroup radio_answer;

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
        initView(view);
        setupFragment();
    }

    private void setupFragment() {
        if (getArguments() != null) {
            Question question = (Question) getArguments().getSerializable(ARG_QUESTION);
            if (question != null) {
                if (question.getQuestion_text() != null) {
                    CardView card_view_part5 = getView().findViewById(R.id.card_view_part5);
                    card_view_part5.setVisibility(View.VISIBLE);
                    questionText.setText(question.getQuestion_text());
                    text1.setText("A." + question.getOption_a());
                    text2.setText("B." + question.getOption_b());
                    text3.setText("C." + question.getOption_c());
                    text4.setText("D." + question.getOption_d());
                }
                if (question.getImage_path() != null) {
                    questionImage.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(question.getImage_path()).into(questionImage);
                }

                // Cấu hình MediaPlayer với audio_path
                if (question.getAudio_path() != null) {
                    LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.line1);
                    linearLayout.setVisibility(View.VISIBLE);
                    initializeAudioPlayer(question.getAudio_path());
                } else {
                    btnPlayPause.setEnabled(false);
                    seekBar.setEnabled(false);
                }
            }
        }
    }

    private void initView(View view) {
        questionText = view.findViewById(R.id.txt_quest);
        questionImage = view.findViewById(R.id.img_question);
        btnPlayPause = view.findViewById(R.id.btn_play_pause);
        btnRewind = view.findViewById(R.id.btn_rewind);
        btnForward = view.findViewById(R.id.btn_forward);
        seekBar = view.findViewById(R.id.seekBar);
        tvCurrentTime = view.findViewById(R.id.tv_current_time);
        tvTotalTime = view.findViewById(R.id.tv_total_time);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        radio_answer = view.findViewById(R.id.radio_answer);
        if (questionText.getText().toString().isEmpty()) {
            questionText.setVisibility(View.GONE);
        }
        Question question = (Question) getArguments().getSerializable(ARG_QUESTION);
        radio_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_a) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn A");
                    question.setSelectedAnswerId("A");
                } else if (checkedId == R.id.radio_b) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn B");
                    question.setSelectedAnswerId("B");

                } else if (checkedId == R.id.radio_c) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn C");
                    question.setSelectedAnswerId("C");

                } else if (checkedId == R.id.radio_d) {
                    Log.d("TAG_radio", question.getCorrect_option() + "Chọn D");
                    question.setSelectedAnswerId("D");

                }
            }
        });


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
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setImageResource(R.drawable.play); // Update UI to show the play button
            }
        }
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
