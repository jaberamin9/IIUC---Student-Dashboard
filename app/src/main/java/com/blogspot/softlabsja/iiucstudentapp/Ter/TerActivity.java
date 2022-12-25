package com.blogspot.softlabsja.iiucstudentapp.Ter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationActivity;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Table.MyTableViewAdapter;
import com.blogspot.softlabsja.iiucstudentapp.Table.TableViewListener;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.Cell;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.ColumnHeader;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.RowHeader;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerActivity extends AppCompatActivity {
    ImageView backBtn;
    ContentLoadingProgressBar loading;
    TextView notFound;
    private SmartMaterialSpinner<String> spinner;
    private List<String> list;
    private List<Pair<String, String>> listValue;
    String semester_id, student_id, eventValidation, matric__no, url;

    ArrayList<TerModel> arrayListMain = new ArrayList<>();
    TerAdapter terAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ter);

        backBtn = findViewById(R.id.backBtn);
        loading = findViewById(R.id.loading);
        notFound = findViewById(R.id.notFound);
        recyclerView = findViewById(R.id.recyclerView);

        Check();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void Check() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document dashboard = Jsoup.connect("https://www.iiuc.ac.bd/student/ter")
                            .cookies(Cookies.getCookies(TerActivity.this))
                            .get();

                    String text = dashboard.html();
                    if (text.contains("This is not registration time or Registration time is over.")) {
                        loading.hide();
                        notFound.setVisibility(View.VISIBLE);
                        notFound.setText(dashboard.select(".notification-box-error > center > b").text());
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(TerActivity.this);
                        Check();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();
                                notFound.setVisibility(View.VISIBLE);
                                notFound.setText("Please Select a Semester");
                                getDataForSpinner();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.hide();
                            Toast.makeText(TerActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void getDataForSpinner() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner = findViewById(R.id.spinner);
                list = new ArrayList<>();
                listValue = new ArrayList<>();
                spinner.setFloatingLabelSize(14);
                spinner.setHintSize(14);
                spinner.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/ter")
                            .cookies(Cookies.getCookies(TerActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"semester_id\"")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                student_id = doc.select("#student_id").attr("value");
                                eventValidation = doc.select("input[name=csrf_iiuc_web]").first().attr("value");
                                matric__no = doc.select("input[name=matric_no]").first().attr("value");

                                Element e = doc.select("#semester_id").get(0);
                                for (int i = 1; i < e.select("option").size(); i++) {
                                    list.add(e.select("option").get(i).text());
                                    listValue.add(new Pair<String, String>(e.select("option").get(i).text(), e.select("option").get(i).attr("value")));
                                }
                                spinner.setItem(list);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                        arrayListMain.clear();
                                        notFound.setVisibility(View.GONE);
                                        semester_id = listValue.get(position).second;
                                        url = "https://www.iiuc.ac.bd/basic/student-ter/" + student_id + "/" + semester_id;
                                        show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(TerActivity.this);
                        getDataForSpinner();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();
                                Toast.makeText(TerActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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

    void show() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document dashboard = Jsoup.connect(url)
                            .cookies(Cookies.getCookies(TerActivity.this))
                            .get();

                    String text = dashboard.html();
                    if (text.contains("Teacher")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Elements row = dashboard.select("#tables > tbody > tr");
                                    for (int i = 0; i < row.size(); i++) {
                                        Element col = dashboard.select("#tables > tbody > tr").get(i);
                                        String cc = null, cn = null, ch = null, ss = null, t = null, s = null, a = null;
                                        ArrayList<Pair<String, String>> arrayList = new ArrayList<>();
                                        for (int j = 1; j < col.childrenSize(); j++) {
                                            if (j == 1) cc = col.select("td").get(j).text();
                                            else if (j == 2) cn = col.select("td").get(j).text();
                                            else if (j == 3) ch = col.select("td").get(j).text();
                                            else if (j == 4) ss = col.select("td").get(j).text();
                                            else if (j == 5) {
                                                Element col2 = col.select("td").get(j);
                                                if (col2.childrenSize() <= 1)
                                                    t = col.select("td").get(j).text();
                                                for (int k = 0; k < col2.childrenSize(); k++) {
//                                                    System.out.println(col2.select("a").get(k) + " ");
                                                    arrayList.add(new Pair<>(col2.select("a").get(k).text(), col2.select("a").get(k).attr("href")));
                                                }
                                            } else if (j == 6) s = col.select("td").get(j).text();
                                            else a = col.select("td").get(j).text();
                                        }
                                        //System.out.println(cc+" "+cn+" "+ch+" "+ss+" "+t+" "+s+" "+a);
                                        arrayListMain.add(new TerModel(cc, cn, ch, ss, t, s, a, arrayList));
                                    }
                                    terAdapter = new TerAdapter(arrayListMain, TerActivity.this);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(TerActivity.this));
                                    recyclerView.setAdapter(terAdapter);
                                    terAdapter.notifyDataSetChanged();
                                } catch (NullPointerException | IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                    System.out.println(e);
                                }
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(TerActivity.this);
                        getDataForSpinner();
                    } else if (text.contains("notification-box-error")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();
                                notFound.setVisibility(View.VISIBLE);
                                notFound.setText(dashboard.select(".notification-box-error > center > b").text());
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();
                                Toast.makeText(TerActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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