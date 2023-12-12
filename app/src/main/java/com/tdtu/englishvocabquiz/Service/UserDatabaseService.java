package com.tdtu.englishvocabquiz.Service;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.UserModel;

import java.util.ArrayList;

public class UserDatabaseService {
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference topicRef;
    private Context context;
    private String ref_col = "users";
    private ArrayList<UserModel> uList = new ArrayList<>();
    private ArrayList<UserModel> userList = new ArrayList<>();

    private boolean returnChecked = false;
    private UserModel user = new UserModel();

    public UserDatabaseService(Context context) {
        this.context = context;
    }

    public ArrayList<UserModel> getAllUser(){
        fb.collection(ref_col).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("TAG", "onComplete: "+"true");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserModel user = document.toObject(UserModel.class);
                        UserModel getUserOut = new UserModel(user);
                        uList.add(getUserOut);
                        Log.e("TAG", document.getId() + " => " + document.getData());
                    }
                }
            }
        });
        return uList;
    }
    public boolean updateUser(String id_acc, UserModel user){
        returnChecked = false;
        fb.collection(ref_col).whereEqualTo("id_acc", id_acc).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserModel user = document.toObject(UserModel.class);
                        //handle update tiep di ne
                        returnChecked = true;
                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting user documents by uid: ", task.getException());
                }
            }
        });
        return returnChecked;
    }
    public UserModel getUserById(String id, OnGetUserListener callback){
        fb.collection(ref_col).whereEqualTo("id_acc",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserModel user = document.toObject(UserModel.class);
                        UserModel getUserOut = new UserModel(user);
                        userList.add(getUserOut);
                        callback.onGetReady(userList.get(0));
                    }
                } else {
                    // Xử lý khi không thành công
                }
            }
        });
        return user;
        }
}
