package com.tdtu.englishvocabquiz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;

public class SuggestVocabAdapter extends RecyclerView.Adapter<SuggestVocabAdapter.SuggestVocabViewHolder> {
    private Context context;
    private ArrayList<VocabularyModel> list;
    private OnItemClickListener listener;
    public SuggestVocabAdapter(Context context, ArrayList<VocabularyModel> list,OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public SuggestVocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuggestVocabViewHolder(LayoutInflater.from(context).inflate(R.layout.suggest_vocab_item, parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull SuggestVocabViewHolder holder, int position) {
        VocabularyModel currentItem = list.get(position);
        holder.tvWord.setText(currentItem.getEnglish());

        // Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnItemClickListener {
        void onItemClick(VocabularyModel item);
    }
    // Method to update the data in the adapter

    public static class SuggestVocabViewHolder extends RecyclerView.ViewHolder{
        TextView tvWord;

        public SuggestVocabViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);

        }

    }
}
