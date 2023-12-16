package com.tdtu.englishvocabquiz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.R;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private Context context;
    private ArrayList<FolderModel> list;

    public FolderAdapter(Context context, ArrayList<FolderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(context).inflate(R.layout.new_folder_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.FolderViewHolder holder, int position) {
        FolderModel item = list.get(position);
        holder.tvFolderName.setText(item.getName());
        holder.tvCreatorName.setText(item.getIdAuthor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class FolderViewHolder extends RecyclerView.ViewHolder{
        TextView tvFolderName;
        ImageView imViewAvatar;
        TextView tvCreatorName;
        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tvFolderName);
            imViewAvatar = itemView.findViewById(R.id.imViewAvatar);
            tvCreatorName = itemView.findViewById(R.id.tvCreatorName);
        }
    }
}


