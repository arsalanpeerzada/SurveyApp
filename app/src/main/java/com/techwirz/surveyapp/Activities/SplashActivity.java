package com.techwirz.surveyapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        View decorView = getWindow().getDecorView();
//        Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//        Remember that you should never show the action bar if the
//        status bar is hidden, so hide that too if necessary.

        tinyDB = new TinyDB(this);

        imageView = (ImageView) findViewById(R.id.image);
//        textView = findViewById(R.id.textview);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.transition);
//
        imageView.startAnimation(anim);
//        textView.startAnimation(anim);

        Thread timer = new Thread() {
            public void run() {
                try {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {

                    String firstname = tinyDB.getString("UserName");

                    if (TextUtils.isEmpty(firstname)) {
                        startActivity(new Intent(SplashActivity.this, OpeningActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                }
            }
        };
        timer.start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}