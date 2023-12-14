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
import com.tdtu.englishvocabquiz.Service.CustomTextToSpeech;

import java.util.ArrayList;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.VocabViewHolder> {
    private Context context;
    private ArrayList<VocabularyModel> list;

    public VocabAdapter(Context context, ArrayList<VocabularyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VocabAdapter.VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VocabViewHolder(LayoutInflater.from(context).inflate(R.layout.new_word_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VocabAdapter.VocabViewHolder holder, int position) {
        holder.word.setText(list.get(position).getEnglish().toString());
        holder.mean.setText(list.get(position).getVietnamese().toString());
        holder.mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(holder.getAdapterPosition()).setMark(true);
                holder.mark.setImageResource(R.drawable.baseline_star_24);
            }
        });
        holder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTextToSpeech textToSpeech = new CustomTextToSpeech(context,holder.word.getText().toString());
                textToSpeech.speak();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VocabViewHolder extends RecyclerView.ViewHolder{
        private TextView word,mean;
        private ImageView speak, mark;

        public VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.tvWord);
            mean = itemView.findViewById(R.id.tvMean);
            speak = itemView.findViewById(R.id.btnSpeech);
            mark = itemView.findViewById(R.id.btnMark);

        }
    }
}
