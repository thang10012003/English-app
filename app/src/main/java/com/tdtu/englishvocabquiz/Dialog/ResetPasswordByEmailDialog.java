package com.tdtu.englishvocabquiz.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tdtu.englishvocabquiz.R;

public class ResetPasswordByEmailDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_reset_password_by_email, null);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setTitle("Xác thực để lấy lại mật khẩu")
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something when the negative button is clicked
                        // For example, dismiss the dialog
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it

        Button btnSendEmail = view.findViewById(R.id.btnSendToEmail);
        EditText emailToGet = view.findViewById(R.id.edtEmailToReset);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailToGet.getText().toString().trim();
                if(!email.isEmpty()){
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Gửi mã xác thực thành công !", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Không tồn tại email !", Toast.LENGTH_SHORT).show();
                                    getDialog().dismiss();
                                }
                            });

                }
            }
        });

        return builder.create();
    }
}
