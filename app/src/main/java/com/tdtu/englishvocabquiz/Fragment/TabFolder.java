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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFolder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFolder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewFolder;
    private FolderAdapter folderAdapter;
    private ArrayList<FolderItem> listFolder;


    public TabFolder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFolder.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFolder newInstance(String param1, String param2) {
        TabFolder fragment = new TabFolder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab_folder, container, false);

        recyclerViewFolder = view.findViewById(R.id.recyclerViewFolder);
        listFolder = new ArrayList<>();
        listFolder.add(new FolderItem("Food","Vu"));
        listFolder.add(new FolderItem("Animal","Thang"));
        listFolder.add(new FolderItem("Car","Tien"));
        listFolder.add(new FolderItem("Food","Vu"));
        listFolder.add(new FolderItem("Animal","Thang"));
        listFolder.add(new FolderItem("Car","Tien"));

        folderAdapter = new FolderAdapter(getContext(), listFolder);
        recyclerViewFolder.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewFolder.setAdapter(folderAdapter);
        return  view;
    }
}