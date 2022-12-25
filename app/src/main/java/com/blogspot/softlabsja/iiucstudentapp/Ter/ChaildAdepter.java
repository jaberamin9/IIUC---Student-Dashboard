package com.blogspot.softlabsja.iiucstudentapp.Ter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Ter.CourseSurvey.CourseSurveyActivity;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission.TerSubmissionActivity;

import java.util.ArrayList;

public class ChaildAdepter extends RecyclerView.Adapter<ChaildAdepter.ViewHolder> {
    ArrayList<ChildModel> arrayList;
    Context mContext;
    int size;
    String name;

    public ChaildAdepter(ArrayList<ChildModel> arrayList, Context context, int size, String name) {
        this.arrayList = arrayList;
        this.mContext = context;
        this.size = size;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.chaild_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setText(arrayList.get(position).getName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (arrayList.get(position).getName().equals("Course Survey")) {
                        Intent intent = new Intent(mContext, CourseSurveyActivity.class);
                        if (size <= 1) intent.putExtra("teacherName", name);
                        intent.putExtra("url", arrayList.get(position).getUrl());
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, TerSubmissionActivity.class);
                        intent.putExtra("teacherName", arrayList.get(position).getName());
                        intent.putExtra("url", arrayList.get(position).getUrl());
                        mContext.startActivity(intent);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
