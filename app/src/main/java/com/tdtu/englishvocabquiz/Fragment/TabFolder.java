package com.tdtu.englishvocabquiz.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdtu.englishvocabquiz.Adapter.FolderAdapter;
import com.tdtu.englishvocabquiz.Adapter.FolderItem;
import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Listener.Folder.OnFolderListReady;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.FolderDatabaseService;

import java.util.ArrayList;


public class TabFolder extends Fragment {



    private RecyclerView recyclerViewFolder;
    private FolderAdapter folderAdapter;
    private ArrayList<FolderModel> listFolder;
    private FolderDatabaseService folderDatabaseService;
    private FirebaseFirestore db;
    private CollectionReference reference;


    public TabFolder() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_folder, container, false);

        recyclerViewFolder = view.findViewById(R.id.recyclerViewFolder);
        folderDatabaseService = new FolderDatabaseService(this.getActivity());
        db = FirebaseFirestore.getInstance();
        reference = db.collection("folders");

        listFolder = new ArrayList<>();
//        listFolder.add(new FolderModel());
//        listFolder.add(new FolderModel());
//        folderAdapter = new FolderAdapter(getContext(), listFolder);
//        recyclerViewFolder.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewFolder.setAdapter(folderAdapter);


//        listFolder =  folderDatabaseService.getListModel(new OnFolderListReady() {
//            @Override
//            public void onListReady(ArrayList<FolderModel> folderlist) {
//                folderAdapter = new FolderAdapter(getContext(), listFolder);
//                recyclerViewFolder.setLayoutManager(new LinearLayoutManager(getActivity()));
//                recyclerViewFolder.setAdapter(folderAdapter);
//                Log.e("TAG", "onListReady: FIRESTORE " + listFolder.size());
//            }
//        });
//        Log.e("TAG", "onCreateView: LIST SIZE: " +  );


        reference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                // Kiểm tra nếu querySnapshot không null và có chứa document mới
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    //xoa list cu
                    listFolder.clear();
//                    cap nhat list moi
                    listFolder =  folderDatabaseService.getListModel(new OnFolderListReady() {
                        @Override
                        public void onListReady(ArrayList<FolderModel> folderlist) {
                            folderAdapter = new FolderAdapter(getContext(), listFolder);
                            recyclerViewFolder.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerViewFolder.setAdapter(folderAdapter);
                        }
                    });
                }else {
                    listFolder.clear();
                }
            }
        });
        return  view;
    }
}