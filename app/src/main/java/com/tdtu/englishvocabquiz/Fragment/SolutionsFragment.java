package com.tdtu.englishvocabquiz.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tdtu.englishvocabquiz.Activity.GptChatActivity;
import com.tdtu.englishvocabquiz.Activity.HomeActivity;
import com.tdtu.englishvocabquiz.Activity.LoginActivity;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.NetworkUtils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolutionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolutionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolutionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolutionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolutionsFragment newInstance(String param1, String param2) {
        SolutionsFragment fragment = new SolutionsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solutions, container, false);

        if (NetworkUtils.isNetworkConnected(getActivity())) {//check internet before start these
            handleChatGpt( view);//show chat gpt
        }else{
            Toast.makeText(getActivity(), "Vui lòng kết nối mạng để sử dụng tiện ích !", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
    void handleChatGpt(View view){
        Button btnChatGpt = view.findViewById(R.id.btnGptChat);
        btnChatGpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GptChatActivity.class));
            }
        });
    }


}