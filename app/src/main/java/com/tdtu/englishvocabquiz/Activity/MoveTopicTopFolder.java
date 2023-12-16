package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tdtu.englishvocabquiz.Adapter.FolderAdapter;
import com.tdtu.englishvocabquiz.Listener.Folder.OnFolderListReady;
import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.FolderDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityAddTopicBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityMoveTopicTopFolderBinding;

import java.util.ArrayList;

public class MoveTopicTopFolder extends AppCompatActivity {
    private ActivityMoveTopicTopFolderBinding binding;
    private ArrayList<FolderModel> listFolder;
    private FolderDatabaseService folderDatabaseService;
    private FolderAdapter folderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoveTopicTopFolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //nhan du lieu tu intent
        Intent intent = getIntent();
        String topicId = intent.getStringExtra("IdTopic");
        String authorId = intent.getStringExtra("IdAuthor");

        listFolder = new ArrayList<>();
        folderDatabaseService = new FolderDatabaseService(getApplicationContext());
//        folderDatabaseService.addTopicToFolder("97Z7GFj52OqVjpWcoJsE","123");

        listFolder =  folderDatabaseService.getListModel(new OnFolderListReady() {
            @Override
            public void onListReady(ArrayList<FolderModel> folderlist) {
                folderAdapter = new FolderAdapter(getApplicationContext(), listFolder);
                folderAdapter.setOnItemClickListener(new FolderAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String idFolder1 = listFolder.get(position).getId();
                        Log.e("TAG", "onItemClick MoveTopicToFolder" + idFolder1 );
                        folderDatabaseService.addTopicToFolder(idFolder1,topicId);
                        Toast.makeText(getApplicationContext(),"Thêm topic vào folder thành công",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                binding.rclFolder.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rclFolder.setAdapter(folderAdapter);

            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}