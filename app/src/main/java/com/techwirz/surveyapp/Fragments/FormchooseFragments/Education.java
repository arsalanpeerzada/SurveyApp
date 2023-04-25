package com.techwirz.surveyapp.Fragments.FormchooseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techwirz.surveyapp.Adapters.formViewAdapter;
import com.techwirz.surveyapp.R;

import java.util.ArrayList;

public class Education extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<String> formString = new ArrayList<>();

    formViewAdapter formViewAdapter;
    LinearLayoutManager linearLayoutManager;

    public Education(ArrayList<String> _formString) {
        // Required empty public constructor

        formString = _formString;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feedbacks, container, false);


        recyclerView = view.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(linearLayoutManager);


        formViewAdapter = new formViewAdapter(getContext(), formString);
        recyclerView.setAdapter(formViewAdapter);



        return view;
    }
}