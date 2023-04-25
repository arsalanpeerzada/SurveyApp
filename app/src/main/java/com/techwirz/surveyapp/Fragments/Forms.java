package com.techwirz.surveyapp.Fragments;

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

public class Forms extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<String> formString = new ArrayList<>();

    public Forms(ArrayList<String> _formString) {
        formString = _formString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forms, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        formViewAdapter formViewAdapter = new formViewAdapter(getContext(), formString);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(formViewAdapter);

        return view;
    }
}