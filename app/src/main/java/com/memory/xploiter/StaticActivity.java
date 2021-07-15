package com.memory.xploiter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;


public class StaticActivity{

//    private static final String TAG = "Mod Menu";
//    public static String cacheDir;


    public static void Start(final Context context) {
        if (!Settings.canDrawOverlays(context)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION",
                    Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                    @Override
                                public void run() {
                          context.startService(new Intent(context, FService.class));
                    }
                }, 1000);
        }
    }

}

