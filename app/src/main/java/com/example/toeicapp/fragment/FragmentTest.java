package com.example.toeicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.toeicapp.R;
import com.example.toeicapp.activty.SlideExamActivity;

public class FragmentTest extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment,container,false);
    }

    private CardView full_test;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        full_test = view.findViewById(R.id.full_test);
        full_test.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), SlideExamActivity.class);
            intent.putExtra("id",1);
            startActivity(intent);
        });
    }
}
