package com.tdtu.englishvocabquiz.Activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;

public class FlashCardActivity extends AppCompatActivity {
    private AnimatorSet front_anim;
    private AnimatorSet back_anim;
    private boolean isFront = true;

    ArrayList<VocabularyModel> test = new ArrayList<>();
    int indexVocab = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            test = VocabularyModel.generate();
        }
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

        front.setCameraDistance(8000 * scale);
        back.setCameraDistance(8000 * scale);

        // Set the front animation
        front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_animator);
        back_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_animator);

//        set default card
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            front.setText(test.get(0).getEnglish());
            back.setText(test.get(0).getVietnamese());
            indexVocab = 0;
        }

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

        // Set button foward and back
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        btnFoward.setOnClickListener(view ->{
            if(indexVocab<test.size()-1){
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
                ;
            }

        });
        btnBack.setOnClickListener(view ->{
            if(indexVocab>0){
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
                ;
            }
        });
    }
    }
}