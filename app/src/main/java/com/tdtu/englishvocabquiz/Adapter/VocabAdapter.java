package com.tdtu.englishvocabquiz.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.VocabViewHolder> {
    private Context context;
    private ArrayList<VocabItem> list;

    public VocabAdapter(Context context, ArrayList<VocabItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VocabAdapter.VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VocabViewHolder(LayoutInflater.from(context).inflate(R.layout.new_vocab_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VocabAdapter.VocabViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VocabViewHolder extends RecyclerView.ViewHolder{
        private EditText word,mean;
        public VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.edtVocab);
            mean = itemView.findViewById(R.id.edtMean);
        }
    }
}
