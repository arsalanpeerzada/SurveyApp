package com.techwirz.surveyapp.Activities.FormActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techwirz.surveyapp.Fragments.FormchooseFragments.Education;
import com.techwirz.surveyapp.Fragments.FormchooseFragments.Feedbacks;
import com.techwirz.surveyapp.Fragments.FormchooseFragments.Health;
import com.techwirz.surveyapp.Fragments.FormchooseFragments.Registration;
import com.techwirz.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

public class FormChoose extends AppCompatActivity {

    public TabLayout tabLayout;
    private ViewPager viewPager;
    Button button;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceDF;
    DatabaseReference databaseReferenceEDU;
    DatabaseReference databaseReferenceHE;
    DatabaseReference databaseReferenceRE;

    ArrayList<String> listfd = new ArrayList<>();
    ArrayList<String> listEDU = new ArrayList<>();
    ArrayList<String> listHE = new ArrayList<>();
    ArrayList<String> listRE = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_choose);

        button = findViewById(R.id.createButton);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("First"));
        tabLayout.addTab(tabLayout.newTab().setText("Second"));
        tabLayout.addTab(tabLayout.newTab().setText("Third"));
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceDF = firebaseDatabase.getReference("Template").child("Feedback");
        databaseReferenceEDU = firebaseDatabase.getReference("Template").child("Education");
        databaseReferenceHE = firebaseDatabase.getReference("Template").child("Health");
        databaseReferenceRE = firebaseDatabase.getReference("Template").child("Registration");

        databaseReferenceDF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mydata = "";
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
//                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue(Map.class);
//                    Toast.makeText(getContext(), value.toString(), Toast.LENGTH_SHORT).show();
                    String temp = dataSnapshot.getKey();
                    String data = (String) dataSnapshot.getValue();

                    mydata = data + "%" + temp;
                    listfd.add(mydata);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceEDU.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mydata = "";
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
//                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue(Map.class);
//                    Toast.makeText(getContext(), value.toString(), Toast.LENGTH_SHORT).show();
                    String temp = dataSnapshot.getKey();
                    String data = (String) dataSnapshot.getValue();

                    mydata = data + "%" + temp;
                    listEDU.add(mydata);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceHE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mydata = "";
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
//                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue(Map.class);
//                    Toast.makeText(getContext(), value.toString(), Toast.LENGTH_SHORT).show();
                    String temp = dataSnapshot.getKey();
                    String data = (String) dataSnapshot.getValue();

                    mydata = data + "%" + temp;
                    listHE.add(mydata);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceRE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mydata = "";
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
//                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue(Map.class);
//                    Toast.makeText(getContext(), value.toString(), Toast.LENGTH_SHORT).show();
                    String temp = dataSnapshot.getKey();
                    String data = (String) dataSnapshot.getValue();

                    mydata = data + "%" + temp;
                    listRE.add(mydata);

                }
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormChoose.this, FormBuilder.class);
                startActivity(intent);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter((getSupportFragmentManager()));
        adapter.addFragment(new Feedbacks(listfd), "FeedBacks");
        adapter.addFragment(new Education(listEDU), "Education");
        adapter.addFragment(new Health(listHE), "Health");
        adapter.addFragment(new Registration(listRE), "Registration");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}