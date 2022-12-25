package com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import static android.content.Context.MODE_PRIVATE;
import static com.blogspot.softlabsja.iiucstudentapp.MainActivity.userAgent;

public class LogIn {
    public LogIn() {
    }

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Element eventValidation;
    Connection.Response loginForm = null;
    String tempStudentId, tempStudentPass;

    public Document SinIn(String studentId, String studentPass, Context context) {

        RunnableFuture f = new FutureTask(new Callable<Document>() {
            @Override
            public Document call() throws Exception {
                loginForm = Jsoup.connect("https://www.iiuc.ac.bd/login")
                        .method(Connection.Method.GET)
                        .userAgent(userAgent)
                        .referrer("http://www.google.com")
                        .execute();
                Document responseDocument = loginForm.parse();
                eventValidation = responseDocument.select("input[name=csrf_iiuc_web]").first();
                Document document;
                if (eventValidation != null) {
                    //System.out.println("Login: "+userAgent);
                    Connection.Response d = Jsoup.connect("https://www.iiuc.ac.bd/login/logining")
                            .data("csrf_iiuc_web", eventValidation.attr("value"))
                            .data("user_id", studentId)
                            .data("user_password", studentPass)
                            .data("submit", "Login")
                            .method(Connection.Method.POST)
                            .cookies(loginForm.cookies())
                            .followRedirects(true)
                            .execute();

                    Map<String, String> cookies = loginForm.cookies();
                    JSONObject jsonObject = new JSONObject(cookies);
                    String jsonString = jsonObject.toString();

                    tempStudentId = studentId;
                    tempStudentPass = studentPass;

                    SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("cookies", jsonString);
                    editor.apply();

                    document = Jsoup.connect("https://www.iiuc.ac.bd/index.php/login/verification")
                            .cookies(loginForm.cookies())
                            .get();
                } else {
                    document = Jsoup.connect("https://www.iiuc.ac.bd/login")
                            .cookies(loginForm.cookies())
                            .get();
                }

                return document;
            }
        });
        new Thread(f).start();

        Document s = null;
        try {
            s = (Document) f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            String html = "<html><head><title>First parse</title></head><body><p id = \"jaber_error\">" + e.toString() + "</p></body></html>";
            s = Jsoup.parse(html);
            System.out.println("lo s: " + html);
            System.out.println("lo D: " + s.text());
        } catch (InterruptedException e) {
            e.printStackTrace();
            String html = "<html><head><title>First parse</title></head><body><p id = \"jaber_error\">" + e.toString() + "</p></body></html>";
            s = Jsoup.parse(html);
            System.out.println("lo s: " + html);
            System.out.println("lo D: " + s.text());
        }
        return s;
    }

    public Document verification(String verificationEt, Context context) {

        RunnableFuture f = new FutureTask(new Callable<Document>() {
            @Override
            public Document call() throws Exception {

                Connection.Response verification = Jsoup.connect("https://www.iiuc.ac.bd/login/verification")
                        .data("csrf_iiuc_web", eventValidation.attr("value"))
                        .data("verification_code", verificationEt)
                        .data("submit", "Login")
                        .method(Connection.Method.POST)
                        .cookies(loginForm.cookies())
                        .execute();

                Document doc = Jsoup.connect("https://www.iiuc.ac.bd/index.php/dashboard/")
                        .cookies(loginForm.cookies())
                        .get();


                //System.out.println("Cookie verification: \n" + verification.cookies());

                String s = String.valueOf(verification.cookies());
                String user_id = s.split(", ")[1];
                String user_verification = s.split(", ")[2];
                String iiucwb_user_id = user_id.split("=")[1];
                String iiucwb_user_verification = user_verification.split("=")[1];
                iiucwb_user_verification = iiucwb_user_verification.replace("}", "");

                //System.out.println("Cookie Login: \n" + loginForm.cookies());

                Map<String, String> cookies = loginForm.cookies();
                cookies.put("iiucwb_user_id", iiucwb_user_id);
                cookies.put("iiucwb_user_verification", iiucwb_user_verification);
                JSONObject jsonObject = new JSONObject(cookies);
                String jsonString = jsonObject.toString();

                //System.out.println("Cookie Update: \n" + loginForm.cookies());

                SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("id", tempStudentId);
                editor.putString("pass", tempStudentPass);
                editor.putString("cookies", jsonString);
                editor.putString("iiucwb_user_id", iiucwb_user_id);
                editor.putString("iiucwb_user_verification", iiucwb_user_verification);
                editor.apply();

                return doc;
            }
        });
        new Thread(f).start();

        Document s = null;
        try {
            s = (Document) f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            String html = "<html><head><title>First parse</title></head><body><p id = \"jaber_error\">" + e.toString() + "</p></body></html>";
            s = Jsoup.parse(html);
        } catch (InterruptedException e) {
            e.printStackTrace();
            String html = "<html><head><title>First parse</title></head><body><p id = \"jaber_error\">" + e.toString() + "</p></body></html>";
            s = Jsoup.parse(html);
        }
        return s;
    }
}
