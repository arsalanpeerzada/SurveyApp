package com.techwirz.surveyapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techwirz.surveyapp.Adapters.formViewAdapter;
import com.techwirz.surveyapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicForm extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<String> formString = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forms, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PublicForm");

        databaseReference.addValueEventListener(new ValueEventListener() {
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
                    formString.add(mydata);

                }

                recyclerView = view.findViewById(R.id.recycler);
                formViewAdapter formViewAdapter = new formViewAdapter(getContext(), formString);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL
                        , false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(formViewAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

