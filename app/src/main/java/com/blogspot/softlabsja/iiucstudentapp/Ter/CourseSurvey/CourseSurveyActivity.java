package com.blogspot.softlabsja.iiucstudentapp.Ter.CourseSurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission.TerSubmissionActivity;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission.TerSubmissionAdapter;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission.TerSubmissionModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CourseSurveyActivity extends AppCompatActivity {

    ImageView backBtn;
    AppCompatButton submitBtn;
    RecyclerView recyclerView;
    String url, eventValidation, userId, teacherName;

    CourseSurveyAdapter courseSurveyAdapter;
    ArrayList<CourseSurveyModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_survey);

        backBtn = findViewById(R.id.backBtn);
        submitBtn = findViewById(R.id.submitBtn);
        recyclerView = findViewById(R.id.recyclerView);

        url = getIntent().getStringExtra("url");
        teacherName = getIntent().getStringExtra("teacherName");
        if (teacherName == null) teacherName = "";
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
                            .cookies(Cookies.getCookies(CourseSurveyActivity.this))
                            .get();

                    String text = dashboard.html();
                    if (text.contains("Aspects of Evaluation")) {
                        eventValidation = dashboard.select("input[name=csrf_iiuc_web]").first().attr("value");
                        userId = dashboard.select("input[id=matric_no]").first().attr("value");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Elements row = dashboard.select(".table > tbody > tr");
                                    for (int i = 0; i < row.size(); i++) {
                                        Element col = dashboard.select(".table > tbody > tr").get(i);
                                        arrayList.add(new CourseSurveyModel(col.select("td").get(0).text(), col.select("td > input").get(0).attr("value")));
                                    }
                                    courseSurveyAdapter = new CourseSurveyAdapter(arrayList, CourseSurveyActivity.this, submitBtn, CourseSurveyActivity.this, eventValidation, url, userId, teacherName);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(CourseSurveyActivity.this));
                                    recyclerView.setAdapter(courseSurveyAdapter);
                                    courseSurveyAdapter.notifyDataSetChanged();

                                } catch (NullPointerException | IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                    System.out.println(e);
                                }
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(CourseSurveyActivity.this);
                        show();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CourseSurveyActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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