package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tdtu.englishvocabquiz.Enum.TopicType;
import com.tdtu.englishvocabquiz.Listener.Topic.OnGetTopicListener;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityAddTopicBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityUpdateTopicBinding;

public class UpdateTopic extends AppCompatActivity {
    private ActivityUpdateTopicBinding binding;
    private Intent intent;
    private TopicDatabaseService topicDatabaseService;
    private TopicModel topicModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topicDatabaseService = new TopicDatabaseService(getApplicationContext());
        intent = getIntent();
        String IdTopic = intent.getStringExtra("IdTopic");

        topicModel = topicDatabaseService.getTopicByID(IdTopic, new OnGetTopicListener() {
            @Override
            public void onListReady(TopicModel topicModel) {
                binding.edtTopic.setText(topicModel.getTopicName().toString());
                if(topicModel.getMode().toString().equals(TopicType.PUBLIC.toString())){
                    binding.tgMode.setChecked(true);
                }else {
                    binding.tgMode.setChecked(false);
                }
                binding.edtDescription.setText(topicModel.getDescription().toString());
            }
        });
        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicModel = topicDatabaseService.getTopicByID(IdTopic, new OnGetTopicListener() {
                    @Override
                    public void onListReady(TopicModel topicModel) {
                        topicModel.setTopicName(binding.edtTopic.getText().toString());
                        if(binding.tgMode.isChecked()){
                            topicModel.setMode(TopicType.PUBLIC.toString());
                        }else {
                            topicModel.setMode(TopicType.PRIVATE.toString());
                        }
                        topicModel.setDescription(binding.edtDescription.getText().toString());
                        topicDatabaseService.updateTopic(IdTopic,topicModel);
                    }
                });
                finish();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}