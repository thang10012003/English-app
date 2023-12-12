package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdtu.englishvocabquiz.Dialog.CreateFolderDialog;
import com.tdtu.englishvocabquiz.Fragment.HomeFragment;
import com.tdtu.englishvocabquiz.Fragment.LibraryFragment;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Fragment.SolutionsFragment;
import com.tdtu.englishvocabquiz.Fragment.UserFragment;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.Service.NetworkUtils;
import com.tdtu.englishvocabquiz.databinding.ActivityHomeBinding;

import org.checkerframework.checker.units.qual.C;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    UserModel userModel;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());



        //get uid of user when no internet
        uid = checkRecentUser();
        if(uid != null){
//            Toast.makeText(this, "Đăng nhập thành công người dùng: "+uid, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Đăng nhập thành công người dùng", Toast.LENGTH_SHORT).show();
            //check internet
            checkInternet();
        }

        binding.navigationBar.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigationHome){
                replaceFragment(new HomeFragment());
            }else if(item.getItemId() == R.id.navigationSolutions){
                replaceFragment(new SolutionsFragment());
            }else if(item.getItemId() == R.id.navigationAdd){
                showBottomDialog();
            }else if(item.getItemId() == R.id.navigationLibrary){
                replaceFragment(new LibraryFragment());
            }else if(item.getItemId() == R.id.navigationUser) {
                replaceFragment(new UserFragment());
            }

            return  true;
        });

    }
    private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

    private void showBottomDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout topic = dialog.findViewById(R.id.layoutTopic);
        LinearLayout folder = dialog.findViewById(R.id.layoutFolder);
        ImageView cancel = dialog.findViewById(R.id.btnCancel);

        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), AddTopic.class));
            }
        });
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//                builder.setTitle("Tạo folder mới");
//
//                final EditText input = new EditText(HomeActivity.this);
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//                builder.setView(input);
//
//                builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String folderName = input.getText().toString();
////                        createNewFolder(folderName);
//                    }
//                });
//
//                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.show();
                dialog.dismiss();
                CreateFolderDialog createFolderDialog = new CreateFolderDialog(dialog.getContext());
                createFolderDialog.showCreateFolderDialog();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private String checkRecentUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("RecentAccount", MODE_PRIVATE);
        if(sharedPreferences != null){
             uid = sharedPreferences.getString("uid", null);
        }
        return uid;
    }
    private void checkInternet(){
        if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
            Toast.makeText(this, "Kết nối Internet thành công !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Kết nối Internet thất bại !", Toast.LENGTH_SHORT).show();
        }
    }
}