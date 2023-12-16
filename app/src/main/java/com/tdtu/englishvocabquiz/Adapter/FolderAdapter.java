package com.tdtu.englishvocabquiz.Adapter;

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

import com.tdtu.englishvocabquiz.Activity.FolderDetails;
import com.tdtu.englishvocabquiz.Activity.HomeActivity;
import com.tdtu.englishvocabquiz.Activity.MoveTopicTopFolder;
import com.tdtu.englishvocabquiz.Fragment.LibraryFragment;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.FolderDatabaseService;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityFolderDetailsBinding;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private Context context;
    private ArrayList<FolderModel> list;
    private FolderDatabaseService folderDatabaseService;
    private UserDatabaseService userDatabaseService;
    private OnItemClickListener listener;

    public FolderAdapter(Context context, ArrayList<FolderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        folderDatabaseService = new FolderDatabaseService(context);
        userDatabaseService = new UserDatabaseService(context);
        return new FolderViewHolder(LayoutInflater.from(context).inflate(R.layout.new_folder_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.FolderViewHolder holder, int position) {
        FolderModel item = list.get(position);
        holder.tvFolderName.setText(item.getNameFolder());
        UserModel userModel = userDatabaseService.getUserById(item.getIdAuthor(), new OnGetUserListener() {
            @Override
            public void onGetReady(UserModel userModel1) {
                holder.tvCreatorName.setText(userModel1.getName());
            }
        });
        if(context instanceof HomeActivity){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FolderDetails.class);
                    intent.putExtra("folderId",list.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("authorId",list.get(holder.getAdapterPosition()).getIdAuthor());
                    context.startActivity(intent);
                }
            });
        }else {
            Log.e("TAG", "onBindViewHolder: " +"MoveTopicToFolder" );
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(holder.getAdapterPosition());
                    }
                }
            });
        }
//        if(context instanceof MoveTopicTopFolder){
//            Log.e("TAG", "onBindViewHolder: " +"MoveTopicToFolder" );
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        listener.onItemClick(holder.getAdapterPosition());
//                    }
//                }
//            });
//        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Setter để thiết lập listener từ bên ngoài Adapter
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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


