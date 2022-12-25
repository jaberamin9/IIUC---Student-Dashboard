package com.blogspot.softlabsja.iiucstudentapp.FeeCalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.ChangePassword.ChangePasswordActivity;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.Profile.BottomSheetDialog;
import com.blogspot.softlabsja.iiucstudentapp.Profile.ProfileActivity;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeeCalculatorActivity extends AppCompatActivity {
    ContentLoadingProgressBar loading;
    String semester_id = null, fee_Student_ID;
    EditText fee_DC, fee_URC, fee_Re_DC, fee_Re_URC, fee_Im_DC, fee_Im_URC;
    Button button;
    TextView fee_text, fee_1, fee_2, fee_3, fee_4, fee_5, fee_6, fee_7, fee_8;
    TextView fee_r11, fee_r12, fee_r13, fee_r14, fee_r15;
    TextView fee_r21, fee_r22, fee_r23, fee_r24, fee_r25;
    TextView fee_r31, fee_r32, fee_r33, fee_r34, fee_r35;
    LinearLayout fee_Result_LL;
    ImageView backBtn_fee;

    private SmartMaterialSpinner<String> spProvince;
    private List<String> provinceList;
    private List<String> provinceListValue;


    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_calculator);

        fee_DC = findViewById(R.id.fee_DC);
        fee_URC = findViewById(R.id.fee_URC);
        fee_Re_DC = findViewById(R.id.fee_Re_DC);
        fee_Re_URC = findViewById(R.id.fee_Re_URC);
        fee_Im_DC = findViewById(R.id.fee_Im_DC);
        fee_Im_URC = findViewById(R.id.fee_Im_URC);

        fee_text = findViewById(R.id.fee_text);
        fee_1 = findViewById(R.id.fee_1);
        fee_2 = findViewById(R.id.fee_2);
        fee_3 = findViewById(R.id.fee_3);
        fee_4 = findViewById(R.id.fee_4);
        fee_5 = findViewById(R.id.fee_5);
        fee_6 = findViewById(R.id.fee_6);
        fee_7 = findViewById(R.id.fee_7);
        fee_8 = findViewById(R.id.fee_8);

        fee_r11 = findViewById(R.id.fee_r11);
        fee_r12 = findViewById(R.id.fee_r12);
        fee_r13 = findViewById(R.id.fee_r13);
        fee_r14 = findViewById(R.id.fee_r14);
        fee_r15 = findViewById(R.id.fee_r15);

        fee_r21 = findViewById(R.id.fee_r21);
        fee_r22 = findViewById(R.id.fee_r22);
        fee_r23 = findViewById(R.id.fee_r23);
        fee_r24 = findViewById(R.id.fee_r24);
        fee_r25 = findViewById(R.id.fee_r25);

        fee_r31 = findViewById(R.id.fee_r31);
        fee_r32 = findViewById(R.id.fee_r32);
        fee_r33 = findViewById(R.id.fee_r33);
        fee_r34 = findViewById(R.id.fee_r34);
        fee_r35 = findViewById(R.id.fee_r35);

        getDataForSpinner();


        backBtn_fee = findViewById(R.id.backBtn_fee);

        fee_Result_LL = findViewById(R.id.fee_Result_LL);

        button = findViewById(R.id.fee_submit_btn);

        loading = findViewById(R.id.fee_loading);
        if (loading != null) {
            loading.setIndeterminate(true);
            loading.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        if (fee_DC == null) {
            fee_DC.setText("0");
        }
        if (fee_URC == null) {
            fee_URC.setText("0");
        }
        if (fee_Re_DC == null) {
            fee_Re_DC.setText("0");
        }
        if (fee_Re_URC == null) {
            fee_Re_URC.setText("0");
        }
        if (fee_Im_DC == null) {
            fee_Im_DC.setText("0");
        }
        if (fee_Im_URC == null) {
            fee_Im_URC.setText("0");
        }
        backBtn_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fee_Student_ID = prefs.getString("id", "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (semester_id == null) {
                    Toast.makeText(getApplicationContext(), "Select Semester", Toast.LENGTH_SHORT).show();
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
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/home/fee-calculator")
                            .cookies(Cookies.getCookies(FeeCalculatorActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"res_form\"")) {
                        Element eventValidation = doc.select("input[name=csrf_iiuc_web]").first();

                        Document d = Jsoup.connect("https://www.iiuc.ac.bd/base/fee_calculator")
                                .data("csrf_iiuc_web", eventValidation.attr("value"))
                                .data("semester_id", semester_id)
                                .data("matric_no", fee_Student_ID)
                                .data("regular_ch", fee_DC.getText().toString())
                                .data("urc_ch", fee_URC.getText().toString())
                                .data("retake_ch", fee_Re_DC.getText().toString())
                                .data("urc_ch_r", fee_Re_URC.getText().toString())
                                .data("imp_ch", fee_Im_DC.getText().toString())
                                .data("urc_ch_imp", fee_Im_URC.getText().toString())
                                .data("submit", "submit")
                                .timeout(20000)
                                .method(Connection.Method.POST)
                                .cookies(Cookies.getCookies(FeeCalculatorActivity.this))
                                .followRedirects(true)
                                .post();
                        String text2 = d.html();
                        if (text2.contains("Total Tution Fee")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fee_Result_LL.setVisibility(View.VISIBLE);
                                    Spanned text = Html.fromHtml(d.select("table > tbody").get(0).select("tr").get(1).select("td").get(1).html());
                                    fee_text.setText(text);

                                    fee_1.setText(d.select("table > tbody").get(0).select("tr").get(0).select("td").get(2).text());
                                    fee_2.setText(d.select("table > tbody").get(0).select("tr").get(1).select("td").get(2).text());
                                    fee_3.setText(d.select("table > tbody").get(0).select("tr").get(2).select("td").get(2).text());
                                    fee_4.setText(d.select("table > tbody").get(0).select("tr").get(3).select("td").get(2).text());
                                    fee_5.setText(d.select("table > tbody").get(0).select("tr").get(4).select("td").get(2).text());
                                    fee_6.setText(d.select("table > tbody").get(0).select("tr").get(5).select("td").get(2).text());
                                    fee_7.setText(d.select("table > tbody").get(0).select("tr").get(6).select("td").get(2).text());
                                    fee_8.setText(d.select("table > tbody").get(0).select("tr").get(7).select("td").get(2).text());


                                    fee_r11.setText(d.select("table > tbody").get(1).select("tr").get(0).select("td").get(0).text());
                                    fee_r12.setText(d.select("table > tbody").get(1).select("tr").get(0).select("td").get(1).text());
                                    fee_r13.setText(d.select("table > tbody").get(1).select("tr").get(0).select("td").get(2).text());
                                    fee_r14.setText(d.select("table > tbody").get(1).select("tr").get(0).select("td").get(3).text());
                                    fee_r15.setText(d.select("table > tbody").get(1).select("tr").get(0).select("td").get(4).text());

                                    fee_r21.setText(d.select("table > tbody").get(1).select("tr").get(1).select("td").get(0).text());
                                    fee_r22.setText(d.select("table > tbody").get(1).select("tr").get(1).select("td").get(1).text());
                                    fee_r23.setText(d.select("table > tbody").get(1).select("tr").get(1).select("td").get(2).text());
                                    fee_r24.setText(d.select("table > tbody").get(1).select("tr").get(1).select("td").get(3).text());
                                    fee_r25.setText(d.select("table > tbody").get(1).select("tr").get(1).select("td").get(4).text());

                                    fee_r31.setText(d.select("table > tbody").get(1).select("tr").get(2).select("td").get(0).text());
                                    fee_r32.setText(d.select("table > tbody").get(1).select("tr").get(2).select("td").get(1).text());
                                    fee_r33.setText(d.select("table > tbody").get(1).select("tr").get(2).select("td").get(2).text());
                                    fee_r34.setText(d.select("table > tbody").get(1).select("tr").get(2).select("td").get(3).text());
                                    fee_r35.setText(d.select("table > tbody").get(1).select("tr").get(2).select("td").get(4).text());

                                    loading.hide();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loading.hide();
                                    fee_Result_LL.setVisibility(View.GONE);
                                    Toast.makeText(FeeCalculatorActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();
                                fee_Result_LL.setVisibility(View.GONE);
                                Toast.makeText(FeeCalculatorActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.hide();
                            fee_Result_LL.setVisibility(View.GONE);
                            Toast.makeText(FeeCalculatorActivity.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void getDataForSpinner() {
        spProvince = findViewById(R.id.fee_Semester_spinner);
        provinceList = new ArrayList<>();
        provinceListValue = new ArrayList<>();
        spProvince.setFloatingLabelSize(14);
        spProvince.setHintSize(14);
        spProvince.setTypeface(Typeface.DEFAULT_BOLD);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/home/fee-calculator")
                            .cookies(Cookies.getCookies(FeeCalculatorActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"res_form\"")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Element e = doc.select("#semester_id").get(0);
                                for (int i = 1; i < e.select("option").size(); i++) {
                                    provinceList.add(e.select("option").get(i).text());
                                    provinceListValue.add(e.select("option").get(i).attr("value"));
                                }
                                spProvince.setItem(provinceList);
                                spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                        semester_id = provinceListValue.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
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