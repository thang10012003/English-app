package com.tdtu.englishvocabquiz.Service;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Objects;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Model.TopicModel;

import java.util.ArrayList;
import java.util.Map;

public class DatabaseService {
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference topicRef;
    private Context context;


    public DatabaseService(Context context) {
        this.context = context;
    }
    public void addTopic(TopicModel topicModel, OnAddTopicListener listener){
        fb.collection("topics")
                .add(topicModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String documentId = documentReference.getId();
                        addIdTopic(documentId,topicModel);
                        listener.OnAddSuccess();
                        Toast.makeText(context, "Thêm thành công",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Thêm thất bại",Toast.LENGTH_LONG).show();
                        listener.OnAddFailure();

                    }
                });
    }
    public void addIdTopic(String id, TopicModel topicModel){
        topicModel.setIdTopic(id);
        Map<String, Object> mapData = topicModel.convertToMap();
//        mapData.keySet("idTopic",id);
        fb.collection("topics")
                .document(id)
                .update(mapData)
                .addOnSuccessListener(aVoid -> {
                    // Đã cập nhật dữ liệu thành công
                    // Thực hiện các hành động khác sau khi đã cập nhật
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi có lỗi xảy ra khi cập nhật dữ liệu
                });
    }
//    public ArrayList<TopicModel> getListTopic(){
//
//    }


}
