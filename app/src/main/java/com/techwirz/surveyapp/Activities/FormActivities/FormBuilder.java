package com.techwirz.surveyapp.Activities.FormActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.techwirz.surveyapp.Activities.PreviewActivity;
import com.techwirz.surveyapp.Interfaces.Interfaces;
import com.techwirz.surveyapp.ObjectWrapperForBinder;
import com.techwirz.surveyapp.R;
import com.techwirz.surveyapp.input.MakeText;
import com.techwirz.surveyapp.input.MultipleInput;
import com.techwirz.surveyapp.input.TextInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class FormBuilder extends AppCompatActivity implements Interfaces {


    String MYPREFERENCES = "savejson";
    LinearLayout baselayout;
    CardView cardView;
    RelativeLayout relativeLayout;
    int type = 0;
    TextView tv, required;
    EditText editText;
    ImageView trash, picture;
    int CardId = 1;
    RelativeLayout.LayoutParams tvparam;
    RelativeLayout.LayoutParams editextparam;
    RelativeLayout.LayoutParams cardviewParam;
    RelativeLayout.LayoutParams requiredParam;
    RelativeLayout.LayoutParams trashparam;
    RelativeLayout.LayoutParams pictureparam;
    Toolbar toolbar;
    JSONObject myJSON;
    JSONObject jsonDialog, jsonObject2;
    JSONArray jsonArray;
    MakeText makeText;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean choice = true;
    Bitmap bit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_builder);

        baselayout = (LinearLayout) findViewById(R.id.content_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myJSON = new JSONObject();
        jsonArray = new JSONArray();
        jsonObject2 = new JSONObject();
        makeText = new MakeText(FormBuilder.this, baselayout, choice, 1);
        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (TextUtils.isEmpty(getIntent().getStringExtra("form"))) {

        } else {
            String index = getIntent().getStringExtra("form");
            updateForm(index);
        }


        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                switch (menuItem.getTitle().toString()) {
                    case "Short Text":
                        type = 1;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Short");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openDialog("Short Text");
                        break;
                    case "Email":
                        type = 3;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openDialog("Email");
                        break;
                    case "Long Text":
                        type = 2;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Long");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openDialog("Long Text");
                        break;
                    case "Number":
                        type = 4;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Number");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openDialog("Number");
                        break;
                    case "Rating":
                        type = 5;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Rating");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openDialog("Rating");
                        break;
                    case "Choice":
                        type = 6;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Choice");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openMultipleDialog("Choice");
                        break;
                    case "Checkbox":
                        type = 7;
                        try {
                            jsonDialog = new JSONObject();
                            jsonDialog.put("Dialog", "Checkbox");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openMultipleDialog("Checkbox");
                        break;
                }

                return false;
            }
        });

    }


    public void openDialog(String title) {
        TextInput textInput = new TextInput(title);
        textInput.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openMultipleDialog(String title) {
        MultipleInput multipleInput = new MultipleInput(title);
        multipleInput.show(getSupportFragmentManager(), "Multiple Dialog");
    }


    @Override
    public void ApplyTexts(String Title, Boolean required, Bitmap bitmap) {

        try {
            jsonDialog.put("Title", Title);
            jsonDialog.put("Required", required);
            jsonDialog.put("Image", bitmap);
            jsonArray.put(jsonDialog);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeText.singlemakeText(Title, required, bitmap, type);

    }

    @Override
    public void MultipleApplyTexts(String Title, Boolean required, Bitmap imageView, List<String> list) {

        try {
            jsonDialog.put("Title", Title);
            jsonDialog.put("Required", required);
            jsonDialog.put("Image", imageView);
            jsonDialog.put("list", list);
            jsonArray.put(jsonDialog);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeText.multiplemaketext(Title, required, imageView, list, type);

    }

    @Override
    public void deleteLister(int cardId) {
        int id = cardId - 1;
        jsonArray.remove(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_publish, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {

            try {

                myJSON.put("myJSON", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final Object objSent = new Object();
            final Bundle bundle = new Bundle();
            bundle.putBinder("object_value", new ObjectWrapperForBinder(baselayout));
            bundle.putString("JSON", myJSON.toString());
            startActivity(new Intent(FormBuilder.this, PreviewActivity.class).putExtras(bundle));

//                        Intent intent = new Intent(MainActivity.this,PreviewActivity.class);
//                        intent.putExtra("Layout",r1);
//                        startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateForm(String index) {
        try {
            jsonDialog = new JSONObject(index);
            jsonArray = jsonDialog.getJSONArray("myJSON");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject child = jsonArray.getJSONObject(i);
                String dialog = child.getString("Dialog");
                String title = child.getString("Title");
                Boolean required = child.getBoolean("Required");
                String bitmap = child.getString("Image");

                List<String> list = new ArrayList<>();
                if (dialog.contains("Choice") || dialog.contains("Checkbox")) {
                    String liststring = child.getString("list");
                    list = Arrays.asList(liststring.split(","));
                }
                try {
                    byte[] encodeByte = Base64.decode(bitmap, Base64.DEFAULT);

                    bit = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                } catch (Exception e) {
                    e.getMessage();

                }
                if (dialog.contains("Short")) {
                    makeText.singlemakeText(title, required, bit, 1);
                } else if (dialog.contains("Long")) {
                    makeText.singlemakeText(title, required, bit, 2);
                } else if (dialog.contains("Email")) {
                    makeText.singlemakeText(title, required, bit, 3);
                } else if (dialog.contains("Number")) {
                    makeText.singlemakeText(title, required, bit, 4);
                } else if (dialog.contains("Rating")) {
                    makeText.singlemakeText(title, required, bit, 5);
                } else if (dialog.contains("Choice")) {
                    makeText.multiplemaketext(title, required, bit, list, 6);
                } else if (dialog.contains("Checkbox")) {
                    makeText.multiplemaketext(title, required, bit, list, 7);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
}
