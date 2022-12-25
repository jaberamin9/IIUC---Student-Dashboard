package com.blogspot.softlabsja.iiucstudentapp.About;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {

    ImageView backBtn, emailBtn, facebookBtn, linkedinBtn, backBtn_p2;
    TextView A_section, A_semester, A_department, A_slog, nameTXT, nameTitel, subTitle1, subTitle2;
    String email, fbLink, linkedinLink;
    CircleImageView imageP;
    ProgressBar progressBar;
    KenBurnsView animImage;
    private AVLoadingIndicatorView loading;
    PhotoView photo_view;
    RelativeLayout image_full;
    int isShowIMG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backBtn = findViewById(R.id.backBtnA);
        A_section = findViewById(R.id.A_section);
        A_semester = findViewById(R.id.A_semester);
        A_department = findViewById(R.id.A_department);
        A_slog = findViewById(R.id.A_slog);
        nameTXT = findViewById(R.id.nameTXT);
        imageP = findViewById(R.id.imageP);
        progressBar = findViewById(R.id.progressBar);
        animImage = findViewById(R.id.animImage);
        nameTitel = findViewById(R.id.nameTitel);
        loading = findViewById(R.id.loading);
        emailBtn = findViewById(R.id.emailBtn);
        facebookBtn = findViewById(R.id.facebookBtn);
        linkedinBtn = findViewById(R.id.linkedinBtn);
        subTitle1 = findViewById(R.id.subTitle1);
        subTitle2 = findViewById(R.id.subTitle2);
        backBtn_p2 = findViewById(R.id.backBtn_p2);
        photo_view = findViewById(R.id.photo_view);
        image_full = findViewById(R.id.image_full);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.overlayColor));
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("about");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        nameTXT.setText("Name: " + dataSnapshot.child("name").getValue());
                        nameTitel.setText((String) dataSnapshot.child("name").getValue());
                        email = (String) dataSnapshot.child("email").getValue();
                        A_section.setText("Section: " + dataSnapshot.child("section").getValue());
                        A_semester.setText("Semester: " + dataSnapshot.child("semester").getValue());
                        A_department.setText("Department: " + dataSnapshot.child("department").getValue());
                        A_slog.setText((String) dataSnapshot.child("slog").getValue());
                        fbLink = (String) dataSnapshot.child("fbLink").getValue();
                        fbLink = (String) dataSnapshot.child("fbLink").getValue();
                        linkedinLink = (String) dataSnapshot.child("linkedinLink").getValue();
                        subTitle1.setText((String) dataSnapshot.child("subTitle1").getValue());
                        subTitle2.setText((String) dataSnapshot.child("subTitle2").getValue());
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InputStream input = null;
                                try {
                                    input = (InputStream) new URL((String) dataSnapshot.child("url").getValue()).getContent();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Bitmap bitmap = BitmapFactory.decodeStream(input);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        imageP.setImageBitmap(bitmap);
                                        animImage.setImageBitmap(bitmap);
                                        loading.hide();
                                        imageP.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    Window window = getWindow();
                                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                                    window.setStatusBarColor(getResources().getColor(R.color.black));
                                                }
                                                photo_view.setImageBitmap(bitmap);
                                                image_full.setVisibility(View.VISIBLE);
                                                isShowIMG = 1;
                                            }
                                        });

                                        backBtn_p2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    Window window = getWindow();
                                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                                    window.setStatusBarColor(getResources().getColor(R.color.colorb));
                                                }
                                                image_full.setVisibility(View.GONE);
                                                isShowIMG = 0;
                                            }
                                        });
                                    }
                                });
                            }
                        });
                        thread.start();
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                        //databaseError
                    }
                });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("Email", email);
//                clipboard.setPrimaryClip(clip);
//                Toast.makeText(AboutActivity.this, "Already copy the E-mail", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                startActivity(Intent.createChooser(emailIntent, ""));
            }
        });
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(getResources().getColor(R.color.overlayColor))
                        .build();
                builder.setDefaultColorSchemeParams(defaultColors);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(AboutActivity.this, Uri.parse(fbLink));
            }
        });
        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(getResources().getColor(R.color.overlayColor))
                        .build();
                builder.setDefaultColorSchemeParams(defaultColors);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(AboutActivity.this, Uri.parse(linkedinLink));
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (isShowIMG == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.overlayColor));
            }
            image_full.setVisibility(View.GONE);
            isShowIMG = 0;
        } else {
            finish();
        }
    }

}