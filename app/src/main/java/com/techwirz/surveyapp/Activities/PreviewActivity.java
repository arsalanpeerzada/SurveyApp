package com.techwirz.surveyapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.Interfaces.Title;
import com.techwirz.surveyapp.R;
import com.techwirz.surveyapp.input.FormTitle;
import com.techwirz.surveyapp.input.MakeText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreviewActivity extends AppCompatActivity implements Title {

    LinearLayout baselayout;
    JSONObject myjson;
    JSONArray jsonArray;
    MakeText makeText;
    Bitmap bit;
    TinyDB tinyDB;
    String index;
    Toolbar toolbar;
    ArrayList<String> formString = new ArrayList<>();
    String title = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        tinyDB = new TinyDB(this);
        Bundle b = getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        index = b.getString("JSON");
        baselayout = findViewById(R.id.linearlayout);
        makeText = new MakeText(PreviewActivity.this, baselayout, false, 2);

        firebaseDatabase = FirebaseDatabase.getInstance();


        formString = tinyDB.getListString("formString");

        try {
            myjson = new JSONObject(index);
            jsonArray = myjson.getJSONArray("myJSON");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject child = jsonArray.getJSONObject(i);
                String dialog = child.getString("Dialog");
                String title = child.getString("Title");
                Boolean required = child.getBoolean("Required");
                String bitmap = child.getString("Image");

                List<String> list = new ArrayList<>();
                if (dialog.contains("Choice") || dialog.contains("Checkbox")) {
                    String liststring = child.getString("list");
                    liststring = liststring.replace('[', ' ');
                    liststring = liststring.replace(']', ' ');
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
//        final Object objReceived = ((ObjectWrapperForBinder) getIntent().getExtras().getBinder("object_value")).getData();
//        Log.d("TAG", "received object=" + objReceived);
//
//        LinearLayout linearLayout = (LinearLayout) objReceived;
//        baselayout = findViewById(R.id.linearlayout);
//
//        if(linearLayout.getParent() != null) {
//            ((ViewGroup)linearLayout.getParent()).removeView(linearLayout); // <- fix
//        }
//        setContentView(linearLayout);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_search) {

            FormTitle textInput = new FormTitle();
            textInput.show(getSupportFragmentManager(), "Example Dialog");


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(String _title, int formcheck) {
        title = _title;

        ArrayList<String> mylist = tinyDB.getListString("formString");
        if (mylist.isEmpty()) {
            formString.add(index + "%" + title);
            tinyDB.putListString("formString", formString);
            startActivity(title, index, formcheck);
        } else {
            for (int a = 0; a < mylist.size(); a++) {
                String[] data = mylist.get(a).split("%");
                if (data[1].equals(title)) {
                    formString.add(a, index + "%" + title);
                    startActivity(title, index, formcheck);
                } else {
                    formString.add(index + "%" + title);
                    tinyDB.putListString("formString", formString);
                    startActivity(title, index, formcheck);
                }
            }
        }


    }

    public void startActivity(String _title, String _index, int formcheck) {

        if (formcheck == 1) {
            databaseReference = firebaseDatabase.getReference("Template").child("Registration");
            databaseReference.child(_title).setValue(_index);
        } else if (formcheck == 2) {
            databaseReference = firebaseDatabase.getReference("PublicForm");
            databaseReference.child(_title).setValue(_index);
        }

        startActivity(new Intent(this, MainActivity.class));
    }


}