package com.memory.xploiter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.crypto.Cipher;

import static com.memory.xploiter.Login.isInternetAvailable;

public class GetKey extends AsyncTask<String, Void, String> {
    private WeakReference<Anima> weakActivity;
    private ProgressDialog pDialog;
    public static final String TAG_DEVICEID = "2";
    public static final String TAG_KEY = "1";
    JSONParserString jsonParserString = new JSONParserString();
    public static final String TAG_DURATION = "4";
    public static final String TAG_MSG = "8";
    public static final String TAG_TIME = "999";
    Date time = new Date();
    Handler handler = new Handler();
    long reqtime;


    private native String Here();
    public GetKey(Anima activity){
        weakActivity = new WeakReference<>(activity);
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        pDialog = dialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
    protected void onPostExecute(final String s) {
        super.onPostExecute(s);
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (getDialog() != null) {
            getDialog().dismiss();
        }

        if(s == null || s.isEmpty()){
            Toast.makeText(activity,"Server Auth error", Toast.LENGTH_LONG).show();
            return;
        }

        if(s.equals("No internet connection")){
            Toast.makeText(activity,s, Toast.LENGTH_LONG).show();
            return;
        }
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(s);
                            //     Log.d("log",s);
                            String url = obj.getString("2000");
                            url = AESUtils.DarKnight.getDecrypted(url);
                            Toast.makeText(getActivity(), AESUtils.DarKnight.getDecrypted(obj.getString(TAG_MSG)), Toast.LENGTH_LONG).show();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            getActivity().startActivity(browserIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }, 2000);
    }

    @Override
    protected String doInBackground(String... strings) {
        if (!isInternetAvailable(getActivity())) {
            return "No internet connection";
        }
        time.setTime(System.currentTimeMillis());
        reqtime = time.getTime();
        //creating request parameters
        HashMap<String, String> params = new HashMap<>();
        params.put(TAG_DEVICEID, AESUtils.DarKnight.getEncrypted(getUniqueId(getActivity())));
        params.put(TAG_KEY, AESUtils.DarKnight.getEncrypted("9166253127"));
        params.put(TAG_TIME, AESUtils.DarKnight.getEncrypted(String.valueOf(reqtime)));
        String rq = null;
        try {
            rq = jsonParserString.makeHttpRequest(Here(), params);
        } catch (KeyStoreException | IOException e) {
            e.printStackTrace();
        }
        //returing the response
        return rq;
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
