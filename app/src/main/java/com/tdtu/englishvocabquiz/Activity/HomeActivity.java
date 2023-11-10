package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdtu.englishvocabquiz.Fragment.HomeFragment;
import com.tdtu.englishvocabquiz.Fragment.LibraryFragment;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Fragment.SolutionsFragment;
import com.tdtu.englishvocabquiz.Fragment.UserFragment;
import com.tdtu.englishvocabquiz.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

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
            }
        });
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
}