package com.tdtu.englishvocabquiz.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdtu.englishvocabquiz.Adapter.FolderAdapter;
import com.tdtu.englishvocabquiz.Adapter.FolderItem;
import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;


public class TabFolder extends Fragment {



    private RecyclerView recyclerViewFolder;
    private FolderAdapter folderAdapter;
    private ArrayList<FolderItem> listFolder;


    public TabFolder() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_folder, container, false);

        recyclerViewFolder = view.findViewById(R.id.recyclerViewFolder);
        listFolder = new ArrayList<>();
//        listFolder.add(new FolderItem("Food","Vu"));
//        listFolder.add(new FolderItem("Animal","Thang"));
//        listFolder.add(new FolderItem("Car","Tien"));
//        listFolder.add(new FolderItem("Food","Vu"));
//        listFolder.add(new FolderItem("Animal","Thang"));
//        listFolder.add(new FolderItem("Car","Tien"));

        folderAdapter = new FolderAdapter(getContext(), listFolder);
        recyclerViewFolder.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewFolder.setAdapter(folderAdapter);
        return  view;
    }
}