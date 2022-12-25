package com.blogspot.softlabsja.iiucstudentapp.ResultView;

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

public class ResultViewActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView recyclerView;
    ArrayList<ResulteViewModels> arrayList = new ArrayList<>();
    ContentLoadingProgressBar loading_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        backBtn = findViewById(R.id.backBtn_rv);
        recyclerView = findViewById(R.id.RV_RecyclerView);
        loading_RV = findViewById(R.id.loading_RV);

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
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/view-result")
                            .cookies(Cookies.getCookies(ResultViewActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"tab-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading_RV.hide();
                                Elements length = doc.select("#tab-1 > table");
                                for (int i = 0; i < length.size(); i += 2) {
                                    Elements data = doc.select("#tab-1 > table");
                                    if (i == 0) {
                                        arrayList.add(new ResulteViewModels(data.get(i).html() + data.get(i + 1).html() + data.get(i + 2).html()));
                                        i++;
                                    } else {
                                        arrayList.add(new ResulteViewModels(data.get(i).html() + data.get(i + 1).html()));
                                    }
                                }
                                ResultViewAdapter adapter = new ResultViewAdapter(arrayList, ResultViewActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ResultViewActivity.this));
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(ResultViewActivity.this);
                        getData();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(ResultViewActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResultViewActivity.this, "Not Pound", Toast.LENGTH_SHORT).show();
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