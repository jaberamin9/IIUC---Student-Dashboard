package com.blogspot.softlabsja.iiucstudentapp.Ter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.R;

import java.util.ArrayList;

public class TerAdapter extends RecyclerView.Adapter<TerAdapter.ViewHolder> {

    ArrayList<TerModel> arrayList;
    ArrayList<ChildModel> childArrayList = new ArrayList<>();
    ChaildAdepter chaildAdepter;
    Context mContext;

    public TerAdapter(ArrayList<TerModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.ter_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subName.setText((position + 1) + ". " + arrayList.get(position).getCn());

        if (arrayList.get(position).getS().equals("Completed"))
            holder.StatusTxt.setTextColor(Color.parseColor("#4AC840"));
        else holder.StatusTxt.setTextColor(Color.parseColor("#D81B60"));
        holder.StatusTxt.setText(arrayList.get(position).getS());
        holder.actionTxt.setText(arrayList.get(position).getA());

        holder.ccTxt.setText(arrayList.get(position).getCc());
        holder.chTxt.setText(arrayList.get(position).getCh());
        holder.ssTxt.setText(arrayList.get(position).getSs());

        // if(arrayList.get(position).getA().equals(""))holder.action.setVisibility(View.GONE);
        holder.expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!arrayList.get(position).isActive) {
                    holder.expandIcon.setImageResource(R.drawable.ic_arrow_down);
                    holder.detailsBtn.setVisibility(View.VISIBLE);
                    childArrayList.clear();
                    try {
                        for (int j = 0; j < arrayList.get(position).arrayList.size(); j++) {
                            childArrayList.add(new ChildModel(arrayList.get(position).arrayList.get(j).first, arrayList.get(position).arrayList.get(j).second));
                        }
                    } catch (Exception ignored) {
                    }
                    if (arrayList.get(position).arrayList.size() <= 1) {
                        holder.teacherName.setVisibility(View.VISIBLE);
                        holder.teacherName.setText(arrayList.get(position).getT().replace("Course Survey", ""));
                    } else {
                        holder.teacherName.setVisibility(View.GONE);
                    }
                    chaildAdepter = new ChaildAdepter(childArrayList, mContext, arrayList.get(position).arrayList.size(), holder.teacherName.getText().toString());
                    holder.childRecyclerView.setHasFixedSize(true);
                    holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    holder.childRecyclerView.setAdapter(chaildAdepter);
                    chaildAdepter.notifyDataSetChanged();
                    arrayList.get(position).isActive = true;
                } else {
                    holder.expandIcon.setImageResource((R.drawable.ic_arrow_up));
                    holder.detailsBtn.setVisibility(View.GONE);
                    arrayList.get(position).isActive = false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName, StatusTxt, actionTxt, ccTxt, chTxt, ssTxt, teacherName;
        LinearLayout detailsBtn, action;
        ImageView expandIcon;
        CardView expandBtn;
        RecyclerView childRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expandBtn = itemView.findViewById(R.id.expandBtn);
            expandIcon = itemView.findViewById(R.id.expandIcon);
            subName = itemView.findViewById(R.id.subName);
            detailsBtn = itemView.findViewById(R.id.detailsBtn);
            action = itemView.findViewById(R.id.action);
            StatusTxt = itemView.findViewById(R.id.StatusTxt);
            actionTxt = itemView.findViewById(R.id.actionTxt);
            ccTxt = itemView.findViewById(R.id.ccTxt);
            chTxt = itemView.findViewById(R.id.chTxt);
            ssTxt = itemView.findViewById(R.id.ssTxt);
            teacherName = itemView.findViewById(R.id.teacherName);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }
}
