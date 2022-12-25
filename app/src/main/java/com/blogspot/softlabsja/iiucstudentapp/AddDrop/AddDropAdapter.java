package com.blogspot.softlabsja.iiucstudentapp.AddDrop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationActivity;
import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationModel;
import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.KeyValueModel;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

public class AddDropAdapter extends RecyclerView.Adapter<AddDropAdapter.ViewHolder> {

    ArrayList<AddDropModel> addDropModels;
    ArrayList<String> CourseSubOptionModel;
    Context mContext;
    TextView h_1, h_2, h_3, h_4, h_5, h_6;
    Button submitBtn;
    ArrayList<KeyValueModel> arrayList = new ArrayList<>();
    Activity activity;
    ContentLoadingProgressBar cr_loading;
    String student_id, eventValidation, matric__no, semester_id, max_credit;

    int currentItemHeight;
    int highestHeight1 = 0;
    int highestHeight2 = 0;
    int highestHeight3 = 0;
    int highestHeight4 = 0;
    int highestHeight5 = 0;
    List<String> s2 = new ArrayList<>(Collections.singletonList("jaber"));
    String s;
    Dialog dialog;
    String ss3;

    public AddDropAdapter(ArrayList<AddDropModel> addDropModels, ArrayList<String> CourseSubOptionModel, Context context,
                          TextView h_1, TextView h_2, TextView h_3, TextView h_4,
                          TextView h_5, TextView h_6, Button submitBtn,
                          Activity activity, ContentLoadingProgressBar cr_loading,
                          String student_id, String eventValidation, String matric__no, String semester_id, String max_credit) {
        this.addDropModels = addDropModels;
        this.CourseSubOptionModel = CourseSubOptionModel;
        this.mContext = context;
        this.h_1 = h_1;
        this.h_2 = h_2;
        this.h_3 = h_3;
        this.h_4 = h_4;
        this.h_5 = h_5;
        this.h_6 = h_6;
        this.submitBtn = submitBtn;
        this.activity = activity;
        this.cr_loading = cr_loading;
        this.student_id = student_id;
        this.eventValidation = eventValidation;
        this.matric__no = matric__no;
        this.semester_id = semester_id;
        this.max_credit = max_credit;
    }

    @NonNull
    @Override
    public AddDropAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.add_drop_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(contactView);

        for (AddDropModel m : addDropModels) {
            currentItemHeight = getWidthOfLargestDescription(mContext, m.getCheckboxValue().text(), myViewHolder.i_1);
            if (currentItemHeight > highestHeight1) {
                highestHeight1 = currentItemHeight;
            }
        }

        for (AddDropModel m : addDropModels) {
            currentItemHeight = getWidthOfLargestDescription(mContext, m.getCourse_Code(), myViewHolder.i_2);
            if (currentItemHeight > highestHeight2) {
                highestHeight2 = currentItemHeight;
            }
        }

        for (AddDropModel m : addDropModels) {
            currentItemHeight = getWidthOfLargestDescription(mContext, m.getCourse_Name(), myViewHolder.i_3);
            if (currentItemHeight > highestHeight3) {
                highestHeight3 = currentItemHeight;
            }
        }

        for (AddDropModel m : addDropModels) {
            currentItemHeight = getWidthOfLargestDescription(mContext, m.getCredit_Hours().text(), myViewHolder.i_4);
            if (currentItemHeight > highestHeight4) {
                highestHeight4 = currentItemHeight;
            }
        }

        for (AddDropModel m : addDropModels) {
            currentItemHeight = getWidthOfLargestDescription(mContext, m.getSection(), myViewHolder.i_5);
            if (currentItemHeight > highestHeight5) {
                highestHeight5 = currentItemHeight;
            }
        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddDropAdapter.ViewHolder holder, int position) {

        holder.i_1.setWidth(highestHeight1);
        holder.i_2.setWidth(highestHeight2);
        holder.i_3.setWidth(highestHeight3);
        holder.i_4.setWidth(highestHeight4);
        holder.i_5.setWidth(highestHeight5);

        h_1.setWidth(highestHeight1);
        h_2.setWidth(highestHeight2);
        h_3.setWidth(highestHeight3);
        h_4.setWidth(highestHeight4);
        h_5.setWidth(highestHeight5);
        holder.llw.measure(0, 0);
        h_6.setWidth(holder.llw.getMeasuredWidth() + 16);

        h_1.setText("SL");
        h_2.setText("Course Code");
        h_3.setText("Course Name");
        h_4.setText("C.H");
        h_5.setText("Section");
        h_6.setText("Sub Section");


        if (!addDropModels.get(position).getCourseState().equals("0")) {
            s = addDropModels.get(position).getCourseState();
//            int f = 0;
//            for (int i=0;i<s2.size();i++) {
//                if (s.equals(s2.get(i))) {
//                    f = 1;
//                    break;
//                }
//            }
            //if(f == 0){
            // s2.add(s);
            holder.Statell.setVisibility(View.VISIBLE);
            holder.StateText.setText(s);
            //}
        }

        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_text, CourseSubOptionModel);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        holder.spinner.setAdapter(langAdapter);

        String sel = addDropModels.get(position).selected.select("option[selected=selected]").text();
        int spinnerPosition = langAdapter.getPosition(sel);
        holder.spinner.setSelection(spinnerPosition);

        int po2 = position + (2 * position);
        int poo2 = position;

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                //System.out.println(holder.spinner.getSelectedItem().toString());
                //System.out.println(item.toString());
                int spinnerPosition = langAdapter.getPosition(holder.spinner.getSelectedItem().toString());
                spinnerPosition += 1;


                String s1 = "course_id[]";
                String checkboxValue = addDropModels.get(poo2).getCheckboxValue().select("input").attr("value");

                String s2 = "credit_hours_" + checkboxValue;
                String creditHoursValue = addDropModels.get(poo2).getCredit_Hours().select("input").attr("value");

                String s3 = "sub_section_" + checkboxValue;
                ss3 = spinnerPosition + "";

                arrayList.set(po2, new KeyValueModel(s1, checkboxValue));
                arrayList.set(po2 + 1, new KeyValueModel(s2, creditHoursValue));
                arrayList.set(po2 + 2, new KeyValueModel(s3, ss3));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Element checkboxValue = addDropModels.get(position).getCheckboxValue();
        Element creditHours = addDropModels.get(position).getCredit_Hours();

        holder.i_1.setText(checkboxValue.text());
        holder.i_2.setText(addDropModels.get(position).getCourse_Code());
        holder.i_3.setText(addDropModels.get(position).getCourse_Name());
        holder.i_4.setText(creditHours.text());
        holder.i_5.setText(addDropModels.get(position).getSection());

        holder.checkBox.setEnabled(false);

        for (int i = 0; i < 200; i++) {
            arrayList.add(new KeyValueModel("0", "0"));
        }

        holder.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDropModels.get(position).setSelected(!addDropModels.get(position).isSelected());
                if (addDropModels.get(position).isSelected()) {
                    holder.tableRow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.sky_blue));
                    holder.checkBox.setEnabled(true);
                    holder.checkBox.setChecked(true);

                    int spinnerPosition = langAdapter.getPosition(holder.spinner.getSelectedItem().toString());
                    spinnerPosition += 1;

                    String s1 = "course_id[]";
                    String checkboxValue = addDropModels.get(position).getCheckboxValue().select("input").attr("value");

                    String s2 = "credit_hours_" + checkboxValue;
                    String creditHoursValue = addDropModels.get(position).getCredit_Hours().select("input").attr("value");

                    String s3 = "sub_section_" + checkboxValue;
                    ss3 = spinnerPosition + "";

                    int po = position + (2 * position);

                    arrayList.set(po, new KeyValueModel(s1, checkboxValue));
                    arrayList.set(po + 1, new KeyValueModel(s2, creditHoursValue));
                    arrayList.set(po + 2, new KeyValueModel(s3, ss3));

                } else {
                    holder.tableRow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundColor));
                    holder.checkBox.setChecked(false);
                    holder.checkBox.setEnabled(false);

                    int po = position + (2 * position);
                    arrayList.set(po, new KeyValueModel("0", "0"));
                    arrayList.set(po + 1, new KeyValueModel("0", "0"));
                    arrayList.set(po + 2, new KeyValueModel("0", "0"));
                }
            }
        });

        String t = addDropModels.get(position).getCheckboxValue().select("input").attr("checked");
        if (t.equals("checked")) {
            holder.tableRow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.sky_blue));
            holder.checkBox.setEnabled(true);
            holder.checkBox.setChecked(true);

            int spinnerPosition2 = langAdapter.getPosition(holder.spinner.getSelectedItem().toString());
            spinnerPosition2 += 1;

            String s1 = "course_id[]";
            String checkboxValue2 = addDropModels.get(position).getCheckboxValue().select("input").attr("value");

            String s2 = "credit_hours_" + checkboxValue2;
            String creditHoursValue = addDropModels.get(position).getCredit_Hours().select("input").attr("value");

            String s3 = "sub_section_" + checkboxValue2;
            ss3 = spinnerPosition2 + "";

            int po = position + (2 * position);

            arrayList.set(po, new KeyValueModel(s1, checkboxValue2));
            arrayList.set(po + 1, new KeyValueModel(s2, creditHoursValue));
            arrayList.set(po + 2, new KeyValueModel(s3, ss3));
        }


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cr_loading.show();
//                for (int i = 2; i < arrayList.size(); i += 3) {
//                    //if (arrayList.get(i).getKey() != "0") {
//                        System.out.println("position " + i);
//                        System.out.println(arrayList.get(i).getKey() + " " + arrayList.get(i).getValue());
//                    //}
//                }
                submit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addDropModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView i_1, i_2, i_3, i_4, i_5, StateText;
        Spinner spinner;
        TableRow tableRow;
        LinearLayout llw, Statell;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            i_1 = itemView.findViewById(R.id.i_1);
            i_2 = itemView.findViewById(R.id.i_2);
            i_3 = itemView.findViewById(R.id.i_3);
            i_4 = itemView.findViewById(R.id.i_4);
            i_5 = itemView.findViewById(R.id.i_5);

            spinner = itemView.findViewById(R.id.spinner2);
            tableRow = itemView.findViewById(R.id.tableRow);
            Statell = itemView.findViewById(R.id.Statell);
            StateText = itemView.findViewById(R.id.StateText);
            llw = itemView.findViewById(R.id.llw);
            checkBox = itemView.findViewById(R.id.c_1);
        }
    }

    public static int getWidthOfLargestDescription(final Context context, final CharSequence text, TextView textView) {
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Point displaySize = new Point();
        wm.getDefaultDisplay().getSize(displaySize);
        final int deviceWidth = displaySize.x;
        //textView.setTypeface(Typeface.DEFAULT);
        textView.setText(text, TextView.BufferType.SPANNABLE);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredWidth();
    }


    private void submit() {
        //have some problems
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Document d = Jsoup.connect("https://www.iiuc.ac.bd/student/add-drop/" + student_id)
                            .data("csrf_iiuc_web", eventValidation)
                            .data("student_id", student_id)
                            .data("matric_no", matric__no)
                            .data("semester_id", semester_id)
                            .data("semester_id", semester_id)
                            .data("max_credit", max_credit)
                            .data(arrayList.get(0).getKey(), arrayList.get(0).getValue())
                            .data(arrayList.get(1).getKey(), arrayList.get(1).getValue())
                            .data(arrayList.get(2).getKey(), arrayList.get(2).getValue())
                            .data(arrayList.get(3).getKey(), arrayList.get(3).getValue())
                            .data(arrayList.get(4).getKey(), arrayList.get(4).getValue())
                            .data(arrayList.get(5).getKey(), arrayList.get(5).getValue())
                            .data(arrayList.get(6).getKey(), arrayList.get(6).getValue())
                            .data(arrayList.get(7).getKey(), arrayList.get(7).getValue())
                            .data(arrayList.get(8).getKey(), arrayList.get(8).getValue())
                            .data(arrayList.get(9).getKey(), arrayList.get(9).getValue())
                            .data(arrayList.get(10).getKey(), arrayList.get(10).getValue())
                            .data(arrayList.get(11).getKey(), arrayList.get(11).getValue())
                            .data(arrayList.get(12).getKey(), arrayList.get(12).getValue())
                            .data(arrayList.get(13).getKey(), arrayList.get(13).getValue())
                            .data(arrayList.get(14).getKey(), arrayList.get(14).getValue())
                            .data(arrayList.get(15).getKey(), arrayList.get(15).getValue())
                            .data(arrayList.get(16).getKey(), arrayList.get(16).getValue())
                            .data(arrayList.get(17).getKey(), arrayList.get(17).getValue())
                            .data(arrayList.get(18).getKey(), arrayList.get(18).getValue())
                            .data(arrayList.get(19).getKey(), arrayList.get(19).getValue())
                            .data(arrayList.get(20).getKey(), arrayList.get(20).getValue())
                            .data(arrayList.get(21).getKey(), arrayList.get(21).getValue())
                            .data(arrayList.get(22).getKey(), arrayList.get(22).getValue())
                            .data(arrayList.get(23).getKey(), arrayList.get(23).getValue())
                            .data(arrayList.get(24).getKey(), arrayList.get(24).getValue())
                            .data(arrayList.get(25).getKey(), arrayList.get(25).getValue())
                            .data(arrayList.get(26).getKey(), arrayList.get(26).getValue())
                            .data(arrayList.get(27).getKey(), arrayList.get(27).getValue())
                            .data(arrayList.get(28).getKey(), arrayList.get(28).getValue())
                            .data(arrayList.get(29).getKey(), arrayList.get(29).getValue())
                            .data(arrayList.get(30).getKey(), arrayList.get(30).getValue())
                            .data(arrayList.get(31).getKey(), arrayList.get(31).getValue())
                            .data(arrayList.get(32).getKey(), arrayList.get(32).getValue())
                            .data(arrayList.get(33).getKey(), arrayList.get(33).getValue())
                            .data(arrayList.get(34).getKey(), arrayList.get(34).getValue())
                            .data(arrayList.get(35).getKey(), arrayList.get(35).getValue())
                            .data(arrayList.get(36).getKey(), arrayList.get(36).getValue())
                            .data(arrayList.get(37).getKey(), arrayList.get(37).getValue())
                            .data(arrayList.get(38).getKey(), arrayList.get(38).getValue())
                            .data(arrayList.get(39).getKey(), arrayList.get(39).getValue())
                            .data(arrayList.get(40).getKey(), arrayList.get(40).getValue())
                            .data(arrayList.get(41).getKey(), arrayList.get(41).getValue())
                            .data(arrayList.get(42).getKey(), arrayList.get(42).getValue())
                            .data(arrayList.get(43).getKey(), arrayList.get(43).getValue())
                            .data(arrayList.get(44).getKey(), arrayList.get(44).getValue())
                            .data(arrayList.get(45).getKey(), arrayList.get(45).getValue())
                            .data(arrayList.get(46).getKey(), arrayList.get(46).getValue())
                            .data(arrayList.get(47).getKey(), arrayList.get(47).getValue())
                            .data(arrayList.get(48).getKey(), arrayList.get(48).getValue())
                            .data(arrayList.get(49).getKey(), arrayList.get(49).getValue())
                            .data(arrayList.get(50).getKey(), arrayList.get(50).getValue())
                            .data(arrayList.get(51).getKey(), arrayList.get(51).getValue())
                            .data(arrayList.get(52).getKey(), arrayList.get(52).getValue())
                            .data(arrayList.get(53).getKey(), arrayList.get(53).getValue())
                            .data(arrayList.get(54).getKey(), arrayList.get(54).getValue())
                            .data(arrayList.get(55).getKey(), arrayList.get(55).getValue())
                            .data(arrayList.get(56).getKey(), arrayList.get(56).getValue())
                            .data(arrayList.get(57).getKey(), arrayList.get(57).getValue())
                            .data(arrayList.get(58).getKey(), arrayList.get(58).getValue())
                            .data(arrayList.get(59).getKey(), arrayList.get(59).getValue())
                            .data(arrayList.get(60).getKey(), arrayList.get(60).getValue())
                            .data("submit", "Submit")
                            .timeout(20000)
                            .method(Connection.Method.POST)
                            .cookies(Cookies.getCookies(mContext))
                            .followRedirects(true)
                            .post();

                    String s0 = "Registration Successfully Completed";
                    String s1 = "Data Insert Failed. Payment not cleared or already registered. Error No. 1";
                    String s2 = "Data Insert Failed. Unauthorized Access. Error No. 4";
                    String s3 = "Data Insert Failed. Total credit is greater than maximum credit or CGPA less than 2.5. Error No. 2";
                    String text2 = d.html();


                    //System.out.println(d.body().select("#tab-1"));

                    if (text2.contains(s0)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cr_loading.hide();

                                dialog = new Dialog(mContext);
                                dialog.setContentView(R.layout.dialog_successfyull);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().setWindowAnimations(R.style.DialogStyle);
                                dialog.setCancelable(false);

                                TextView text = dialog.findViewById(R.id.text_s);
                                Button ok = dialog.findViewById(R.id.ok);

                                text.setText("Registration is successfully completed!");
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(mContext, CourseRegistrationActivity.class);
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
                                cr_loading.hide();
                                Toast.makeText(mContext, s3, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains(s1)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cr_loading.hide();
                                Toast.makeText(mContext, s1, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains(s2)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cr_loading.hide();
                                Toast.makeText(mContext, s2, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (text2.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(mContext);
                        submit();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cr_loading.hide();
                                Toast.makeText(mContext, "Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cr_loading.hide();
                            Toast.makeText(mContext, e + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();

    }
}
