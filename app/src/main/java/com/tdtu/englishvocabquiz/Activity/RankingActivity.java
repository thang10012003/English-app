package com.tdtu.englishvocabquiz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.Listener.Result.OnGetResultListener;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.ResultModel;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Adapter.TopRankAdapter;
import com.tdtu.englishvocabquiz.Model.TopRankModel;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {
    private TopicDatabaseService topicDatabaseService;
    private ArrayList<TopRankModel> topRankModelList;
    private UserDatabaseService userDatabaseService;

    private int i = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnShare = findViewById(R.id.btnShare);
        RecyclerView topRankView = findViewById(R.id.topRankView);

        Intent intent = getIntent();
        String idTopic = intent.getStringExtra("idTopic");

        topRankModelList = new ArrayList<>();

//        ArrayList<TopRankModel> generateList = TopRankModel.generate(20);
        topicDatabaseService = new TopicDatabaseService(getApplicationContext());
        userDatabaseService = new UserDatabaseService(getApplicationContext());
        TopRankAdapter adapter = new TopRankAdapter(topRankModelList);
        topRankView.setAdapter(adapter);
        topRankView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        topicDatabaseService.getListResultDes(idTopic, new OnGetResultListener() {
            @Override
            public void onGetReady(ArrayList<ResultModel> resultModel) {
                for(i = 0; i<resultModel.size();i+=1){


                }
            }
        });

    }
}