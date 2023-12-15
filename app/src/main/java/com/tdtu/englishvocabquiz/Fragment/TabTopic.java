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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

import java.util.ArrayList;
import java.util.EventListener;


public class TabTopic extends Fragment {



    private RecyclerView recyclerViewTopic;
    private TopicAdapter topicAdapter;
    private ArrayList<TopicModel> listTopic;
    private TopicDatabaseService databaseService;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;


    public TabTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_topic, container, false);

        recyclerViewTopic = view.findViewById(R.id.recyclerViewTopic);

        databaseService = new TopicDatabaseService(this.getActivity());

        //ket noi firestore
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("topics");


        listTopic = new ArrayList<>();


        collectionReference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                // Kiểm tra nếu querySnapshot không null và có chứa document mới
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    //xoa list cu
                    listTopic.clear();
                    //cap nhat list moi
                    listTopic = databaseService.getListTopic(new OnTopicListReady() {
                        @Override
                        public void onListReady(ArrayList<TopicModel> topicList) {
                            if(topicList != null){
                                topicAdapter = new TopicAdapter(getContext(), listTopic);
                                recyclerViewTopic.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerViewTopic.setAdapter(topicAdapter);

                            }
                        }
                    });
                }else {
                    listTopic.clear();
                }
            }
        });

        return  view;
    }
}
