package com.tdtu.englishvocabquiz.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Adapter.TopicItem;
import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;


public class TabTopic extends Fragment {



    private RecyclerView recyclerViewTopic;
    private TopicAdapter topicAdapter;
    private ArrayList<TopicItem> listTopic;


    public TabTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_topic, container, false);

        recyclerViewTopic = view.findViewById(R.id.recyclerViewTopic);
        listTopic = new ArrayList<>();
//        listTopic.add(new TopicItem("Food",2,"Vu"));
//        listTopic.add(new TopicItem("Animal",3,"Thang"));
//        listTopic.add(new TopicItem("Car",5,"Tien"));
//        listTopic.add(new TopicItem("Food",6,"Vu"));
//        listTopic.add(new TopicItem("Animal",2,"Thang"));
//        listTopic.add(new TopicItem("Car",3,"Tien"));

        topicAdapter = new TopicAdapter(getContext(), listTopic);
        recyclerViewTopic.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewTopic.setAdapter(topicAdapter);
        return  view;
    }
}