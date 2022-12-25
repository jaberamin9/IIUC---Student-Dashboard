package com.blogspot.softlabsja.iiucstudentapp.Profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {
    ImageView backBtn, backBtn_p2, textShowBtn, textShowBtn2, textShowBtn3, textShowBtn4, emailBtn, telephoneBtn;
    TextView id_sub, pro_id_no, pro_date_of_b, pro_gender, pro_religion, pro_nationality, pro_Father_Name, pro_Mother_Name, pro_pre_Address, pro_per_Address, pro_number, pro_alt_number, pro_email, pro_birthC, pro_National_ID;
    LinearLayout text_hide, text_hide2, text_hide3, text_hide4;
    LinearLayout clickBtnText, clickBtnText2, clickBtnText3, clickBtnText4, BirthC_LL, National_ID_LL;
    ContentLoadingProgressBar loading;
    CircleImageView student_Profile_img;
    TextView profile_name;
    PhotoView photo_view;
    RelativeLayout image_full;
    RelativeLayout updateBtn;
    int isShowIMG = 0;
    int f = 0, f2 = 0, f3 = 0, f4 = 0;
    String profile_Link, number, alt_number, email, birthC, nationalID;

    ImageView animImage;
    private AVLoadingIndicatorView loading1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backBtn = findViewById(R.id.backBtn_p);
        backBtn_p2 = findViewById(R.id.backBtn_p2);
        updateBtn = findViewById(R.id.updateBtn);

        student_Profile_img = findViewById(R.id.student_Profile_img);
        profile_name = findViewById(R.id.profile_name);
        photo_view = findViewById(R.id.photo_view);
        image_full = findViewById(R.id.image_full);

        textShowBtn = findViewById(R.id.textShowBtn);
        textShowBtn2 = findViewById(R.id.textShowBtn2);
        textShowBtn3 = findViewById(R.id.textShowBtn3);
        textShowBtn4 = findViewById(R.id.textShowBtn4);

        text_hide = findViewById(R.id.text_hide);
        text_hide2 = findViewById(R.id.text_hide2);
        text_hide3 = findViewById(R.id.text_hide3);
        text_hide4 = findViewById(R.id.text_hide4);

        clickBtnText = findViewById(R.id.clickBtnText);
        clickBtnText2 = findViewById(R.id.clickBtnText2);
        clickBtnText3 = findViewById(R.id.clickBtnText3);
        clickBtnText4 = findViewById(R.id.clickBtnText4);

        pro_id_no = findViewById(R.id.pro_id_no);
        pro_date_of_b = findViewById(R.id.pro_date_of_b);
        pro_gender = findViewById(R.id.pro_gender);
        pro_religion = findViewById(R.id.pro_religion);
        pro_nationality = findViewById(R.id.pro_nationality);

        BirthC_LL = findViewById(R.id.BirthC_LL);
        National_ID_LL = findViewById(R.id.National_ID_LL);
        pro_birthC = findViewById(R.id.pro_birthC);
        pro_National_ID = findViewById(R.id.pro_National_ID);

        pro_Father_Name = findViewById(R.id.pro_Father_Name);
        pro_Mother_Name = findViewById(R.id.pro_Mother_Name);

        pro_pre_Address = findViewById(R.id.pro_pre_Address);
        pro_per_Address = findViewById(R.id.pro_per_Address);

        pro_number = findViewById(R.id.pro_number);
        pro_alt_number = findViewById(R.id.pro_alt_number);
        pro_email = findViewById(R.id.pro_email);

        loading = findViewById(R.id.loading_p);

        animImage = findViewById(R.id.animImage);
        loading1 = findViewById(R.id.loading);
        id_sub = findViewById(R.id.id_sub);
        telephoneBtn = findViewById(R.id.telephoneBtn);
        emailBtn = findViewById(R.id.emailBtn);

        Intent intent = getIntent();
        if (intent != null) {
            profile_Link = intent.getStringExtra("img_link");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clickBtnText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (f % 2 == 0) {
                    textShowBtn.setImageResource(R.drawable.ic_arrow_down);
                    //clickBtnText.setBackgroundResource(R.drawable.rounded_corners_up_pro);
                    text_hide.setVisibility(View.VISIBLE);
                } else {
                    textShowBtn.setImageResource(R.drawable.ic_arrow_up);
                    //clickBtnText.setBackgroundResource(R.drawable.rounded_corners_pro);
                    text_hide.setVisibility(View.GONE);
                }
                f++;
            }
        });
        clickBtnText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f2 % 2 == 0) {
                    textShowBtn2.setImageResource(R.drawable.ic_arrow_down);
                    //clickBtnText2.setBackgroundResource(R.drawable.rounded_corners_up_pro);
                    text_hide2.setVisibility(View.VISIBLE);
                } else {
                    textShowBtn2.setImageResource(R.drawable.ic_arrow_up);
                    //clickBtnText2.setBackgroundResource(R.drawable.rounded_corners_pro);
                    text_hide2.setVisibility(View.GONE);
                }
                f2++;
            }
        });
        clickBtnText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f3 % 2 == 0) {
                    textShowBtn3.setImageResource(R.drawable.ic_arrow_down);
                    //clickBtnText3.setBackgroundResource(R.drawable.rounded_corners_up_pro);
                    text_hide3.setVisibility(View.VISIBLE);
                } else {
                    textShowBtn3.setImageResource(R.drawable.ic_arrow_up);
                    //clickBtnText3.setBackgroundResource(R.drawable.rounded_corners_pro);
                    text_hide3.setVisibility(View.GONE);
                }
                f3++;
            }
        });
        clickBtnText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f4 % 2 == 0) {
                    textShowBtn4.setImageResource(R.drawable.ic_arrow_down);
                    //clickBtnText4.setBackgroundResource(R.drawable.rounded_corners_up_pro);
                    text_hide4.setVisibility(View.VISIBLE);
                } else {
                    textShowBtn4.setImageResource(R.drawable.ic_arrow_up);
                    //clickBtnText4.setBackgroundResource(R.drawable.rounded_corners_pro);
                    text_hide4.setVisibility(View.GONE);
                }
                f4++;
            }
        });

        getData();
    }

    private void getData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/admin/profile")
                            .cookies(Cookies.getCookies(ProfileActivity.this))
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"tab-1\"")) {
                        Elements t = doc.select("table > tbody");

                        //profile_Link = t.get(0).select("img.img-responsive").attr("src");

                        InputStream input = null;
                        try {
                            input = (InputStream) new URL(profile_Link).getContent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(input);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.hide();

                                student_Profile_img.setImageBitmap(bitmap);
                                animImage.setImageBitmap(bitmap);
                                loading1.hide();
                                String name = t.get(1).select("tr").get(1).select("td").get(1).text();
                                profile_name.setText(name);

                                pro_id_no.setText(t.get(1).select("tr").get(0).select("td").get(1).text());
                                id_sub.setText(t.get(1).select("tr").get(0).select("td").get(1).text());
                                pro_date_of_b.setText(t.get(1).select("tr").get(2).select("td").get(1).text());

                                pro_nationality.setText(t.get(2).select("tr").get(4).select("td").get(1).text());

                                pro_Father_Name.setText(t.get(1).select("tr").get(3).select("td").get(1).text());
                                pro_Mother_Name.setText(t.get(2).select("tr").get(0).select("td").get(1).text());

                                pro_pre_Address.setText(t.get(1).select("tr").get(4).select("td").get(1).text());
                                pro_per_Address.setText(t.get(2).select("tr").get(1).select("td").get(1).text());

                                if (text.contains("id=\"birth_certificate\"")) {
                                    BirthC_LL.setVisibility(View.VISIBLE);
                                    National_ID_LL.setVisibility(View.VISIBLE);

                                    String gender = t.get(1).select("tr").get(8).select("td").get(1).text();
                                    pro_gender.setText(gender);
                                    pro_religion.setText(t.get(1).select("tr").get(9).select("td").get(1).text());

                                    number = t.get(1).select("tr").get(5).select("td").get(1).select("input").attr("value");
                                    pro_number.setText(number);
                                    telephoneBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:" + number));
                                            startActivity(intent);
                                        }
                                    });

                                    alt_number = t.get(1).select("tr").get(6).select("td").get(1).select("input").attr("value");
                                    pro_alt_number.setText(alt_number);

                                    email = t.get(1).select("tr").get(7).select("td").get(1).select("input").attr("value");
                                    emailBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                                            startActivity(Intent.createChooser(emailIntent, ""));
                                        }
                                    });

                                    pro_email.setText(email);

                                    birthC = t.get(2).select("tr").get(2).select("td").get(1).select("input").attr("value");
                                    pro_birthC.setText(birthC);
                                    nationalID = t.get(2).select("tr").get(3).select("td").get(1).select("input").attr("value");
                                    pro_National_ID.setText(nationalID);

                                    updateBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            BottomSheetDialog bottomSheet = new BottomSheetDialog(number, alt_number, email, birthC, nationalID, ProfileActivity.this);
                                            bottomSheet.show(getSupportFragmentManager(), "BottomSheetDialog");
                                        }
                                    });
                                } else {
                                    BirthC_LL.setVisibility(View.GONE);
                                    National_ID_LL.setVisibility(View.GONE);

                                    String gender = t.get(1).select("tr").get(6).select("td").get(1).text();
                                    pro_gender.setText(gender);
                                    pro_religion.setText(t.get(1).select("tr").get(7).select("td").get(1).text());

                                    number = t.get(1).select("tr").get(5).select("td").get(1).select("input").attr("value");
                                    pro_number.setText(number);
                                    alt_number = t.get(2).select("tr").get(2).select("td").get(1).select("input").attr("value");
                                    pro_alt_number.setText(alt_number);
                                    email = t.get(2).select("tr").get(3).select("td").get(1).select("input").attr("value");
                                    pro_email.setText(email);

                                    updateBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            BottomSheetDialog bottomSheet = new BottomSheetDialog(number, alt_number, email, ProfileActivity.this);
                                            bottomSheet.show(getSupportFragmentManager(), "BottomSheetDialog");
                                        }
                                    });
                                }


                                student_Profile_img.setOnClickListener(new View.OnClickListener() {
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

                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(ProfileActivity.this);
                        getData();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(ProfileActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProfileActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (isShowIMG == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorb));
            }
            image_full.setVisibility(View.GONE);
            isShowIMG = 0;
        } else {
            finish();
        }
    }

    @Override
    public void onButtonClicked(String text) {
        getData();
    }
}