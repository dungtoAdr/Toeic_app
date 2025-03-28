package com.example.toeicapp.activty;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.VocabularyAdapter;
import com.example.toeicapp.model.Vocabulary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VocabularyAdapter adapter;
    private List<Vocabulary> favoriteWords;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recyclerViewFavorites);
        toolbar = findViewById(R.id.tool_bar);
        ActionToolBar();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load danh sách yêu thích từ SharedPreferences
        loadFavoriteWords();

        adapter = new VocabularyAdapter(this, favoriteWords);
        recyclerView.setAdapter(adapter);
    }

    private void loadFavoriteWords() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString("favorite_words", null);
        Type type = new TypeToken<List<Vocabulary>>() {}.getType();
        favoriteWords = gson.fromJson(json, type);

        if (favoriteWords == null) {
            favoriteWords = new ArrayList<>();
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}