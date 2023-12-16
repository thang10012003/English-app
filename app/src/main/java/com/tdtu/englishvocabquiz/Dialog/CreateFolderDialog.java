package com.tdtu.englishvocabquiz.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.tdtu.englishvocabquiz.Listener.Folder.OnAddFolderListener;
import com.tdtu.englishvocabquiz.Service.FolderDatabaseService;

public class CreateFolderDialog {
    private Context context;
    private FolderDatabaseService folderDatabaseService;

    public CreateFolderDialog(Context context) {
        this.context = context;
    }

    public void showCreateFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tạo folder mới");

        folderDatabaseService = new FolderDatabaseService(context);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String folderName = input.getText().toString();
                // Gọi hàm để xử lý việc tạo folder với folderName ở đây (createNewFolder(folderName);)
                folderDatabaseService.addFolder(folderName, new OnAddFolderListener() {
                    @Override
                    public void OnAddSuccess() {

                    }

                    @Override
                    public void OnAddFailure() {

                    }
                });
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
