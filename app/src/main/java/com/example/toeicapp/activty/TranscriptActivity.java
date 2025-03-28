package com.example.toeicapp.activty;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.toeicapp.R;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TranscriptActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton btnPlayPause, btnRewind, btnForward;
    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalTime, text1, text2, text3, text4;
    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private TextView questionText, toolbar_title;
    private ImageView img_question;
    private RadioGroup radio_answer;
    private RadioButton radio_a, radio_b, radio_c, radio_d;
    private LinearLayout line1;
    private ScrollView scroll_paragraph;
    private CardView card_view_part5;
    private Toolbar toolbar;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiToeic apiToeic;
    private Question question = new Question();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);
        initView();
        initData();
        ActionToolBar();
        toolbar_title.setOnClickListener(v -> showAnswerBottomDialog());
    }

    private void showAnswerBottomDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_answer, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView txt_transcript = bottomSheetView.findViewById(R.id.txt_transcript);
        String formattedText = question.getTranscript().replaceAll("(\\(A\\)|\\(B\\)|\\(C\\)|\\(D\\)|(?<!\\()A|(?<!\\()B|(?<!\\()C|(?<!\\()D)", "\n$1");

        txt_transcript.setText(formattedText.trim());

        // Lấy View gốc của BottomSheet để điều chỉnh chiều cao
        View parent = (View) bottomSheetView.getParent();
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);

        // Đặt chiều cao của dialog là 50% màn hình
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        parent.getLayoutParams().height = screenHeight / 2;

        // Áp dụng thay đổi
        parent.requestLayout();

        // Hiển thị dialog
        bottomSheetDialog.show();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initData() {
        int id = getIntent().getIntExtra("id", 1);
        compositeDisposable.add(apiToeic.getQuestion_id(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questionIdModel -> {
                            if (questionIdModel.isSuccess()) {
                                question = questionIdModel.getQuestion();
                                setupActivity();
                            } else {
                                Log.d("TAGgggg_transcript", questionIdModel.getMessage());
                            }
                        }, throwable -> {
                            Log.d("TAGgggg_transcript", throwable.getMessage());
                        }
                )
        );
    }

    private void setupActivity() {
        if (question != null) {
            if (question.getImage_path() != null) {
                img_question.setVisibility(View.VISIBLE);
                Glide.with(this).load(question.getImage_path()).into(img_question);
            }
            if (question.getAudio_path() != null) {
                line1.setVisibility(View.VISIBLE);
                initializeAudioPlayer(question.getAudio_path());
            } else {
                btnPlayPause.setEnabled(false);
                seekBar.setEnabled(false);
            }
            if (question.getQuestion_text() != null) {
                CardView card_view_part5 = findViewById(R.id.card_view_part5);
                card_view_part5.setVisibility(View.VISIBLE);
                questionText.setText(question.getQuestion_text());
                text1.setText("A." + question.getOption_a());
                text2.setText("B." + question.getOption_b());
                text3.setText("C." + question.getOption_c());
                text4.setText("D." + question.getOption_d());
            }
            if (question.getParagraph_path() != null) {
                if (questionText == null) {
                    questionText.setVisibility(View.GONE);
                }
                scroll_paragraph.setVisibility(View.VISIBLE);
                TextView paragraph_text = findViewById(R.id.paragraph_text);
                paragraph_text.setText(question.getParagraph_path());
                card_view_part5.setVisibility(View.VISIBLE);
                questionText.setText(question.getQuestion_text());
                text1.setText("A." + question.getOption_a());
                text2.setText("B." + question.getOption_b());
                text3.setText("C." + question.getOption_c());
                text4.setText("D." + question.getOption_d());
            }
        }
    }

    private void initView() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.tool_bar);
        questionText = findViewById(R.id.txt_quest);
        img_question = findViewById(R.id.img_question);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnRewind = findViewById(R.id.btn_rewind);
        btnForward = findViewById(R.id.btn_forward);
        seekBar = findViewById(R.id.seekBar);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        radio_a = findViewById(R.id.radio_a);
        radio_b = findViewById(R.id.radio_b);
        radio_c = findViewById(R.id.radio_c);
        radio_d = findViewById(R.id.radio_d);
        radio_answer = findViewById(R.id.radio_answer);
        line1 = findViewById(R.id.line1);
        scroll_paragraph = findViewById(R.id.scroll_paragraph);
        card_view_part5 = findViewById(R.id.card_view_part5);
        if (questionText.getText().toString().isEmpty()) {
            questionText.setVisibility(View.GONE);
        }
        String answer_user = getIntent().getStringExtra("user_answer");
        if (answer_user != null) {
            switch (answer_user) {
                case "A":
                    radio_a.setChecked(true);
                    break;
                case "B":
                    radio_b.setChecked(true);
                    break;
                case "C":
                    radio_c.setChecked(true);
                    break;
                case "D":
                    radio_d.setChecked(true);
                    break;
                default:
                    // Không chọn radio nào cả
                    radio_a.setChecked(false);
                    radio_b.setChecked(false);
                    radio_c.setChecked(false);
                    radio_d.setChecked(false);
                    break;
            }
        } else {
            // Nếu answer_user là null, đảm bảo không chọn radio nào
            radio_a.setChecked(false);
            radio_b.setChecked(false);
            radio_c.setChecked(false);
            radio_d.setChecked(false);
        }
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
                    handler.post(updateSeekBar);
                }
                isPlaying = !isPlaying;
            });

            // Sự kiện tua lại
            btnRewind.setOnClickListener(v -> {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(Math.max(currentPosition - 5000, 0));
            });

            // Sự kiện tua tới
            btnForward.setOnClickListener(v -> {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(Math.min(currentPosition + 5000, mediaPlayer.getDuration()));
            });

            // Cập nhật tiến trình SeekBar
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.setOnCompletionListener(mp -> {
                btnPlayPause.setImageResource(R.drawable.play);
                isPlaying = false;
                seekBar.setProgress(0);
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
                handler.postDelayed(this, 1000);
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
                btnPlayPause.setImageResource(R.drawable.play);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateSeekBar);
    }
}