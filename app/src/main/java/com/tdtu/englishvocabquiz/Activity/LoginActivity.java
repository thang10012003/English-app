package com.tdtu.englishvocabquiz.Activity;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

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
import com.tdtu.englishvocabquiz.Dialog.ResetPasswordByEmailDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private FirebaseFirestore fb;
    private DatabaseReference ref;
    private CollectionReference reference;
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
//        db = FirebaseDatabase.getInstance();
//        ref = db.getReference("users");
        fb = FirebaseFirestore.getInstance();
        reference = fb.collection("users");


        //render new user have signed up
        setPreviousData();
        binding.emailEdt.setText("thang@gmail.com");
        binding.passEdt.setText("123456789");
        //submit sign up btn
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
                                        // Lưu vào SharedPreferences
                                        sharedPreferences = getSharedPreferences("QuizPreference", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("uid", uid);
                                        editor.apply();

                                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu không đúng.",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        dialog.dismiss();
                        binding.passEdt.setError("Mật khẩu bị trống !");
                    }
                }else if(email.isEmpty()){
                    dialog.dismiss();
                    binding.emailEdt.setError("Email bị trống !");
                }else{
                    dialog.dismiss();
                    binding.emailEdt.setError("Email sai!");
                }

            }
        });

        handleForgotPass();

        //back tbn
        binding.backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setPreviousData(){
        Intent ii = getIntent();
        String email = ii.getStringExtra("email");
        String pass = ii.getStringExtra("password");

        binding.emailEdt.setText(email);
        binding.passEdt.setText(pass);
    }
    public void showLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void handleForgotPass(){
        binding.btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordByEmailDialog rDialog = new ResetPasswordByEmailDialog();
                rDialog.show(getSupportFragmentManager(), "CustomDialogFragment");
            }
        });
    }
}