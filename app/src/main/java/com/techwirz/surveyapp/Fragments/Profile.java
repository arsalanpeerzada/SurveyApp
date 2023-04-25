package com.techwirz.surveyapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mukesh.tinydb.TinyDB;
import com.techwirz.surveyapp.Activities.ChartActivity;
import com.techwirz.surveyapp.Activities.LoginActivity;
import com.techwirz.surveyapp.R;

public class Profile extends Fragment {

    View view;
    TextView response, signout;
    TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        signout = view.findViewById(R.id.SignOut);

        response = view.findViewById(R.id.Responses);
        tinyDB = new TinyDB(requireContext());

        String firstName = tinyDB.getString("UserName");


        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), ChartActivity.class));
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getContext(), "Something's Wrong, Delete Applications Storage", Toast.LENGTH_SHORT).show();

                } else {

                    tinyDB.clear();
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        });

        return view;
    }


}