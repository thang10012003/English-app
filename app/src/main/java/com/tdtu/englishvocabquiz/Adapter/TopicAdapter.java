package com.tdtu.englishvocabquiz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private Context context;
    private ArrayList<TopicItem> list;

    public TopicAdapter(Context context, ArrayList<TopicItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TopicAdapter.TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(context).inflate(R.layout.new_topic_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdapter.TopicViewHolder holder, int position) {
        TopicItem item = list.get(position);
        holder.tvTopicName.setText(item.getFolderName());
        holder.tvCountWord.setText(item.getCountWord() + " học phần");
        holder.tvCreatorName.setText(item.getCreatorName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class TopicViewHolder extends RecyclerView.ViewHolder{
        TextView tvTopicName;
        ImageView imViewAvatar;
        TextView tvCreatorName;
        TextView tvCountWord;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopicName = itemView.findViewById(R.id.tvTopicName);
            imViewAvatar = itemView.findViewById(R.id.imViewAvatar);
            tvCountWord = itemView.findViewById(R.id.tvCountWord);
            tvCreatorName = itemView.findViewById(R.id.tvCreatorName);
        }
    }
}


