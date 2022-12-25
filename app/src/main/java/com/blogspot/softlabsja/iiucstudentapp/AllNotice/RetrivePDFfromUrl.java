package com.blogspot.softlabsja.iiucstudentapp.AllNotice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.net.ssl.HttpsURLConnection;

import static com.blogspot.softlabsja.iiucstudentapp.Utils.getRootDirPath;


class RetrivePDFfromUrl extends AsyncTask<String, Integer, InputStream> {

    private final Context context;
    private final ProgressDialog progressDialog;
    private PowerManager.WakeLock mWakeLock;
    private final PDFView pdfView;
    private final String check;

    private static final double SPACE_KB = 1024;
    private static final double SPACE_MB = 1024 * SPACE_KB;
    private static final double SPACE_GB = 1024 * SPACE_MB;
    private static final double SPACE_TB = 1024 * SPACE_GB;


    public RetrivePDFfromUrl(Context context, ProgressDialog progressDialog, PDFView pdfView, String check) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.pdfView = pdfView;
        this.check = check;
    }

    @Override
    protected InputStream doInBackground(String... strings) {
        // we are using inputstream
        // for getting out PDF.
        InputStream inputStream = null;
        InputStream input = null;
        HttpURLConnection connection = null;
//------------------------for Cookies-----------------------
        String s = String.valueOf(Cookies.getCookies(context));
        StringBuilder sb = new StringBuilder(s);
        sb.deleteCharAt(s.length() - 1);
        sb.deleteCharAt(0);
        s = String.valueOf(sb);
        s = s.replaceAll("\\s", "");
        s = s.replaceAll(",", ";");
//----------------------------------------------------------
        try {

            URL url = new URL(strings[0]);
            // below is the step where we are
            // creating our connection.
            connection = (HttpURLConnection) url.openConnection();
            if (check == null)
                connection.addRequestProperty("Cookie", s);
            connection.connect();
            HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            if (check == null)
                urlConnection.addRequestProperty("Cookie", s);
            if (urlConnection.getResponseCode() == 200) {
                // response is success.
                // we are getting input stream from url
                // and storing it in our variable.

                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        inputStream.close();
                        return null;
                    }

                    total += count;
                    progressDialog.setProgressNumberFormat((bytes2String(total)) + "/" + (bytes2String(fileLength)));
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                }
            }

        } catch (IOException e) {
            // this is the method
            // to handle errors.
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException ignored) {

            }
            if (connection != null)
                connection.disconnect();
        }
        return inputStream;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        //progressDialog.show();
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // if we get here, length is known, now set indeterminate to false
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        // after the execution of our async
        // task we are loading our pdf in our pdf view.
        mWakeLock.release();
        pdfView.fromStream(inputStream)
                .enableDoubletap(true)
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        progressDialog.dismiss();
                    }
                })
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                })

                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        t.printStackTrace();
                    }
                })
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages) {

                    }
                })
                .spacing(10)
                //.autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(true) // snap pages to screen boundaries
                .nightMode(false) // toggle night mode
                .load();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(InputStream inputStream) {
        super.onCancelled(inputStream);
    }

    public static String bytes2String(long sizeInBytes) {

        NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);

        try {
            if (sizeInBytes < SPACE_KB) {
                return nf.format(sizeInBytes) + " Byte(s)";
            } else if (sizeInBytes < SPACE_MB) {
                return nf.format(sizeInBytes / SPACE_KB) + " KB";
            } else if (sizeInBytes < SPACE_GB) {
                return nf.format(sizeInBytes / SPACE_MB) + " MB";
            } else if (sizeInBytes < SPACE_TB) {
                return nf.format(sizeInBytes / SPACE_GB) + " GB";
            } else {
                return nf.format(sizeInBytes / SPACE_TB) + " TB";
            }
        } catch (Exception e) {
            return sizeInBytes + " Byte(s)";
        }

    }
}
