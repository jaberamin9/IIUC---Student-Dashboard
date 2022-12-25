package com.blogspot.softlabsja.iiucstudentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.softlabsja.iiucstudentapp.About.AboutActivity;
import com.blogspot.softlabsja.iiucstudentapp.ForgetPassword.ForgetPasswordActivity;
import com.blogspot.softlabsja.iiucstudentapp.AddDrop.AddDropActivity;
import com.blogspot.softlabsja.iiucstudentapp.AllNotice.AllNoticeActivity;
import com.blogspot.softlabsja.iiucstudentapp.AllNotice.NoticeViewActivity;
import com.blogspot.softlabsja.iiucstudentapp.ChangePassword.ChangePasswordActivity;
import com.blogspot.softlabsja.iiucstudentapp.CourseRegistration.CourseRegistrationActivity;
import com.blogspot.softlabsja.iiucstudentapp.CourseStatus.CourseStatusActivity;
import com.blogspot.softlabsja.iiucstudentapp.FeeCalculator.FeeCalculatorActivity;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.LogIn;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.blogspot.softlabsja.iiucstudentapp.Profile.ProfileActivity;
import com.blogspot.softlabsja.iiucstudentapp.RegistrationSummary.RegistrationSummaryActivity;
import com.blogspot.softlabsja.iiucstudentapp.ResultView.ResultViewActivity;
import com.blogspot.softlabsja.iiucstudentapp.Table.MyTableViewAdapter;
import com.blogspot.softlabsja.iiucstudentapp.Table.TableViewListener;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.Cell;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.ColumnHeader;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.RowHeader;
import com.blogspot.softlabsja.iiucstudentapp.Ter.TerActivity;
import com.evrencoskun.tableview.TableView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;


public class MainActivity extends AppCompatActivity {

    LinearLayout loginBox, verifyBox, mainBox, ip_address_box;
    RelativeLayout ip_address_box2;
    EditText studentId, studentPass, verificationEt;
    TextView forget_password, ip_address_txt, ip_address_txt2;
    ImageView showHidePass;
    Button loginBtn, verify;
    String imgSrc;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static String userAgent;
    MyTableViewAdapter adapter;
    private AVLoadingIndicatorView loading;
    ContentLoadingProgressBar loadingBtn, loadingBtn2;
    CardView codeEditTextBox;
    ViewPager viewPager;
    FlowingDrawer mDrawer;
    NavigationView vNavigation;
    Toolbar toolbar;
    boolean click = false;

    private static final int REQ_USER_CONSENT = 200;
    //SmsBroadcastReceiver smsBroadcastReceiver;
    private InputMethodManager manager;
    SharedPreferences.Editor editor;
    LogIn logIn = new LogIn();

    FirebaseDatabase database;
    private FirebaseAuth mAuth;

    RelativeLayout shut_down, myEmail_M, updateNow;
    TextView shutDownTxt;


    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final Handler handler = new Handler();
    private Runnable runnable;

    TextView r1;
    String getDate;
    String time;
    String textT;
    int f = 1;

    LottieAnimationView hart, update_lotti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_coordinator_ntb);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        loginBox = findViewById(R.id.login_box);
        verifyBox = findViewById(R.id.verify_box);
        mainBox = findViewById(R.id.main_Box);
        loading = findViewById(R.id.loading);
        loadingBtn = findViewById(R.id.loadingBtn);
        loadingBtn2 = findViewById(R.id.loadingBtn2);
        loginBtn = findViewById(R.id.loginBtn);
        verify = findViewById(R.id.verify);
        studentId = findViewById(R.id.student_id);
        studentPass = findViewById(R.id.student_pass);
        verificationEt = findViewById(R.id.verificationEt);
        viewPager = findViewById(R.id.vp_horizontal_ntb);
        codeEditTextBox = findViewById(R.id.codeediteTextBox);
        shut_down = findViewById(R.id.shut_down);
        myEmail_M = findViewById(R.id.myEmail_M);
        shutDownTxt = findViewById(R.id.shutDownTxt);
        updateNow = findViewById(R.id.updateNow);
        showHidePass = findViewById(R.id.showHidePass);
        forget_password = findViewById(R.id.forget_password);

        ip_address_box = findViewById(R.id.ip_address_box);
        ip_address_txt = findViewById(R.id.ip_address_txt);

        ip_address_box2 = findViewById(R.id.ip_address_box2);
        ip_address_txt2 = findViewById(R.id.ip_address_txt2);

        hart = findViewById(R.id.hart);
        update_lotti = findViewById(R.id.update_lotti);
//---------------------------------------- Write a message to the database-----------------------------------------
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//-------------------------------------------------------------------------------------------------------------------

        shut_down.setVisibility(View.GONE);

//---------------------------------------------for change progressBar Color .white--------------------------------------------
        if (loadingBtn != null) {
            loadingBtn.setIndeterminate(true);
            loadingBtn.getIndeterminateDrawable().setColorFilter(0xCDC51162, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        if (loadingBtn2 != null) {
            loadingBtn2.setIndeterminate(true);
            loadingBtn2.getIndeterminateDrawable().setColorFilter(0xCDC51162, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
//-----------------------------------------------------------------------------------------------------------------------------

        mDrawer = findViewById(R.id.drawerlayout);
        vNavigation = findViewById(R.id.vNavigation);

        show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.backgroundColor));
        }


//----------------------------------------------------Firebase----------------------------------------------------------------------
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference reference = database.getReference("shutdown");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                    String value = dataSnapshot.getValue(String.class);
                                    if (value.equals("jaber")) {
                                        String versionName = BuildConfig.VERSION_NAME;
                                        DatabaseReference reference1 = database.getReference("version");
                                        reference1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                                String value = dataSnapshot.getValue(String.class);
                                                if (Float.parseFloat(value) <= Float.parseFloat(versionName)) {
                                                    myData();
                                                    shut_down.setVisibility(View.GONE);
                                                } else {
                                                    shut_down.setVisibility(View.VISIBLE);
                                                    hart.setVisibility(View.GONE);
                                                    update_lotti.setVisibility(View.VISIBLE);
                                                    shutDownTxt.setText("Please update the latest version\n current version: " + versionName + "\n latest version: " + value);
                                                    myEmail_M.setVisibility(View.GONE);
                                                    updateNow.setVisibility(View.VISIBLE);
                                                    hide();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NotNull DatabaseError error) {
                                                //Toast.makeText(MainActivity.this, error.toException() + "", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        shut_down.setVisibility(View.VISIBLE);
                                        hart.setVisibility(View.VISIBLE);
                                        update_lotti.setVisibility(View.GONE);
                                        shutDownTxt.setText("This app is temporarily shut down by the developer. If you have any query please contact us.");
                                        updateNow.setVisibility(View.GONE);
                                        myEmail_M.setVisibility(View.VISIBLE);
                                        hide();
                                    }
                                }

                                @Override
                                public void onCancelled(@NotNull DatabaseError error) {
                                    //Toast.makeText(MainActivity.this, error.toException() + "", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
//-----------------------------------------------------------------------------------------------------------------------------


        //startSmsUserConsent();

        userAgent = new WebView(this).getSettings().getUserAgentString();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setVisibility(View.GONE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });

        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });

        myEmail_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email", "jaberamin9@gmail.com");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Already copy the E-mail", Toast.LENGTH_SHORT).show();
            }
        });
        updateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // package name of the app
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });


        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Home")) {
                    Toast.makeText(MainActivity.this, "You are already Home tab", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getTitle().equals("Course Registration")) {
                    Intent intent = new Intent(MainActivity.this, CourseRegistrationActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("All Notice")) {
                    Intent intent = new Intent(MainActivity.this, AllNoticeActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Payment History")) {
                    Intent intent = new Intent(MainActivity.this, NoticeViewActivity.class);
                    intent.putExtra("check", "payHis");
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("About")) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Result View")) {
                    Intent intent = new Intent(MainActivity.this, ResultViewActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Registration Summary")) {
                    Intent intent = new Intent(MainActivity.this, RegistrationSummaryActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Change Password")) {
                    Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Course Status")) {
                    Intent intent = new Intent(MainActivity.this, CourseStatusActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Profile")) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("img_link", imgSrc);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Fee Calculator")) {
                    Intent intent = new Intent(MainActivity.this, FeeCalculatorActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Add/Drop")) {
                    Intent intent = new Intent(MainActivity.this, AddDropActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("TER")) {
                    Intent intent = new Intent(MainActivity.this, TerActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Log Out")) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Document logout = Jsoup.connect("https://www.iiuc.ac.bd/base/logout")
                                        .cookies(Cookies.getCookies(MainActivity.this))
                                        .get();
                                editor.putString("id", "");
                                editor.putString("pass", "");
                                editor.putString("cookies", "");
                                editor.putString("iiucwb_user_id", "");
                                editor.putString("iiucwb_user_verification", "");
                                editor.apply();

                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                } else {
                    Toast.makeText(MainActivity.this, "This Feature Is Coming Soon!", Toast.LENGTH_SHORT).show();
                }
                return false;

            }
        });

        studentPass.setTransformationMethod(new PasswordTransformationMethod());
        showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click) {
                    studentPass.setTransformationMethod(null);
                    showHidePass.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_hide));
                    studentPass.setSelection(studentPass.getText().length());
                    click = true;
                } else {
                    studentPass.setTransformationMethod(new PasswordTransformationMethod());
                    showHidePass.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_show));
                    studentPass.setSelection(studentPass.getText().length());
                    click = false;
                }
            }
        });
    }


//------------------------------------------For OTP-------------------------------------------
//    private void startSmsUserConsent() {
//        SmsRetrieverClient client = SmsRetriever.getClient(this);
//        //We can add sender phone number or leave it blank
//        // I'm adding null here
//        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                //Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                //Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQ_USER_CONSENT) {
//            if ((resultCode == RESULT_OK) && (data != null)) {
//                //That gives all message to us.
//                // We need to get the code from inside with regex
//                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
//                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                //textViewMessage.setText(String.format("%s - %s", getString(R.string.received_message), message));
//                getOtpFromMessage(message);
//            }
//        }
//    }

//    private void getOtpFromMessage(String message) {
//        // This will match any 6 digit number in the message
//        String messag = message.split("is ")[1];
//        verificationEt.setText(messag);
//
//    }

//    private void registerBroadcastReceiver() {
//        smsBroadcastReceiver = new SmsBroadcastReceiver();
//        smsBroadcastReceiver.smsBroadcastReceiverListener =
//                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
//                    @Override
//                    public void onSuccess(Intent intent) {
//                        startActivityForResult(intent, REQ_USER_CONSENT);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                    }
//                };
//        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
//        registerReceiver(smsBroadcastReceiver, intentFilter);
//    }
//---------------------------------------------------------------------------------------------


    @Override
    protected void onStart() {
        super.onStart();
        //registerBroadcastReceiver();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(smsBroadcastReceiver);
        handler.removeCallbacks(runnable);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initUI(Document dashboard) {

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 6;
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
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.page_1, null, false);

                    CircleImageView studentImg = view.findViewById(R.id.student_img);
                    TextView studentName = view.findViewById(R.id.student_name);
                    TextView studentIdText = view.findViewById(R.id.student_id);
                    TextView studentNumber = view.findViewById(R.id.student_number);
                    TextView studentDepName = view.findViewById(R.id.student_dep_name);
                    TextView cgpa = view.findViewById(R.id.cgpa);
                    TextView c_cgpa = view.findViewById(R.id.c_cgpa);
                    TextView credit = view.findViewById(R.id.credit);
                    TextView out_balance = view.findViewById(R.id.out_balance);
                    r1 = view.findViewById(R.id.r1);
                    TextView r2 = view.findViewById(R.id.r2);
                    TextView r3 = view.findViewById(R.id.r3);
                    TextView r4 = view.findViewById(R.id.r4);
                    CardView card1 = view.findViewById(R.id.card1);
                    CardView card3 = view.findViewById(R.id.card3);
                    RelativeLayout bc_p1 = view.findViewById(R.id.bc_p1);
                    CardView cd_4 = view.findViewById(R.id.cd_4);
                    TextView numberTxtN = view.findViewById(R.id.numberTxtN);


//*******************************************************For Student Profile Image*********************************************************

                    Elements img2 = dashboard.select(".sidebar-header");
                    for (int i = 0; i < img2.size(); i++) {
                        imgSrc = img2.get(i).select("img.img-responsive").attr("src");
                    }
                    editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("img", imgSrc);
                    editor.apply();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InputStream input = (InputStream) new URL(imgSrc).getContent();
                                Bitmap bitmap = BitmapFactory.decodeStream(input);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        studentImg.setImageBitmap(bitmap);
                                        studentImg.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                                intent.putExtra("img_link", imgSrc);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();


//*************************************************************END************************************************************************

//*******************************************************For Student Profile Name*********************************************************

                    String name = dashboard.select(".col-md-7 > h2").text();
//                    String mainName = name.substring(name.indexOf(">") + 1, name.lastIndexOf("<"));
                    studentName.setText(name);

                    String stdId = dashboard.select(".col-md-7 > table > tbody > tr > td").get(0).select("p").text();
//                    String mainName = name.substring(name.indexOf(">") + 1, name.lastIndexOf("<"));
                    if (stdId.contains(",")) {
                        stdId = stdId.replace(",", "\n");
                        studentIdText.setText(stdId);

                        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                        bc_p1.getLayoutParams().height = (int) (230 * scale + 0.5f);
                    } else {
                        studentIdText.setText(stdId);
                    }

//*************************************************************END************************************************************************

//*********************************************************For Student Number or Registration No*************************************************************
                    String txt = null, txt2 = null;
                    String reg_Num = dashboard.select(".col-md-7 > table > tbody > tr > td").get(1).select("p").text();
                    if (reg_Num != null) {
                        String number = dashboard.select(".biobox > p").toString();
                        String mainNumber = number.replaceAll("[^0-9]", "");
                        String n = mainNumber.substring(0, 11);

                        reg_Num = reg_Num.replace("Registration No:", "");
                        txt = "<b>Number: </b><br>" + n + "<br>" + "<b>Registration No:</b><br>" + reg_Num;
                        txt2 = "<b>Number: </b>" + n + "<br>" + "<b>Registration No:</b>" + reg_Num;
                        studentNumber.setText(Html.fromHtml(txt));
                    } else {
                        numberTxtN.setVisibility(View.VISIBLE);
                        String number = dashboard.select(".biobox > p").toString();
                        String mainNumber = number.replaceAll("[^0-9]", "");
                        txt = mainNumber.substring(0, 11);
                        txt2 = txt;
                        studentNumber.setText(txt);
                    }

                    String finalTxt = String.valueOf(Html.fromHtml(txt2));
                    studentNumber.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Number&Registration", finalTxt);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Number and Registration No. copied", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
//*************************************************************END************************************************************************

//*********************************************************For Department Name*************************************************************
                    String getstudentDeptName = null;
                    Elements deptName = dashboard.select(".col-md-2 > div");
                    for (int i = 0; i < deptName.size(); i++) {
                        if (i == 2) {
                            getstudentDeptName = deptName.get(i).select("div").text();
                            getstudentDeptName = getstudentDeptName.substring(14);
                            studentDepName.setText(getstudentDeptName);
                        }
                    }

                    String finalStdId = stdId;
                    String finalGetstudentDeptName = getstudentDeptName;
                    bc_p1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("All", "Name: " + name + "\n" + finalStdId + "\n" + finalTxt + "\nDepartment Name:" + finalGetstudentDeptName);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "This section copied all text.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

//*************************************************************END************************************************************************

//*********************************************************For Student CGPA*************************************************************
                    String cgp = null;
                    Elements cg = dashboard.select(".income-rate-total > h3");
                    for (int i = 0; i < cg.size(); i++) {
                        if (i == 0) {
                            cgp = cg.get(i).text();
                            cgpa.setText(cgp);
                        }
                    }
                    String cCgpa = dashboard.select(".income-rate-total > h3 > span").text();
                    c_cgpa.setText(cCgpa);

                    String crad = null;
                    Elements cra = dashboard.select(".price-adminpro-rate > h3 > span");
                    for (int i = 0; i < cra.size(); i++) {
                        if (i == 0) {
                            crad = cra.get(i).text();
                            credit.setText(crad);
                        }
                    }
                    String outBalance = dashboard.select(".price-adminpro-rate > h3 > span > span").text();
                    out_balance.setText(outBalance);

                    String finalCgp = cgp;
                    cgpa.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("cgpa", finalCgp + " Minimum Degree Requirement");
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Copied text.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    c_cgpa.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("cgpa", "Current CGPA " + cCgpa);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Copied text.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    String finalCrad = crad;
                    credit.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("cgpa", "Required Credit Hours " + finalCrad);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Copied text.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    out_balance.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("cgpa", "Outstanding Balance " + outBalance + " Tk");
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Copied text.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
//---------------------------------------Timer-------------------------------------------------
                    Elements scriptElements = dashboard.getElementsByTag("script");
                    for (Element element : scriptElements) {
                        if (element.data().contains("countDownDate")) {
                            Pattern pattern = Pattern.compile(".*countDownDate = ([^;]*);");
                            Matcher matcher = pattern.matcher(element.data());
                            if (matcher.find()) {
                                getDate = matcher.group(1);
                                getDate = getDate.replaceAll(".getTime\\(\\)", "");
                                getDate = getDate.substring(getDate.indexOf('\"') + 1, getDate.lastIndexOf('\"')).trim();
                                time = "06:00:00";
                            } else {
                                System.err.println("No match found!");
                            }
                            break;
                        }
                    }
//---------------------------------------------------------------------------------------------

                    Elements r13 = dashboard.select(".bg-warning > div");
                    for (int i = 0; i < r13.size(); i++) {
                        if (r13.size() == 2) {
                            if (i == 0) {
                                textT = r13.get(i).text();
                                if (!textT.equals("")) {
                                    r1.setText(textT);
                                    countDownStart(getDate + " " + time, r1, textT);
                                }
                            }
                            if (i == 1) {
                                String getr234 = r13.get(i).text();
                                if (!getr234.equals("")) {
                                    r3.setText(getr234);
                                }
                            }
                        } else {
                            card1.setVisibility(View.GONE);
                            if (i == 0) {
                                String getr234 = r13.get(i).text();
                                if (!getr234.equals("")) {
                                    r3.setText(getr234);
                                }
                            }
                        }
                    }


                    Elements r24 = dashboard.select(".bg-danger > div");
                    for (int i = 0; i < r24.size(); i++) {
                        if (i == 0) {
                            String getr234 = r24.get(i).text();
                            r2.setText(getr234);
                        }
                        if (i == 1) {
                            String getr234 = r24.get(i).text();
                            if (!getr234.equals("")) {
                                r4.setText(getr234);
                            }
                        } else {
                            cd_4.setVisibility(View.GONE);
                        }
                    }

//*************************************************************END************************************************************************

                    container.addView(view);
                    return view;
                } else if (position == 1) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.page_2, null, false);

                    TableView tableView = view.findViewById(R.id.content_container);
                    TextView testText = view.findViewById(R.id.testText);

                    adapter = new MyTableViewAdapter();
                    tableView.setAdapter(adapter);
                    tableView.setTableViewListener(new TableViewListener(tableView));

                    testText.setText(dashboard.select("#tab1default > h2").text());

                    Elements tab1default_data = dashboard.select("#tab1default tr");
                    List<RowHeader> list_RH = new ArrayList<>();
                    if (tab1default_data.size() < 15) {
                        for (int i = 0; i < 15; i++) {
                            RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
                            list_RH.add(header);
                        }
                    } else {
                        for (int i = 0; i < tab1default_data.size() - 1; i++) {
                            RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
                            list_RH.add(header);
                        }
                    }

                    List<ColumnHeader> list_Ch = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        String title = "------------" + (i + 1) + "------------";
                        int nRandom = new Random().nextInt();
                        if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                            title = "------------" + (i + 1) + "------------";
                        }

                        ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                        list_Ch.add(header);
                    }


                    List<List<Cell>> list = new ArrayList<>();
                    Elements length = dashboard.select("#tab1default tr");
                    if (tab1default_data.size() < 15) {
                        for (int i = 0; i < 16 - 1; i++) {
                            if (i >= 0 && i < 9) {
                                List<Cell> cellList = new ArrayList<>();
                                Element tab1default = dashboard.select("#tab1default > .table > tbody > tr").get(i);
                                for (int j = 0; j < 2; j++) {
                                    Elements ro = tab1default.select("td");
                                    Object text = ro.get(j).text();
                                    String id = j + "-" + i;
                                    Cell cell;
                                    cell = new Cell(id, text);
                                    cellList.add(cell);
                                }
                                list.add(cellList);
                            } else {
                                List<Cell> cellList = new ArrayList<>();
                                for (int j = 0; j < 2; j++) {
                                    Object text = "";
                                    String id = j + "-" + i;
                                    Cell cell;
                                    cell = new Cell(id, text);
                                    cellList.add(cell);
                                }
                                list.add(cellList);
                            }
                        }
                    } else {
                        for (int i = 0; i < length.size() - 1; i++) {
                            List<Cell> cellList = new ArrayList<>();
                            Element tab1default = dashboard.select("#tab1default > .table > tbody > tr").get(i);
                            for (int j = 0; j < 2; j++) {
                                Elements ro = tab1default.select("td");
                                Object text = ro.get(j).text();
                                String id = j + "-" + i;
                                Cell cell;
                                cell = new Cell(id, text);
                                cellList.add(cell);
                            }
                            list.add(cellList);
                        }
                    }

                    adapter.setAllItems(list_Ch, list_RH, list);

                    container.addView(view);
                    return view;
                } else if (position == 2) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.page_3, null, false);

                    TableView tableView = view.findViewById(R.id.content_container);
                    TextView testText = view.findViewById(R.id.testText);

                    adapter = new MyTableViewAdapter();
                    tableView.setAdapter(adapter);
                    tableView.setTableViewListener(new TableViewListener(tableView));

                    Elements c24 = dashboard.select("#tab-4 tr");
                    List<RowHeader> list_RH = new ArrayList<>();
                    if (c24.size() < 15) {
                        for (int i = 0; i < 15; i++) {
                            RowHeader header = new RowHeader(String.valueOf(i), "" + (i + 1));
                            list_RH.add(header);
                        }
                    } else {
                        for (int i = 0; i < c24.size(); i++) {
                            RowHeader header = new RowHeader(String.valueOf(i), "" + (i + 1));
                            list_RH.add(header);
                        }
                    }

                    List<ColumnHeader> list_Ch = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        String title = (i + 1) + "";
                        ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                        list_Ch.add(header);
                    }


                    List<List<Cell>> list = new ArrayList<>();
                    Elements length = dashboard.select("#tab-4 tr");

                    if (length.size() < 15) {
                        for (int i = 0; i < 15; i++) {
                            if (i >= 0 && i < 5) {
                                List<Cell> cellList = new ArrayList<>();
                                Element tab1default = dashboard.select("#tab-4 > .table > tbody > tr").get(i);
                                for (int j = 0; j < 2; j++) {
                                    Elements ro = tab1default.select("td");
                                    Object text = ro.get(j).text();
                                    String id = j + "-" + i;
                                    Cell cell;
                                    cell = new Cell(id, text);
                                    cellList.add(cell);
                                }
                                list.add(cellList);
                            } else {
                                List<Cell> cellList = new ArrayList<>();
                                for (int j = 0; j < 2; j++) {
                                    Object text = "";
                                    String id = j + "-" + i;
                                    Cell cell;
                                    cell = new Cell(id, text);
                                    cellList.add(cell);
                                }
                                list.add(cellList);
                            }
                        }
                    } else {
                        for (int i = 0; i < length.size(); i++) {
                            List<Cell> cellList = new ArrayList<>();
                            Element tab1default = dashboard.select("#tab-4 > .table > tbody > tr").get(i);
                            for (int j = 0; j < 2; j++) {
                                Elements ro = tab1default.select("td");
                                Object text = ro.get(j).text();
                                String id = j + "-" + i;
                                Cell cell;
                                cell = new Cell(id, text);
                                cellList.add(cell);
                            }
                            list.add(cellList);
                        }
                    }

                    adapter.setAllItems(list_Ch, list_RH, list);
                    container.addView(view);
                    return view;
                } else if (position == 3) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.page_4, null, false);

                    TableView tableView = view.findViewById(R.id.content_container);
                    TextView testText = view.findViewById(R.id.testText);

                    adapter = new MyTableViewAdapter();
                    tableView.setAdapter(adapter);
                    tableView.setTableViewListener(new TableViewListener(tableView));

                    Elements th = dashboard.select("#tab-1 tr th");
                    List<ColumnHeader> list_Ch = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        String title = th.get(i).text();
                        int nRandom = new Random().nextInt();
                        if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                            title = th.get(i).text();
                        }

                        ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                        list_Ch.add(header);
                    }

                    List<List<Cell>> list = null;
                    List<RowHeader> list_RH = null;
                    Elements tab_1 = dashboard.select("#tab-1 > .tables > tbody > tr");
                    String isNull = tab_1.text();
                    if (!isNull.equals("")) {

                        Elements c24 = dashboard.select("#tab-1 th");
                        list_RH = new ArrayList<>();
                        for (int i = 0; i < c24.size(); i++) {
                            RowHeader header = new RowHeader(String.valueOf(i), "" + (i + 1));
                            list_RH.add(header);
                        }

                        list = new ArrayList<>();
                        Elements length = dashboard.select("#tab-1 tr");
                        for (int i = 0; i < length.size() - 1; i++) {
                            List<Cell> cellList = new ArrayList<>();
                            Element tab1default = dashboard.select("#tab-1 > .tables > tbody > tr").get(i);
                            for (int j = 0; j < 2; j++) {
                                Elements ro = tab1default.select("td");
                                Object text = ro.get(j).text();
                                String id = j + "-" + i;
                                Cell cell;
                                cell = new Cell(id, text);
                                cellList.add(cell);
                            }
                            list.add(cellList);
                        }
                    } else {
                        list_RH = new ArrayList<>();
                        for (int i = 0; i < 15; i++) {
                            RowHeader header = new RowHeader(String.valueOf(i), "" + (i + 1));
                            list_RH.add(header);
                        }

                        list = new ArrayList<>();
                        for (int i = 0; i < 15; i++) {
                            List<Cell> cellList = new ArrayList<>();
                            if (i == 0) {
                                for (int j = 0; j < 6; j++) {
                                    Object text = "null";
                                    String id = j + "-" + i;
                                    Cell cell;
                                    cell = new Cell(id, text);
                                    cellList.add(cell);
                                }
                                list.add(cellList);
                            } else {
                                for (int j = 0; j < 6; j++) {
                                    Object text = "";
                                    String id = j + "-" + i;
                                    Cell cell;
                                    cell = new Cell(id, text);
                                    cellList.add(cell);
                                }
                                list.add(cellList);
                            }
                        }
                    }
                    adapter.setAllItems(list_Ch, list_RH, list);
                    container.addView(view);
                    return view;
                } else if (position == 4) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.page_5, null, false);

                    TextView testText = view.findViewById(R.id.testText);
                    TextView advisorText = view.findViewById(R.id.advisorText);

                    testText.setText(dashboard.select("#tab-2 > h2").text());
                    String tab_2 = dashboard.select("#tab-2 p").html();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        advisorText.setText(Html.fromHtml(tab_2, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        advisorText.setText(Html.fromHtml(tab_2));
                    }

                    advisorText.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("AdvisorNumber", tab_2.replaceAll("\\D+", ""));
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Advisor number copied.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    container.addView(view);
                    return view;
                } else if (position == 5) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.page_6, null, false);

                    TableView tableView = view.findViewById(R.id.content_container);
                    TextView testText = view.findViewById(R.id.testText);

                    adapter = new MyTableViewAdapter();
                    tableView.setAdapter(adapter);
                    tableView.setTableViewListener(new TableViewListener(tableView));

                    testText.setText(dashboard.select("#tab-3 > b").text());

                    Elements rowLength = dashboard.select("#tab-3 tr");
                    List<RowHeader> list_RH = new ArrayList<>();
                    for (int i = 0; i < rowLength.size() - 1; i++) {
                        RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
                        list_RH.add(header);
                    }

                    Elements th = dashboard.select("#tab-3 tr th");
                    List<ColumnHeader> list_Ch = new ArrayList<>();
                    for (int i = 1; i < 6; i++) {
                        String title = th.get(i).text();
                        int nRandom = new Random().nextInt();
                        if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                            title = th.get(i).text();
                        }

                        ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                        list_Ch.add(header);
                    }


                    List<List<Cell>> list = new ArrayList<>();
                    Elements length = dashboard.select("#tab-3 tr");
                    for (int i = 0; i < length.size() - 1; i++) {
                        List<Cell> cellList = new ArrayList<>();
                        Element c24 = dashboard.select("#tab-3 > #tables > tbody > tr").get(i);
                        for (int j = 1; j < 6; j++) {
                            Elements ro = c24.select("td");
                            Object text = ro.get(j).text();
                            String id = j + "-" + i;
                            Cell cell;
                            cell = new Cell(id, text);
                            cellList.add(cell);
                        }
                        list.add(cellList);
                    }
                    adapter.setAllItems(list_Ch, list_RH, list);
                    container.addView(view);
                    return view;
                }

                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview_my);

        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_home),
                        Color.parseColor(colors[0]))
                        .title("Home")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_overview),
                        Color.parseColor(colors[1]))
                        .title("Overview")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_contact),
                        Color.parseColor(colors[2]))
                        .title("Contact")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_academic),
                        Color.parseColor(colors[3]))
                        .title("Academic")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_advisor),
                        Color.parseColor(colors[4]))
                        .title("Advisor")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_syllabus),
                        Color.parseColor(colors[5]))
                        .title("Syllabus")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
    }

    public void getData(Document dashboard) {
        initUI(dashboard);
        verifyBox.setVisibility(View.GONE);
        mainBox.setVisibility(View.VISIBLE);
    }

    public void login() {
        loginBtn.setEnabled(true);
        loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBtn.show();
                loginBtn.setTextColor(Color.parseColor("#8effffff"));

                if (studentId.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter Your ID", Toast.LENGTH_SHORT).show();
                    loadingBtn.hide();
                    loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                } else if (studentPass.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter Your Password", Toast.LENGTH_SHORT).show();
                    loadingBtn.hide();
                    loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                } else {
                    loginBtn.setEnabled(false);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Document doc = logIn.SinIn(studentId.getText().toString().trim(), studentPass.getText().toString().trim(), MainActivity.this);
                            String text = doc.html();
                            boolean found = text.contains("verification_code");
                            if (found) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginBox.setVisibility(View.GONE);
                                        codeEditTextBox.setVisibility(View.VISIBLE);
                                        verifyBox.setVisibility(View.VISIBLE);
                                        loadingBtn.hide();
                                        setVerification();
                                    }
                                });
                            } else if (text.contains("user_id")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingBtn.hide();
                                        loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                                        showMesseg("User ID or Password does not match!!");
                                        ip_address_box.setVisibility(View.GONE);
                                        editor.putString("id", "");
                                        editor.putString("pass", "");
                                        editor.putString("cookies", "");
                                        editor.apply();
                                        loginBtn.setEnabled(true);
                                    }
                                });
                            } else if (text.contains("Service not available in your country")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingBtn.hide();
                                        loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                                        ip_address_box.setVisibility(View.VISIBLE);
                                        ip_address_txt.setText(doc.select("p").get(5).text().replace("(We will store your information)", ""));
                                        editor.putString("id", "");
                                        editor.putString("pass", "");
                                        editor.putString("cookies", "");
                                        editor.apply();
                                        loginBtn.setEnabled(true);
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Element error = doc.getElementById("jaber_error");
                                        loadingBtn.hide();
                                        ip_address_box.setVisibility(View.GONE);
                                        loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                                        showMesseg(error.text());
                                        editor.putString("id", "");
                                        editor.putString("pass", "");
                                        editor.putString("cookies", "");
                                        editor.apply();
                                        loginBtn.setEnabled(true);
                                    }
                                });
                            }
                        }
                    });
                    thread.start();
                }
            }
        });
        studentPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (manager.isActive()) {
                        manager.hideSoftInputFromWindow(studentPass.getApplicationWindowToken(), 0);
                    }
                    loadingBtn.show();
                    loginBtn.setTextColor(Color.parseColor("#8effffff"));

                    if (studentId.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Enter Your ID", Toast.LENGTH_SHORT).show();
                        loadingBtn.hide();
                        loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                    } else if (studentPass.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Enter Your Password", Toast.LENGTH_SHORT).show();
                        loadingBtn.hide();
                        loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                    } else {
                        loginBtn.setEnabled(false);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Document doc = logIn.SinIn(studentId.getText().toString().trim(), studentPass.getText().toString(), MainActivity.this);
                                String text = doc.html();
                                boolean found = text.contains("verification_code");
                                if (found) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loginBox.setVisibility(View.GONE);
                                            codeEditTextBox.setVisibility(View.VISIBLE);
                                            verifyBox.setVisibility(View.VISIBLE);
                                            loadingBtn.hide();
                                            setVerification();
                                        }
                                    });
                                } else if (text.contains("user_id")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingBtn.hide();
                                            loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                                            showMesseg("User ID or Password does not match!!");
                                            ip_address_box.setVisibility(View.GONE);
                                            editor.putString("id", "");
                                            editor.putString("pass", "");
                                            editor.putString("cookies", "");
                                            editor.apply();
                                            loginBtn.setEnabled(true);
                                        }
                                    });
                                } else if (text.contains("Service not available in your country")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingBtn.hide();
                                            loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                                            ip_address_box.setVisibility(View.VISIBLE);
                                            ip_address_txt.setText(doc.select("p").get(5).text().replace("(We will store your information)", ""));
                                            editor.putString("id", "");
                                            editor.putString("pass", "");
                                            editor.putString("cookies", "");
                                            editor.apply();
                                            loginBtn.setEnabled(true);
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Element error = doc.getElementById("jaber_error");
                                            loadingBtn.hide();
                                            ip_address_box.setVisibility(View.GONE);
                                            loginBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                                            showMesseg(error.text());
                                            editor.putString("id", "");
                                            editor.putString("pass", "");
                                            editor.putString("cookies", "");
                                            editor.apply();
                                            loginBtn.setEnabled(true);
                                        }
                                    });
                                }
                            }
                        });
                        thread.start();
                    }
                    studentPass.clearFocus();
                }
                return false;
            }
        });
    }

    public void setVerification() {
        verify.setText("verify");
        verificationEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (manager.isActive()) {
                        manager.hideSoftInputFromWindow(verificationEt.getApplicationWindowToken(), 0);
                    }

                    if (verificationEt.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Enter The Verification Code", Toast.LENGTH_SHORT).show();
                    } else {
                        verify.setEnabled(false);
                        loadingBtn2.show();
                        verify.setTextColor(Color.parseColor("#FFFFFFFF"));
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Document dashboard = logIn.verification(verificationEt.getText().toString(), MainActivity.this);

                                String word = "img-responsive";
                                String text = dashboard.html();
                                boolean found = text.contains(word);
                                if (found) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                Window window = getWindow();
                                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                                window.setStatusBarColor(getResources().getColor(R.color.colorb));
                                            }
                                            toolbar.setVisibility(View.VISIBLE);
                                            toolbar.setNavigationIcon(R.drawable.ic_menu);
                                            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                                            loadingBtn2.hide();
                                            verifyBox.setVisibility(View.GONE);

                                            getData(dashboard);
                                            verify.setEnabled(true);
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingBtn2.hide();
                                            verify.setTextColor(Color.parseColor("#FFFFFFFF"));
                                            showMesseg("Verification Failed");
                                            verificationEt.getText().clear();
                                            editor.putString("id", "");
                                            editor.putString("pass", "");
                                            editor.putString("cookies", "");
                                            editor.apply();
                                            codeEditTextBox.setVisibility(View.GONE);
                                            verify.setText("Login Again!");
                                            verify.setEnabled(true);
                                            verify.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    verifyBox.setVisibility(View.GONE);
                                                    loginBox.setVisibility(View.VISIBLE);
                                                    login();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                        thread.start();
                    }
                    verificationEt.clearFocus();
                }
                return false;
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificationEt.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter The Verification Code", Toast.LENGTH_SHORT).show();
                } else {
                    verify.setEnabled(false);
                    loadingBtn2.show();
                    verify.setTextColor(Color.parseColor("#FFFFFFFF"));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Document dashboard = logIn.verification(verificationEt.getText().toString(), MainActivity.this);

                            String word = "img-responsive";
                            String text = dashboard.html();
                            boolean found = text.contains(word);
                            if (found) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            Window window = getWindow();
                                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                            window.setStatusBarColor(getResources().getColor(R.color.colorb));
                                        }
                                        toolbar.setVisibility(View.VISIBLE);
                                        toolbar.setNavigationIcon(R.drawable.ic_menu);
                                        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                                        loadingBtn2.hide();
                                        verifyBox.setVisibility(View.GONE);

                                        getData(dashboard);
                                        verify.setEnabled(true);
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingBtn2.hide();
                                        verify.setTextColor(Color.parseColor("#FFFFFFFF"));
                                        showMesseg("Verification Failed");
                                        verificationEt.getText().clear();
                                        editor.putString("id", "");
                                        editor.putString("pass", "");
                                        editor.putString("cookies", "");
                                        editor.apply();
                                        codeEditTextBox.setVisibility(View.GONE);
                                        verify.setText("Login Again!");
                                        verify.setEnabled(true);
                                        verify.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                verifyBox.setVisibility(View.GONE);
                                                loginBox.setVisibility(View.VISIBLE);
                                                login();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                    thread.start();
                }
            }
        });
    }

    public void myData() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String id = prefs.getString("id", "");
        String iiucwb_user_verification = prefs.getString("iiucwb_user_verification", "");

        if (id.equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorb2));
            }
            loginBox.setVisibility(View.VISIBLE);
            verifyBox.setVisibility(View.GONE);
            mainBox.setVisibility(View.GONE);
            toolbar.setNavigationIcon(null);
            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE);
            hide();

            login();
        } else {

            loginBox.setVisibility(View.GONE);
            verifyBox.setVisibility(View.GONE);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document deash = Jsoup.connect("https://www.iiuc.ac.bd/index.php/dashboard/")
                                .cookies(Cookies.getCookies(MainActivity.this))
                                .get();
                        String word = "img-responsive";
                        String text = deash.html();
                        boolean found = text.contains(word);
                        if (found) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Window window = getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.setStatusBarColor(getResources().getColor(R.color.colorb));
                                    }
                                    toolbar.setVisibility(View.VISIBLE);
                                    mainBox.setVisibility(View.VISIBLE);
                                    toolbar.setNavigationIcon(R.drawable.ic_menu);
                                    mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                                    hide();
                                    //Toast.makeText(MainActivity.this, "Without Verification", Toast.LENGTH_SHORT).show();
                                    getData(deash);
                                }
                            });

                        } else if (text.contains("Service not available in your country")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hide();
                                    ip_address_box2.setVisibility(View.VISIBLE);
                                    ip_address_txt2.setText(deash.select("p").get(5).text().replace("(We will store your information)", ""));
                                }
                            });
                        } else {
                            if (!iiucwb_user_verification.equals("")) {
                                UpdateSession updateSession = new UpdateSession();
                                Document doc = updateSession.Update(MainActivity.this);

                                String word2 = "img-responsive";
                                String text2 = doc.html();
                                Boolean found2 = text2.contains(word2);
                                if (found2) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                Window window = getWindow();
                                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                                window.setStatusBarColor(getResources().getColor(R.color.colorb));
                                            }
                                            toolbar.setVisibility(View.VISIBLE);
                                            mainBox.setVisibility(View.VISIBLE);
                                            toolbar.setNavigationIcon(R.drawable.ic_menu);
                                            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                                            hide();
                                            //Toast.makeText(MainActivity.this, "Login Without Verification", Toast.LENGTH_SHORT).show();
                                            getData(doc);
                                        }
                                    });

                                } else if (text.contains("user_id")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showMesseg("Failed to connect to www.iiuc.ac.bd");
                                            System.out.println(doc);
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Element error = doc.getElementById("jaber_error");
                                            showMesseg(error.text());
                                        }
                                    });
                                }
                            } else {
                                showMesseg("Error");
                            }
                        }
                    } catch (IOException e) {
                        showMesseg("Failed to connect to www.iiuc.ac.bd\n" + e);
                    }
                }
            });
            thread.start();
        }

    }

    public void hide() {
        loading.hide();
        f = 1;
    }

    public void show() {
        loading.show();
        f = 0;
    }

    public void showMesseg(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (f == 1) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    countDownStart(getDate + " " + time, r1, textT);
                }
            }, 5000);
        }
        super.onResume();
    }

    private void countDownStart(String EVENT_DATE_TIME, TextView r1, String s) {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        r1.setText(s + " " + String.format("%02d", Days) + "Days " + String.format("%02d", Hours) + "H " + String.format("%02d", Minutes) + "M " + String.format("%02d", Seconds) + "S");
                        //System.out.println(String.format("%02d", Seconds));
                    } else {
                        r1.setText(s + " EXPIRED");
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
}