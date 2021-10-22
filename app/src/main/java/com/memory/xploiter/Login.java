package com.memory.xploiter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.TrustManagerFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.memory.xploiter.Anima.SSSS;
import static com.memory.xploiter.JSONParserString.publickey;

public class Login extends AsyncTask<String, Void, String> {
    private WeakReference<Anima> weakActivity;
    private ProgressDialog pDialog;
    // this is for a basic leech for idiots
    private  String a = "a";
     private boolean isforce = false;
    private String url;
    JSONParserString rq = new JSONParserString();
    public static String newsrcpatch;
    public static boolean isfree;
    public static boolean issrcenable ;
    public static String latestsrc;
    public static int time;
    public static String key = null ;
    private SharedPreferences configPrefs;
    public static native String Check();

    public Login(Anima activity){
        weakActivity = new WeakReference<>(activity);
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        pDialog = dialog;
    }


    @Override
    protected void onPreExecute() {
        Anima activity = getActivity();
        if (activity == null) {
            return;
        }

        if (getDialog() != null) {
            getDialog().setMessage("Checking your connection ...");
            getDialog().show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if (!isInternetAvailable(getActivity())) {
            return "No internet connection";
        }
        String s = null;
        try {
            key = strings[0];
            JSONObject params = new JSONObject();
            params.put("uname", key);
            params.put("load",getActivity().getPackageName());
            params.put("cs", getUniqueId(getActivity()));
            s = rq.makeHttpRequest(SSSS(),params);
        } catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }




    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onPostExecute(String s) {
        configPrefs = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        final Anima activity = getActivity();

        if (activity == null) {
            return;
        }
        if (getDialog() != null) {
            getDialog().dismiss();
        }
        getDialog().dismiss();
        if(s == null || s.isEmpty()){
            Toast.makeText(activity,(Html.fromHtml("<font face='monospace'> <font color='#ff0000'>Server Error!</font></font>")), Toast.LENGTH_LONG).show();
            return;
        }

        if(s.equals("No internet connection")){
            Toast.makeText(activity,s, Toast.LENGTH_LONG).show();
            return;
        }
        try {
            JSONObject ack = new JSONObject(s);
            String decData = Utils.profileDecrypt(ack.get("data").toString(), ack.get("hash").toString());
            if (!verify(decData, ack.get("sign").toString(), publickey)) {
                Toast.makeText(activity, (Html.fromHtml("<font face='monospace'> <font color='#ff0000'>Incorrect Data!</font></font>")), Toast.LENGTH_LONG).show();
                return;
            }
            final JSONObject data = new JSONObject(decData);
            if (!Settings.canDrawOverlays(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, 123);
            }
            isforce = data.getBoolean("force");
            url = data.getString("updateurl");
            String msg = data.getString("message");

            if (!data.get("CurrVersion").equals("v" + BuildConfig.VERSION_NAME)) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                    ab.setTitle("New Update Found!");
                    ab.setMessage("New Version DeadEyE Internal Esp" + data.get("CurrVersion") + " Available To Download");
                    ab.setPositiveButton("Download", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        activity.startActivity(i);
                    });
                    if (isforce) {
                        ab.setNegativeButton("Exit", (dialog, which) -> {
                            try {
                                activity.finish();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
                    } else {
                        ab.setNegativeButton("Continue", (dialog, which) -> {
                            try {
                                if (data.getBoolean("valid")) {
                                    newsrcpatch = data.getString("src");
                                    issrcenable = data.getBoolean("srcenable");
                                    latestsrc = data.getString("latestsrcversion");
                                    isfree =  data.getBoolean("free");
                                    time = data.getInt("left");
                          //          Log.d("Latest",latestsrc);
                                    if (!isfree) {
                                        try {
                                            Intent intent = new Intent(activity,Class.forName(activity.sGameActivity));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            activity.startActivity(intent);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(5000);
                                                        Intent i = new Intent(activity.getApplicationContext(), FService.class);
                                                        i.putExtra("gamename", Login.Check());
                                                       i.putExtra("time",time);
                                                       activity.startService(i);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                            Toast.makeText(activity, "Duration : " + time+ "min", Toast.LENGTH_SHORT).show();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {

                                        try {
                                            Intent intent = new Intent(activity,Class.forName(activity.sGameActivity));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            activity.startActivity(intent);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(5000);
                                                        Intent i = new Intent(activity.getApplicationContext(), FService.class);
                                                        i.putExtra("gamename", Login.Check());
                                                        i.putExtra("time",time);
                                                        activity.startService(i);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                            Toast.makeText(activity, "Duration : " +time+ "min", Toast.LENGTH_SHORT).show();

                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    //getActivity().finish();
                                } else {
                                    //customize essa mensagem no server
                                    Toast.makeText(activity, Html.fromHtml("<font color='#ff0000'>" + msg+ "</font>"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    ab.setCancelable(false);
                    ab.show();
                } else {
                    if (data.getBoolean("valid")) {
                        newsrcpatch = data.getString("src");
                        issrcenable = data.getBoolean("srcenable");
                        time = data.getInt("left");
                        latestsrc = data.getString("latestsrcversion");
                        isfree =  data.getBoolean("free");
                        try {
                            Intent intent = new Intent(activity,Class.forName(activity.sGameActivity));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(5000);
                                        Intent i = new Intent(activity.getApplicationContext(), FService.class);
                                        i.putExtra("gamename", Login.Check());
                                        i.putExtra("time",time);
                                        activity.startService(i);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            Toast.makeText(activity, "Duration : " + time + "min", Toast.LENGTH_SHORT).show();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //customize essa mensagem no server
                        Toast.makeText(activity, Html.fromHtml("<font color='#ff0000'>" +msg+ "</font>"), Toast.LENGTH_LONG).show();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

    }

    public static boolean isInternetAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isServiceRunning() {
        if (a != "a") {
            return true;
        } else {
            return false;
        }

    }

    private Anima getActivity() {
        return weakActivity.get();
    }

    private ProgressDialog getDialog() {
        return pDialog;
    }

    private PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }



    private String encrypt(String plainText, byte[] keyBytes) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(keyBytes));
        return Utils.toBase64(encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    private boolean verify(String plainText, String signature, byte[] keyBytes) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(getPublicKey(keyBytes));
        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
        return publicSignature.verify(Utils.fromBase64(signature));
    }

    private String getUniqueId(Context ctx) {
        String key = (getDeviceName() + Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID) + Build.HARDWARE).replace(" ", "");
        UUID uniqueKey = UUID.nameUUIDFromBytes(key.getBytes());
        return uniqueKey.toString().replace("-", "");
    }


    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }
}