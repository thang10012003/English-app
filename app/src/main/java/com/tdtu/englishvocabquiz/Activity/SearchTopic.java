package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tdtu.englishvocabquiz.Adapter.FolderAdapter;
import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Listener.Folder.OnFolderListReady;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivitySearchTopicBinding;

import java.util.ArrayList;

public class SearchTopic extends AppCompatActivity {

    private ActivitySearchTopicBinding binding;
    private TopicDatabaseService topicDatabaseService;
    private TopicAdapter topicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
        topicDatabaseService = new TopicDatabaseService(getApplicationContext());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(keyword != null){
             topicDatabaseService.SearchingTopic(keyword, new OnTopicListReady() {
                 @Override
                 public void onListReady(ArrayList<TopicModel> topicList) {
                     topicAdapter = new TopicAdapter(getApplicationContext(), topicList);
                     binding.rclTopic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                     binding.rclTopic.setAdapter(topicAdapter);
                 }
             });
        }

    }
}