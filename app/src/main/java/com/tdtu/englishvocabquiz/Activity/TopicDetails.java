package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.ActivityChangePasswordBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityTopicDetailsBinding;

public class TopicDetails extends AppCompatActivity {

    ActivityTopicDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String TopicName = intent.getStringExtra("TopicName");
        String NumberOfVocab = String.valueOf(intent.getIntExtra("NumberOfVocab",1));
        String IdAuthor = intent.getStringExtra("IdAuthor");
        binding.tvtopic.setText(TopicName);
        binding.tvNumWord.setText(NumberOfVocab + " thuật ngữ");
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}