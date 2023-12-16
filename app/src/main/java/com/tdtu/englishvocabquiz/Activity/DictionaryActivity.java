package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.ActivityDictionaryBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class DictionaryActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    ActivityDictionaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictionaryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
    void callApiDiction(){

    }
}