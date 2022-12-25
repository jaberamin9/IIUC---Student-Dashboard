package com.blogspot.softlabsja.iiucstudentapp.AllNotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

import static com.blogspot.softlabsja.iiucstudentapp.MainActivity.userAgent;

public class AllNoticeActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageView backBtn;
    TextView titleN;
    SwipeRefreshLayout refreshLayout;
    ContentLoadingProgressBar loading_AN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notice);

        backBtn = findViewById(R.id.backBtn);
        titleN = findViewById(R.id.titelN);

        viewPager = findViewById(R.id.tab_ViewPager);
        refreshLayout = findViewById(R.id.swipe);
        loading_AN = findViewById(R.id.loading_AN);

        initUI();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                initUI();
                refreshLayout.setRefreshing(false);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initUI() {

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                View view = null;
                if (position == 0) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.notice, null, false);

                    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                    SearchView searchView = view.findViewById(R.id.searchView1);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Document doc = Jsoup.connect("https://www.iiuc.ac.bd/home/notice/")
                                        .userAgent(userAgent)
                                        .referrer("http://www.google.com")
                                        .get();

                                String word = "Notice";
                                String text = doc.html();
                                Boolean found = text.contains(word);
                                if (found) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loading_AN.hide();
                                            ArrayList<NoticeModel> arrayList = new ArrayList<NoticeModel>();

                                            Elements length = doc.select(".container-fluid > .col-lg-12 > a[href]");
                                            for (int i = 0; i < length.size(); i++) {
                                                Element notice = doc.select(".container-fluid > .col-lg-12 > a[href]").get(i);
                                                //System.out.println(notice.attr("href"));
                                                arrayList.add(new NoticeModel(notice.text(), notice.attr("href")));
                                            }
                                            if (arrayList.isEmpty()) {
                                                length = doc.select(".subpage-content > .container-fluid > .row > .container > .col-sm-12 > .row");
                                                for (int i = 0; i < length.size(); i++) {
                                                    Element notice = doc.select(".subpage-content > .container-fluid > .row > .container > .col-sm-12 > .row > .container-fluid > .col-lg-12 > .col-sm-11 > div[style] > a[href]").get(i);
                                                    //System.out.println(notice.attr("href"));
                                                    arrayList.add(new NoticeModel(notice.text(), notice.attr("href")));
                                                }
                                            }


                                            NoticeAdapter adapter = new NoticeAdapter(arrayList, AllNoticeActivity.this);
                                            recyclerView.setAdapter(adapter);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(AllNoticeActivity.this));
                                            adapter.notifyDataSetChanged();

                                            searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                                                @Override
                                                public boolean onQueryTextSubmit(String query) {
                                                    return false;
                                                }

                                                @Override
                                                public boolean onQueryTextChange(String newText) {
                                                    adapter.getFilter().filter(newText);
                                                    return false;
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AllNoticeActivity.this, "Not Pound", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                    container.addView(view);
                    return view;
                } else if (position == 1) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.press_release, null, false);

                    RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
                    SearchView searchView2 = view.findViewById(R.id.searchView2);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Document doc = Jsoup.connect("https://www.iiuc.ac.bd/home/press/")
                                        .userAgent(userAgent)
                                        .referrer("http://www.google.com")
                                        .get();

                                String word = "Press Release";
                                String text = doc.html();
                                Boolean found = text.contains(word);
                                if (found) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loading_AN.hide();
                                            ArrayList<NoticeModel> arrayList = new ArrayList<NoticeModel>();

                                            Elements length = doc.select(".container-fluid > .col-lg-12 > a[href]");
                                            for (int i = 0; i < length.size(); i++) {
                                                Element notice = doc.select(".container-fluid > .col-lg-12 > a[href]").get(i);
                                                //System.out.println(notice.attr("href"));
                                                arrayList.add(new NoticeModel(notice.text(), notice.attr("href")));
                                            }
                                            if (arrayList.isEmpty()) {
                                                length = doc.select(".subpage-content > .container-fluid > .row > .container > .col-sm-12 > .row");
                                                for (int i = 0; i < length.size(); i++) {
                                                    Element notice = doc.select(".subpage-content > .container-fluid > .row > .container > .col-sm-12 > .row > .container-fluid > .col-lg-12 > .col-sm-11 > div[style] > a[href]").get(i);
                                                    //System.out.println(notice.attr("href"));
                                                    arrayList.add(new NoticeModel(notice.text(), notice.attr("href")));
                                                }
                                            }

                                            NoticeAdapter adapter = new NoticeAdapter(arrayList, AllNoticeActivity.this);
                                            recyclerView.setAdapter(adapter);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(AllNoticeActivity.this));
                                            adapter.notifyDataSetChanged();

                                            searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                @Override
                                                public boolean onQueryTextSubmit(String query) {
                                                    return false;
                                                }

                                                @Override
                                                public boolean onQueryTextChange(String newText) {
                                                    adapter.getFilter().filter(newText);
                                                    return false;
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AllNoticeActivity.this, "Not Pound", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                    container.addView(view);
                    return view;
                } else if (position == 2) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.tenders, null, false);

                    RecyclerView recyclerView = view.findViewById(R.id.recyclerView3);
                    SearchView searchView3 = view.findViewById(R.id.searchView3);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Document doc = Jsoup.connect("https://www.iiuc.ac.bd/home/tenders/")
                                        .userAgent(userAgent)
                                        .referrer("http://www.google.com")
                                        .get();

                                String word = "Tenders";
                                String text = doc.html();
                                Boolean found = text.contains(word);
                                if (found) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loading_AN.hide();
                                            ArrayList<NoticeModel> arrayList = new ArrayList<NoticeModel>();

                                            Elements length = doc.select(".container-fluid > .col-lg-12 > a[href]");
                                            for (int i = 0; i < length.size(); i++) {
                                                Element notice = doc.select(".container-fluid > .col-lg-12 > a[href]").get(i);
                                                //System.out.println(notice.attr("href"));
                                                arrayList.add(new NoticeModel(notice.text(), notice.attr("href")));
                                            }
                                            if (arrayList.isEmpty()) {
                                                length = doc.select(".subpage-content > .container-fluid > .row > .container > .col-sm-12 > .row");
                                                for (int i = 0; i < length.size(); i++) {
                                                    Element notice = doc.select(".subpage-content > .container-fluid > .row > .container > .col-sm-12 > .row > .container-fluid > .col-lg-12 > .col-sm-11 > div[style] > a[href]").get(i);
                                                    //System.out.println(notice.attr("href"));
                                                    arrayList.add(new NoticeModel(notice.text(), notice.attr("href")));
                                                }
                                            }
                                            NoticeAdapter adapter = new NoticeAdapter(arrayList, AllNoticeActivity.this);
                                            recyclerView.setAdapter(adapter);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(AllNoticeActivity.this));
                                            adapter.notifyDataSetChanged();

                                            searchView3.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                @Override
                                                public boolean onQueryTextSubmit(String query) {
                                                    return false;
                                                }

                                                @Override
                                                public boolean onQueryTextChange(String newText) {
                                                    adapter.getFilter().filter(newText);
                                                    return false;
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AllNoticeActivity.this, "Not Pound", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                    container.addView(view);
                    return view;
                }

                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview_my);

        final NavigationTabBar navigationTabBar = findViewById(R.id.tab_notice);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(this, R.drawable.ic_notice),
                Color.parseColor(colors[0]))
                .title("Notice")
                .build()
        );

        models.add(new NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(this, R.drawable.ic_press),
                Color.parseColor(colors[1]))
                .title("Press Release")
                .build()
        );

        models.add(new NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(this, R.drawable.ic_tenders),
                Color.parseColor(colors[2]))
                .title("Tenders")
                .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
    }

}