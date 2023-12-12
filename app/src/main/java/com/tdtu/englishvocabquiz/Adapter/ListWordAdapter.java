package com.tdtu.englishvocabquiz.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class ListWordAdapter extends RecyclerView.Adapter<ListWordAdapter.ViewHolder> {
    private ArrayList<VocabularyModel> vocabList;
    private Context context;
    private TextToSpeech textToSpeech;

    public ListWordAdapter(ArrayList<VocabularyModel> list, Context context){
        vocabList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ListWordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.new_word_item, parent, false);

        return new ListWordAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListWordAdapter.ViewHolder holder, int position) {
        VocabularyModel vocabulary = vocabList.get(position);
        holder.tvWord.setText(vocabulary.getEnglish());
        holder.tvMean.setText(vocabulary.getVietnamese());

        if(context != null){
            textToSpeech = new TextToSpeech(context, status -> {
                // if No error is found then only it will run
                if (status != TextToSpeech.ERROR) {
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            });

            textToSpeech.setLanguage(Locale.UK);

            holder.btnSpeech.setOnClickListener(view ->{
                String toSpeak = holder.tvWord.getText().toString();
                String utteranceId = UUID.randomUUID().toString();
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            });
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvWord;
        TextView tvMean;
        Button btnSpeech;
        Button btnMark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            tvMean = itemView.findViewById(R.id.tvMean);
            btnSpeech = itemView.findViewById(R.id.btnSpeech);
            btnMark = itemView.findViewById(R.id.btnMark);
        }
    }
}
