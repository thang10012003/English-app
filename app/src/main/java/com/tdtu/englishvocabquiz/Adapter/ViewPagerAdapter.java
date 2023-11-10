package com.tdtu.englishvocabquiz.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tdtu.englishvocabquiz.Fragment.LibraryFragment;
import com.tdtu.englishvocabquiz.Fragment.TabFolder;
import com.tdtu.englishvocabquiz.Fragment.TabTopic;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull LibraryFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            Log.e("TAG", "createFragment: "+"tabtopic" );
            return new TabTopic();
        }else if(position == 1){
            Log.e("TAG", "createFragment: "+"tabfolder" );
            return new TabFolder();
        }
        return  new TabTopic();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
