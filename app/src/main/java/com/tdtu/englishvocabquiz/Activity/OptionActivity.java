package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.ActivityAddTopicBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityOptionBinding;

public class OptionActivity extends AppCompatActivity {

    ActivityOptionBinding binding;
    String IdTopic = "";
    String featureType = "";
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_option);
        binding = ActivityOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        IdTopic = getIntent().getStringExtra("IdTopic");
        featureType = getIntent().getStringExtra("featureType");

        switch (featureType){
            case "card":{
                intent = new Intent(getApplicationContext(), FlashCardActivity.class);
                break;
            }
            case "choice":{
                intent = new Intent(getApplicationContext(), MultipleChoiceActivity.class);
                break;
            }
            case "typeWord":{
                intent = new Intent(getApplicationContext(), TypeWordActivity.class);
                break;
            }
            default:{
                break;
            }
        }

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean englishMode = !binding.radioVN.isChecked();
                Boolean shuffle = binding.shuffleSwitch.isEnabled();
                intent.putExtra("englishMode", englishMode);
                intent.putExtra("shuffle", shuffle);
                intent.putExtra("IdTopic", IdTopic);
                startActivity(intent);
                finish();
            }
        });
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}