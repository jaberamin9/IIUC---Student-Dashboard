package com.blogspot.softlabsja.iiucstudentapp.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.ContentLoadingProgressBar;

import com.blogspot.softlabsja.iiucstudentapp.AllNotice.NoticeModel;
import com.blogspot.softlabsja.iiucstudentapp.ChangePassword.ChangePasswordActivity;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class BottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;
    String number, alt_number, email, birthC, nationalID;
    EditText update_number, update_alt_number, update_email, update_birthC, update_nationalID;
    Button submit_btn;
    ContentLoadingProgressBar loadingBtn_Bo;
    Activity activity;
    LinearLayout BirthLL;

    public BottomSheetDialog(String number, String alt_number, String email, String birthC, String nationalID, Activity activity) {
        this.number = number;
        this.alt_number = alt_number;
        this.email = email;
        this.birthC = birthC;
        this.nationalID = nationalID;
        this.activity = activity;
    }

    public BottomSheetDialog(String number, String alt_number, String email, Activity activity) {
        this.number = number;
        this.alt_number = alt_number;
        this.email = email;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        submit_btn = v.findViewById(R.id.submit_btn);
        update_number = v.findViewById(R.id.update_number);
        update_alt_number = v.findViewById(R.id.update_alt_number);
        update_email = v.findViewById(R.id.update_email);
        update_birthC = v.findViewById(R.id.update_birthC);
        update_nationalID = v.findViewById(R.id.update_nationalID);
        loadingBtn_Bo = v.findViewById(R.id.loadingBtn_Bo);
        BirthLL = v.findViewById(R.id.BirthLL);

        if (loadingBtn_Bo != null) {
            loadingBtn_Bo.setIndeterminate(true);
            loadingBtn_Bo.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        update_number.setText(number);
        update_alt_number.setText(alt_number);
        update_email.setText(email);

        if (birthC != null) {
            BirthLL.setVisibility(View.VISIBLE);
            update_birthC.setText(birthC);
        }

        if (nationalID != null) {
            BirthLL.setVisibility(View.VISIBLE);
            update_nationalID.setText(nationalID);
        }

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(update_email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"));
                if (update_number.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Enter Your Number", Toast.LENGTH_SHORT).show();
                } else if (update_number.getText().toString().trim().length() != 11) {
                    Toast.makeText(activity, "Number length must be 11 digits", Toast.LENGTH_SHORT).show();
                } else if (update_alt_number.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Enter Your Alternative Number", Toast.LENGTH_SHORT).show();
                } else if (update_alt_number.getText().toString().trim().length() != 11) {
                    Toast.makeText(activity, "Alternative Number length must be 11 digits", Toast.LENGTH_SHORT).show();
                } else if (update_email.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmailId(update_email.getText().toString().trim())) {
                    Toast.makeText(activity, "Email is not valid!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBtn_Bo.show();
                    setCancelable(false);
                    getData();
                }
                //mListener.onButtonClicked(pname, radioText);
                //dismiss();
            }
        });

        return v;
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }


    private void getData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/admin/profile")
                            .cookies(Cookies.getCookies(activity))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"tab-1\"")) {
                        Element eventValidation = doc.select("input[name=csrf_iiuc_web]").first();

                        Document d = Jsoup.connect("https://www.iiuc.ac.bd/admin/profile")
                                .data("csrf_iiuc_web", eventValidation.attr("value"))
                                .data("mobile_number", update_number.getText().toString().trim())
                                .data("alternative_number", update_alt_number.getText().toString().trim())
                                .data("email", update_email.getText().toString().trim())
                                .data("birth_certificate", update_birthC.getText().toString().trim())
                                .data("national_id", update_nationalID.getText().toString().trim())
                                .data("submit", "Submit")
                                .method(Connection.Method.POST)
                                .cookies(Cookies.getCookies(activity))
                                .followRedirects(true)
                                .timeout(20000)
                                .post();
                        String text2 = d.html();
                        if (text2.contains("Data Inserted Successfully")) {

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingBtn_Bo.hide();
                                    Toast.makeText(activity, "Successfully Update:)", Toast.LENGTH_SHORT).show();
                                    mListener.onButtonClicked("successfully");
                                    dismiss();
                                }
                            });
                        } else if (text.contains("user_id")) {
                            UpdateSession updateSession = new UpdateSession();
                            updateSession.Update(activity);
                            getData();
//                            activity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(activity, "Update Session", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        } else {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, "Please try again!", Toast.LENGTH_SHORT).show();
                                    setCancelable(true);
                                    loadingBtn_Bo.hide();
                                }
                            });
                        }

                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(activity);
                        getData();
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Please try again!", Toast.LENGTH_SHORT).show();
                                setCancelable(true);
                                loadingBtn_Bo.hide();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}

