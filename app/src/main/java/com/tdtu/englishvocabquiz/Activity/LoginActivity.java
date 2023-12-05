package com.tdtu.englishvocabquiz.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private UserModel userModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //firebase init
        auth = FirebaseAuth.getInstance();
        //database
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("users");
        //submit sign up btn

        binding.emailEdt.setText("tt@gmail.com");
        binding.passEdt.setText("123456");
        binding.loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //dialog loading
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                String email = binding.emailEdt.getText().toString().trim();
                String pass = binding.passEdt.getText().toString().trim();
                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email,pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        //thanh cong thi tat dialog
                                        dialog.dismiss();
                                        //get uid
                                        FirebaseUser user = auth.getCurrentUser();
                                        String uid = user.getUid();


                                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        ref.child(uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()) {

                                                    sharedPreferences = getSharedPreferences("QuizPreference", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("uid",snapshot.child("id_acc").getValue(String.class));
                                                    editor.putString("uid",snapshot.child("id_acc").getValue(String.class));
                                                    editor.commit();

//                                                    bundle.putString("createDate",snapshot.child("createDate").getValue(String.class));
//                                                    bundle.putString("gender",snapshot.child("gender").getValue(String.class));
//                                                    bundle.putString("avt",snapshot.child("avt").getValue(String.class));
//                                                    bundle.putString("mobile",snapshot.child("mobile").getValue(String.class));
//                                                    bundle.putString("name",snapshot.child("name").getValue(String.class));

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(LoginActivity.this,"Thất bại",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu không đúng.",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        binding.passEdt.setError("Mật khẩu bị trống !");
                    }
                }else if(email.isEmpty()){
                    binding.emailEdt.setError("Email bị trống !");
                }else{
                    binding.emailEdt.setError("Email sai!");
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
    public void showLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}