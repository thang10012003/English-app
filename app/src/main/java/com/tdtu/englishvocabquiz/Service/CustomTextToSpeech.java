package com.tdtu.englishvocabquiz.Service;

import android.content.Context;
import android.speech.tts.TextToSpeech;


public class CustomTextToSpeech {
    private Context context;
    private TextToSpeech textToSpeech;
    private String word;

    public CustomTextToSpeech(Context context, String word) {
        this.context = context;
        this.word = word;
        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    // TextToSpeech initialization successful
                    speak();
                }
            }
        });
    }

    public void speak() {
        if (textToSpeech != null) {
            textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
