package com.tdtu.englishvocabquiz.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tdtu.englishvocabquiz.Listener.Topic.OnDeleteTopicListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnDeleteWordListener;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

public class ConfirmDeleteDialog {
    private Context context;
    private TopicDatabaseService topicDatabaseService;
    private String IdTopic;
    private OnDeleteTopicListener listener;
    private String idWord;
    private OnDeleteWordListener listenerWord;

    public ConfirmDeleteDialog(Context context, String IdTopic,OnDeleteTopicListener listener) {
        this.context = context;
        this.IdTopic = IdTopic;
        this.listener = listener;
        topicDatabaseService = new TopicDatabaseService(context);
    }

    public ConfirmDeleteDialog(Context context, String idTopic, OnDeleteWordListener listener, String idWord) {
        this.context = context;
        this.IdTopic = idTopic;
        this.listenerWord = listener;
        this.topicDatabaseService = new TopicDatabaseService(context);
        this.idWord = idWord;
    }

    public void showCreateDialogTopic(){
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
    public void showCreateDialogWord(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn chắc chắn muốn xóa");


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                topicDatabaseService.deleteWordById(IdTopic,idWord);
                listenerWord.OnDeleteSuccess();
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
