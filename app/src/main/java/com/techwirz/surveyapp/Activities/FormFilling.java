package com.techwirz.surveyapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.R;
import com.techwirz.surveyapp.config.API;
import com.techwirz.surveyapp.input.MakeText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormFilling extends AppCompatActivity {

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
    Button submit;
    CardView imageview;
    ImageView one, two, three, four;
    private static int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_filling);

        tinyDB = new TinyDB(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baselayout = findViewById(R.id.linearlayout);
        makeText = new MakeText(FormFilling.this, baselayout, false, 2);

        submit = findViewById(R.id.submitButton);
        imageview = findViewById(R.id.imageview);
        one = findViewById(R.id.image1);
        two = findViewById(R.id.image2);
        three = findViewById(R.id.image3);
        four = findViewById(R.id.image4);


        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();


        String dataa = data.toString();
        String[] mylist = dataa.split("/");
        title = mylist[4];

        String index = getJsonString(title);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Toast.makeText(FormFilling.this, "Data Saved", Toast.LENGTH_SHORT).show();


                for (int i = 0; i < baselayout.getChildCount(); i++) {
                    CardView cardlayout = (CardView) baselayout.getChildAt(i);
                    RelativeLayout mylayout = (RelativeLayout) cardlayout.getChildAt(0);

                    for (int j = 0; j < mylayout.getChildCount(); j++) {

                        View view = mylayout.getChildAt(j);


                        //view type
                        if (view.getClass() == RatingBar.class) {
                            Log.d("Tmage", "ImageView");
                        }
                        if (view.getClass() == TextView.class) {
                            String text = ((TextView) view).getText().toString();
                            Log.d("TextView", text);
                        }
                        if (view.getClass() == EditText.class) {

                            String editText = ((EditText) view).getText().toString();
                            Log.d("EditText", editText);
                        }
                        if (view.getClass() == LinearLayout.class) {
                            View view1 = ((LinearLayout) view).getChildAt(0);
                            if (view1.getClass() == RadioGroup.class) {
                                int radioGroup = ((RadioGroup) view1).getCheckedRadioButtonId();
                                RadioButton radioButton = (RadioButton) findViewById(radioGroup);
                                Log.d("radio Button", (String) radioButton.getText());
                            }

                            if (view1.getClass() == LinearLayout.class) {
                                int childCount = ((LinearLayout) view1).getChildCount();
                                for (int z = 0; z < childCount; z++) {
                                    CheckBox mycheck = (CheckBox) ((LinearLayout) view1).getChildAt(z);

                                    if (mycheck.isChecked()) {
                                        String data = mycheck.getText().toString();
                                        Log.d("radio Button", data);
                                    }
                                }
                            }
                        }


                    }
                }


            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 2);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 3);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 4);
            }
        });

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

    public String getJsonString(String _title) {
        String jsonString = "";
        ArrayList<String> mylist = tinyDB.getListString("formString");
        if (mylist.isEmpty()) {
            Toast.makeText(this, "Public Forms are Coming Soon", Toast.LENGTH_SHORT).show();
        } else {
            for (int a = 0; a < mylist.size(); a++) {
                String[] data = mylist.get(a).split("%");
                if (data[1].equals(_title)) {
                    title = data[1];
                    jsonString = data[0];

                }
            }
        }

        return jsonString;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_link, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.link) {
            imageview.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (requestCode == 1)
                one.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            else if (requestCode == 2)
                two.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            else if (requestCode == 3)
                three.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            else if (requestCode == 4)
                four.setImageBitmap(BitmapFactory.decodeFile(picturePath));


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FormFilling.this.finish();
    }

    public void saveForm(String formID, String data) {


        String postUrl = API.SAVE;


        RequestParams params = new RequestParams();
        params.put("form_id", formID);
        params.put("response_csv", data);


        //Server Hit
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(postUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                JSONObject testV = null;

                try {
                    String body = new String(responseBody);
                    testV = new JSONObject(body);

                    if (testV.getString("status").contains("200")) {
                        Toast.makeText(FormFilling.this, "Authenticated", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(FormFilling.this, testV.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Utils.ProgressDialog.dismiss();
                }
            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                JSONObject testV = null;

//                    String body = new String(responseBody);
//                    testV = new JSONObject(body);
//                    JSONObject data = testV.getJSONObject("data");
//                    String error2 = data.getString("message");
                Toast.makeText(FormFilling.this, "Error", Toast.LENGTH_SHORT).show();

                //  Utils.ProgressDialog.dismiss();

            }
        });

    }
}