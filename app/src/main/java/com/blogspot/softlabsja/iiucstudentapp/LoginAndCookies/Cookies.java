package com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Cookies {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    Cookies() {
    }

    public static Map<String, String> getCookies(Context context) {
        Map<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        try {
            if (prefs != null) {
                String jsonString = prefs.getString("cookies", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }
}
