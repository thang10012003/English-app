package com.tdtu.englishvocabquiz.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityAddWordBinding;
import com.tdtu.englishvocabquiz.databinding.ActivityTopicDetailsBinding;

import java.util.Calendar;
import java.util.Date;

public class AddWord extends AppCompatActivity {

    private ActivityAddWordBinding binding;
    private Uri image = null;
    private StorageReference storageReference;
    private VocabularyModel newWord;
    private TopicDatabaseService topicDatabaseService;
    private String TopicName;
    private String IdTopic;
    private String hrefImg;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) { //set img on folder on view
            //handle show activity to choose imgs
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(getApplicationContext()).load(image).into(binding.imgWord);
                }
            } else {
                Toast.makeText(AddWord.this, "Bạn chưa chọn ảnh nào !", Toast.LENGTH_SHORT).show();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageReference = FirebaseStorage.getInstance().getReference();
        topicDatabaseService = new TopicDatabaseService(this);

        Intent intent = getIntent();
         TopicName = intent.getStringExtra("TopicName");
         IdTopic = intent.getStringExtra("IdTopic");

         if(TopicName != null && IdTopic != null){
             binding.tvTopicName.setText("Topic: " + TopicName);
             handleAddWord();
         }


        getItentFromSearch();//render eng and viet after search

        handleUploadImg();


        handleShowSearchEnglishWord();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void handleUploadImg(){
        binding.btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);//set img on view
                saveImg();//save img on database and get href img
            }
        });
    }
    private void getItentFromSearch(){
        Intent intent = getIntent();
        if (intent != null) {
            String eng = intent.getStringExtra("englishWord");
            String viet = intent.getStringExtra("vietnameWord");
            // Use the data as needed
            binding.edtWord.setText(eng);
            binding.edtMean.setText(viet);
        }
    }
    public void saveImg(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Img_Word").child(image.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(AddWord.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        //upload img on cloud storage
        storageReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                String imageURL = urlImage.toString();
                //cap nhat lai anh
                hrefImg = imageURL;
                Toast.makeText(AddWord.this, "Tải dữ liệu ảnh thành công !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddWord.this, "Tải dữ liệu ảnh lên cơ sở dữ liệu không thành công !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    private void handleAddWord(){
        String word = binding.edtWord.getText().toString();
        String mean = binding.edtMean.getText().toString();

            binding.btnAddWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(validateWord(word,mean)) {
                        saveWordIntoTopic( prepareVocab( word, mean));
                    }
                }
            });




    }
    private void saveWordIntoTopic(VocabularyModel newWord){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        topicDatabaseService.addWordToTopic(IdTopic, newWord, new OnAddTopicListener() {
            @Override
            public void OnAddSuccess() {
                Toast.makeText(AddWord.this, "Thêm từ vựng thành công !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
            @Override
            public void OnAddFailure() {
                Toast.makeText(AddWord.this, "Thêm từ vựng thất bại !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private VocabularyModel prepareVocab(String word,String mean){

        VocabularyModel newWord = new VocabularyModel();


            newWord.setIdTopic(IdTopic);
            newWord.setEnglish(word);
            newWord.setVietnamese(mean);

            if(hrefImg!=null){
                newWord.setImgUrl(hrefImg);
            }else{
                newWord.setImgUrl(null);
            }

            newWord.setDesc("Chưa có");

            Date createDate = Calendar.getInstance().getTime();
            newWord.setCreateDate(createDate);

            newWord.setProgress("Chưa có");
            newWord.setCountAchieve(0);
            newWord.setMark(false);

            return newWord;

    }
    private boolean validateWord(String word,String mean){
        if(word.isEmpty()){
            Toast.makeText(this, "Vui lòng không để trống từ vựng !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mean.isEmpty()){
            Toast.makeText(this, "Vui lòng không để trống từ vựng !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void handleShowSearchEnglishWord(){
        binding.tvSearchWordMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchEnglishWordActivity.class));
                finish();
            }
        });
    }
}