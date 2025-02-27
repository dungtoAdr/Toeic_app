package com.example.toeicapp.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.toeicapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentTranslate extends Fragment {

    private TextInputEditText inputText;
    private TextInputEditText outputText;
    private TextInputLayout inputLayout;
    private TextInputLayout outputLayout;
    private Button translateButton, micButton, cameraButton;
    private Translator translator;
    private TextRecognizer textRecognizer;
    private ImageView swap_img;
    private boolean isEnglishToVietnamese = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.translate_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
//        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
//        translator.downloadModelIfNeeded(conditions)
//                .addOnSuccessListener(unused -> Log.d("TAG_translate", "Mô hình dịch đã sẵn sàng!"))
//                .addOnFailureListener(e -> Log.d("TAG_translate", "Lỗi tải mô hình: " + e.getMessage()));

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        initializeTranslator();
        translateButton.setOnClickListener(v -> translateText());
        micButton.setOnClickListener(v -> startSpeechRecognition());
        cameraButton.setOnClickListener(v -> Toast.makeText(getContext(), "OCR chưa hỗ trợ!", Toast.LENGTH_SHORT).show());
        swap_img.setOnClickListener(v -> swapLanguages());
    }

    private void initView(View view) {
        inputText = view.findViewById(R.id.inputText);
        outputText = view.findViewById(R.id.outputText);
        translateButton = view.findViewById(R.id.translateButton);
        micButton = view.findViewById(R.id.micButton);
        cameraButton = view.findViewById(R.id.cameraButton);
        swap_img = view.findViewById(R.id.swap_img);
        inputLayout = view.findViewById(R.id.inputLayout);
        outputLayout = view.findViewById(R.id.outputLayout);
    }

    private void translateText() {
        String text = inputText.getText().toString();
        if (text.isEmpty()) {
            outputText.setText("Vui lòng nhập văn bản");
            return;
        }

        outputText.setText("Đang dịch...");
        translator.translate(text)
                .addOnSuccessListener(outputText::setText)
                .addOnFailureListener(e -> outputText.setText("Lỗi dịch: " + e.getMessage()));
    }

    private void initializeTranslator() {
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(isEnglishToVietnamese ? TranslateLanguage.ENGLISH : TranslateLanguage.VIETNAMESE)
                .setTargetLanguage(isEnglishToVietnamese ? TranslateLanguage.VIETNAMESE : TranslateLanguage.ENGLISH)
                .build();

        if (translator != null) {
            translator.close();
        }

        translator = Translation.getClient(options);
        translator.downloadModelIfNeeded().addOnSuccessListener(unused -> Log.d("TAG_translate", "Mô hình dịch đã sẵn sàng!"));
    }

    private void swapLanguages() {
//        CardView card_input, card_output;
//        card_input = getView().findViewById(R.id.card_input);
//        card_output = getView().findViewById(R.id.card_output);
//
//        float startXInput = card_input.getY();
//        float startXOutput = card_output.getY();
//
//        ObjectAnimator animInput = ObjectAnimator.ofFloat(card_input, "y", startXOutput);
//        ObjectAnimator animOutput = ObjectAnimator.ofFloat(card_output, "y", startXInput);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animInput, animOutput);
//        animatorSet.setDuration(500); // Đặt thời gian chạy animation
//        animatorSet.start();

        isEnglishToVietnamese = !isEnglishToVietnamese;
        inputLayout.setHint(isEnglishToVietnamese ? "Tiếng Anh" : "Tiếng Việt");
        outputLayout.setHint(isEnglishToVietnamese ? "Tiếng Việt" : "Tiếng Anh");
        String tempText = inputText.getText().toString();
        inputText.setText(outputText.getText().toString());
        outputText.setText(tempText);
        initializeTranslator();

//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//        });
    }


    // Xử lý giọng nói
    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        speechRecognitionLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> speechRecognitionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty()) {
                        inputText.setText(matches.get(0));
                    }
                } else {
                    Toast.makeText(getContext(), "Không nhận diện được giọng nói", Toast.LENGTH_SHORT).show();
                }
            });

    // Xử lý nhận diện văn bản từ ảnh
    private void captureImage() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageCaptureLauncher.launch(intent);
        }
    }

    private final ActivityResultLauncher<Intent> imageCaptureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    if (bitmap != null) {
                        processImage(bitmap);
                    } else {
                        Toast.makeText(getContext(), "Không nhận diện được ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void processImage(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        textRecognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    String detectedText = visionText.getText();
                    if (detectedText.isEmpty()) {
                        Toast.makeText(getContext(), "Không tìm thấy văn bản", Toast.LENGTH_SHORT).show();
                    } else {
                        inputText.setText(detectedText);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi nhận diện ảnh: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        translator.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureImage();
        }
    }
}
