package com.blogspot.softlabsja.iiucstudentapp.CourseStatus;

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

public class CourseStatusActivity extends AppCompatActivity {
    ImageView backBtn;
    ContentLoadingProgressBar loading_CS;
    RecyclerView recyclerView;
    ArrayList<CourseStatusModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_status);
        backBtn = findViewById(R.id.backBtn_cs);
        loading_CS = findViewById(R.id.loading_CS);
        recyclerView = findViewById(R.id.recyclerView_CS);

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
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/course-status")
                            .cookies(Cookies.getCookies(CourseStatusActivity.this))
                            .timeout(20000)
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"tab-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading_CS.hide();

                                Elements length = doc.select("table > tbody");
                                //----------------------for table data-------------------------------
                                Elements size = length.get(1).select("tr");
                                int f = 0;
                                for (int i = 0; i < size.size(); i++) {
                                    if (size.get(i).select("td").get(0).text().equals("On Going Courses")) {
                                        String s = null;
                                        for (int j = i; ; j++) {
                                            if (size.get(j).select("td").get(0).text().equals("Total Credit")) {
                                                arrayList.add(new CourseStatusModel(s, doc.select("table > thead > tr > th")));
                                                break;
                                            }
                                            s += "<tr>" + length.get(1).select("tr").get(j + 1).html() + "</tr>";
                                        }
                                    } else if (size.get(i).select("td").get(0).text().equals("Completed Courses")) {
                                        String s = null;
                                        for (int j = i; ; j++) {
                                            if (size.get(j).select("td").get(0).text().equals("Total Credit")) {
                                                arrayList.add(new CourseStatusModel(s, doc.select("table > thead > tr > th")));
                                                break;
                                            }
                                            s += "<tr>" + length.get(1).select("tr").get(j + 1).html() + "</tr>";
                                        }
                                    } else if (size.get(i).select("td").get(0).text().equals("Result Up-Coming")) {
                                        String s = null;
                                        for (int j = i; ; j++) {
                                            if (size.get(j).select("td").get(0).text().equals("Total Credit")) {
                                                arrayList.add(new CourseStatusModel(s, doc.select("table > thead > tr > th")));
                                                break;
                                            }
                                            s += "<tr>" + length.get(1).select("tr").get(j + 1).html() + "</tr>";
                                        }
                                    } else if (size.get(i).select("td").get(0).text().equals("Waiting Course")) {
                                        String s = null;
                                        for (int j = i; ; j++) {
                                            if (size.get(j).select("td").get(0).text().equals("Total Credit")) {
                                                arrayList.add(new CourseStatusModel(s, doc.select("table > thead > tr > th")));
                                                break;
                                            }
                                            s += "<tr>" + length.get(1).select("tr").get(j + 1).html() + "</tr>";
                                        }
                                    } else if (size.get(i).select("td").get(0).text().equals("Previous Failed Courses")) {
                                        String s = null;
                                        for (int j = i; ; j++) {
                                            if (size.get(j).select("td").get(0).text().equals("Total Credit")) {
                                                arrayList.add(new CourseStatusModel(s, doc.select("table > thead > tr > th")));
                                                break;
                                            }
                                            s += "<tr>" + length.get(1).select("tr").get(j + 1).html() + "</tr>";
                                        }
                                    } else if (size.get(i).select("td").get(0).text().equals("Pending/Missing Courses")) {
                                        String s = null;
                                        for (int j = i; j < size.size(); j++) {
                                            s += "<tr>" + length.get(1).select("tr").get(j).html() + "</tr>";
                                            if (j == size.size() - 1) {
                                                arrayList.add(new CourseStatusModel(s, doc.select("table > thead > tr > th")));
                                                f = 1;
                                                break;
                                            }
                                        }
                                    }

                                    if (f == 1)
                                        break;
                                }

                                CourseStatusAdapter adapter = new CourseStatusAdapter(arrayList, CourseStatusActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CourseStatusActivity.this));
                                adapter.notifyDataSetChanged();
                                //--------------------------------------------------------------------------
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(CourseStatusActivity.this);
                        getData();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(CourseStatusActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CourseStatusActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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