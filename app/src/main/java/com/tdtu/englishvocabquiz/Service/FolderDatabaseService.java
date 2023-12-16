package com.tdtu.englishvocabquiz.Service;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;
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
import com.tdtu.englishvocabquiz.Listener.Folder.OnAddFolderListener;
import com.tdtu.englishvocabquiz.Listener.Folder.OnFolderListReady;
import com.tdtu.englishvocabquiz.Listener.Folder.OnGetFolderListener;
import com.tdtu.englishvocabquiz.Listener.Folder.OnGetListIdTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnGetTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnTopicListReady;
import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;

import org.apache.commons.collections.ArrayStack;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FolderDatabaseService {
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference folderRef = fb.collection("folders");
    private String idAuthor = FirebaseAuth.getInstance().getUid();

    private Context context;
    private  String authorId;
    private ArrayList<FolderModel> folderList = new ArrayList<>();
    private FolderModel folderModel;

    public FolderDatabaseService(Context context) {
        this.context = context;
    }

    public void addFolder(String nameFolder, OnAddFolderListener listener){
        FolderModel folderModel = new FolderModel();
        folderModel.setNameFolder(nameFolder);
        folderModel.setIdAuthor(idAuthor);
        folderRef
            .add(folderModel.convertToMap())
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    String documentId = documentReference.getId();
                    addIdFolder(documentId,folderModel);
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
    //cap nhat lai id cho folder
    public void addIdFolder(String id, FolderModel folderModel){
        folderModel.setId(id);
        Map<String, Object> mapData = folderModel.convertToMap();

        folderRef
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
    public ArrayList<FolderModel> getListModel(OnFolderListReady callback){
        authorId = FirebaseAuth.getInstance().getUid();


        folderRef.whereEqualTo("idAuthor", authorId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("TAG", "onCompleteFOLDER: "+"true");
//                    folderList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getString("id");
                        String topicName = document.getString("nameFolder");
                        Date createDate = document.getTimestamp("createDate").toDate();
                        String idAuthor = document.getString("idAuthor");

                        folderModel = new FolderModel(id,topicName,idAuthor);
//                        FolderModel folderModel = new FolderModel();
                        folderList.add(folderModel);
                        Log.e("TAG", document.getId() + " => " + document.getData());
                    }
//                    Log.e("TAG","so luong phan tu" + folderList.size());
//                    ArrayList<FolderModel> list = new ArrayList<>();
                    callback.onListReady(folderList);
//                    callback.onListReady(list);
                }else{
                    callback.onListReady(null);
                }
            }
        });
//        ArrayList<FolderModel> list = new ArrayList<>();
//        list.add(new FolderModel());
        return folderList;
//        return list;
    }
    //xoa 1 folder
    public void deleteFolder(String folderId){
        folderRef
                .document(folderId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xóa thành công
                        Log.e("TAG", "Đã xóa topic có id: " + folderId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi xảy ra lỗi trong quá trình xóa
                        Log.e("TAG", "Lỗi khi xóa topic có id: " + folderId, e);
                    }
                });
    }

    public void addTopicToFolder(String folderId, String topicId){
        Map<String,Object> map = new HashMap<>();
        map.put("idTopic",topicId);
        folderRef.document(folderId)
                .collection("topics")
                .document(topicId)
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document đã được tạo thành công
                        Log.d("TAG", "Document đã được tạo thành công!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Đã có lỗi xảy ra khi tạo document
                        Log.w("TAG", "Lỗi khi tạo document", e);
                    }
                });

    }
    public void getFolderById(String folderId, OnGetFolderListener callback){

        folderRef.document(folderId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        FolderModel folder = document.toObject(FolderModel.class);
                        callback.onGetReady(folder);
                    } else {

                    }
                } else {

                }
            }
        });
    }
    public void getTopicFromFolder(String folderId, OnGetListIdTopicListener callback){
        authorId = FirebaseAuth.getInstance().getUid();
        ArrayList<TopicModel> topicList = new ArrayList<>();
        ArrayList<String> topicIdList = new ArrayList<>();
        TopicDatabaseService topicDatabaseService = new TopicDatabaseService(context);
        folderRef
            .document(folderId)
            .collection("topics")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.e("TAG", "onComplete: "+"true");
                        //                    topicList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idTopic = document.getString("idTopic");

                            topicIdList.add(idTopic);
//                            TopicModel  topicModel =  topicDatabaseService.getTopicByID(idTopic, new OnGetTopicListener() {
//                                @Override
//                                public void onListReady(TopicModel topicModel1) {
//                                    topicList.add(topicModel1);
//                                }
//                            });

                        }
//                        callback.onListReady(topicList);
                        callback.onListReady(topicIdList);
                    }else{
                        callback.onListReady(null);
                    }
                }
            });
    }
    public void deleteTopicFromFolder(String folderId,String topicId){
        folderRef
                .document(folderId)
                .collection("topics")
                .document(topicId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xóa thành công
                        Log.e("TAG", "Đã xóa topic có id: " + folderId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi xảy ra lỗi trong quá trình xóa
                        Log.e("TAG", "Lỗi khi xóa topic có id: " + folderId, e);
                    }
                });
    }
}
