package com.tdtu.englishvocabquiz.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.ActivitySignUpBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
@RequiresApi(api = Build.VERSION_CODES.O)

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;

    private FirebaseAuth auth;
//    private FirebaseDatabase db;
    private FirebaseFirestore db;
//    private DatabaseReference ref;
    private CollectionReference ref;
    private String createDate = null;
    private FirebaseUser user;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //firebase init
        auth = FirebaseAuth.getInstance();
        //database
        db = FirebaseFirestore.getInstance();
        ref = db.collection("users");

        //init dialog
        createNewDialog();

        //submit sign up btn
        binding.signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email = binding.emailEdt.getText().toString().trim();
                String pass = binding.passEdt.getText().toString().trim();
                String conPass = binding.confPassEdt.getText().toString().trim();
                String name = binding.nameEdt.getText().toString().trim();

                        if(validData(email,pass,conPass,name)){
                            createNewUser(email,pass,name);
                        }
                }


        });

        //back tbn
        binding.backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void createNewDialog(){
         builder = new AlertDialog.Builder(getApplicationContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
         dialog = builder.create();
    }
    private void createNewUser(String email, String pass,String name){
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = auth.getCurrentUser();
                    String uid = user.getUid();
                    Toast.makeText(SignUpActivity.this,uid.toString(),Toast.LENGTH_LONG).show();

                    updateUserFirestore(name,uid,email,pass); //show login activity when successfully
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this,"Đăng ký thất bại !!!",Toast.LENGTH_LONG).show();
                        }
                    });
    }
    private void updateUserFirestore(String name,String uid,String email,String pass){
                                //get now date
                                LocalDate currentDate = LocalDate.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
                                String createDate = currentDate.format(formatter);

                                //create user model
                                String gender ="Chưa có";
                                String avt ="Chưa có";
                                String mobile ="Chưa có ";
                                int posts = 0;

                                UserModel newUser = new UserModel(name,gender,createDate,posts,avt,uid,mobile);

                                //add child to db
////                                ref.(uid).setValue(newUser);
                                ref.add(newUser)
                                        .addOnSuccessListener(documentReference -> {
                                            Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());

                                            Toast.makeText(SignUpActivity.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                                           //send uid
                                            intent.putExtra("uid",uid);
                                            intent.putExtra("email",email);
                                            intent.putExtra("password",pass);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.w("TAG", "Error adding document", e);
                                            Toast.makeText(SignUpActivity.this,"Không thể cập nhật thông tin người dùng !!!",Toast.LENGTH_LONG).show();
                                        });


    }
    private boolean validData(String email, String pass, String conPass, String name){
        //validate
        if(email.isEmpty()){
            binding.emailEdt.setError("Email không được để trống");
            return false;
        }
        else if(pass.isEmpty()){
            binding.passEdt.setError("Mật khẩu không được để trống");
            return false;

        }
        else if(conPass.isEmpty()){
            binding.confPassEdt.setError("Mật khẩu xác nhận không được để trống");
            return false;

        }
        else if (!conPass.equals(pass)){
            binding.confPassEdt.setError("Mật khẩu xác nhận không chính xác");
            return false;


        }else if (name.isEmpty()){
            binding.confPassEdt.setError("Vui lòng nhập họ tên !");
            return false;


        }
        return true;
    }
    private void createNewUser_old(String email, String pass){
        Log.e("TAG", "onClick: "+"success");
        //create acc
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            //                        @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //get uid
                    FirebaseUser user = auth.getCurrentUser();
                    String uid = user.getUid();
                    Log.e("TAG", "onComplete: "+ uid);

//
                }else{
                    Toast.makeText(SignUpActivity.this,"Đăng ký thất bại !!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}