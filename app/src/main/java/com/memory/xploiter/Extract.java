package com.memory.xploiter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Extract extends AsyncTask<String, Integer, String> {

    private Context instance;
    private PowerManager.WakeLock mWakeLock;
    ProgressDialog mProgressDialog = null;
    ProgressMonitor progressMonitor;
    ZipFile ze;
    public Extract(Context context) {
        this.instance = context;
        Toast.makeText(instance,"Do Not Cancel This",Toast.LENGTH_LONG).show();
        mProgressDialog = new ProgressDialog(instance);
        mProgressDialog.setMessage("Extracting Files...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
    }

    protected String doInBackground(String... url_download) {
        String inpath = url_download[0];
        String out = url_download[1];
        try {
            ZipFile ze = new ZipFile(inpath);
            ze.extractAll(out);
            progressMonitor = ze.getProgressMonitor();
            ze.setRunInThread(true);
            while (!progressMonitor.getState().equals(ProgressMonitor.State.READY)) {
                publishProgress(progressMonitor.getPercentDone());
                mProgressDialog.setMessage(progressMonitor.getFileName());
                System.out.println(" done: " + progressMonitor.getPercentDone());
                System.out.println("Current file: " + progressMonitor.getFileName());
                System.out.println("Current task: " + progressMonitor.getCurrentTask());
                Thread.sleep(100);
            }
            if (progressMonitor.getResult().equals(ProgressMonitor.Result.SUCCESS)) {
                System.out.println("Successfully added folder to zip");
            } else if (progressMonitor.getResult().equals(ProgressMonitor.Result.ERROR)) {
                System.out.println("Error occurred. Error message: " + progressMonitor.getException().getMessage());
            } else if (progressMonitor.getResult().equals(ProgressMonitor.Result.CANCELLED)) {
                System.out.println("Task cancelled");
            }

        } catch (ZipException | InterruptedException e) {
            e.printStackTrace();
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
        }else{
            Toast.makeText(instance, "Extract Resouces Extraction Done", Toast.LENGTH_SHORT).show();
        }
    }
}