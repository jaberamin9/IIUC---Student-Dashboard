package com.blogspot.softlabsja.iiucstudentapp.AddDrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationActivity;
import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationAdapter;
import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationModel;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Table.MyTableViewAdapter;
import com.blogspot.softlabsja.iiucstudentapp.Table.TableViewListener;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.Cell;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.ColumnHeader;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.RowHeader;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.evrencoskun.tableview.TableView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddDropActivity extends AppCompatActivity {
    String url;
    ImageView backBtn;
    MyTableViewAdapter adapter;
    TableView tableView;
    TextView matric_no, semester_name, mobile_number, department, programm, semester_number, total_c, tch, notFound, extra, extra1;
    LinearLayout linearLayout, lll3;
    ContentLoadingProgressBar loading_CR;

    private SmartMaterialSpinner<String> spProvince;
    private List<String> provinceList;
    private List<String> provinceListValue;
    String semester_id, student_id, eventValidation, matric__no, max_credit;

    RecyclerView recyclerView;
    HorizontalScrollView scrollView;
    ArrayList<AddDropModel> arrayList = new ArrayList<AddDropModel>();
    ArrayList<String> stringArrayList = new ArrayList<>();
    RelativeLayout button_v;
    AppCompatButton cr_submit_btn;
    ContentLoadingProgressBar cr_loading;
    LinearLayout ll_cr;

    TextView h_1, h_2, h_3, h_4, h_5, h_6;
    Element selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_drop);
        backBtn = findViewById(R.id.backBtn);
        matric_no = findViewById(R.id.matric_no);
        semester_name = findViewById(R.id.semester_name);
        mobile_number = findViewById(R.id.mobile_number);
        department = findViewById(R.id.department);
        programm = findViewById(R.id.programm);
        semester_number = findViewById(R.id.semester_number);
        total_c = findViewById(R.id.total_c);
        tch = findViewById(R.id.tch);
        notFound = findViewById(R.id.notfound);
        linearLayout = findViewById(R.id.lll);
        lll3 = findViewById(R.id.lll3);
        loading_CR = findViewById(R.id.loading_CR);

        button_v = findViewById(R.id.button_v);
        cr_submit_btn = findViewById(R.id.cr_submit_btn);
        cr_loading = findViewById(R.id.cr_loading);
        ll_cr = findViewById(R.id.ll_cr);

        tableView = findViewById(R.id.table_View);

        recyclerView = findViewById(R.id.recyclerView_cr);
        scrollView = findViewById(R.id.scrollView1);

        h_1 = findViewById(R.id.h_1);
        h_2 = findViewById(R.id.h_2);
        h_3 = findViewById(R.id.h_3);
        h_4 = findViewById(R.id.h_4);
        h_5 = findViewById(R.id.h_5);
        h_6 = findViewById(R.id.h_6);

        extra = findViewById(R.id.extra);
        extra1 = findViewById(R.id.extra1);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Check();
    }

    private void Check() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document dashboard = Jsoup.connect("https://www.iiuc.ac.bd/student/add-drop")
                            .cookies(Cookies.getCookies(AddDropActivity.this))
                            .get();

                    String text = dashboard.html();
                    if (text.contains("It is not add/drop time")) {
                        loading_CR.hide();
                        notFound.setVisibility(View.VISIBLE);
                        notFound.setText(dashboard.select(".notification-box-error > center > b").text());
                        tableView.setVisibility(View.GONE);
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(AddDropActivity.this);
                        Check();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading_CR.hide();
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
                            loading_CR.hide();
                            Toast.makeText(AddDropActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void Show() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document dashboard = Jsoup.connect("https://www.iiuc.ac.bd/student/add-drop")
                            .cookies(Cookies.getCookies(AddDropActivity.this))
                            .get();

                    String text = dashboard.html();
                    if (text.contains("id=\"semester_id\"")) {

                        student_id = dashboard.select("#student_id").attr("value");
                        eventValidation = dashboard.select("input[name=csrf_iiuc_web]").first().attr("value");
                        matric__no = dashboard.select("input[name=matric_no]").first().attr("value");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                linearLayout.setVisibility(View.VISIBLE);

                                url = "https://www.iiuc.ac.bd/basic/student-add-drop/" + student_id + "/" + semester_id;

                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Document dashboard = Jsoup.connect(url)
                                                    .cookies(Cookies.getCookies(AddDropActivity.this))
                                                    .get();

                                            String text = dashboard.html();
                                            if (text.contains("name=\"course_id[]\"")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        max_credit = dashboard.select("input[name=max_credit]").first().attr("value");
                                                        tableView.setVisibility(View.GONE);
                                                        scrollView.setVisibility(View.VISIBLE);
                                                        button_v.setVisibility(View.VISIBLE);
                                                        ll_cr.setVisibility(View.GONE);
                                                        Registration(dashboard);
                                                    }
                                                });
                                            } else if (text.contains("It is not add/drop time")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        loading_CR.hide();
                                                        notFound.setVisibility(View.VISIBLE);
                                                        notFound.setText(dashboard.select(".notification-box-error > center > b").text());
                                                        tableView.setVisibility(View.GONE);
                                                        linearLayout.setVisibility(View.GONE);
                                                        scrollView.setVisibility(View.GONE);
                                                        button_v.setVisibility(View.GONE);
                                                    }
                                                });
                                            } else if (text.contains("Your Payment Not Clear. If you already pay, please contact to ACFD to open block.")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        loading_CR.hide();
                                                        notFound.setVisibility(View.VISIBLE);
                                                        notFound.setText(dashboard.select(".notification-box-error > center > b").text());
                                                        tableView.setVisibility(View.GONE);
                                                        linearLayout.setVisibility(View.GONE);
                                                        scrollView.setVisibility(View.GONE);
                                                        button_v.setVisibility(View.GONE);
                                                    }
                                                });
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        scrollView.setVisibility(View.GONE);
                                                        tableView.setVisibility(View.VISIBLE);
                                                        button_v.setVisibility(View.GONE);
                                                        ll_cr.setVisibility(View.VISIBLE);
                                                        extra.setVisibility(View.GONE);
                                                        extra1.setVisibility(View.GONE);

                                                        adapter = new MyTableViewAdapter();
                                                        tableView.setAdapter(adapter);
                                                        tableView.setTableViewListener(new TableViewListener(tableView));

                                                        Elements rowLength = dashboard.select("#tables > tbody > tr");
                                                        List<RowHeader> list_RH = new ArrayList<>();
                                                        for (int i = 0; i < rowLength.size() - 1; i++) {
                                                            RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
                                                            list_RH.add(header);
                                                        }

                                                        Elements th = dashboard.select("#tables > thead > tr > th");
                                                        List<ColumnHeader> list_Ch = new ArrayList<>();
                                                        for (int i = 1; i < th.size(); i++) {
                                                            String title = th.get(i).text();
                                                            int nRandom = new Random().nextInt();
                                                            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                                                                title = th.get(i).text();
                                                            }

                                                            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                                                            list_Ch.add(header);
                                                        }

                                                        List<List<Cell>> list = new ArrayList<>();
                                                        String word = "This is not registration time or Registration time is over.";
                                                        String texts = dashboard.html();

                                                        boolean found = texts.contains(word);
                                                        if (found) {
                                                            notFound.setVisibility(View.VISIBLE);
                                                            notFound.setText(dashboard.select(".notification-box-error > center > b").text());
                                                            tableView.setVisibility(View.GONE);
                                                        } else {
                                                            notFound.setVisibility(View.GONE);
                                                            tableView.setVisibility(View.VISIBLE);

                                                            try {
                                                                Elements length = dashboard.select("#tables > tbody > tr");
                                                                for (int i = 0; i < length.size(); i++) {
                                                                    List<Cell> cellList = new ArrayList<>();
                                                                    Element c24 = dashboard.select("#tables > tbody > tr").get(i);
                                                                    for (int j = 1; j < 9 - 1; j++) {
                                                                        Elements ro = c24.select("td");
                                                                        Object text = ro.get(j).text();
                                                                        String id = j + "-" + i;
                                                                        Cell cell;
                                                                        cell = new Cell(id, text);
                                                                        cellList.add(cell);
                                                                    }
                                                                    list.add(cellList);
                                                                }
                                                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                                                e.printStackTrace();
                                                                System.out.println(e);
                                                            }
                                                        }
                                                        adapter.setAllItems(list_Ch, list_RH, list);


                                                        Elements tbody = dashboard.select("tbody");
                                                        for (int i = 0; i < tbody.size(); i++) {
                                                            if (i == 0) {
                                                                Elements tbod = tbody.get(i).select("td");
                                                                matric_no.setText(tbod.get(0).text());
                                                                semester_name.setText(tbod.get(2).text());
                                                                mobile_number.setText(tbod.get(4).text());
                                                                department.setText(tbod.get(1).text());
                                                                programm.setText(tbod.get(5).text());
                                                                semester_number.setText(tbod.get(7).text());
                                                                extra.setVisibility(View.VISIBLE);
                                                                extra.setText(tbod.get(9).text());
                                                            }
                                                        }

                                                        total_c.setText(dashboard.select("td > center > b").text());
                                                        Elements total = dashboard.select("#tables > tbody > tr > td > b");
                                                        tch.setText(total.attr("style", "color:green;").text());

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
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(AddDropActivity.this);
                        Show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void getDataForSpinner() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spProvince = findViewById(R.id.spinner1);
                provinceList = new ArrayList<>();
                provinceListValue = new ArrayList<>();
                spProvince.setFloatingLabelSize(14);
                spProvince.setHintSize(14);
                spProvince.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/add-drop")
                            .cookies(Cookies.getCookies(AddDropActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"semester_id\"")) {
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
                                        notFound.setVisibility(View.GONE);
                                        semester_id = provinceListValue.get(position);
                                        arrayList.clear();
                                        stringArrayList.clear();
                                        Show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(AddDropActivity.this);
                        getDataForSpinner();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading_CR.hide();
                                Toast.makeText(AddDropActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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

    private void Registration(Document document) {

        Elements tbody = document.select("tbody");
        for (int i = 0; i < tbody.size(); i++) {
            if (i == 0) {
                Elements tbod = tbody.get(i).select("td");
                for (int j = 0; j < tbod.size(); j++) {
                    matric_no.setText(tbod.get(0).text());
                    semester_name.setText(tbod.get(2).text());
                    mobile_number.setText(tbod.get(4).text());
                    department.setText(tbod.get(1).text());
                    programm.setText(tbod.get(3).text());
                    semester_number.setText(tbod.get(5).text());
                    extra.setVisibility(View.VISIBLE);
                    extra1.setVisibility(View.VISIBLE);
                    extra.setText(tbod.get(6).text());
                    extra1.setText(tbod.get(7).text());
                }
            }
        }

        String courseState = "0";
        int size = document.select("#tables > tbody").get(0).select("tr").size();
        final int[] f = {0};
        for (int i = 0; i < size; i++) {
            int checkSize = document.select("#tables > tbody").get(0).select("tr").get(i).select("td").size();
            if (checkSize > 1) {
                if (document.select("#tables > tbody").get(0).select("tr").get(i - 1).select("td").text().equals("Previous Failed Courses")) {
                    courseState = "Previous Failed Courses";
                } else if (document.select("#tables > tbody").get(0).select("tr").get(i - 1).select("td").text().equals("New Offered Courses")) {
                    courseState = "New Offered Courses";
                } else if (document.select("#tables > tbody").get(0).select("tr").get(i - 1).select("td").text().equals("Previous Missing Courses")) {
                    courseState = "Previous Missing Courses";
                } else if (document.select("#tables > tbody").get(0).select("tr").get(i - 1).select("td").text().equals("Advanced Courses")) {
                    courseState = "Advanced Courses";
                } else if (document.select("#tables > tbody").get(0).select("tr").get(i - 1).select("td").text().equals("Suggest for Improvement")) {
                    courseState = "Suggest for Improvement";
                }

                if (f[0] == 0) {
                    int subSize = document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(5).select("option").size();
                    for (int j = 0; j < subSize; j++) {
                        stringArrayList.add(document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(5).select("option").get(j).html());
                    }
                    f[0] = 1;
                }
                int subSize2 = document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(5).select("option").size();
                for (int j = 0; j < subSize2; j++) {
                    selected = document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(5);
                }

                arrayList.add(new AddDropModel(
                        courseState,
                        document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(0),
                        document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(3),
                        selected,
                        document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(1).text(),
                        document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(2).text(),
                        document.select("#tables > tbody").get(0).select("tr").get(i).select("td").get(4).text()));


            }

        }

        AddDropAdapter adapter = new AddDropAdapter(arrayList, stringArrayList, AddDropActivity.this, h_1, h_2, h_3, h_4, h_5, h_6, cr_submit_btn, this, cr_loading, student_id, eventValidation, matric__no, semester_id, max_credit);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddDropActivity.this));
        adapter.notifyDataSetChanged();

    }
}