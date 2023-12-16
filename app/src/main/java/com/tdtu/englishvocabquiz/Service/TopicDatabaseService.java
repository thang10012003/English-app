package com.tdtu.englishvocabquiz.Service;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.opencsv.CSVWriter;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnGetTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Listener.Word.OnGetWordListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TopicDatabaseService {
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference topicRef = fb.collection("topics");
    private Context context;
    private ArrayList<TopicModel> topicList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private TopicModel topicModel;



    public TopicDatabaseService(Context context) {
        this.context = context;
    }

    //them topic
    public void addTopic(TopicModel topicModel, OnAddTopicListener listener){
        topicRef
                .add(topicModel.convertToMap())
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
        topicRef
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
    public TopicModel getTopicByID(String topicId, OnGetTopicListener callback ){
        topicRef.document(topicId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        TopicModel topic = document.toObject(TopicModel.class);
                        callback.onListReady(topic);
                    } else {

                    }
                } else {

                }
            }
        });
        return  topicModel;
    }
    //lay danh sach cac topic cua user
    public ArrayList<TopicModel> getListTopic(OnTopicListReady callback){
//        sharedPreferences = context.getSharedPreferences("QuizPreference", MODE_PRIVATE);
//        String authorId = sharedPreferences.getString("uid","" );
        String authorId = FirebaseAuth.getInstance().getUid();

        topicRef.whereEqualTo("idAuthor", authorId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("TAG", "onComplete TOPIC: "+"true TOPIC");
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
    //lay cac topic tru topic cua user
    public ArrayList<TopicModel> getListTopicExceptId(OnTopicListReady callback){
//        sharedPreferences = context.getSharedPreferences("QuizPreference", MODE_PRIVATE);
        String authorId = FirebaseAuth.getInstance().getUid().toString();

        topicRef.whereNotEqualTo("idAuthor", authorId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
        topicRef
                .document(topicId)
                .delete()
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
        topicRef
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
    //cap nhat tu theo field
    public void updateFieldTopic(String topicId,TopicModel topicModel ,String field, String newValue){
        if(newValue.equals("0")){

            topicModel.setNumberOfVocab(topicModel.getNumberOfVocab()+1);
        }else {
            topicModel.setNumberOfVocab(topicModel.getNumberOfVocab()-1);
        }
        Map<String, Object> mapData = topicModel.convertToMap();


        topicRef
                .document(topicId)
                .update(mapData)
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {
                });
    }
    //Cap nhat so tu
    public void updateNumWordTopic(String topicId,TopicModel topicModel,int flag){

        Map<String,Object> updates = topicModel.convertToMap();
        if(flag == 0){
            updates.replace("numberOfVocab",topicModel.getNumberOfVocab()+1);
        }else {
            updates.replace("numberOfVocab",topicModel.getNumberOfVocab()-1);

        }
        topicRef
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
    public void addWordToTopic(String topicId,VocabularyModel vocab, OnAddTopicListener listener){
        topicRef.document(topicId)
                .collection("words")
                .add(vocab)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String documentId = documentReference.getId();
                        addIdWord(topicId,documentId,vocab);
//                        updateNumWordTopic(topicId,);
                        topicModel =  getTopicByID(topicId, new OnGetTopicListener() {
                            @Override
                            public void onListReady(TopicModel topicList) {

                                updateNumWordTopic(topicId, topicList,0);
                            }
                        });
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
    //cap nhat lai id cho word
    public void addIdWord(String topicId, String id, VocabularyModel vocabularyModel){
        vocabularyModel.setId(id);
        Map<String, Object> mapData = vocabularyModel.convertToMap();
        topicRef
                .document(topicId)
                .collection("words")
                .document(id)
                .update(mapData)
                .addOnSuccessListener(aVoid -> {
                    Log.e("TAG", "addIdWord: " + id );
                })
                .addOnFailureListener(e -> {
                });
    }
    //cap nhat tu theo field
    public void updateFieldWord(String topicId, String id, VocabularyModel vocabularyModel,String field, String newValue){
        Boolean mark;
        Map<String, Object> mapData;
        switch (field){
            case "mark":
                mark = Boolean.parseBoolean(newValue);
                vocabularyModel.setMark(mark);
                mapData = vocabularyModel.convertToMap();
                break;
            case "english":
                vocabularyModel.setEnglish(newValue);
                mapData = vocabularyModel.convertToMap();
                break;
            default:
                mapData = vocabularyModel.convertToMap();
                mapData.replace(field,newValue);
                break;
        }


        topicRef
                .document(topicId)
                .collection("words")
                .document(id)
                .update(mapData)
                .addOnSuccessListener(aVoid -> {
                    Log.e("TAG", "updateFieldWord: " + id );
                })
                .addOnFailureListener(e -> {
                });
    }


    public ArrayList<VocabularyModel> getWordFromTopic(String topicId, OnWordListReady callback){
        ArrayList<VocabularyModel> wordList = new ArrayList<>();
        topicRef
            .document(topicId)
            .collection("words")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("TAG", "onComplete: "+"true");
    //                    topicList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idTopic = document.getString("idTopic");
                        String id = document.getString("id");
                        String english = document.getString("english");
                        String vietnamese = document.getString("vietnamese");
                        String imgUrl = document.getString("imgUrl");
                        boolean mark = document.getBoolean("mark");
                        Date createDate = document.getTimestamp("createDate").toDate();
                        String progress = document.getString("progress");
                        int countAchieve = document.getLong("countAchieve").intValue();
                        String desc = document.getString("desc");

                        VocabularyModel model = new VocabularyModel( id,  english,  vietnamese,  createDate,  progress,  mark,  imgUrl,  desc,  idTopic,  countAchieve);
                        wordList.add(model);
                    }
                    callback.onListReady(wordList);
                }else{
                    callback.onListReady(null);
                }
            }
        });
        return wordList;
    }
    //xoa 1 tu trong topic
    public void deleteWordById(String topicId, String id){
        topicRef
                .document(topicId)
                .collection("words")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xóa thành công
                        Log.e("TAG", "Đã xóa word có id: " + id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi xảy ra lỗi trong quá trình xóa
                        Log.e("TAG", "Lỗi khi xóa word có id: " + id, e);
                    }
                });

    }
    //lay tu bang id
    public VocabularyModel getWordByID(String topicId, String id, OnGetWordListener callback ){
        topicRef.document(topicId).
        collection("words").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        VocabularyModel vocab = document.toObject(VocabularyModel.class);
                        callback.onGetReady(vocab);
                    } else {

                    }
                } else {

                }
            }
        });
        return  new VocabularyModel();
    }
    //xuat csv cua topic

}
