package com.blogspot.softlabsja.iiucstudentapp.AllNotice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> implements Filterable {

    ArrayList<NoticeModel> mnoticeModels;
    ArrayList<NoticeModel> allNoticeModel;
    Context mContext;

    public NoticeAdapter(ArrayList<NoticeModel> noticeModels, Context context) {
        this.mnoticeModels = noticeModels;
        this.mContext = context;
        this.allNoticeModel = noticeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.item_notice, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mnoticeModels.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeViewActivity.class);
                intent.putExtra("url", mnoticeModels.get(position).getUrl());
                intent.putExtra("title", mnoticeModels.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mnoticeModels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                filterResults.count = allNoticeModel.size();
                filterResults.values = allNoticeModel;
            } else {
                List<NoticeModel> resultsModel = new ArrayList<>();
                String searchStr = constraint.toString().toLowerCase();
                for (NoticeModel itemsModel : allNoticeModel) {
                    if (itemsModel.getTitle().toLowerCase().contains(searchStr)) {
                        resultsModel.add(itemsModel);
                    }
                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //mnoticeModels.clear();
            mnoticeModels = (ArrayList<NoticeModel>) results.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_notice);
        }
    }
}
