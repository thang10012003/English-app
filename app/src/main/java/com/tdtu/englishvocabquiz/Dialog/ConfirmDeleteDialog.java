package com.tdtu.englishvocabquiz.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tdtu.englishvocabquiz.Listener.Folder.OnDeleteFolderListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnDeleteTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnGetTopicListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnDeleteWordListener;
import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Service.FolderDatabaseService;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

public class ConfirmDeleteDialog {
    private Context context;
    private TopicDatabaseService topicDatabaseService;
    private String idTopic;
    private OnDeleteTopicListener listener;
    private String idWord;
    private OnDeleteWordListener listenerWord;
    private FolderDatabaseService folderDatabaseService;
    private String idFolder;
    private OnDeleteFolderListener folderListener;
    private OnDeleteFolderListener folderListener1;

    public ConfirmDeleteDialog(Context context, String idTopic,OnDeleteTopicListener listener) {
        this.context = context;
        this.idTopic = idTopic;
        this.listener = listener;
        topicDatabaseService = new TopicDatabaseService(context);
    }

    public ConfirmDeleteDialog(Context context, String idTopic, OnDeleteWordListener listener, String idWord) {
        this.context = context;
        this.idTopic = idTopic;
        this.listenerWord = listener;
        this.topicDatabaseService = new TopicDatabaseService(context);
        this.idWord = idWord;
    }
    public ConfirmDeleteDialog(Context context, String idFolder, OnDeleteFolderListener listener) {
            this.context = context;
            this.idFolder = idFolder;
            this.folderListener = listener;
            folderDatabaseService = new FolderDatabaseService(context);
    }
    public ConfirmDeleteDialog(Context context, String idFolder, OnDeleteFolderListener listener, String idTopic) {
        this.context = context;
        this.idTopic = idTopic;
        this.folderListener1 = listener;
        this.folderDatabaseService = new FolderDatabaseService(context);
        this.idFolder = idFolder;
    }


    public void showCreateDialogTopic(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn chắc chắn muốn xóa");


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                topicDatabaseService.deleteTopic(idTopic);
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
                topicDatabaseService.deleteWordById(idTopic,idWord);
//                topicDatabaseService.updateNumWordTopic(IdTopic, topicDatabaseService.getTopicByID(IdTopic, new OnGetTopicListener() {
//                    @Override
//                    public void onListReady(TopicModel topicModel) {
//
//                    }
//                }),1);
                TopicModel topicModel = topicDatabaseService.getTopicByID(idTopic, new OnGetTopicListener() {
                    @Override
                    public void onListReady(TopicModel topicModel) {
                        topicDatabaseService.updateNumWordTopic(idTopic, topicModel,1);
                    }
                });
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
    public void showCreateDialogFolder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn chắc chắn muốn xóa");


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                folderDatabaseService.deleteFolder(idFolder);
                folderListener.OnDeleteSuccess();
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
    public void showCreateDialogFolderDeleteTopic(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn chắc chắn muốn xóa");


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                folderDatabaseService.deleteTopicFromFolder(idFolder, idTopic);
                folderListener1.OnDeleteSuccess();
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
