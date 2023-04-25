package com.techwirz.surveyapp.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techwirz.surveyapp.Activities.FormActivities.FormBuilder;
import com.techwirz.surveyapp.R;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class formViewAdapter extends RecyclerView.Adapter<formViewAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<String> arrayList;

    public formViewAdapter(Context context, ArrayList<String> _arralist) {
        this.context = context;
        this.arrayList = _arralist;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.list_formviewlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (arrayList.isEmpty()) {

        } else {
            final String[] list = arrayList.get(position).split("%");


            holder.title.setText(list[1]);
            holder.jsonString.setText(list[0]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, FormBuilder.class).putExtra("form", list[0]));
                }
            });

            holder.Responses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Resposes will be downloaded from here", Toast.LENGTH_SHORT).show();
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = list[1].replaceAll(" ", "");
                    String URL = "https://surveyapp.pakistanisp.org/form/" + data;
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Text", URL);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(context, "Paste URL for share the form", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView jsonString;
        ImageView share;
        ImageView Responses;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = view.findViewById(R.id.title);
            jsonString = view.findViewById(R.id.jsonstring);
            share = view.findViewById(R.id.share);
            Responses = view.findViewById(R.id.Responses);

        }
    }


}
