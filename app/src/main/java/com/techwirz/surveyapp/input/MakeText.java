package com.techwirz.surveyapp.input;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import com.techwirz.surveyapp.Interfaces.Interfaces;
import com.techwirz.surveyapp.R;

import java.util.List;

public class MakeText {

    LinearLayout baselayout;
    CardView cardView;
    RelativeLayout relativeLayout;
    int type = 0;
    TextView tv, required;
    AppCompatRatingBar ratingBar;
    EditText editText;
    ImageView trash, picture;
    int CardId = 0;
    RelativeLayout.LayoutParams tvparam;
    RelativeLayout.LayoutParams editextparam;
    RelativeLayout.LayoutParams cardviewParam;
    RelativeLayout.LayoutParams requiredParam;
    RelativeLayout.LayoutParams trashparam;
    RelativeLayout.LayoutParams pictureparam;
    RelativeLayout.LayoutParams ratingparam;
    Context context;
    FragmentActivity activity;
    boolean choice = false;
    Interfaces interfaces;
    String text = null;
    int myactivity = 0;


    public MakeText(Context _context, LinearLayout _baseLayout, boolean _choice, int _myactivity) {
        this.context = _context;
        baselayout = _baseLayout;
        this.choice = _choice;
        myactivity = _myactivity;
        if (myactivity == 1) {
            interfaces = (Interfaces) context;
        }
    }

    public void singlemakeText(String _text, Boolean _required, Bitmap bitmap, int _type) {

        cardView = new CardView(context);
        relativeLayout = new RelativeLayout(context);
        this.type = _type;
        text = _text;

        tv = new TextView(context);
        editText = new EditText(context);
        required = new TextView(context);
        trash = new ImageView(context);
        picture = new ImageView(context);
        ratingBar = new AppCompatRatingBar(context);


        tvparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        editextparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        cardviewParam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        requiredParam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        trashparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        pictureparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        ratingparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);


        cardviewParam.setMargins(20, 20, 20, 20);
        requiredParam.setMargins(10, 20, 20, 0);
        trashparam.setMargins(0, 5, 40, 10);
        pictureparam.setMargins(20, 5, 20, 20);
        ratingparam.setMargins(20, 20, 5, 20);


        cardView.setRadius(20);
        cardView.setCardElevation(10);


        required.setText("Required*");
        required.setTextSize(10);
        required.setTextColor(Color.RED);
        requiredParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        trashparam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        trashparam.width = 50;
        trashparam.height = 50;
        trashparam.addRule(RelativeLayout.ALIGN_RIGHT, 3);

        picture.getAdjustViewBounds();
        picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        pictureparam.height = 10;


        tvparam.leftMargin = 20;
        tvparam.topMargin = 10;
        editextparam.leftMargin = 20;
        editextparam.bottomMargin = 20;


        tv.setTextSize((float) 20);
        tv.setPadding(20, 5, 20, 0);


        tv.setId(View.generateViewId());
        editText.setId(View.generateViewId());
        trash.setId(View.generateViewId());
        picture.setId(View.generateViewId());
        ratingBar.setId(View.generateViewId());


        editextparam.addRule(RelativeLayout.BELOW, tv.getId());

        pictureparam.addRule(RelativeLayout.BELOW, trash.getId());
        ratingparam.addRule(RelativeLayout.BELOW, tv.getId());


        if (_required) {
            required.setVisibility(View.VISIBLE);
        } else {
            required.setVisibility(View.GONE);
        }

        if (type == 2) {
            editText.setMaxLines(4);
            editText.setHint("Long Answer");
        } else if
        (type == 1) {
            editText.setSingleLine();
            editText.setHint("Short Answer");
            Drawable d = new BitmapDrawable(context.getResources(), bitmap);
            picture.setImageDrawable(d);
        } else if (type == 3) {
            editText.setSingleLine();
            editText.setHint("Email");
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (type == 4) {
            editText.setSingleLine();
            editText.setHint("Number");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (type == 5)

            ratingBar.setLayoutParams(ratingparam);

        tv.setText(text);
        if (choice) {
            trash.setImageResource(R.drawable.trash);
            trash.setLayoutParams(trashparam);
        }
        picture.setLayoutParams(pictureparam);
        tv.setLayoutParams(tvparam);
        editText.setLayoutParams(editextparam);
        cardView.setLayoutParams(cardviewParam);
        required.setLayoutParams(requiredParam);

        if (relativeLayout.getParent() != null) {
            ((ViewGroup) relativeLayout.getParent()).removeView(relativeLayout);
        }

        if (type == 5) {
            relativeLayout.addView(ratingBar);
            relativeLayout.addView(picture);
            trashparam.addRule(RelativeLayout.BELOW, tv.getId());
        } else {
            relativeLayout.addView(editText);
            trashparam.addRule(RelativeLayout.BELOW, editText.getId());
        }


        relativeLayout.addView(tv);
        relativeLayout.addView(required);
        relativeLayout.addView(trash);

        cardView.addView(relativeLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardView.setId(CardId);
        CardId++;
        baselayout.addView(cardView);


        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View view = (View) v.getParent().getParent();
                interfaces.deleteLister(CardId);
                baselayout.removeView(view);

            }
        });
    }

    public void multiplemaketext(String _text, Boolean _required, Bitmap bitmap, List<String> list, int _type) {

        this.type = _type;
        this.text = _text;
        RadioGroup radioGroup;
        RadioButton radioButton;
        CheckBox checkBox;
        LinearLayout multiple_layout = new LinearLayout(context);
        RelativeLayout.LayoutParams multiple_layoutParams = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        multiple_layout.setOrientation(LinearLayout.VERTICAL);

        cardView = new CardView(context);
        relativeLayout = new RelativeLayout(context);

        tv = new TextView(context);
        editText = new EditText(context);
        required = new TextView(context);
        trash = new ImageView(context);
        picture = new ImageView(context);


        tvparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        editextparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        cardviewParam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        requiredParam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        trashparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

        pictureparam = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);


        cardviewParam.setMargins(20, 20, 20, 20);
        requiredParam.setMargins(10, 20, 20, 0);
        trashparam.setMargins(0, 5, 40, 5);
        pictureparam.setMargins(20, 5, 20, 20);


        cardView.setRadius(20);
        cardView.setCardElevation(10);


        required.setText("Required*");
        required.setTextSize(10);
        required.setTextColor(Color.RED);
        requiredParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        trashparam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        trashparam.width = 50;
        trashparam.height = 50;
        trashparam.addRule(RelativeLayout.ALIGN_RIGHT, 3);

        picture.getAdjustViewBounds();
        picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        pictureparam.height = 10;


        tvparam.leftMargin = 20;
        tvparam.topMargin = 10;
        editextparam.leftMargin = 20;
        editextparam.bottomMargin = 20;
        multiple_layoutParams.leftMargin = 20;
        multiple_layoutParams.bottomMargin = 20;


        tv.setTextSize((float) 20);
        tv.setPadding(20, 5, 20, 0);


        tv.setId(View.generateViewId());
        editText.setId(View.generateViewId());
        trash.setId(View.generateViewId());
        picture.setId(View.generateViewId());

        if (myactivity == 1) {
            editextparam.addRule(RelativeLayout.BELOW, tv.getId());
            trashparam.addRule(RelativeLayout.BELOW, tv.getId());
            pictureparam.addRule(RelativeLayout.BELOW, trash.getId());
            multiple_layoutParams.addRule(RelativeLayout.BELOW, picture.getId());
            trash.setImageResource(R.drawable.trash);
            trash.setLayoutParams(trashparam);
            relativeLayout.addView(trash);

        } else if (myactivity == 2) {
            editextparam.addRule(RelativeLayout.BELOW, tv.getId());
            pictureparam.addRule(RelativeLayout.BELOW, tv.getId());
            multiple_layoutParams.addRule(RelativeLayout.BELOW, picture.getId());
        }


        if (_required) {
            required.setVisibility(View.VISIBLE);
        } else {
            required.setVisibility(View.GONE);
        }

        if (type == 6) {
            editText.setHint("Multiple Choice");
            RadioGroup radioGroup1 = new RadioGroup(context);
            for (int i = 0; i < list.size(); i++) {

                radioButton = new RadioButton(context);
                String rtext = list.get(i).replace("[", "");
                String ltext = rtext.replace("]", "");
                String nText = ltext.trim();
                radioButton.setText(nText);

                radioGroup1.addView(radioButton);

            }
            multiple_layout.addView(radioGroup1);
        } else if (type == 7) {
            editText.setSingleLine();
            editText.setHint("Check Box");
            LinearLayout linearLayout = new LinearLayout(context);
            for (int i = 0; i < list.size(); i++) {
                checkBox = new CheckBox(context);
                String rtext = list.get(i).replace("[", "");
                String ltext = rtext.replace("]", "");
                String nText = ltext.trim();
                checkBox.setText(nText);

                linearLayout.addView(checkBox);

            }
            multiple_layout.addView(linearLayout);
        }

//        if (choice) {
//
//        }

        tv.setText(text);
        picture.setLayoutParams(pictureparam);
        tv.setLayoutParams(tvparam);
        //  editText.setLayoutParams(editextparam);
        cardView.setLayoutParams(cardviewParam);
        required.setLayoutParams(requiredParam);
        multiple_layout.setLayoutParams(multiple_layoutParams);

        relativeLayout.addView(tv);
        relativeLayout.addView(required);
        // relativeLayout.addView(editText);

        relativeLayout.addView(picture);
        relativeLayout.addView(multiple_layout);

        cardView.addView(relativeLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardView.setId(CardId);
        CardId++;
        baselayout.addView(cardView);


        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View view = (View) v.getParent().getParent();
                interfaces.deleteLister(CardId);
                baselayout.removeView(view);

            }
        });

    }
}
