package com.tdtu.englishvocabquiz.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdtu.englishvocabquiz.Adapter.TopicAdapter;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    RecyclerView recommendTopic;
    RecyclerView yourTopic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /// code list of topic
        recommendTopic = view.findViewById(R.id.recommendTopic);
        yourTopic = view.findViewById(R.id.yourTopic);

        ArrayList<TopicModel> modelList = new ArrayList<>();
        modelList.add(new TopicModel("topic1", "test topic", 2, new Date(), "en", "vu"));
        modelList.add(new TopicModel("topic2", "test topic 2", 3, new Date(), "vn", "vu"));
        TopicAdapter adapter = new TopicAdapter(getContext(), modelList);

        yourTopic.setAdapter(adapter);
        yourTopic.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendTopic.setAdapter(adapter);
        recommendTopic.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        /// code list of topic

        // Inflate the layout for this fragment
        return view;//inflater.inflate(R.layout.fragment_home, container, false);


    }
}