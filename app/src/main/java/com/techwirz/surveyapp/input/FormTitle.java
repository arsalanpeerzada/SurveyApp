package com.techwirz.surveyapp.input;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.techwirz.surveyapp.Interfaces.Title;
import com.techwirz.surveyapp.R;

public class FormTitle extends AppCompatDialogFragment {
    private TextView dialogTitle;
    Dialog dialog;
    Button cancel, submit;
    private EditText editTextTextPersonName;
    Title mytitle;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_title);

        dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        editTextTextPersonName = (EditText) dialog.findViewById(R.id.editTextTextPersonName);
        cancel = dialog.findViewById(R.id.cancel);
        submit = dialog.findViewById(R.id.submit);
        radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogroup);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTextPersonName.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (TextUtils.isEmpty(title)) {
                    editTextTextPersonName.setError("This should not be empty");
                } else {

                    radioButton = (RadioButton) dialog.findViewById(selectedId);
                    if (radioButton.getText().equals("Personal")) {
                        String titles = title.replace(" ", "");
                        mytitle.setTitle(titles,1);
                    } else if (radioButton.getText().equals("Public")) {
                        String titles = title.replace(" ", "");
                        mytitle.setTitle(titles,2);
                    } else {
                        String titles = title.replace(" ", "");
                        mytitle.setTitle(titles,1);
                    }


                    dialog.dismiss();
                }

            }
        });

        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mytitle = (Title) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must Implement Interface");
        }
    }

}
