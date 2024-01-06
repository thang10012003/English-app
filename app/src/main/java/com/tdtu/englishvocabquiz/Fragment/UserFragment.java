package com.tdtu.englishvocabquiz.Fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdtu.englishvocabquiz.Activity.ChangePasswordActivity;
import com.tdtu.englishvocabquiz.Activity.EditProfileActivity;
import com.tdtu.englishvocabquiz.Activity.LoginActivity;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;

import java.util.ArrayList;


public class UserFragment extends Fragment {

    private String uid;
    private UserModel userModel;
    private TextView tvUid;
    private TextView tvEdit;
    private TextView tvname;
    private TextView tvPhoneNumber;
    private TextView tvGender;
    private TextView tvCreateDate, tvChangePassword;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private ArrayList<UserModel> datalist;


    private UserModel currDataUser;
    private String ref_col = "users";
    private String id_user;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    ImageView imgUpload;



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
         imgUpload =view.findViewById(R.id.uploadImgView);
         tvChangePassword = view.findViewById(R.id.btnChangePass);
      getUidByAuthen();
        db = FirebaseFirestore.getInstance();


        if(uid != null){
            getUserOnDatabase_byUid(uid);
        }






        handleSignOut( view);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), EditProfileActivity.class));




            }
        });
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
            }
        });

        return  view;
    }
    private void getUidByAuthen(){
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid().toString();
    }
    private void getUserOnDatabase_byUid(String uid){
        db.collection("users").whereEqualTo("id_acc", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserModel user = document.toObject(UserModel.class);
                        saveUserData(user);//get firts user needed
                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting user documents by uid: ", task.getException());
                }
            }
        });
    }
    private void handleSignOut(View view){
        Button btnsignOut = view.findViewById(R.id.btnSignOut);
        btnsignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }
    private void saveUserData(UserModel user){
        currDataUser = new UserModel(user);
        renderDataOnView( currDataUser);
    }
    private void renderDataOnView(UserModel user){
        tvUid.setText(auth.getCurrentUser().getEmail());
        tvname.setText(user.getName());
        tvPhoneNumber.setText(user.getMobile());
        tvGender.setText(user.getGender());
        tvCreateDate.setText(user.getCreateDate());
        Glide.with(getActivity()).load(user.getAvt()).into(imgUpload);
    }

}
