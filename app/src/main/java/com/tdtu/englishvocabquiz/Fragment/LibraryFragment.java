package com.tdtu.englishvocabquiz.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.tdtu.englishvocabquiz.Activity.AddTopic;
import com.tdtu.englishvocabquiz.Activity.EditProfileActivity;
import com.tdtu.englishvocabquiz.Adapter.ViewPagerAdapter;
import com.tdtu.englishvocabquiz.Dialog.CreateFolderDialog;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.FragmentLibraryBinding;


public class LibraryFragment extends Fragment {



    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    ImageView btnCreate;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        btnCreate = view.findViewById(R.id.btnCreate);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                super.onPageSelected(position);
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager2.getCurrentItem() == 0){
                    getActivity().startActivity(new Intent(getActivity(), AddTopic.class));
                }else {
                    CreateFolderDialog createFolderDialog = new CreateFolderDialog(getActivity());
                    createFolderDialog.showCreateFolderDialog();
                }
            }
        });

        return view;
    }
}