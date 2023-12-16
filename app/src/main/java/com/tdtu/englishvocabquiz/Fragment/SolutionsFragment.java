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
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.NetworkUtils;


public class SolutionsFragment extends Fragment {


    public SolutionsFragment() {
        // Required empty public constructor
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