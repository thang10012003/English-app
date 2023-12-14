package com.tdtu.englishvocabquiz.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tdtu.englishvocabquiz.Listener.Topic.OnDeleteTopicListener;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

public class ConfirmDeleteDialog {
    Context context;
    TopicDatabaseService topicDatabaseService;
    String IdTopic;
    OnDeleteTopicListener listener;

    public ConfirmDeleteDialog(Context context, String IdTopic,OnDeleteTopicListener listener) {
        this.context = context;
        this.IdTopic = IdTopic;
        this.listener = listener;
        topicDatabaseService = new TopicDatabaseService(context);
    }
    public void showCreateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn chắc chắn muốn xóa");


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                topicDatabaseService.deleteTopic(IdTopic);
                listener.OnDeleteSuccess();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
