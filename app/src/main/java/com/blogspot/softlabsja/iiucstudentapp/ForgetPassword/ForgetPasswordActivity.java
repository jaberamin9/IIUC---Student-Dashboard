package com.blogspot.softlabsja.iiucstudentapp.ForgetPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.blogspot.softlabsja.iiucstudentapp.MainActivity.userAgent;

public class ForgetPasswordActivity extends AppCompatActivity {
    ImageView FP_backBtn;
    EditText FP_student_id, FP_mobile_number, FP_date_of_birth, FP_verificationEt, FP_new_password, FP_re_type_password;
    Button submit, FP_verify, FP_resetPasswordBtn;
    ContentLoadingProgressBar loading, loading2, loading3;
    LinearLayout forget_password_box, FP_verify_box, new_password_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorb2));
        }

        FP_backBtn = findViewById(R.id.FP_backBtn);
        FP_student_id = findViewById(R.id.FP_student_id);
        FP_mobile_number = findViewById(R.id.FP_mobile_number);
        FP_date_of_birth = findViewById(R.id.FP_date_of_birth);
        submit = findViewById(R.id.FP_submitBtn);
        loading = findViewById(R.id.FP_loading);
        FP_verificationEt = findViewById(R.id.FP_verificationEt);
        FP_verify = findViewById(R.id.FP_verify);
        loading2 = findViewById(R.id.FP_loading2);
        forget_password_box = findViewById(R.id.forget_password_box);
        FP_verify_box = findViewById(R.id.FP_verify_box);

        FP_new_password = findViewById(R.id.FP_new_password);
        FP_re_type_password = findViewById(R.id.FP_re_type_password);
        FP_resetPasswordBtn = findViewById(R.id.FP_resetPasswordBtn);
        new_password_box = findViewById(R.id.new_password_box);
        loading3 = findViewById(R.id.FP_loading3);

        FP_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forget_password_box.setVisibility(View.VISIBLE);
        FP_verify_box.setVisibility(View.GONE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FP_student_id.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter ID No", Toast.LENGTH_SHORT).show();
                } else if (FP_mobile_number.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (FP_date_of_birth.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter Date of Birth", Toast.LENGTH_SHORT).show();
                } else {
                    loading.show();
                    ForgetPassword();
                }
            }
        });
    }

    void ForgetPassword() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection.Response loginForm = Jsoup.connect("https://www.iiuc.ac.bd/login/forget-password")
                            .method(Connection.Method.GET)
                            .userAgent(userAgent)
                            .referrer("http://www.google.com")
                            .execute();
                    Document responseDocument = loginForm.parse();
                    Element eventValidation = responseDocument.select("input[name=csrf_iiuc_web]").first();

                    if (eventValidation != null) {
                        Connection.Response d = Jsoup.connect("https://www.iiuc.ac.bd/login/forget-password")
                                .data("csrf_iiuc_web", eventValidation.attr("value"))
                                .data("user_id", FP_student_id.getText().toString().trim())
                                .data("mobile_number", FP_mobile_number.getText().toString().trim())
                                .data("date_of_birth", FP_date_of_birth.getText().toString().trim())
                                .data("submit", "Submit")
                                .method(Connection.Method.POST)
                                .cookies(loginForm.cookies())
                                .followRedirects(true)
                                .execute();

                        String text = d.parse().html();
                        if (text.contains("id=\"verification_code\"")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loading.hide();
                                    forget_password_box.setVisibility(View.GONE);
                                    FP_verify_box.setVisibility(View.VISIBLE);

                                    FP_verify.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (FP_verificationEt.getText().toString().trim().equalsIgnoreCase("")) {
                                                Toast.makeText(getApplicationContext(), "Enter The Verification Code", Toast.LENGTH_SHORT).show();
                                            } else {
                                                loading2.show();
                                                Thread thread = new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            Connection.Response verification = Jsoup.connect("https://www.iiuc.ac.bd/index.php/login/forget-verify")
                                                                    .data("csrf_iiuc_web", eventValidation.attr("value"))
                                                                    .data("verification_code", FP_verificationEt.getText().toString().trim())
                                                                    .data("submit", "Verify")
                                                                    .method(Connection.Method.POST)
                                                                    .cookies(loginForm.cookies())
                                                                    .followRedirects(true)
                                                                    .execute();

                                                            String text = verification.parse().html();
                                                            if (text.contains("id=\"new_password\"")) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        loading2.hide();
                                                                        FP_verify_box.setVisibility(View.GONE);
                                                                        new_password_box.setVisibility(View.VISIBLE);

                                                                        FP_resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {

                                                                                if (FP_new_password.getText().toString().trim().equalsIgnoreCase("")) {
                                                                                    Toast.makeText(getApplicationContext(), "Enter New Password", Toast.LENGTH_SHORT).show();
                                                                                } else if (FP_new_password.getText().toString().trim().length() < 8) {
                                                                                    Toast.makeText(getApplicationContext(), "Password length must be 8 Character", Toast.LENGTH_SHORT).show();
                                                                                } else if (FP_new_password.getText().toString().trim().matches("[0-9]+")) {
                                                                                    Toast.makeText(getApplicationContext(), "Only numeric password not allow", Toast.LENGTH_SHORT).show();
                                                                                } else if (FP_re_type_password.getText().toString().trim().equalsIgnoreCase("")) {
                                                                                    Toast.makeText(getApplicationContext(), "Retype New Password", Toast.LENGTH_SHORT).show();
                                                                                } else if (!FP_re_type_password.getText().toString().trim().equals(FP_new_password.getText().toString().trim())) {
                                                                                    Toast.makeText(getApplicationContext(), "Password field does not match", Toast.LENGTH_SHORT).show();
                                                                                } else {
                                                                                    loading3.show();
                                                                                    Thread thread = new Thread(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            try {
                                                                                                Connection.Response newPassword = Jsoup.connect("https://www.iiuc.ac.bd/login/reset-password")
                                                                                                        .data("csrf_iiuc_web", eventValidation.attr("value"))
                                                                                                        .data("new_password", FP_new_password.getText().toString().trim())
                                                                                                        .data("re_password", FP_re_type_password.getText().toString().trim())
                                                                                                        .data("submit", "Reset Password")
                                                                                                        .method(Connection.Method.POST)
                                                                                                        .cookies(loginForm.cookies())
                                                                                                        .followRedirects(true)
                                                                                                        .execute();

                                                                                                String text = newPassword.parse().html();
                                                                                                if (text.contains("Password changed successful")) {
                                                                                                    runOnUiThread(new Runnable() {
                                                                                                        @Override
                                                                                                        public void run() {
                                                                                                            loading3.hide();
                                                                                                            Toast.makeText(ForgetPasswordActivity.this, "Password changed successful:)\nPlease login now", Toast.LENGTH_SHORT).show();
                                                                                                            finish();
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } catch (IOException e) {
                                                                                                e.printStackTrace();
                                                                                                runOnUiThread(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {
                                                                                                        loading3.hide();
                                                                                                        Toast.makeText(ForgetPasswordActivity.this, "connection error server", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                    thread.start();
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                });


                                                            } else if (text.contains("Invalid Verification Code")) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        loading2.hide();
                                                                        Toast.makeText(ForgetPasswordActivity.this, "Invalid Verification Code! Try again", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }

                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    loading2.hide();
                                                                    Toast.makeText(ForgetPasswordActivity.this, "connection error server", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                                thread.start();
                                            }
                                        }
                                    });
                                }
                            });
                        } else if (text.contains("Too many attempt. You are blocked today.")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loading.hide();
                                    Toast.makeText(ForgetPasswordActivity.this, "Too many attempt. You are blocked today.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (text.contains("Given Information not match")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loading.hide();
                                    Toast.makeText(ForgetPasswordActivity.this, "Given Information not match", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();
                                Toast.makeText(ForgetPasswordActivity.this, "Sorry you are using VPN or change your connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.hide();
                            Toast.makeText(ForgetPasswordActivity.this, "connection error server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();

    }
}