package com.tdtu.englishvocabquiz.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Objects;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Model.TopicModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DatabaseService {
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference topicRef;
    private Context context;
    private ArrayList<TopicModel> topicList = new ArrayList<>();



    public DatabaseService(Context context) {
        this.context = context;
    }

    //them topic
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
    //cap nhat lai id cho topic
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
    //lay danh sach cac topic
    public ArrayList<TopicModel> getListTopic(OnTopicListReady callback){
        fb.collection("topics").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("TAG", "onComplete: "+"true");
//                    topicList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idTopic = document.getString("idTopic");
                        String topicName = document.getString("topicName");
                        String description = document.getString("description");
                        int numberOfVocab = document.getLong("numberOfVocab").intValue();
                        Date createDate = document.getTimestamp("createDate").toDate();
                        String mode = document.getString("mode");
                        String idAuthor = document.getString("idAuthor");

                        TopicModel topic = new TopicModel( idTopic,  topicName,  description, numberOfVocab,  createDate,  mode,  idAuthor);
                        topicList.add(topic);
                        Log.e("TAG", document.getId() + " => " + document.getData());
                    }
//                    Log.e("TAG","so luong phan tu" + topicList.size());
                    callback.onListReady(topicList);
                }else{
                    callback.onListReady(null);
                }
            }
        });
        return topicList;
    }
    //xoa 1 topic
    public void deleteTopic(String topicId){
        fb.collection("topics").document(topicId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xóa thành công
                        Log.e("TAG", "Đã xóa topic có id: " + topicId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi xảy ra lỗi trong quá trình xóa
                        Log.e("TAG", "Lỗi khi xóa topic có id: " + topicId, e);
                    }
                });

    }
    //cap nhat thong tin mot topic
    public void updateTopic(String topicId,TopicModel topicModel){
        Map<String,Object> updates = topicModel.convertToMap();
        fb.collection("topics")
                .document(topicId)
                .update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xóa thành công
                        Log.e("TAG", "Đã cập nhật thành công id: " + topicId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi xảy ra lỗi trong quá trình xóa
                        Log.e("TAG", "Lỗi khi cập nhật topic có id: " + topicId, e);
                    }
                });
    }
}
