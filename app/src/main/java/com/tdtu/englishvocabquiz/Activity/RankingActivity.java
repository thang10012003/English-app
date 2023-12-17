package com.tdtu.englishvocabquiz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

//        topRankModelList = TopRankModel.generate(20);
        topicDatabaseService = new TopicDatabaseService(getApplicationContext());
        userDatabaseService = new UserDatabaseService(getApplicationContext());
        topicDatabaseService.getListResultDes(idTopic, new OnGetResultListener() {
            @Override
            public void onGetReady(ArrayList<ResultModel> resultModel) {
//                                        Log.e("TAG", "list size: "+resultModel.size());
//                                        Log.e("TAG", "list size: "+resultModel.get(0).getNumRight());
//                                        Log.e("TAG", "list size: "+resultModel.get(1).getNumRight());

                if(resultModel!=null && resultModel.size() >0){

                        TopRankAdapter adapter = new TopRankAdapter(getApplicationContext(), resultModel);
                        topRankView.setAdapter(adapter);
                        topRankView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}