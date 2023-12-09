package com.tdtu.englishvocabquiz.Dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.tdtu.englishvocabquiz.R;

public class ProgressDialog {
    AlertDialog dialog;
    Context context;
    public ProgressDialog(Context context){
        this.context = context;
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.progress_layout)
                .setCancelable(false)
                .create();
    }
    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
