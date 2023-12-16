package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;


import com.tdtu.englishvocabquiz.Adapter.SuggestVocabAdapter;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.EnglishVocabularyOptions;
import com.tdtu.englishvocabquiz.databinding.ActivitySearchEnglishWordBinding;

import java.util.ArrayList;
import java.util.Map;


public class SearchEnglishWordActivity extends AppCompatActivity implements SuggestVocabAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private SuggestVocabAdapter adapter;
    private ArrayList<VocabularyModel> originalList = new ArrayList<>();
    private EnglishVocabularyOptions evo;
    ActivitySearchEnglishWordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchEnglishWordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        handleRenderVocabByOptionsAndSearch();//set up to choose word

        if (originalList != null) {
            //render the list of word needed to search
            renderWordList(view);
            handleWordSearch(view);
        } else {
            Toast.makeText(getApplicationContext(), "Không có dữ liệu từ vựng !", Toast.LENGTH_SHORT).show();
        }
    }

    public void renderWordList(View view) {
        recyclerView = view.findViewById(R.id.recyWordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // Set up the adapter
        adapter = new SuggestVocabAdapter(getApplicationContext(), originalList, this);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<VocabularyModel> getVocabList(Map<String,String> vocab) {

        ArrayList<VocabularyModel> vList = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, String> entry : vocab.entrySet()) {
            vList.add(new VocabularyModel(String.valueOf(i),entry.getKey(), entry.getValue(), null, null, false, null, null, null, 0));
            i++;
        }
        return vList;
    }
    public void handleWordSearch(View view) {
        EditText edtSearch = view.findViewById(R.id.edtWordSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterData(String query) {
        ArrayList<VocabularyModel> filteredList = new ArrayList<>();

        for (VocabularyModel item : originalList) {
            if (item.getEnglish().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        updateVocabListAdapter(filteredList);
    }

    private void updateVocabListAdapter( ArrayList<VocabularyModel> filteredList){
        // Update the RecyclerView with the filtered data
        adapter = new SuggestVocabAdapter(getApplicationContext(), filteredList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(VocabularyModel item) {
        Toast.makeText(this, item.getVietnamese(), Toast.LENGTH_SHORT).show();
        Intent ii = new Intent(getApplicationContext(),AddWord.class);
        ii.putExtra("englishWord",item.getEnglish());
        ii.putExtra("vietnameWord",item.getVietnamese());
        startActivity(ii);
        finish();
    }
    private void handleShowEngWordOptions(String[] items){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        binding.spinnerEngOptions.setAdapter(adapter);


        binding.spinnerEngOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item
                String selectedItem = (String) parentView.getItemAtPosition(position);

                originalList.clear();
                ArrayList<VocabularyModel> vList = getVocabList( evo.optionsSelected(selectedItem)); //show vocabs of option selected
                originalList.addAll(vList);
                updateVocabListAdapter(vList);


                Toast.makeText(getApplicationContext(), "Danh sách từ thuộc: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

    }
    private void handleRenderVocabByOptionsAndSearch(){
         evo = new EnglishVocabularyOptions();

        handleShowEngWordOptions(evo.topics());
        //set all first
        ArrayList<VocabularyModel> vList = getVocabList(evo.getAllTopics());
        originalList.addAll(vList);

    }

}