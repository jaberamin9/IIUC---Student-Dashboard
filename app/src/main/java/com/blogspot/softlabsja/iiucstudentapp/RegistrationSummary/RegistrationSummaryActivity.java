package com.blogspot.softlabsja.iiucstudentapp.RegistrationSummary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class RegistrationSummaryActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView recyclerView;
    ArrayList<RegistrationSummaryModel> arrayList = new ArrayList<RegistrationSummaryModel>();
    ContentLoadingProgressBar loading_RS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_summary);

        backBtn = findViewById(R.id.backBtn_rv);
        recyclerView = findViewById(R.id.RS_RecyclerView);
        loading_RS = findViewById(R.id.loading_RS);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();
    }

    private void getData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/reg-summary")
                            .cookies(Cookies.getCookies(RegistrationSummaryActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("tables")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading_RS.hide();
                                Elements length = doc.select("#tables > tbody > tr");
                                for (int i = 0; i < length.size(); i++) {
                                    arrayList.add(new RegistrationSummaryModel(
                                            i + 1,
                                            length.get(i).select("td").get(1).text(),
                                            length.get(i).select("td").get(2).text(),
                                            length.get(i).select("td").get(3).text(),
                                            length.get(i).select("td").get(4).text(),
                                            length.get(i).select("td").get(5).text(),
                                            length.get(i).select("td").get(6).text(),
                                            length.get(i).select("td").get(7).text(),
                                            length.get(i).select("td").get(8).text(),
                                            length.get(i).select("td").get(9).text(),
                                            length.get(i).select("td").get(10)
                                    ));
                                }

                                RegistrationSummaryAdapter adapter = new RegistrationSummaryAdapter(arrayList, RegistrationSummaryActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(RegistrationSummaryActivity.this));
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(RegistrationSummaryActivity.this);
                        getData();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(RegistrationSummaryActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistrationSummaryActivity.this, "Not Pound", Toast.LENGTH_SHORT).show();
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