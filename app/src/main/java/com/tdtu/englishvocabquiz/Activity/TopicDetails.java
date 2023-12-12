package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tdtu.englishvocabquiz.Adapter.ListWordAdapter;
import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Adapter.VocabItem;
import com.tdtu.englishvocabquiz.Dialog.ConfirmDeleteDialog;
import com.tdtu.englishvocabquiz.Listener.Topic.OnDeleteTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Listener.Topic.OnWordListReady;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityChangePasswordBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityTopicDetailsBinding;

import java.util.*;

public class TopicDetails extends AppCompatActivity {

    ActivityTopicDetailsBinding binding;
    private UserDatabaseService userDatabaseService = new UserDatabaseService(getApplication());
    private TopicDatabaseService topicDatabaseService = new TopicDatabaseService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String TopicName = intent.getStringExtra("TopicName");
        String NumberOfVocab = String.valueOf(intent.getIntExtra("NumberOfVocab",1));
        String IdAuthor = intent.getStringExtra("IdAuthor");
        String IdTopic = intent.getStringExtra("IdTopic");
//        String AuthorName = intent.getStringExtra("AuthorName");
        userDatabaseService.getUserById(IdAuthor,new OnGetUserListener() {
            @Override
            public void onGetReady(UserModel userModel) {
                String AuthorName = userModel.getName();
                binding.tvNameAuthor.setText(AuthorName);            }
        });
        binding.tvtopic.setText(TopicName);
        binding.tvNumWord.setText(NumberOfVocab + " thuật ngữ");

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(TopicDetails.this, IdTopic, new OnDeleteTopicListener() {
                    @Override
                    public void OnDeleteSuccess() {
                        finish();
                    }

                    @Override
                    public void OnDeleteFailure() {

                    }
                });
                dialog.showCreateDialog();
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), AddWord.class);
                intent1.putExtra("IdTopic" ,IdTopic);
                intent1.putExtra("TopicName",TopicName);
                startActivity(intent1);
            }
        });

        //Word List code
        ArrayList<VocabularyModel> vocabList = VocabularyModel.generate();

        if(IdTopic != null){
            vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                @Override
                public void onListReady(ArrayList<VocabularyModel> vocabList) {
                    RecyclerView rclWord = findViewById(R.id.rclWord);
                    ListWordAdapter adapter = new ListWordAdapter(vocabList, getApplicationContext());
                    rclWord.setAdapter(adapter);
                    rclWord.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            });
        }


        //Word List code
    }
}