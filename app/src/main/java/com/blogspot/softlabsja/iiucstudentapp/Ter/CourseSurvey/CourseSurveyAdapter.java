package com.blogspot.softlabsja.iiucstudentapp.Ter.CourseSurvey;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerActivity;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission.TerSubmissionActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CourseSurveyAdapter extends RecyclerView.Adapter<CourseSurveyAdapter.ViewHolder> {

    ArrayList<CourseSurveyModel> arrayList;
    Context mContext;
    AppCompatButton submitBtn;
    Activity activity;
    String eventValidation, url, userId, teacherName;
    ArrayList<Pair<Integer, String>> arrayListData = new ArrayList<>();
    String teacherNameTemp = "";
    String yearTemp = "";
    String s1 = "";
    String s2 = "";
    ProgressBar ProgressBarSubmit;
    Dialog dialog1;

    public CourseSurveyAdapter(ArrayList<CourseSurveyModel> arrayList, Context context, AppCompatButton submitBtn, Activity activity, String eventValidation, String url, String userId, String teacherName) {
        this.arrayList = arrayList;
        this.mContext = context;
        this.submitBtn = submitBtn;
        this.activity = activity;
        this.eventValidation = eventValidation;
        this.url = url;
        this.userId = userId;
        this.teacherName = teacherName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.course_survey_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.header.setVisibility(View.VISIBLE);
        } else {
            holder.header.setVisibility(View.GONE);
        }
        teacherNameTemp = teacherName;
        holder.teacherNameEte.setText(teacherNameTemp);
        holder.yearEte.setText(yearTemp);
        holder.op1Ete.setText(s1);
        holder.op2Ete.setText(s2);

        holder.teacherNameEte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                teacherNameTemp = holder.teacherNameEte.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.yearEte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                yearTemp = holder.yearEte.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.op1Ete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s1 = holder.op1Ete.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.op2Ete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s2 = holder.op2Ete.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.nameTxt.setText((position + 1) + ". " + arrayList.get(position).name);
        holder.expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!arrayList.get(position).isActive) {
                    holder.expandIcon.setImageResource(R.drawable.ic_arrow_down);
                    holder.detailsBtn.setVisibility(View.VISIBLE);
                    arrayList.get(position).isActive = true;
                } else {
                    holder.expandIcon.setImageResource((R.drawable.ic_arrow_up));
                    holder.detailsBtn.setVisibility(View.GONE);
                    arrayList.get(position).isActive = false;
                }
            }
        });

        for (int i = 0; i < arrayList.get(position).stringArrayList.size(); i++) {
            if (!arrayList.get(position).stringArrayList.get(i).equals("-1")) {
                if (i == 0) {
                    holder.a1.setChecked(true);
                    holder.a2.setChecked(false);
                    holder.a3.setChecked(false);
                    holder.a4.setChecked(false);
                    holder.a5.setChecked(false);
                } else if (i == 1) {
                    holder.a2.setChecked(true);
                    holder.a1.setChecked(false);
                    holder.a3.setChecked(false);
                    holder.a4.setChecked(false);
                    holder.a5.setChecked(false);
                } else if (i == 2) {
                    holder.a3.setChecked(true);
                    holder.a1.setChecked(false);
                    holder.a2.setChecked(false);
                    holder.a4.setChecked(false);
                    holder.a5.setChecked(false);
                } else if (i == 3) {
                    holder.a4.setChecked(true);
                    holder.a1.setChecked(false);
                    holder.a2.setChecked(false);
                    holder.a3.setChecked(false);
                    holder.a5.setChecked(false);
                } else if (i == 4) {
                    holder.a5.setChecked(true);
                    holder.a1.setChecked(false);
                    holder.a2.setChecked(false);
                    holder.a3.setChecked(false);
                    holder.a4.setChecked(false);
                }
            }
        }

        holder.a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.a2.setChecked(false);
                holder.a3.setChecked(false);
                holder.a4.setChecked(false);
                holder.a5.setChecked(false);
                arrayList.get(position).stringArrayList.set(0, holder.a1.getText().toString());
                getData((position + 1), holder.a1.getText().toString());
            }
        });
        holder.a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.a1.setChecked(false);
                holder.a3.setChecked(false);
                holder.a4.setChecked(false);
                holder.a5.setChecked(false);
                arrayList.get(position).stringArrayList.set(1, holder.a2.getText().toString());
                getData((position + 1), holder.a2.getText().toString());
            }
        });
        holder.a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.a1.setChecked(false);
                holder.a2.setChecked(false);
                holder.a4.setChecked(false);
                holder.a5.setChecked(false);
                arrayList.get(position).stringArrayList.set(2, holder.a3.getText().toString());
                getData((position + 1), holder.a3.getText().toString());
            }
        });
        holder.a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.a1.setChecked(false);
                holder.a2.setChecked(false);
                holder.a3.setChecked(false);
                holder.a5.setChecked(false);
                arrayList.get(position).stringArrayList.set(3, holder.a4.getText().toString());
                getData((position + 1), holder.a4.getText().toString());
            }
        });

        holder.a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.a1.setChecked(false);
                holder.a2.setChecked(false);
                holder.a3.setChecked(false);
                holder.a4.setChecked(false);
                arrayList.get(position).stringArrayList.set(4, holder.a5.getText().toString());
                getData((position + 1), holder.a5.getText().toString());
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1 = new Dialog(mContext);
                dialog1.setContentView(R.layout.dialog_2);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.getWindow().setWindowAnimations(R.style.DialogStyle);
                dialog1.setCancelable(false);

                TextView text = dialog1.findViewById(R.id.text_s);
                Button ok = dialog1.findViewById(R.id.ok);
                Button cancelBtn = dialog1.findViewById(R.id.cancelBtn);
                ProgressBarSubmit = dialog1.findViewById(R.id.ProgressBarSubmit);

                String s = "";
                s += "Teacher Name: " + teacherNameTemp + "\nYear of Study: " + yearTemp + "\nOP1: " + s1 + "\nOP2: " + s2 + "\n";
                for (int i = 0; i < arrayListData.size(); i++) {
                    s += "Number " + arrayListData.get(i).first + " question is " + arrayListData.get(i).second + "\n";
                }
                text.setText(s);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arrayListData.size() == 10) {
                            ProgressBarSubmit.setVisibility(View.VISIBLE);
                            submitData();
                        } else {
                            Toast.makeText(mContext, "Please answer all questions.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        RadioButton a5, a4, a3, a2, a1;
        ImageView expandIcon;
        CardView expandBtn;
        LinearLayout detailsBtn, header;
        EditText teacherNameEte, yearEte, op1Ete, op2Ete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherNameEte = itemView.findViewById(R.id.teacherNameEte);
            yearEte = itemView.findViewById(R.id.yearEte);
            op1Ete = itemView.findViewById(R.id.op1Ete);
            op2Ete = itemView.findViewById(R.id.op2Ete);
            header = itemView.findViewById(R.id.header);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            a5 = itemView.findViewById(R.id.a5);
            a4 = itemView.findViewById(R.id.a4);
            a3 = itemView.findViewById(R.id.a3);
            a2 = itemView.findViewById(R.id.a2);
            a1 = itemView.findViewById(R.id.a1);
            expandBtn = itemView.findViewById(R.id.expandBtn);
            expandIcon = itemView.findViewById(R.id.expandIcon);
            detailsBtn = itemView.findViewById(R.id.detailsBtn);
        }
    }

    void getData(Integer name, String value) {
        int f = 0, pos = 0;
        for (int i = 0; i < arrayListData.size(); i++) {
            if (arrayListData.get(i).first.equals(name)) {
                f = 1;
                pos = i;
            }
        }
        if (f == 1) arrayListData.set(pos, new Pair<>(name, value));
        else arrayListData.add(new Pair<>(name, value));

        Collections.sort(arrayListData, new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(final Pair<Integer, String> o1, final Pair<Integer, String> o2) {
                if (o1.first > o2.first) {
                    return 1;
                } else if (o1.first.equals(o2.first)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        for (int i = 0; i < arrayListData.size(); i++) {
            System.out.println(arrayListData.get(i).first + " " + arrayListData.get(i).second);
        }
    }

    void submitData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Document d = Jsoup.connect(url)
                            .data("csrf_iiuc_web", eventValidation)
                            .data("matric_no", userId)
                            .data("teacher_name", "")
                            .data("study_year", "")

                            .data("tp_name[]", arrayListData.get(0).first + "")
                            .data("tp_" + arrayListData.get(0).first, arrayListData.get(0).second)
                            .data("tp_name[]", arrayListData.get(1).first + "")
                            .data("tp_" + arrayListData.get(1).first, arrayListData.get(1).second)
                            .data("tp_name[]", arrayListData.get(2).first + "")
                            .data("tp_" + arrayListData.get(2).first, arrayListData.get(2).second)
                            .data("tp_name[]", arrayListData.get(3).first + "")
                            .data("tp_" + arrayListData.get(3).first, arrayListData.get(3).second)
                            .data("tp_name[]", arrayListData.get(4).first + "")
                            .data("tp_" + arrayListData.get(4).first, arrayListData.get(4).second)
                            .data("tp_name[]", arrayListData.get(5).first + "")
                            .data("tp_" + arrayListData.get(5).first, arrayListData.get(5).second)
                            .data("tp_name[]", arrayListData.get(6).first + "")
                            .data("tp_" + arrayListData.get(6).first, arrayListData.get(6).second)
                            .data("tp_name[]", arrayListData.get(7).first + "")
                            .data("tp_" + arrayListData.get(7).first, arrayListData.get(7).second)
                            .data("tp_name[]", arrayListData.get(8).first + "")
                            .data("tp_" + arrayListData.get(8).first, arrayListData.get(8).second)
                            .data("tp_name[]", arrayListData.get(9).first + "")
                            .data("tp_" + arrayListData.get(9).first, arrayListData.get(9).second)

                            .data("best_feature", "")
                            .data("improve_course", "")

                            .data("submit", "Submit")
                            .timeout(20000)
                            .method(Connection.Method.POST)
                            .cookies(Cookies.getCookies(mContext))
                            .followRedirects(true)
                            .post();
                    String s1 = "Data Insert Failed. Payment not cleared or already registered. Error No. 1";
                    String s2 = "Data Insert Failed. Unauthorized Access. Error No. 4";
                    String s3 = "Data Insert Failed. Total credit is greater than maximum credit or CGPA less than 2.5. Error No. 2";
                    String s4 = "Data Insert Failed";
                    String text2 = d.html();

                    if (!text2.contains(s1) && !text2.contains(s3) && !text2.contains(s2) && !text2.contains(s4)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarSubmit.setVisibility(View.GONE);
                                dialog1.dismiss();
                                Dialog dialog = new Dialog(mContext);
                                dialog.setContentView(R.layout.dialog_successfyull);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().setWindowAnimations(R.style.DialogStyle);
                                dialog.setCancelable(false);

                                TextView text = dialog.findViewById(R.id.text_s);
                                Button ok = dialog.findViewById(R.id.ok);

                                text.setText("Data Inserted Successfull.");
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(mContext, TerActivity.class);
                                        mContext.startActivity(intent);
                                        ((Activity) mContext).finish();
                                    }
                                });
                                dialog.show();
                            }
                        });
                    } else if (text2.contains(s3)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarSubmit.setVisibility(View.GONE);
                                Toast.makeText(mContext, s3, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains(s1)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarSubmit.setVisibility(View.GONE);
                                Toast.makeText(mContext, s1, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains(s2)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarSubmit.setVisibility(View.GONE);
                                Toast.makeText(mContext, s2, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains(s4)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarSubmit.setVisibility(View.GONE);
                                Toast.makeText(mContext, s4, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(mContext);
                        submitData();
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarSubmit.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBarSubmit.setVisibility(View.GONE);
                            Toast.makeText(mContext, e + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }
}
