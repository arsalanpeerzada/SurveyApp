package com.techwirz.surveyapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.R;
import com.techwirz.surveyapp.config.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.First_Name)
    EditText first_Name;

    @BindView(R.id.Last_Name)
    EditText last_name;

    @BindView(R.id.email_edittext)
    EditText email;

    @BindView(R.id.password_edittext)
    EditText password;

    @BindView(R.id.confirm_Passord)
    EditText confirmPassword;

    @BindView(R.id.custom_signup_button)
    Button SignUp;


    @BindView(R.id.back_Button)
    TextView back;


    String FirstName, LastName, Email, Password;
    int onBack = 0;
    HashMap<String, String> mydata = new HashMap<String, String>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        tinyDB = new TinyDB(this);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadData()) {
                    getLogin(FirstName, LastName, Password, Email);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public boolean loadData() {

        Boolean isFirstName, isLastName, isEmail, isPassword = false;

        if (TextUtils.isEmpty(first_Name.getText())) {
            first_Name.setError("First Name cannot be empty ");
            isFirstName = false;
        } else {
            isFirstName = true;
            FirstName = first_Name.getText().toString();
        }
        if (TextUtils.isEmpty(last_name.getText())) {
            isLastName = false;
            last_name.setError("Last Name cannot be empty ");
        } else {
            isLastName = true;
            LastName = last_name.getText().toString();
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            isEmail = false;
            email.setError("Email cannot be empty ");
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                Email = email.getText().toString();
                isEmail = true;
            } else {
                isEmail = false;
                email.setError("Email is Invalid");
            }

        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Password cannot be empty");
            isPassword = false;
        } else {
        }
        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            isPassword = false;
            confirmPassword.setError("Password cannot be empty");
        } else {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                isPassword = true;
                Password = password.getText().toString();
            } else {
                isPassword = false;
                confirmPassword.setError("Password does not match");
            }
        }

        if (isEmail && isFirstName && isLastName && isPassword) {
            return true;
        } else {
            return false;
        }


    }

    @Override
    public void onBackPressed() {
        onBack++;
        if (onBack >= 2) {
            startActivity(new Intent(SignUp.this, LoginActivity.class));
            this.finish();
        } else {
            Toast.makeText(this, "Press Back one more time to exit Sign Up", Toast.LENGTH_SHORT).show();
        }
    }

    public void getLogin(String firstName, String lastName, String password, String email) {


        String postUrl = API.REGISTER;


        RequestParams params = new RequestParams();
        params.put("fname", firstName);
        params.put("lname", lastName);
        params.put("password", password);
        params.put("email", email);


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
                        String token = testV.getString("access_token");

                        mydata.put("Email", Email);
                        mydata.put("FirstName", FirstName);
                        mydata.put("LastName", LastName);
                        mydata.put("Password", Password);
                        databaseReference.child(FirstName).setValue(mydata);

                        tinyDB.putString("UserName", FirstName);
                        tinyDB.putString("token", token);

                        Toast.makeText(SignUp.this, "New User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, MainActivity.class));
                        SignUp.this.finish();


                    } else if (testV.getString("status").contains("status")) {

                        Toast.makeText(SignUp.this, testV.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Utils.ProgressDialog.dismiss();
                }
            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                JSONObject testV = null;
                try {

                    String body = new String(responseBody);
                    testV = new JSONObject(body);
                    JSONObject data = testV.getJSONObject("data");
                    String error2 = data.getString("message");
                    Toast.makeText(SignUp.this, error2, Toast.LENGTH_SHORT).show();

                    //  Utils.ProgressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Utils.ProgressDialog.dismiss();
                    Toast.makeText(SignUp.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}