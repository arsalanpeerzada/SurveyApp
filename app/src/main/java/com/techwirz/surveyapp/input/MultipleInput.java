package com.techwirz.surveyapp.input;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.techwirz.surveyapp.Interfaces.Interfaces;
import com.techwirz.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MultipleInput extends AppCompatDialogFragment {

    private TextView dialogTitle;
    private Switch requiredSwitch;
    private EditText editTextTextPersonName;
    String Title = null;
    Interfaces multipleInputListener;
    private static int RESULT_LOAD_IMAGE = 1;
    View view;
    Dialog dialog;
    Button cancel, submit;
    ImageView imageView;
    LinearLayout options;
    EditText multipleoptions;
    boolean picture = false;
    Button addoptions;
    int editTextBoxId = 0;
    List<String> arraylist;


    public MultipleInput(String _Title) {
        this.Title = _Title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        EditText editTitle;
        Boolean required;
        ImageView picturegetter;

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_multipleinput);


        dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        requiredSwitch = (Switch) dialog.findViewById(R.id.requiredSwitch);
        editTextTextPersonName = (EditText) dialog.findViewById(R.id.editTextTextPersonName);
        picturegetter = dialog.findViewById(R.id.picturegetter);
        cancel = dialog.findViewById(R.id.cancel);
        submit = dialog.findViewById(R.id.submit);
        options = dialog.findViewById(R.id.options);
        addoptions = dialog.findViewById(R.id.addoptions);


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
                if (TextUtils.isEmpty(title)) {
                    editTextTextPersonName.setError("This should not be empty");
                } else {
                    Boolean required = requiredSwitch.isChecked();
                    arraylist = new ArrayList<>();


                    for (int i = 0; i < editTextBoxId; i++) {
                        View view = options.getChildAt(i);

                        EditText s = (EditText) view;
                        arraylist.add(s.getText().toString());

                    }

                    if (picture) {
                        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        multipleInputListener.MultipleApplyTexts(title, required, image, arraylist);
                    } else {
                        Drawable image = getResources().getDrawable(R.drawable.picture);
                        Bitmap imagea = ((BitmapDrawable) image).getBitmap();
                        multipleInputListener.MultipleApplyTexts(title, required, imagea, arraylist);
                    }

                    dialog.dismiss();
                }


            }
        });

        picturegetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        addoptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(editTextTextPersonName.getText().toString())) {

                    EditText editText = (EditText) options.getChildAt(0);
                    multipleoptions = new EditText(getContext());

                    RelativeLayout.LayoutParams editextparam = new RelativeLayout.LayoutParams
                            ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

                    editextparam.setMargins(20, 0, 0, 0);

                    multipleoptions.setLayoutParams(editextparam);
                    multipleoptions.setHint("Option");
                    multipleoptions.setId(editTextBoxId);
                    multipleoptions.requestFocus();
                    editTextBoxId++;

                    Log.d("TAG", multipleoptions.getHint().toString());
                    options.addView(multipleoptions);


                } else {
                    editTextTextPersonName.setError("This cant be empty");
                }
            }
        });


        dialogTitle.setText(Title);

        dialog.show();
        //  return dialog;
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            multipleInputListener = (Interfaces) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must Implement Interface");
        }
    }

//    public interface MultipleInputListener {
//        void MultipleApplyTexts(String Title, Boolean required, Bitmap imageView, List<String> list);
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView = (ImageView) dialog.findViewById(R.id.imagecontainer);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            picture = true;
        }
    }
}