package com.techwirz.surveyapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.techwirz.surveyapp.R;

public class OpeningActivity extends AppCompatActivity {

    private Button btnEmail;
    private TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        btnEmail = (Button) findViewById(R.id.btnEmail);
        tvLogin = (TextView) findViewById(R.id.tv_login);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpeningActivity.this, SignUp.class));
            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpeningActivity.this, LoginActivity.class));
            }
        });
    }
}