package com.memory.xploiter;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

public class Flogo extends Service{

    private WindowManager windowManager;
    private static  Flogo Instance;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private  int Chech=0;
    private String myDaemon;
    private FrameLayout rootFrame;
    private ImageView mMenuHeadImageView;
    private LinearLayout mRootContainer;
    private RelativeLayout mCollapsed;
    Loader loader = new Loader();
    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        createOver();
    }

    @Override
    public void onDestroy() {
        if (mMenuHeadImageView != null) windowManager.removeView(rootFrame);
        stopSelf();
    }
    @SuppressLint("InflateParams")
    private int convertDipToPixels(int i) {
        return (int) ((((float) i) * getResources().getDisplayMetrics().density) + 0.5f);
    }
    private native String FlashOn();
    void createOver() {
        rootFrame = new FrameLayout(getBaseContext());
        mRootContainer = new LinearLayout(getBaseContext());
        mCollapsed = new RelativeLayout(getBaseContext());


        rootFrame.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        mRootContainer.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mRootContainer.setOrientation(LinearLayout.HORIZONTAL);
        mCollapsed.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mMenuHeadImageView = new ImageView(getBaseContext());
        mMenuHeadImageView.setLayoutParams(new LinearLayout.LayoutParams(150, 130));
        byte[] decode = Base64.decode(FlashOn(), 0);
        mMenuHeadImageView.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        mMenuHeadImageView.setImageAlpha(255);

        ((ViewGroup.MarginLayoutParams) mMenuHeadImageView.getLayoutParams()).topMargin = convertDipToPixels(10);
        mCollapsed.addView(mMenuHeadImageView);

        mRootContainer.addView(mCollapsed);
        rootFrame.addView(mRootContainer);

        final WindowManager.LayoutParams params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            //Add the view to the window.
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }
        //Specify the chat head position
        //Initially view will be added to top-left corner
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 200;
        //getting windows services and adding the floating view to it
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(rootFrame, params);

        final GestureDetector gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        //floating window setting
        mMenuHeadImageView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            Run();
                            return false;
                        }

                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                           BRun();
                            return false;
                        }

                        @Override
                        public boolean onDoubleTapEvent(MotionEvent e) {
                            return false;
                        }
                    });

                    return true;
                }
                else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;


                        case MotionEvent.ACTION_MOVE:
                            //this code is helping the widget to move around the screen with fingers
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(rootFrame, params);
                            return true;
                    }
                    return false;
                }
            }
        });
    }
    private native String FlashMid();
    public void Run(){

        switch (Chech){
            case 0:
                byte[] decode = Base64.decode(FlashMid(), 0);
                Loader.SwitchMemory(16);
                Loader.SwitchMemory(26);
                mMenuHeadImageView.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
                Toast.makeText(Instance," Player Speed Increased To 20M/s",Toast.LENGTH_SHORT).show();
                Chech=1;
                break;
            case 1:
                Chech = 1;
                byte[] decode2 = Base64.decode(FlashOn(), 0);
                Loader.SwitchMemory(17);
                Loader.SwitchMemory(26);
                mMenuHeadImageView.setImageBitmap(BitmapFactory.decodeByteArray(decode2, 0, decode2.length));
                Toast.makeText(Instance,"Player Speed Restored To Normal",Toast.LENGTH_SHORT).show();
                Chech =0;
                break;
        }
    }

    private native String FlashOff();
    public void BRun(){
        switch (Chech){
            case 0:
                Chech = 1;
                byte[] decode = Base64.decode((FlashOff()), 0);
                mMenuHeadImageView.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
                Loader.SwitchMemory(25);
                Loader.SwitchMemory(17);
                Toast.makeText(Instance,"Player Speed Increased TO 200 M/s Activated",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Chech = 0;
                byte[] decode2 = Base64.decode(FlashOn(), 0);
                Loader.SwitchMemory(26);
                Loader.SwitchMemory(17);
                mMenuHeadImageView.setImageBitmap(BitmapFactory.decodeByteArray(decode2, 0, decode2.length));
                Toast.makeText(Instance,"Player Speed Restored To Normal",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}