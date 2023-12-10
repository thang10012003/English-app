package com.tdtu.englishvocabquiz.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Adapter.TopicItem;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.DatabaseService;

import java.util.ArrayList;


public class TabTopic extends Fragment {



    private RecyclerView recyclerViewTopic;
    private TopicAdapter topicAdapter;
    private ArrayList<TopicModel> listTopic;
    private DatabaseService databaseService;


    public TabTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_topic, container, false);

        recyclerViewTopic = view.findViewById(R.id.recyclerViewTopic);

        databaseService = new DatabaseService(this.getActivity());
        listTopic = new ArrayList<>();
        listTopic = databaseService.getListTopic(new OnTopicListReady() {
            @Override
            public void onListReady(ArrayList<TopicModel> topicList) {
                if(topicList != null){
//                    Log.e("TAG", "list size: " + listTopic.size() );
                    Log.e("TAG", "onListReady: "+listTopic.get(0).getIdAuthor());
                    topicAdapter = new TopicAdapter(getContext(), listTopic);
                    recyclerViewTopic.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViewTopic.setAdapter(topicAdapter);
                }else {
                
                }
            }
        });



        return  view;
    }
}