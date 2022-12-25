package com.blogspot.softlabsja.iiucstudentapp;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public final class Utils {

    private Utils() {
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            File myDir = new File(root);
            if (!myDir.exists()) {
                myDir.getParentFile().mkdirs();
            }

            File file = new File(String.valueOf(myDir));
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }
}
