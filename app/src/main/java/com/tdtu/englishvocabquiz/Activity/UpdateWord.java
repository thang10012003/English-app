package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tdtu.englishvocabquiz.Dialog.ConfirmDeleteDialog;
import com.tdtu.englishvocabquiz.Listener.Word.OnDeleteWordListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnGetWordListener;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityAddTopicBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityUpdateWordBinding;

public class UpdateWord extends AppCompatActivity {
    private ActivityUpdateWordBinding binding;
    private VocabularyModel vocabularyModel;
    private TopicDatabaseService topicDatabaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String IdTopic = intent.getStringExtra("IdTopic");
        String id = intent.getStringExtra("IdWord");

        topicDatabaseService = new TopicDatabaseService(getApplicationContext());

        vocabularyModel = topicDatabaseService.getWordByID(IdTopic, id, new OnGetWordListener() {
            @Override
            public void onGetReady(VocabularyModel vocabularyModel) {
                binding.edtWord.setText(vocabularyModel.getEnglish());
                binding.edtMean.setText(vocabularyModel.getVietnamese());

            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vocabularyModel = topicDatabaseService.getWordByID(IdTopic, id, new OnGetWordListener() {
                    @Override
                    public void onGetReady(VocabularyModel vocabularyModel) {
                        String word = binding.edtWord.getText().toString().toLowerCase();
                        String mean = binding.edtMean.getText().toString().toLowerCase();

//                        vocabularyModel.setEnglish(word);
//                        vocabularyModel.setVietnamese(mean);
                        topicDatabaseService.updateFieldWord(IdTopic,id,vocabularyModel,"english",word);
                        topicDatabaseService.updateFieldWord(IdTopic,id,vocabularyModel,"vietnamese",mean);

                    }
                });
                finish();
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDeleteDialog confirmDeleteDialog = new ConfirmDeleteDialog(UpdateWord.this ,IdTopic, new OnDeleteWordListener() {
                    @Override
                    public void OnDeleteSuccess() {
                        finish();
                    }

                    @Override
                    public void OnDeleteFailure() {

                    }
                },id);
                confirmDeleteDialog.showCreateDialogWord();
            }
        });


    }
}