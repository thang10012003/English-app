package com.tdtu.englishvocabquiz.Activity;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.ActivityEditProfileBinding;

import java.util.ArrayList;
import java.util.Currency;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private DatabaseReference ref;
    StorageReference storageRef;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alert;
    StorageReference storageReference;
    Uri image = null;
    String uid = null ;
    private UserModel currDataUser;
    private String ref_col = "users";
    private String id_user;
    private String gender;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) { //set img on folder on view
            //handle show activity to choose imgs
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(getApplicationContext()).load(image).into(binding.uploadImage);
                }
            } else {
                Toast.makeText(EditProfileActivity.this, "Bạn chưa chọn ảnh nào !", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Log.d("TAG", "onCreate: SUcceess");
        //init user database
        db = FirebaseFirestore.getInstance();
        //init storage
        storageRef = FirebaseStorage.getInstance().getReference();
        //get current id of user
        getUidByAuthen();//set uid
        Log.d("TAG", uid);
        if(uid != null){
            //find user by id_acc on firestore
            //currDataUser = new UserModel(getUserOnDatabase_byUid(uid)); //set currDataUser
            getUserOnDatabase_byUid(uid);
            if(currDataUser != null || id_user != null){
                //update user when recieve args

            }


        }
        //alert dialog
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông Báo");
        alertDialogBuilder.setMessage("Đang cập nhật thông tin...");

        binding.edtuploadGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderOptionsDialog();
            }
        });
        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);//set img on view
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void showGenderOptionsDialog() {
        final CharSequence[] items = {"Nam","Nữ"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Place Option");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Lấy lựa chọn của người dùng và đặt nó vào EditText "Place"
                RadioGroup edtPlace = findViewById(R.id.edtuploadGender);
                edtPlace.setTextDirection(items[item].length());
            }
        });
        builder.show();
    }
    private void setInfomationOfUserOnDB(UserModel user){
        db.collection(ref_col).document(id_user).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditProfileActivity.this, "Cập nhật dữ liệu người dùng thành công.", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Cập nhật người dùng thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getAllUserOnDatabase(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                  UserModel user = document.toObject(UserModel.class);
                                  Log.d(TAG, user.getId_acc());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void getUserOnDatabase_byUid(String uid){
        db.collection("users").whereEqualTo("id_acc", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserModel user = document.toObject(UserModel.class);
                        saveUserData(user,document.getId());//get firts user needed
                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting user documents by uid: ", task.getException());
                }
            }
        });
    }
    private void saveUserData(UserModel user, String thisId){
        currDataUser = new UserModel(user);
        id_user = thisId;
    }
    private void getUidByAuthen(){
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid().toString();
    }
    private void getDataOnViewAndChangeUser(){
        String name = binding.edtuploadName.getText().toString().trim();
        String phone = binding.edtUploadPhoneNumber.getText().toString().trim();
        getValueOfRadioGender();

        //change user got before update on firestore
        if(!name.isEmpty()){
            currDataUser.setName(name);
        }
        if(!phone.isEmpty()){
            currDataUser.setMobile(phone);
        }
        if(!gender.isEmpty()){
            currDataUser.setGender(gender);
        }

    }
    private void getValueOfRadioGender(){
        if(binding.rgFemale.isChecked()){
            gender="Nữ";
        }
        if(binding.rgMale.isChecked()){
            gender="Nam";
        }
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("avatarImages").child(image.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
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
                //change profile user
                currDataUser.setAvt(imageURL);
                getDataOnViewAndChangeUser();

                //update user in here

                setInfomationOfUserOnDB(currDataUser);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Tải dữ liệu ảnh và dữ liệu user lên cơ sở dữ liệu không thành công !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


//        public void uploadData(){
//            String name = binding.edtuploadName.toString();
//            String gender = binding.edtuploadGender.getText().toString();
//            String phonenumber = binding.edtUploadPhoneNumber.getText().toString();
//            String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//                UserModel dataClass = new UserModel(name,gender,currentDate,5,image.toString(),);
//            //We are changing the child from title to currentDate,
//            // because we will be updating title as well and it may affect child value.
//
//            FirebaseDatabase.getInstance().getReference("Android Tutorials").child(currentDate)
//                    .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
    }
}


//
//        //back home
//        binding.backBtn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//
//            }
//        });
//
        //upload img


//
//
//
//        });
//
//        //submit edit profile
//        binding.submitBtn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                String gender="Chưa có";
//                String name = binding.newNameEdt.getText().toString().trim();
//                String mobile=binding.newMobileEdt.getText().toString().trim();
//                if(binding.maleCb.isChecked()){
//                    gender="Nam";
//                }
//                if(binding.femaleCb.isChecked()){
//                    gender="Nữ";
//                }
//
//                if(name.isEmpty()){
//                    Toast.makeText(EditProfileActivity.this, "Vui lòng điền tên !", Toast.LENGTH_SHORT).show();
//                }
//                if(mobile.isEmpty()){
//                    Toast.makeText(EditProfileActivity.this, "Vui lòng điền số điệnt thoại.", Toast.LENGTH_SHORT).show();
//                }
//
//
//                    //handle update - if having image will insert but wont
//                    updateDataUser(name,mobile,gender);
//
//
//
//            }
//        });


//    private void updateDataUser(String name, String mobile, String gender) {
//        alert = alertDialogBuilder.create();
//        alert.show();
//        //if user want to upload img then upload on firebase db
//        String avt = null;
//        if(image != null){
//            avt = uploadImg(image);//
//        }
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String uid = auth.getCurrentUser().getUid();
//        ref = db.getReference("users").child(uid);
//
//        Map<String,Object> newInfo = new HashMap<>();
//        newInfo.put("name",name);
//        newInfo.put("mobile",mobile);
//        newInfo.put("gender",gender);
//        if(avt != null){
//            newInfo.put("avt",avt);
//        }
//
//        ref.updateChildren(newInfo)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                alert.dismiss();
//                Toast.makeText(EditProfileActivity.this, "Cập nhật thông tin thành công.", Toast.LENGTH_SHORT).show();
//                binding.reviewProfileBtn.setEnabled(true);
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        alert.dismiss();
//                        Toast.makeText(EditProfileActivity.this, "Cập nhật thông tin thất bại !.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//    private void showDataFromProfileUser() {
//        Intent i = getIntent();
//        Bundle  bundle = i.getExtras();
//
//       binding.newNameEdt.setText((String)bundle.get("userName"));
//       binding.newMobileEdt.setText((String)bundle.get("mobile"));
//       String gender = (String) bundle.get("gender");
//       if(gender.equals("Name")){
//           binding.maleCb.isChecked();
//       }
//        if(gender.equals("Nữ")){
//            binding.femaleCb.isChecked();
//        }
//    }
//
/*   private String uploadImg(Uri image) { //update img on cloud storage
        //set progress alert
        alertDialogBuilder.setMessage("Đang tải ảnh lên hệ thống...");
        AlertDialog imgAlert = alertDialogBuilder.create();
       imgAlert.show();

     *//*  //set submit prevent
        binding.submitBtn.setEnabled(false);*//*

        String imgName = UUID.randomUUID().toString();
       StorageReference Sref = storageRef.child("avatarImages/"+ imgName);
       Sref.putFile(image)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                *//*binding.submitBtn.setEnabled(true);*//*
                Toast.makeText(EditProfileActivity.this, "Cập nhật ảnh thành công !", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       *//* binding.submitBtn.setEnabled(true);*//*
                        Toast.makeText(EditProfileActivity.this, "Cập nhật ảnh thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        return imgName;
   }
}*/
