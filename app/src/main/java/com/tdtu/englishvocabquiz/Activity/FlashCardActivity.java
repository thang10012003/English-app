package com.tdtu.englishvocabquiz.Activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.CustomTextToSpeech;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

import java.util.ArrayList;
import java.util.Collections;

public class FlashCardActivity extends AppCompatActivity {
    TopicDatabaseService topicDatabaseService;
    private AnimatorSet front_anim;
    private AnimatorSet back_anim;
    private boolean isFront = true;
    Handler handler = new Handler();

    ArrayList<VocabularyModel> test = new ArrayList<>();
    int indexVocab = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        String IdTopic = getIntent().getStringExtra("IdTopic");
        Boolean englishMode = getIntent().getBooleanExtra("englishMode", false);
        Boolean shuffle = getIntent().getBooleanExtra("shuffle", false);
        topicDatabaseService = new TopicDatabaseService(getApplication());

        // Create Animator Object
        // For this we add animator folder inside res
        // Now we will add the animator to our card
        // we now need to modify the camera scale
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;

        ConstraintLayout card = findViewById(R.id.card);
        TextView front = findViewById(R.id.card_front);
        TextView back = findViewById(R.id.card_back);
        ImageButton btnFoward = findViewById(R.id.btnFoward);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnSpeak = findViewById(R.id.btnSpeak);
        ImageButton btnClose = findViewById(R.id.btnClose);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView progressText = findViewById(R.id.progress);
        ToggleButton btnAuto = findViewById(R.id.btnAuto);

        front.setCameraDistance(8000 * scale);
        back.setCameraDistance(8000 * scale);

        // Set the front animation
        front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_animator);
        back_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_animator);

//      event flip
        View.OnClickListener flip = view -> {
            if (isFront) {
                front_anim.setTarget(front);
                back_anim.setTarget(back);
                front_anim.start();
                back_anim.start();
                isFront = false;
            } else {
                front_anim.setTarget(back);
                back_anim.setTarget(front);
                back_anim.start();
                front_anim.start();
                isFront = true;
            }
        };
        // Set the event listener
        front.setOnClickListener(flip);
        back.setOnClickListener(flip);

        ///////////////////////// get list from db, set first card
        test = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
            @Override
            public void onListReady(ArrayList<VocabularyModel> vocabList) {
                if(!englishMode){
                    for(VocabularyModel v:test){
                        String temp = v.getEnglish();
                        v.setEnglish(v.getVietnamese());
                        v.setVietnamese(temp);
                    }
                }
                if(shuffle) Collections.shuffle(test);
                front.setText(test.get(0).getEnglish());
                back.setText(test.get(0).getVietnamese());

                front.setText(test.get(0).getEnglish());
                back.setText(test.get(0).getVietnamese());
                progressBar.setMax(test.size());
                progressBar.setProgress(1);
                progressText.setText("1/"+test.size());
                String speechText = englishMode?test.get(0).getEnglish():test.get(0).getVietnamese();
                CustomTextToSpeech text2Speech = new CustomTextToSpeech(getApplicationContext(), speechText);
                text2Speech.speak();
            }
        });

        indexVocab = 0;
        if(getApplicationContext() != null) {
            btnSpeak.setOnClickListener(view -> {
                CustomTextToSpeech text2Speech = new CustomTextToSpeech(getApplicationContext(), front.getText().toString());
                text2Speech.speak();
            });

            // Set button forward and back
            btnFoward.setOnClickListener(view -> {
                if (indexVocab < test.size() - 1) {
                    indexVocab++;
                    front.setText(test.get(indexVocab).getEnglish());
                    if (!isFront) {
                        front_anim.setTarget(back);
                        back_anim.setTarget(front);
                        back_anim.start();
                        front_anim.start();
                        isFront = true;
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(() -> back.setText(test.get(indexVocab).getVietnamese()), 500);

                    String speechText = englishMode?test.get(indexVocab).getEnglish():test.get(indexVocab).getVietnamese();
                    CustomTextToSpeech text2Speech = new CustomTextToSpeech(getApplicationContext(), speechText);
                    text2Speech.speak();

                    progressBar.setProgress(indexVocab+1);
                    progressText.setText(indexVocab+1+"/"+test.size());
                }

            });
            btnBack.setOnClickListener(view -> {
                if (indexVocab > 0) {
                    indexVocab--;
                    front.setText(test.get(indexVocab).getEnglish());
                    back.setText("");
                    if (!isFront) {
                        front_anim.setTarget(back);
                        back_anim.setTarget(front);
                        back_anim.start();
                        front_anim.start();
                        isFront = true;
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(() -> back.setText(test.get(indexVocab).getVietnamese()), 500);

                    String speechText = englishMode?test.get(indexVocab).getEnglish():test.get(indexVocab).getVietnamese();
                    CustomTextToSpeech text2Speech = new CustomTextToSpeech(getApplicationContext(), speechText);
                    text2Speech.speak();

                    progressBar.setProgress(indexVocab+1);
                    progressText.setText(indexVocab+1+"/"+test.size());
                }
            });
        }
        ////////////////// auto switch button
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isFront){
                    front.performClick();
                }
                else btnFoward.performClick();
                handler.postDelayed(this, 3000);
            }
        };
//        Runnable runnable1 = new Runnable() {
//            @Override
//            public void run() {
//                // Công việc cần thực hiện tự động
//                front.performClick();
//                handler.postDelayed(this, 3000);
//            }
//        };
        btnAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handler.postDelayed(runnable, 3000);
            }
        });

        ////////////////// close button
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}