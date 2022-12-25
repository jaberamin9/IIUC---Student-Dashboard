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

public class UpdateSession {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public UpdateSession() {
    }

    public Document Update(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String id = prefs.getString("id", "");
        String pass = prefs.getString("pass", "");
        String iiucwb_user_id = prefs.getString("iiucwb_user_id", "");
        String iiucwb_user_verification = prefs.getString("iiucwb_user_verification", "");

        RunnableFuture f = new FutureTask(new Callable<Document>() {
            @Override
            public Document call() throws Exception {
                Connection.Response loginForm = Jsoup.connect("https://www.iiuc.ac.bd/login")
                        .method(Connection.Method.GET)
                        .userAgent(userAgent)
                        .referrer("http://www.google.com")
                        .execute();
                //System.out.println("Update: "+userAgent);
                //System.out.println("Old Cookies: " + Cookies.getCookies(context));

                Map<String, String> cookies = loginForm.cookies();
                cookies.put("iiucwb_user_id", iiucwb_user_id);
                cookies.put("iiucwb_user_verification", iiucwb_user_verification);
                JSONObject jsonObject = new JSONObject(cookies);
                String jsonString = jsonObject.toString();

                SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("cookies", jsonString);
                editor.apply();

                //System.out.println("New Cookies: " + jsonString);

                Document responseDocument = loginForm.parse();
                Element eventValidation = responseDocument.select("input[name=csrf_iiuc_web]").first();

                Connection.Response d = Jsoup.connect("https://www.iiuc.ac.bd/login/logining")
                        .data("csrf_iiuc_web", eventValidation.attr("value"))
                        .data("user_id", id)
                        .data("user_password", pass)
                        .data("submit", "Login")
                        .method(Connection.Method.POST)
                        .cookies(Cookies.getCookies(context))
                        .execute();

                Document document = Jsoup.connect("https://www.iiuc.ac.bd/index.php/dashboard/")
                        .cookies(Cookies.getCookies(context))
                        .get();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            String html = "<html><head><title>First parse</title></head><body><p id = \"jaber_error\">" + e.toString() + "</p></body></html>";
            s = Jsoup.parse(html);
        }
        return s;
    }
}
