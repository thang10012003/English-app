package com.tdtu.englishvocabquiz.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdtu.englishvocabquiz.Activity.SearchTopic;
import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;


public class HomeFragment extends Fragment {


    private TopicDatabaseService topicDatabaseService;
    private TopicDatabaseService topicDatabaseServiceRec;
    private ArrayList<TopicModel> recommendTopicList = new ArrayList<>();
    private ArrayList<TopicModel> yourTopicList = new ArrayList<>();
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    private RecyclerView recommendTopic;
    private RecyclerView yourTopic;
    private TopicAdapter yourTopicAdapter;
    private TopicAdapter recTopicAdapter;
    private EditText edtKeyword;
    private Button btnSearch;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("topics");
        /// code list of topic
        recommendTopic = view.findViewById(R.id.recommendTopic);
        yourTopic = view.findViewById(R.id.yourTopic);
        btnSearch = view.findViewById(R.id.btnSearch);
        edtKeyword = view.findViewById(R.id.search_bar);
        topicDatabaseService = new TopicDatabaseService(getActivity());
        topicDatabaseServiceRec = new TopicDatabaseService(getActivity());

        collectionReference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                // Kiểm tra nếu querySnapshot không null và có chứa document mới
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    //xoa list cu
                    recommendTopicList.clear();
                    //cap nhat list moi
                    recommendTopicList = topicDatabaseServiceRec.getListTopicExceptId(new OnTopicListReady() {
                        @Override
                        public void onListReady(ArrayList<TopicModel> topicList) {
                            if(topicList != null){
                                recTopicAdapter = new TopicAdapter(getContext(), recommendTopicList);
//                                yourTopic.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recommendTopic.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                recommendTopic.setAdapter(recTopicAdapter);

                            }
                        }
                    });
                }else {
                    recommendTopicList.clear();
                    recommendTopic.setAdapter(recTopicAdapter);
                }
            }
        });
        collectionReference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                // Kiểm tra nếu querySnapshot không null và có chứa document mới
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    //xoa list cu
                    yourTopicList.clear();
                    //cap nhat list moi
                    yourTopicList = topicDatabaseService.getListTopic(new OnTopicListReady() {
                        @Override
                        public void onListReady(ArrayList<TopicModel> topicList) {
                            if(topicList != null){
                                yourTopicAdapter = new TopicAdapter(getContext(), yourTopicList);
//                                yourTopic.setLayoutManager(new LinearLayoutManager(getActivity()));
                                yourTopic.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                yourTopic.setAdapter(yourTopicAdapter);

                            }
                        }
                    });
                }else {
                    yourTopicList.clear();
                    yourTopic.setAdapter(yourTopicAdapter);
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtKeyword.getText().toString().equals("")){
                    Intent intent = new Intent(getActivity(), SearchTopic.class);
                    intent.putExtra("keyword",edtKeyword.getText().toString());
                    startActivity(intent);
                }
            }
        });

        return view;


    }
}