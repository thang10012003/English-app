package com.tdtu.englishvocabquiz.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Adapter.VocabAdapter;
import com.tdtu.englishvocabquiz.Dialog.ConfirmDeleteDialog;
import com.tdtu.englishvocabquiz.Listener.Folder.OnDeleteFolderListener;
import com.tdtu.englishvocabquiz.Listener.Folder.OnGetFolderListener;
import com.tdtu.englishvocabquiz.Listener.Folder.OnGetListIdTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnGetTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.FolderDatabaseService;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityFolderDetailsBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityTopicDetailsBinding;

import java.util.ArrayList;

public class FolderDetails extends AppCompatActivity {

    private ActivityFolderDetailsBinding binding;
    private FolderDatabaseService folderDatabaseService;
    private UserDatabaseService userDatabaseService;
    private TopicDatabaseService topicDatabaseService;
    private FirebaseFirestore db;
    private CollectionReference folderRef;
    private ArrayList<TopicModel> topicList = new ArrayList<>();
    private TopicAdapter topicAdapter;
    private String folderId, authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFolderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        folderDatabaseService = new FolderDatabaseService(getApplicationContext());
        userDatabaseService = new UserDatabaseService(getApplicationContext());
        topicDatabaseService = new TopicDatabaseService(getApplicationContext());

        db = FirebaseFirestore.getInstance();
        folderRef = db.collection("folders");

        Intent intent = getIntent();
        folderId = intent.getStringExtra("folderId");
        authorId = intent.getStringExtra("authorId");

        UserModel userModel = userDatabaseService.getUserById(authorId, new OnGetUserListener() {
            @Override
            public void onGetReady(UserModel userModel1) {
                binding.tvNameAuthor.setText(userModel1.getName());
            }
        });

        folderDatabaseService.getFolderById(folderId, new OnGetFolderListener() {
            @Override
            public void onGetReady(FolderModel folderModel) {
                binding.tvtopic.setText(folderModel.getNameFolder());
                binding.tvNumTopic.setText(folderModel.getNumTopic() + " chủ đề");

            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDeleteDialog confirmDeleteDialog = new ConfirmDeleteDialog(FolderDetails.this, folderId, new OnDeleteFolderListener() {
                    @Override
                    public void OnDeleteSuccess() {
//                        Toast.makeText(getApplicationContext(),"Xoa folder thanh cong",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void OnDeleteFailure() {
//                        Toast.makeText(getApplicationContext(),"Xoa folder that bai",Toast.LENGTH_SHORT).show();
                    }
                });
                confirmDeleteDialog.showCreateDialogFolder();
            }
        });

//     showTopic();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Đây là nơi bạn đặt các hành động mà bạn muốn thực hiện khi Activity trở lại trạng thái active

        // Ví dụ: cập nhật dữ liệu khi Activity được khôi phục
       showTopic();
    }
    public void showTopic(){
        topicList.clear();
        folderDatabaseService.getTopicFromFolder(folderId, new OnGetListIdTopicListener() {
            @Override
            public void onListReady(ArrayList<String> topicIdList) {
                topicAdapter = new TopicAdapter(getApplicationContext(), topicList);

                binding.rclTopic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rclTopic.setAdapter(topicAdapter);
                for (String idTopic : topicIdList) {
                    TopicModel topicModel = topicDatabaseService.getTopicByID(idTopic, new OnGetTopicListener() {
                        @Override
                        public void onListReady(TopicModel topicModel1) {
                            topicList.add(topicModel1);
                            topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(getApplicationContext(), TopicDetails.class);
                                    intent.putExtra("TopicName", topicList.get(position).getTopicName());
                                    intent.putExtra("NumberOfVocab", topicList.get(position).getNumberOfVocab());
                                    intent.putExtra("IdAuthor", topicList.get(position).getIdAuthor());
                                    intent.putExtra("IdTopic", topicList.get(position).getIdTopic());
                                    intent.putExtra("folder", folderId);
                                    startActivity(intent);
                                }
                            });
                            topicAdapter.notifyDataSetChanged();

                        }
                    });
                }
            }
        });
    }
}