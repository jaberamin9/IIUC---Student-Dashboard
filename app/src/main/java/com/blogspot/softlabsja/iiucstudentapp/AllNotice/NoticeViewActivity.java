package com.blogspot.softlabsja.iiucstudentapp.AllNotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.About.AboutActivity;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.downloader.PRDownloader;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

public class NoticeViewActivity extends AppCompatActivity {
    ImageView backBtn, downloadBtn;
    TextView title, contentText, pdf_or_img;
    String url;
    String title_n;
    PDFView pdfView;
    ProgressDialog progressDialog;
    PhotoView imageView;
    RetrivePDFfromUrl retrivePDFfromUrl;
    CardView cardView;
    String check;

    String pdf_link = null;

    String pdf_link_notice = null;
    String img_link = null;
    String cText2 = null;
    String checks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);

        backBtn = findViewById(R.id.backBtn);
        downloadBtn = findViewById(R.id.downloadBtn);
        title = findViewById(R.id.name_t);
        pdfView = findViewById(R.id.pdfView);
        imageView = findViewById(R.id.imageView);
        contentText = findViewById(R.id.contentText);
        cardView = findViewById(R.id.contentCart);
        pdf_or_img = findViewById(R.id.pdf_or_img);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        PRDownloader.initialize(getApplicationContext());

        //retrivePDFfromUrl = new RetrivePDFfromUrl(this, progressDialog, pdfView,checks);

        Intent intent = getIntent();
        check = intent.getStringExtra("check");
        System.out.println(check);
        if (check != null) {
            retrivePDFfromUrl = new RetrivePDFfromUrl(this, progressDialog, pdfView, checks);
            if (check.equals("RegistrationS")) {
                String RS_url = intent.getStringExtra("RS_url");
                String RS_name = intent.getStringExtra("RS_name");
                title.setText(RS_name);
                getRSPDF(RS_url);

                downloadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Download(RS_url, RS_name + ".pdf");
                    }
                });
            } else {
                title.setText("Payment History");
                paymentHistory();
                downloadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Download(pdf_link, "Payment History.pdf");
                    }
                });
            }
        } else {
            checks = "jaber";
            retrivePDFfromUrl = new RetrivePDFfromUrl(this, progressDialog, pdfView, checks);
            url = intent.getStringExtra("url");
            title_n = intent.getStringExtra("title");
            if (title_n != null) {
                title.setText(title_n);
            }
            getPdfUrl();
            downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!pdf_link_notice.equals("")) {
                        Download(pdf_link_notice, title_n + ".pdf");
                    } else if (!img_link.equals("")) {
                        Download(img_link, title_n + ".jpg");
                    }
                }
            });
            downloadBtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Dialog mDialog = new Dialog(NoticeViewActivity.this);
                    TextView close, linkTxt, linkName;
                    Button btn;
                    mDialog.setContentView(R.layout.custompopup);
                    close = mDialog.findViewById(R.id.txtclose);
                    linkTxt = mDialog.findViewById(R.id.pdfLink);
                    linkName = mDialog.findViewById(R.id.linkName);
                    if (!pdf_link_notice.equals("")) {
                        linkTxt.setText(pdf_link_notice);
                        linkName.setText("PDF link");
                    } else if (!img_link.equals("")) {
                        linkTxt.setText(img_link);
                        linkName.setText("IMG link");
                    }
                    btn = mDialog.findViewById(R.id.pdfLinkBtn);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String copyLink = null;
                            if (!pdf_link_notice.equals("")) {
                                copyLink = pdf_link_notice;
                            } else if (!img_link.equals("")) {
                                copyLink = img_link;
                            }
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("copyLinkPDF", copyLink);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(NoticeViewActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();
                    return true;
                }
            });

        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();//dismiss dialog
                retrivePDFfromUrl.cancel(true);
                finish();
            }
        });
        progressDialog.show();
    }

    private void getPdfUrl() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url)
                            .get();

                    String word = title_n;
                    String text = doc.html();
                    if (!text.equals("")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Elements cText = doc.select(".col-sm-12 > div > .well > p");
                                cText2 = cText.text();
                                System.out.println("**" + cText2);
                                if (!cText2.equals("")) {
                                    cardView.setVisibility(View.VISIBLE);
                                    contentText.setText(cText2);
                                }

                                Elements pdf_url = doc.select(".col-sm-12 > div > .well > div > a[href]");
                                pdf_link_notice = pdf_url.attr("href");
                                if (!pdf_link_notice.equals("")) {
                                    retrivePDFfromUrl.execute(pdf_link_notice);
                                    pdf_or_img.setText("PDF");
                                } else {
                                    pdfView.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                    Elements img_url = doc.select(".col-sm-12 > div > .well > div > img[src]");
                                    img_link = img_url.attr("src");
                                    Picasso.get()
                                            .load(img_link)
                                            .into(imageView);
                                    progressDialog.dismiss();
                                    pdf_or_img.setText("IMG");
                                }


                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NoticeViewActivity.this, "Not Pound", Toast.LENGTH_SHORT).show();
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

    private void paymentHistory() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/phistory")
                            .cookies(Cookies.getCookies(NoticeViewActivity.this))
                            .get();

                    String text = doc.html();
                    if (text.contains("btn-primary")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Elements pdf_url = doc.select(".btn-primary");
                                pdf_link = pdf_url.attr("href");
                                retrivePDFfromUrl.execute(pdf_link);
                                pdf_or_img.setText("PDF");
                            }
                        });
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(NoticeViewActivity.this);
                        paymentHistory();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(NoticeViewActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NoticeViewActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void getRSPDF(String RS_url) {
        pdf_or_img.setText("PDF");
        retrivePDFfromUrl.execute(RS_url);
    }

    private void Download(String link, String name) {
        String s = String.valueOf(Cookies.getCookies(this));
        StringBuilder sb = new StringBuilder(s);
        sb.deleteCharAt(s.length() - 1);
        sb.deleteCharAt(0);
        s = String.valueOf(sb);
        s = s.replaceAll("\\s", "");
        s = s.replaceAll(",", ";");

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
        if (checks == null)
            request.addRequestHeader("Cookie", s);
        request.setTitle(name);
        request.allowScanningByMediaScanner();
        request.setAllowedOverMetered(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/IIUC/" + name);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast.makeText(this, "Downloading Started..\nPath: Download/IIUC folder.", Toast.LENGTH_LONG).show();
    }
}