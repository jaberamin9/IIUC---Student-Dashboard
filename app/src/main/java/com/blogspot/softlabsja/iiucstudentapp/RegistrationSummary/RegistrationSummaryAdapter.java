package com.blogspot.softlabsja.iiucstudentapp.RegistrationSummary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.AllNotice.NoticeViewActivity;
import com.blogspot.softlabsja.iiucstudentapp.R;

import java.util.ArrayList;

public class RegistrationSummaryAdapter extends RecyclerView.Adapter<RegistrationSummaryAdapter.ViewHolder> {

    ArrayList<RegistrationSummaryModel> mRegistrationModels;
    Context mContext;

    public RegistrationSummaryAdapter(ArrayList<RegistrationSummaryModel> mRegistrationModels, Context context) {
        this.mRegistrationModels = mRegistrationModels;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.registration_summary_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.RS_semesterNo.setText("Semester: " + mRegistrationModels.get(position).getRS_semesterNo());

        holder.RS_semester.setText(mRegistrationModels.get(position).getM_RS_semester());
        holder.RS_enrolled.setText(mRegistrationModels.get(position).getM_RS_enrolled());
        holder.RS_total_CH.setText(mRegistrationModels.get(position).getM_RS_total_CH());

        holder.RS_Urc.setText(mRegistrationModels.get(position).getM_RS_Urc());
        holder.RS_Dc.setText(mRegistrationModels.get(position).getM_RS_Dc());
        holder.RS_Cost.setText(mRegistrationModels.get(position).getM_RS_Cost());

        holder.RS_Payment.setText(mRegistrationModels.get(position).getM_RS_Payment());
        holder.RS_Gpa.setText(mRegistrationModels.get(position).getM_RS_Gpa());
        holder.RS_Cgpa.setText(mRegistrationModels.get(position).getM_RS_Cgpa());


        holder.RS_PrintRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeViewActivity.class);
                intent.putExtra("check", "RegistrationS");
                intent.putExtra("RS_url", mRegistrationModels.get(position).getLink().select("a").get(0).attr("href"));
                intent.putExtra("RS_name", "Print Registration");
                mContext.startActivity(intent);
            }
        });

        holder.RS_PaymentAdvisingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeViewActivity.class);
                intent.putExtra("check", "RegistrationS");
                intent.putExtra("RS_url", mRegistrationModels.get(position).getLink().select("a").get(1).attr("href"));
                intent.putExtra("RS_name", "Payment Advising");
                mContext.startActivity(intent);
            }
        });

        holder.RS_MidAdmitCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeViewActivity.class);
                intent.putExtra("check", "RegistrationS");
                intent.putExtra("RS_url", mRegistrationModels.get(position).getLink().select("a").get(2).attr("href"));
                intent.putExtra("RS_name", "Mid Admit Card");
                mContext.startActivity(intent);
            }
        });

        holder.RS_FinalAdmitCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeViewActivity.class);
                intent.putExtra("check", "RegistrationS");
                intent.putExtra("RS_url", mRegistrationModels.get(position).getLink().select("a").get(3).attr("href"));
                intent.putExtra("RS_name", "Final Admit Card");
                mContext.startActivity(intent);
            }
        });

        holder.RS_GradeSheetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeViewActivity.class);
                intent.putExtra("check", "RegistrationS");
                intent.putExtra("RS_url", mRegistrationModels.get(position).getLink().select("a").get(4).attr("href"));
                intent.putExtra("RS_name", "Grade Sheet");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRegistrationModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView RS_semesterNo, RS_semester, RS_enrolled, RS_total_CH, RS_Urc, RS_Dc, RS_Cost, RS_Payment, RS_Gpa, RS_Cgpa;
        Button RS_PrintRegistrationBtn, RS_PaymentAdvisingBtn, RS_MidAdmitCardBtn, RS_FinalAdmitCardBtn, RS_GradeSheetBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RS_semesterNo = itemView.findViewById(R.id.RS_semesterNo);

            RS_semester = itemView.findViewById(R.id.RS_semester);
            RS_enrolled = itemView.findViewById(R.id.RS_enrolled);
            RS_total_CH = itemView.findViewById(R.id.RS_total_CH);

            RS_Urc = itemView.findViewById(R.id.RS_Urc);
            RS_Dc = itemView.findViewById(R.id.RS_Dc);
            RS_Cost = itemView.findViewById(R.id.RS_Cost);

            RS_Payment = itemView.findViewById(R.id.RS_Payment);
            RS_Gpa = itemView.findViewById(R.id.RS_Gpa);
            RS_Cgpa = itemView.findViewById(R.id.RS_Cgpa);

            RS_PrintRegistrationBtn = itemView.findViewById(R.id.RS_PrintRegistrationBtn);
            RS_PaymentAdvisingBtn = itemView.findViewById(R.id.RS_PaymentAdvisingBtn);
            RS_MidAdmitCardBtn = itemView.findViewById(R.id.RS_MidAdmitCardBtn);
            RS_FinalAdmitCardBtn = itemView.findViewById(R.id.RS_FinalAdmitCardBtn);
            RS_GradeSheetBtn = itemView.findViewById(R.id.RS_GradeSheetBtn);
        }
    }
}
