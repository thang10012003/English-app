package com.tdtu.englishvocabquiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.ResultModel;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Model.TopRankModel;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;

import java.util.ArrayList;
import java.util.List;

public class TopRankAdapter extends RecyclerView.Adapter<TopRankAdapter.ViewHolder> {
//    private final List<TopRankModel> topRankList;
    private final List<ResultModel> topRankList;
    private Context context;
    private  UserDatabaseService userDatabaseService;

//    public TopRankAdapter(ArrayList<TopRankModel> list) {
//        topRankList = list;
//    }
//    public TopRankAdapter(Context context, ArrayList<TopRankModel> list) {
//        this.topRankList = list;
//        this.context = context;
//    }
    public TopRankAdapter(Context context, ArrayList<ResultModel> list) {
        this.topRankList = list;
        this.context = context;
        userDatabaseService = new UserDatabaseService(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.ranking_layout, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        TopRankModel topRank = topRankList.get(position);
        UserModel userModel = userDatabaseService.getUserById(topRankList.get(position).getIdAuthor(), new OnGetUserListener() {
            @Override
            public void onGetReady(UserModel userModel1) {
                TopRankModel topRank = new TopRankModel(userModel1.getName(), holder.getAdapterPosition()+1,topRankList.get(holder.getAdapterPosition()).getNumRight());
                switch (holder.getAdapterPosition()){
                    case 0:{
                        holder.topRankLayout.setBackgroundColor(Color.parseColor("#ffff00"));
                        holder.rankNumber.setTextColor(Color.parseColor("#000000"));
                        holder.name.setTextColor(Color.parseColor("#000000"));
                        holder.score.setTextColor(Color.parseColor("#000000"));
                        holder.avt.setBackgroundResource(R.drawable.baseline_person_24_black);
                        break;
                    }
                    case 1:{
                        holder.topRankLayout.setBackgroundColor(Color.parseColor("#d7d7d7"));
                        holder.rankNumber.setTextColor(Color.parseColor("#000000"));
                        holder.name.setTextColor(Color.parseColor("#000000"));
                        holder.score.setTextColor(Color.parseColor("#000000"));
                        holder.avt.setBackgroundResource(R.drawable.baseline_person_24_black);
                        break;
                    }
                    case 2:{
                        holder.topRankLayout.setBackgroundColor(Color.parseColor("#ad8a56"));
                        break;
                    }
                    default: break;
                }
                holder.rankNumber.setText(topRank.getRank().toString());
                holder.name.setText(topRank.getUserName());
//        holder.avt.setBackground();
                holder.score.setText(topRank.getScore().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return topRankList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankNumber;
        ImageView avt;
        TextView name;
        TextView score;
        LinearLayout topRankLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rankNumber = itemView.findViewById(R.id.topRankNumber);
            avt = itemView.findViewById(R.id.topRankAvatar);
            name = itemView.findViewById(R.id.topRankName);
            score = itemView.findViewById(R.id.topRankScore);
            topRankLayout = itemView.findViewById(R.id.topRankLayout);
        }
    }
}
