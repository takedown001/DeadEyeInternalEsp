package com.memory.xploiter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.util.Timer;

public class Loader extends Activity {

    private Service floating;
    static boolean count = false;

    private Context ctx;
    private Timer timer;
    private WindowManager windowManager;
    private ESPView overlayView;

    public static native void DrawOn(ESPView espView, Canvas canvas);
    public static native void SwitchMemory(int num);
    public  native void Close();
    public static native void Switch(int num, boolean flag);
    public native void SetAim(int num, int value);
    public native void Range(int range);
    public native void Size(int num,float range);
    public native void BSpeed(int BSpeed);
    public void Init(Context context, Service service){
        try {
            ctx = context;
            floating = service;
            timer = new Timer();
                System.loadLibrary("tersafe3");
                System.loadLibrary("tersafe2");
            windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            DrawCanvas();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DrawCanvas() {
        overlayView = new ESPView(ctx);
        final LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                getLayoutType(),
                LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL,

                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        windowManager.addView(overlayView, params);
    }


    public int getLayoutType() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = LayoutParams.TYPE_PHONE;
        }
        return LAYOUT_FLAG;
    }

    public void Destroy() {
        if (overlayView != null) { windowManager.removeView(overlayView);
        overlayView = null;
        }
        Close();
    }

}
