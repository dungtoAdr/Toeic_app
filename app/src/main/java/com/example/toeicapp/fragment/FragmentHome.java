package com.example.toeicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.toeicapp.R;
import com.example.toeicapp.activty.GrammarActivity;
import com.example.toeicapp.activty.ListenningActivity;
import com.example.toeicapp.activty.ReadingActivity;
import com.example.toeicapp.activty.SlideActivity;
import com.example.toeicapp.activty.TopicActivity;

public class FragmentHome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }


    private CardView item_reading, item_listening, item_vocabulary, item_grammar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        onClickItemReading();
        onClickItemListening();
        onCLickItemVocabulary();
        onCLickItemGrammar();

    }

    private void onCLickItemVocabulary() {
        item_vocabulary.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), TopicActivity.class);
            startActivity(intent);
        });
    }

    private void onCLickItemGrammar() {
        item_grammar.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), GrammarActivity.class);
            startActivity(intent);
        });
    }

    private void onClickItemListening() {
        item_listening.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Listening", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(), ListenningActivity.class);
            startActivity(intent);
        });
    }

    private void onClickItemReading() {
        item_reading.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reading", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(), ReadingActivity.class);
            startActivity(intent);
        });
    }

    private void initView(View view) {
        item_listening = view.findViewById(R.id.item_listening);
        item_reading = view.findViewById(R.id.item_reading);
        item_vocabulary = view.findViewById(R.id.item_vocabulary);
        item_grammar = view.findViewById(R.id.item_grammar);
    }
}
