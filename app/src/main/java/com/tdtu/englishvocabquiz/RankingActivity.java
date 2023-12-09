package com.tdtu.englishvocabquiz;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnShare = findViewById(R.id.btnShare);
        RecyclerView topRankView = findViewById(R.id.topRankView);

        ArrayList<TopRankModel> generateList = TopRankModel.generate(20);
        TopRankAdapter adapter = new TopRankAdapter(generateList);
        topRankView.setAdapter(adapter);
        topRankView.setLayoutManager(new LinearLayoutManager(this));
    }
}