package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}