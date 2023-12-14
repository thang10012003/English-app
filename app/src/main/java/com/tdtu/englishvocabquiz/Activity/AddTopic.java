package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tdtu.englishvocabquiz.Dialog.ProgressDialog;
import com.tdtu.englishvocabquiz.Enum.TopicType;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityAddTopicBinding;

import java.util.Calendar;
import java.util.Date;

public class AddTopic extends AppCompatActivity {
    private ActivityAddTopicBinding binding;
    private TopicDatabaseService databaseService;
    private TopicModel topicModel;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseService = new TopicDatabaseService(getApplicationContext());
        sharedPreferences = getApplication().getSharedPreferences("QuizPreference",0);
        progressDialog = new ProgressDialog(this);


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.tgMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tgMode.isChecked()){
                    binding.tvMode.setText("Mọi người");
                }else {
                    binding.tvMode.setText("Chỉ mình tôi");
                }
            }
        });


        //an vao nut hoan thanh
        binding.btnFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressDialog.show();
                Log.e("TAG", "onClick: UID " +  sharedPreferences.getString("uid",""));
                if(validateData()){
                    String topicName = binding.edtTopic.getText().toString();
                    String mode = binding.tgMode.isChecked()? TopicType.PUBLIC.toString() :TopicType.PRIVATE.toString();
                    String description = binding.edtDescription.getText().toString();
                    String idAuthor = sharedPreferences.getString("uid","");
                    //String idAuthor = FirebaseAuth.getInstance().getUid();
                    int numberOfVocab = 0;
                    Date createDate = Calendar.getInstance().getTime();
                    TopicModel topic = new TopicModel(topicName, description, numberOfVocab,  createDate,  mode,  idAuthor);
                    databaseService.addTopic(topic, new OnAddTopicListener() {
                        @Override
                        public void OnAddSuccess() {
                            progressDialog.dismiss();
                            finish();
                        }

                        @Override
                        public void OnAddFailure() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Tạo topic thất bại",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    progressDialog.dismiss();
                    binding.edtTopic.setError("Vui lòng nhập tên topic");
                }
            }
        });
    }
    public boolean validateData(){
        if(binding.edtTopic.getText().toString().equals("")){
            return false;
        }

        return true;
    }
}






//    private VocabAdapter adapter;
//    private ArrayList<VocabItem> list;
//an vao nut setting
//        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        list = new ArrayList<>();
//        adapter = new VocabAdapter(getApplicationContext(),list);
//        binding.rclAddTopic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        binding.rclAddTopic.setAdapter(adapter);
//an vao them tu
//        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list.add(new VocabItem());
//                adapter.notifyDataSetChanged();
////                adapter.notifyItemInserted();
//            }
//
//        });