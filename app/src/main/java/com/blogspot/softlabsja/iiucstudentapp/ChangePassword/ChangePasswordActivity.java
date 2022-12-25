package com.blogspot.softlabsja.iiucstudentapp.ChangePassword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationActivity;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.MainActivity;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.ResultView.ResultViewActivity;
import com.blogspot.softlabsja.iiucstudentapp.ResultView.ResultViewAdapter;
import com.blogspot.softlabsja.iiucstudentapp.ResultView.ResulteViewModels;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ChangePasswordActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    EditText currentPassword, newPassword, rePassword;
    Button submitBtn;
    ImageView backBtn;
    ContentLoadingProgressBar loading;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        backBtn = findViewById(R.id.backBtn_cp);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        rePassword = findViewById(R.id.rePassword);
        submitBtn = findViewById(R.id.submitBtn);
        loading = findViewById(R.id.loading);
        if (loading != null) {
            loading.setIndeterminate(true);
            loading.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String pass = prefs.getString("pass", "");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter Your Current Password", Toast.LENGTH_SHORT).show();
                } else if (!currentPassword.getText().toString().trim().equals(pass)) {
                    Toast.makeText(getApplicationContext(), "Current password does not match", Toast.LENGTH_SHORT).show();
                } else if (newPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (newPassword.getText().toString().trim().length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password length must be 8 Character", Toast.LENGTH_SHORT).show();
                } else if (newPassword.getText().toString().trim().matches("[0-9]+")) {
                    Toast.makeText(getApplicationContext(), "Only numeric password not allow", Toast.LENGTH_SHORT).show();
                } else if (rePassword.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Retype New Password", Toast.LENGTH_SHORT).show();
                } else if (!rePassword.getText().toString().trim().equals(newPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Password field does not match", Toast.LENGTH_SHORT).show();
                } else {
                    loading.show();
                    getData();
                }
            }
        });
    }

    private void getData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/index.php/admin/password")
                            .cookies(Cookies.getCookies(ChangePasswordActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"new_password\"")) {
                        Element eventValidation = doc.select("input[name=csrf_iiuc_web]").first();

                        Document d = Jsoup.connect("https://www.iiuc.ac.bd/index.php/admin/password")
                                .data("csrf_iiuc_web", eventValidation.attr("value"))
                                .data("current_password", currentPassword.getText().toString().trim())
                                .data("new_password", newPassword.getText().toString().trim())
                                .data("re_password", rePassword.getText().toString().trim())
                                .data("submit", "Submit")
                                .method(Connection.Method.POST)
                                .cookies(Cookies.getCookies(ChangePasswordActivity.this))
                                .followRedirects(true)
                                .post();
                        String text2 = d.html();
                        if (text2.contains("id=\"new_password\"")) {
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("pass", newPassword.getText().toString());
                            editor.apply();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loading.hide();

                                    dialog = new Dialog(ChangePasswordActivity.this);
                                    dialog.setContentView(R.layout.dialog_successfyull);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.getWindow().setWindowAnimations(R.style.DialogStyle);
                                    dialog.setCancelable(false);

                                    TextView text = dialog.findViewById(R.id.text_s);
                                    Button ok = dialog.findViewById(R.id.ok);

                                    text.setText("Password Change Successfully!\nGo To Dashboard");
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                        } else if (text.contains("user_id")) {
                            UpdateSession updateSession = new UpdateSession();
                            updateSession.Update(ChangePasswordActivity.this);
                            getData();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(ChangePasswordActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loading.hide();
                                    Toast.makeText(ChangePasswordActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(ChangePasswordActivity.this);
                        getData();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(ChangePasswordActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangePasswordActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
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