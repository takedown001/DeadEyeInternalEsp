package com.memory.xploiter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.Bitmap.Config;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.Bitmap;
import android.util.Base64;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Half;
import android.icu.util.Calendar;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.os.SystemClock;

public class ESPView extends View implements Runnable {
    Paint paint31;
    Paint mStrokePaint;
    Paint mStrokePaint2;
    Paint mFilledPaint;
    Paint mFilledPaint2;
    Paint mStrokePaintX;
    Paint mTextPaint;
    Paint mTextPaint2;
    Paint mTextPaint3;
    Paint mXTextPaint;
    Paint mFilledPaint3;
    Paint mStrokePaint3;
    Paint p;
    Thread mThread;
    private int mFPS = 0;
    private int mFPSCounter = 0;
    private long mFPSTime = 0;
    int FPS = 60;
    static long sleepTime;
    Date time;
    SimpleDateFormat formatter;
    SimpleDateFormat formatter2;
    static Context ctx;
    public static void ChangeFps(int fps){
        sleepTime =1000/(20+fps);
    }

    Bitmap[] OTHER = new Bitmap[5];

    public ESPView(Context context) {
        super(context, null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
        time = new Date();
        formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sleepTime = 1000 / FPS;
        mThread = new Thread(this);
        mThread.start();
    }

    public native String Deadeye();
    public native String DeadEye();

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null && getVisibility() == VISIBLE) {
            ClearCanvas(canvas);
            int height = canvas.getHeight();
            float f = (height - 20);
            time.setTime(System.currentTimeMillis());
            //DrawText(canvas, 255, 128, 0, 0,1.1f, formatter2.format(time) + "  " + formatter.format(time), (canvas.getWidth()/2), 45, 28);
            DrawText(canvas, 255, 0, 255, 0, 0.5f, Deadeye() + "ESP Beta FPS : " + mFPS, 200, 100, 20);
            DrawText(canvas,255, 0, 255, 0,0.5f ,"@"+DeadEye() + "_TG",canvas.getWidth() - 130, canvas.getHeight() - 30, 20);
            Loader.DrawOn(this, canvas);
        }
    }

    public void DrawText(Canvas cvs, int a, int r, int g, int b,float stroke, String txt, float posX, float posY, float size) {
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(30);
        if (SystemClock.uptimeMillis() - mFPSTime > 1000) {
            mFPSTime = SystemClock.uptimeMillis();
            mFPS = mFPSCounter;
            mFPSCounter = 0;
        } else {
            mFPSCounter++;
        }
        cvs.drawText(txt , posX, posY, mTextPaint);
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        while (mThread.isAlive() && !mThread.isInterrupted()) {
            try {
                long t1 = System.currentTimeMillis();
                postInvalidate();
                long td = System.currentTimeMillis() - t1;
                Thread.sleep(Math.max(Math.min(0, sleepTime - td), sleepTime));
            } catch (InterruptedException it) {
                Log.e("OverlayThread", it.getMessage());
            }
        }
    }

    public void InitializePaints() {
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(Color.rgb(255, 255, 255));

        mStrokePaintX = new Paint();
        mStrokePaintX.setStyle(Paint.Style.STROKE);
        mStrokePaintX.setAntiAlias(true);
        mStrokePaintX.setColor(Color.rgb(255,255,0));
        mStrokePaintX.setStrokeWidth(2.0f);

        mFilledPaint = new Paint();
        mFilledPaint.setStyle(Paint.Style.FILL);
        mFilledPaint.setAntiAlias(true);
        mFilledPaint.setColor(Color.rgb(255, 255, 255));

        mStrokePaint2 = new Paint();
        mStrokePaint2.setStyle(Paint.Style.STROKE);
        mStrokePaint2.setAntiAlias(true);
        mStrokePaint2.setColor(Color.rgb(255, 255, 255));

        mStrokePaint3 = new Paint();
        mStrokePaint3.setStyle(Paint.Style.STROKE);
        mStrokePaint3.setAntiAlias(true);
        mStrokePaint3.setColor(Color.rgb(255, 255, 255));

        mFilledPaint3 = new Paint();
        mFilledPaint3.setStyle(Paint.Style.FILL);
        mFilledPaint3.setAntiAlias(true);
        mFilledPaint3.setColor(Color.rgb(255, 255, 255));

        mFilledPaint2 = new Paint();
        mFilledPaint2.setStyle(Paint.Style.FILL);
        mFilledPaint2.setAntiAlias(true);
        mFilledPaint2.setColor(Color.rgb(255, 255, 255));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.rgb(255, 255, 255));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(1.2f);

        mXTextPaint = new Paint();
        mXTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mXTextPaint.setAntiAlias(true);
        mXTextPaint.setColor(Color.rgb(0, 0, 0));
        mXTextPaint.setTextAlign(Paint.Align.CENTER);
        mXTextPaint.setStrokeWidth(1.5f);

        mTextPaint2 = new Paint();
        mTextPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint2.setAntiAlias(true);
        mTextPaint2.setColor(Color.rgb(255, 255, 255));
        mTextPaint2.setTextAlign(Paint.Align.CENTER);
        mTextPaint2.setStrokeWidth(1.1f);

        mTextPaint3 = new Paint();
        mTextPaint3.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint3.setAntiAlias(true);
        mTextPaint3.setColor(Color.rgb(255, 255, 255));
        mTextPaint3.setTextAlign(Paint.Align.CENTER);
        mTextPaint3.setStrokeWidth(1.1f);
    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void DrawText3(Canvas cvs, int a, int r, int g, int b,float stroke, String txt, float posX, float posY, float size) {
        mTextPaint.setColor(Color.rgb(r, g, b));
        mTextPaint.setAlpha(a);
        mTextPaint.setStrokeWidth(stroke);
        if (getRight() > 1920 || getBottom() > 1920)
            mTextPaint.setTextSize(4 + size);
        else if (getRight() == 1920 || getBottom() == 1920)
            mTextPaint.setTextSize(2 + size);
        else
            mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    public void DrawLine(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mTextPaint.setStrokeWidth(lineWidth);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }

    public void DrawLine1(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(lineWidth);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }

    public void DrawRect(Canvas cvs, int a, int r, int g, int b, float stroke, float x, float y, float width, float height) {
        mStrokePaint.setStrokeWidth(stroke);
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        cvs.drawRect(x, y,  width,  height, mStrokePaint);
    }

    public void DrawFilledRect(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height ) {
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        cvs.drawRect(x, y, width, height, mFilledPaint);

    }

    public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float stroke, float posX, float posY, float radius) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(stroke);
        cvs.drawCircle(posX, posY, radius, mStrokePaint);
    }
    public void DrawFilledRect3(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
        mFilledPaint2.setColor(Color.rgb(r, g, b));
        mFilledPaint2.setAlpha(a);
        cvs.drawRect(x-45, y-5,  width+45,  (height + 1.2f),mFilledPaint2);
    }
    public void DrawFilledRect33(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
        mFilledPaint2.setColor(Color.rgb(r, g, b));
        mFilledPaint2.setAlpha(a);
        cvs.drawRect(x, y,  width, height,mFilledPaint2);
    }

    public void DrawRect2(Canvas cvs, int a, int r, int g, int b, float stroke, float x, float y, float width, float height) {
        mStrokePaint2.setStrokeWidth(stroke);
        mStrokePaint2.setColor(Color.rgb(r, g, b));
        mStrokePaint2.setAlpha(a);
        cvs.drawRect(x-45, y-5,  width+45,  (height + 1.2f), mStrokePaint2);
    }

    public void DebugText(String s) {
        System.out.println(s);
    }

    public void DrawText(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(a,r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    public void DrawText1(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mStrokePaint3.setARGB(a,r, g, b);
        mStrokePaint3.setTextSize(size);
        cvs.drawText(txt, posX, posY, mStrokePaint3);
    }
    public void DrawTextDistance(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(0, 0 ,0, 0);
        mTextPaint.setAlpha(180);
        cvs.drawRect(posX-44,posY-18,posX-79,posY+6,mTextPaint);
        mStrokePaint.setColor(Color.rgb(0, 0, 0));
        cvs.drawRect(posX-44,posY-18,posX-79,posY+6,mStrokePaint);
        mTextPaint.setTextSize(13);
        mTextPaint.setARGB(255,255,255,255);
        cvs.drawText(txt, posX-62, posY, mTextPaint);
    }

    public void DrawTextAltert(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(a,r,g,b);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    public void DrawName(Canvas cvs, int a, int r, int g, int b, String nametxt,int teamid, float posX, float posY, float size) {
        String[] namesp = nametxt.split(":");
        char[] nameint = new char[namesp.length];
        for (int i = 0; i < namesp.length; i++)
            nameint[i] = (char) Integer.parseInt(namesp[i]);
        String realname = new String(nameint);
        mTextPaint.setARGB(a,r,g,b);
        mTextPaint.setStrokeWidth(0.5f);
        mTextPaint.setTextSize(size);
        cvs.drawText("" + realname, posX, posY + 5.0f, mTextPaint);
        mTextPaint3.setARGB(200,255, 165, 0);
        mTextPaint3.setTextSize(17);
        cvs.drawText("" + teamid, posX - 98.0f, posY, mTextPaint3);
    }

    public void DrawFilledCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius) {
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        cvs.drawCircle(posX, posY, radius, mFilledPaint);
    }

    public void DrawHead(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius) {
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        mFilledPaint.setARGB(255,255,0,0);
        cvs.drawCircle(posX, posY, radius, mFilledPaint);
    }

    public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float)bitmap.getWidth() / maxWidth;
        float heightRatio = (float)bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int)(((float)width / bitmap.getWidth()) * bitmap.getHeight());
        }
        // Height constrained.
        else {
            height = maxHeight;
            width = (int)(((float)height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        float ratioX = (float)width / bitmap.getWidth();
        float ratioY = (float)height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}


