package com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerActivity;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerAdapter;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TerSubmissionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView backBtn;

    String teacherName, url, eventValidation;

    ArrayList<TerSubmissionModel> arrayListMain = new ArrayList<>();
    TerSubmissionAdapter terSubmissionAdapter;

    AppCompatButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ter_submission);

        backBtn = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.recyclerView);
        submitBtn = findViewById(R.id.submitBtn);

        teacherName = getIntent().getStringExtra("teacherName");
        url = getIntent().getStringExtra("url");
        show();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void show() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document dashboard = Jsoup.connect(url)
                            .cookies(Cookies.getCookies(TerSubmissionActivity.this))
                            .get();

                    String text = dashboard.html();
                    if (text.contains(teacherName)) {
                        eventValidation = dashboard.select("input[name=csrf_iiuc_web]").first().attr("value");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Elements row = dashboard.select("#tables > tbody > tr");
                                    for (int i = 0; i < row.size(); i++) {
                                        Element col = dashboard.select("#tables > tbody > tr").get(i);
                                        String s = col.select("td").get(1).text();
                                        if (!s.equals(""))
                                            arrayListMain.add(new TerSubmissionModel(col.select("td").get(1).text()));
                                    }
                                    terSubmissionAdapter = new TerSubmissionAdapter(arrayListMain, TerSubmissionActivity.this, submitBtn, TerSubmissionActivity.this, eventValidation, url);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(TerSubmissionActivity.this));
                                    recyclerView.setAdapter(terSubmissionAdapter);
                                    terSubmissionAdapter.notifyDataSetChanged();
                                } catch (NullPointerException | IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                    System.out.println(e);
                                }
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(TerSubmissionActivity.this);
                        show();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TerSubmissionActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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