package com.tdtu.englishvocabquiz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.Activity.AddTopic;
import com.tdtu.englishvocabquiz.Activity.RankingActivity;
import com.tdtu.englishvocabquiz.Activity.UpdateWord;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.CustomTextToSpeech;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

import java.util.ArrayList;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.VocabViewHolder> {
    private Context context;
    private ArrayList<VocabularyModel> list;
    private TopicDatabaseService topicDatabaseService;
    private OnItemClickListener listener;

    public VocabAdapter(Context context, ArrayList<VocabularyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VocabAdapter.VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        topicDatabaseService = new TopicDatabaseService(context);
        return new VocabViewHolder(LayoutInflater.from(context).inflate(R.layout.new_word_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VocabAdapter.VocabViewHolder holder, int position) {
        VocabularyModel item = list.get(position);
        holder.word.setText(list.get(position).getEnglish().toString());
        holder.mean.setText(list.get(position).getVietnamese().toString());
        if(list.get(holder.getAdapterPosition()).isMark()){
            holder.mark.setImageResource(R.drawable.baseline_star_24);
        }else {
            holder.mark.setImageResource(R.drawable.baseline_star_border_24);

        }
        holder.mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTopic = list.get(holder.getAdapterPosition()).getIdTopic();
                String id = list.get(holder.getAdapterPosition()).getId();
                VocabularyModel vocabularyModel = list.get(holder.getAdapterPosition());
                if(list.get(holder.getAdapterPosition()).isMark()){
                    list.get(holder.getAdapterPosition()).setMark(false);

                    topicDatabaseService.updateFieldWord(idTopic,id,vocabularyModel,"mark","false");
                    holder.mark.setImageResource(R.drawable.baseline_star_24);
                }else {
                    list.get(holder.getAdapterPosition()).setMark(true);

                    topicDatabaseService.updateFieldWord(idTopic,id,vocabularyModel,"mark","true");
                    holder.mark.setImageResource(R.drawable.baseline_star_border_24);
                }
            }
        });
        holder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTextToSpeech textToSpeech = new CustomTextToSpeech(context,holder.word.getText().toString());
                textToSpeech.speak();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("TAG", "onClick IdTopic: "+ item.getIdTopic());
//                Log.e("TAG", "onClick IdWord: "+ item.getId());
//                Intent intent = new Intent(context, UpdateWord.class);
////                intent.putExtra("IdTopic", item.getIdTopic());
////                intent.putExtra("IdWord",item.getId());
////                context.startActivity(intent);
//                context.startActivity(intent);
                if (listener != null) {
                    listener.onItemClick(position);
                }

            }
        });
    }
    // Interface cho sự kiện click
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Setter để thiết lập listener từ bên ngoài Adapter
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
