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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.R;
import com.techwirz.surveyapp.config.API;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_edittext)
    EditText email;

    @BindView(R.id.password_edittext)
    EditText password;

    @BindView(R.id.custom_signin_button)
    Button login;

    @BindView(R.id.signUp_Button)
    TextView SignUP;

    String Email, Password;

    boolean Issignin;


    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.white));// set status background white
        ButterKnife.bind(this);


        tinyDB = new TinyDB(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadData()) {
                    getLogin(Email, Password);

                }
            }
        });

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });

    }

    public boolean loadData() {
        Boolean isEmail, isPassword;

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Email cannot be empty");
            isEmail = false;
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
            isPassword = true;
            Password = password.getText().toString();
        }

        if (isEmail && isPassword) {
            return true;
        } else {
            return false;
        }
    }

    public void getLogin(String username, String password) {


        String postUrl = API.LOGIN;


        RequestParams params = new RequestParams();
        params.put("email", username);
        params.put("password", password);


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
                        String username = testV.getString("user");

                        tinyDB.putString("UserName", username);
                        tinyDB.putString("token", token);

                        Toast.makeText(LoginActivity.this, "Authenticated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        LoginActivity.this.finish();

                    } else {

                        Toast.makeText(LoginActivity.this, testV.getString("message"), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, error2, Toast.LENGTH_SHORT).show();

                    //  Utils.ProgressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Utils.ProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}