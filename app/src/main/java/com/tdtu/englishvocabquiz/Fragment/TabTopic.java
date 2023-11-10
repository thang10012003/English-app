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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabTopic#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TabTopic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewTopic;
    private TopicAdapter topicAdapter;
    private ArrayList<TopicItem> listTopic;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabTopic.
     */
    // TODO: Rename and change types and number of parameters
    public static TabTopic newInstance(String param1, String param2) {
        TabTopic fragment = new TabTopic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TabTopic() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_topic, container, false);

        recyclerViewTopic = view.findViewById(R.id.recyclerViewTopic);
        listTopic = new ArrayList<>();
        listTopic.add(new TopicItem("Food",2,"Vu"));
        listTopic.add(new TopicItem("Animal",3,"Thang"));
        listTopic.add(new TopicItem("Car",5,"Tien"));
        listTopic.add(new TopicItem("Food",6,"Vu"));
        listTopic.add(new TopicItem("Animal",2,"Thang"));
        listTopic.add(new TopicItem("Car",3,"Tien"));

        topicAdapter = new TopicAdapter(getContext(), listTopic);
        recyclerViewTopic.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewTopic.setAdapter(topicAdapter);
        return  view;
    }
}