package com.tdtu.englishvocabquiz.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdtu.englishvocabquiz.Adapter.VocabAdapter;
import com.tdtu.englishvocabquiz.Dialog.ConfirmDeleteDialog;
import com.tdtu.englishvocabquiz.Listener.Topic.OnDeleteTopicListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityTopicDetailsBinding;

import java.util.*;

public class TopicDetails extends AppCompatActivity {

    private ActivityTopicDetailsBinding binding;
    private UserDatabaseService userDatabaseService = new UserDatabaseService(getApplication());
    private TopicDatabaseService topicDatabaseService = new TopicDatabaseService(this);
    private FirebaseFirestore firestore;
    private CollectionReference collectionReference;
    private ArrayList<VocabularyModel> vocabList;
    private VocabAdapter vocabAdapter;
    String featureType = "";
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

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("topics");
        topicDatabaseService = new TopicDatabaseService(getApplication());
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

        /////////////////////// go to study feature
        binding.btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OptionActivity.class);
                intent1.putExtra("IdTopic" ,IdTopic);
                intent1.putExtra("featureType" ,"card");
                startActivity(intent1);
            }
        });
        binding.btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OptionActivity.class);
                intent1.putExtra("IdTopic" ,IdTopic);
                intent1.putExtra("featureType" ,"choice");
                startActivity(intent1);
            }
        });

        binding.btnTypeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OptionActivity.class);
                intent1.putExtra("IdTopic" ,IdTopic);
                intent1.putExtra("featureType" ,"typeWord");
                startActivity(intent1);
            }
        });
        ////////////////////////

        vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
            @Override
            public void onListReady(ArrayList<VocabularyModel> vocabList) {
                vocabAdapter = new VocabAdapter(getApplicationContext(), vocabList);
                binding.rclWord.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rclWord.setAdapter(vocabAdapter);
            }
        });

        collectionReference.document(IdTopic)
                .collection("words")
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                // Kiểm tra nếu querySnapshot không null và có chứa document mới
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    //xoa list cu
                    vocabList.clear();
                    //cap nhat list moi
                    vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                        @Override
                        public void onListReady(ArrayList<VocabularyModel> vocabList) {

                            if (vocabList != null) {
                                vocabAdapter = new VocabAdapter(getApplicationContext(), vocabList);
                                binding.rclWord.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.rclWord.setAdapter(vocabAdapter);
                            }
                        }
                    });
                }else {
                    vocabList.clear();
                }
            }
        });
    }
}



