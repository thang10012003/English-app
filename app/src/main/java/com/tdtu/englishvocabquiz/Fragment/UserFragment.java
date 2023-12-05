package com.tdtu.englishvocabquiz.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.englishvocabquiz.Activity.EditProfileActivity;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Model.UserModel;

import java.util.ArrayList;


public class UserFragment extends Fragment {

    private String uid;
    private UserModel userModel;
    private TextView tvUid;
    private TextView tvEdit;
    private TextView tvname;
    private TextView tvPhoneNumber;
    private TextView tvGender;
    private TextView tvCreateDate;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private ArrayList<UserModel> datalist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);

        tvUid = view.findViewById(R.id.tvUid);
        tvEdit = view.findViewById(R.id.tvEdit);
        tvname = view.findViewById(R.id.tvNameUser);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvGender = view.findViewById(R.id.tvGender);
        tvCreateDate = view.findViewById(R.id.tvCreateDate);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        datalist = new ArrayList<>();
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });
        sharedPreferences = getContext().getSharedPreferences("QuizPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String uid = sharedPreferences.getString("uid","");



        ValueEventListener  eventListener=  databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    datalist.clear();

                    userModel =  snapshot.getValue(UserModel.class);
                    datalist.add(userModel);

                    editor.putString("name",userModel.getName());
                    editor.putString("gender",userModel.getGender());
                    editor.putString("createDate",userModel.getCreateDate());
                    editor.putInt("posts",userModel.getPosts());
                    editor.putString("avt", userModel.getAvt());
                    editor.putString("mobile", userModel.getMobile());
                    editor.commit();
//                    userCurrModel = new UserModel(name, gender,createDate, posts, avt, uid, mobile);
//                    snapshot.getValue("name","");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        Log.e("TAG", "user: "+datalist.get(0).toString());
        tvname.setText(sharedPreferences.getString("name", ""));
        tvPhoneNumber.setText(sharedPreferences.getString("mobile",""));
        tvGender.setText(sharedPreferences.getString("gender",""));
        tvCreateDate.setText(sharedPreferences.getString("createDate",""));

        return  view;
    }
}
//                    String createDate = snapshot.child("createDate").getValue(String.class);
//                    String gender = snapshot.child("gender").getValue(String.class);
//                    String avt = snapshot.child("avt").getValue(String.class);
//                    String  mobile = snapshot.child("mobile").getValue(String.class);
//                    String name = snapshot.child("name").getValue(String.class);
//                    Integer post = snapshot.child("post").getValue(Integer.class);