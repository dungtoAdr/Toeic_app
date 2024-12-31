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
import com.example.toeicapp.activty.VocabularyActivity;

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
        onClickItemLisening();
        onCLickItemVocabulary();
        onCLickItemGrammar();

    }

    private void onCLickItemVocabulary() {
        item_vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), VocabularyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onCLickItemGrammar() {
        item_grammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void onClickItemLisening() {
        item_listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Listening", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickItemReading() {
        item_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reading", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        item_listening = view.findViewById(R.id.item_listening);
        item_reading = view.findViewById(R.id.item_reading);
        item_vocabulary = view.findViewById(R.id.item_vocabulary);
        item_grammar = view.findViewById(R.id.item_grammar);
    }
}
