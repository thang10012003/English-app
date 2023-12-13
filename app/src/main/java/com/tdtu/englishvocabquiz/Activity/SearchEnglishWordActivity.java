package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.tdtu.englishvocabquiz.Adapter.SuggestVocabAdapter;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.databinding.ActivitySearchEnglishWordBinding;

import java.util.ArrayList;


public class SearchEnglishWordActivity extends AppCompatActivity implements SuggestVocabAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private SuggestVocabAdapter adapter;
    private ArrayList<VocabularyModel> originalList = new ArrayList<>();
    ActivitySearchEnglishWordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchEnglishWordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //get all of the vocab from firestore to the list
        originalList.addAll(getVocabList());

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

    public ArrayList<VocabularyModel> getVocabList() {
        ArrayList<VocabularyModel> vList = new ArrayList<>();

        String[] englishNouns = {
                "Dog", "Cat", "Table", "Chair", "Computer", "Phone", "Book", "Car", "House", "Tree",
                "Flower", "River", "City", "Friend", "Family", "Time", "Money", "Love", "Job", "Food",
                "Water", "Air", "Earth", "Sun", "Moon", "Child", "Parent", "School", "Teacher", "Student",
                "Doctor", "Hospital", "Music", "Art", "Movie", "Television", "Ocean", "Mountain", "Country",
                "Language", "Idea", "Problem", "Solution", "Government", "President", "War", "Peace",
                "Nature", "Science", "Technology"
        };
        String[] vietnameseDefinitions = {
                "Chó", "Mèo", "Bàn", "Ghế", "Máy tính", "Điện thoại", "Sách", "Ô tô", "Nhà", "Cây",
                "Hoa", "Sông", "Thành phố", "Bạn bè", "Gia đình", "Thời gian", "Tiền", "Tình yêu", "Công việc", "Thức ăn",
                "Nước", "Không khí", "Trái đất", "Mặt trời", "Mặt trăng", "Đứa trẻ", "Phụ huynh", "Trường học", "Giáo viên", "Học sinh",
                "Bác sĩ", "Bệnh viện", "Âm nhạc", "Nghệ thuật", "Phim", "Truyền hình", "Đại dương", "Núi", "Quốc gia",
                "Ngôn ngữ", "Ý tưởng", "Vấn đề", "Giải pháp", "Chính phủ", "Tổng thống", "Chiến tranh", "Hòa bình",
                "Thiên nhiên", "Khoa học", "Công nghệ"
        };
        for(int i=0;i<englishNouns.length;i++){
            vList.add(new VocabularyModel(String.valueOf(i), englishNouns[i], vietnameseDefinitions[i], null, null, false, null, null, null, 0));
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
}