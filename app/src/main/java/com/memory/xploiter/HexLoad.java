package com.memory.xploiter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;;

public class HexLoad extends AsyncTask<String, Integer, String>
{

    private Context instance;
    private PowerManager.WakeLock mWakeLock;
    ProgressMonitor progressMonitor;
    ProgressDialog mProgressDialog =null;
    private String check;
    public HexLoad(Context context) {
        this.instance = context;
        mProgressDialog = new ProgressDialog(instance);
        mProgressDialog.setMessage("Downloading Files...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
    }

    protected String doInBackground(String... url_download)
    {   check = url_download[2];
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(url_download[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(url_download[1]);
            byte[] data = new byte[8192];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (fileLength > 0)
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) instance.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
       mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (result != null) {
            new AlertDialog.Builder(instance)
                    .setMessage("Something Went Wrong , Restart App")
                    .setPositiveButton("OK", null)
                    .show();
        }
        if(check.equals("data")){
            if(new File(instance.getFilesDir().toString()+"/data.zip").exists()){
                new AlertDialog.Builder(instance)
                        .setTitle("Extract Resources")
                        .setMessage("Extract Your Resources")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, which) -> {
                           new Extract(instance).execute(instance.getFilesDir().toString()+"/data.zip",instance.getExternalFilesDir("UE4Game").toString());
                        }).show();
            }
        }
    }
}
