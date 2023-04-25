package com.techwirz.surveyapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.Activities.FormActivities.FormChoose;
import com.techwirz.surveyapp.Fragments.Forms;
import com.techwirz.surveyapp.Fragments.Profile;
import com.techwirz.surveyapp.Fragments.PublicForm;
import com.techwirz.surveyapp.Fragments.Settings;
import com.techwirz.surveyapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SpaceNavigationView spaceNavigationView;
    Toolbar toolbar;
    TinyDB tinyDB;
    ArrayList<String> formString = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        tinyDB = new TinyDB(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("AppName");


        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        // if (tinyDB.getListString("formString").isEmpty()) {

        //     }
        formString = tinyDB.getListString("formString");


        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Forms", R.drawable.forms));
        spaceNavigationView.addSpaceItem(new SpaceItem("Quiz", R.drawable.quiz));
        spaceNavigationView.addSpaceItem(new SpaceItem("Settings", R.drawable.settings));
        spaceNavigationView.addSpaceItem(new SpaceItem("Profile", R.drawable.user));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");

                Intent intent = new Intent(MainActivity.this, FormChoose.class);
                startActivity(intent);

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {

                switch (itemIndex) {
                    case 0:
                        openFragment(new Forms(formString));
                        break;
                    case 1:
                        openFragment(new PublicForm());
                        break;
                    case 2:
                        openFragment(new Settings());
                        break;
                    case 3:
                        openFragment(new Profile());
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        openFragment(new Forms(formString));
                        break;
                    case 1:
                        openFragment(new PublicForm());
                        break;
                    case 2:
                        openFragment(new Settings());
                        break;
                    case 3:
                        openFragment(new Profile());
                        break;
                }
            }
        });

        openFragment(new Forms(formString));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    public void openFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.bottomnav_framelayout, fragment).commit();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_firstactivity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.privacypolicy) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, PrivacyPolicy.class);
//            startActivity(intent);
        } else if (id == R.id.aboutus) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, AboutUs.class);
//            startActivity(intent);
        }
//        else if (id == R.id.search) {
//            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
////            Intent intent = new Intent(MainActivity.this, SearchActvity.class);
////            startActivity(intent);
//        }
        else if (id == R.id.AboutQ) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, AboutQuran.class);
//            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}