package com.memory.xploiter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Process;
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
import static com.memory.xploiter.FService.getConfig;

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
    Paint mTextStroke;
    Paint mTextStroke2;
    Paint p;
    Thread mThread;
    private int mFPS = 0;
    private int mFPSCounter = 0;
    private long mFPSTime = 0;
    int FPS = 30;
    static long sleepTime;
    Date time;
    SimpleDateFormat formatter;
    public static void ChangeFps(int fps) {
        sleepTime = 1000 / (25 + fps);
    }

    public ESPView(Context context) {
        super(context, null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
        time = new Date();
        formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sleepTime = 1000 / FPS;
        mThread = new Thread(this);
        mThread.start();
    }
    public native String FPS();
    public native String DeadEye();

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null && getVisibility() == VISIBLE) {
            ClearCanvas(canvas);
            int height = canvas.getHeight();
            float f = (height - 20);
            time.setTime(System.currentTimeMillis());

       //      DrawText(canvas, 255, 0, 255, 0, 0.5f, DeadEye() + " FPS : " + mFPS, 200, 90, 25); // Deadeye
            DrawText(canvas, 255, 0, 255, 0, 0.5f, FPS()+ mFPS, 200, 90, 35);
//           DrawText(canvas, 255, 0, 255, 0, 0.5f, "@" + Deadeye() + " & " +"@" + DeadEye() + "_TG", canvas.getWidth() - 260, canvas.getHeight() - 25, 20);
           DrawText(canvas, 255, 0, 255, 0, 0.5f,DeadEye(), canvas.getWidth() - 130, canvas.getHeight() - 30, 20);
           Loader.DrawOn(this, canvas);
           FService.onCanvasDraw(canvas,canvas.getWidth(),canvas.getHeight(),canvas.getDensity());
        }
    }


    public void DrawText(Canvas cvs, int a, int r, int g, int b, float stroke, String txt, float posX, float posY, float size) {
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(30);
        if (SystemClock.uptimeMillis() - mFPSTime > 1000) {
            mFPSTime = SystemClock.uptimeMillis();
            mFPS = mFPSCounter;
            mFPSCounter = 0;
        } else {
            mFPSCounter++;
        }
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (mThread.isAlive() && !mThread.isInterrupted()) {
            try {
                long t1 = System.currentTimeMillis();
                postInvalidate();
                long td = System.currentTimeMillis() - t1;
                Thread.sleep(Math.max(Math.min(0, sleepTime - td), sleepTime));


            } catch (InterruptedException it) {
                //   Log.e("OverlayThread", Objects.requireNonNull(it.getMessage()));
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
        mStrokePaintX.setColor(Color.rgb(255, 255, 0));
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


        mTextStroke = new Paint();
        mTextStroke.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextStroke.setAntiAlias(true);
        mTextStroke.setColor(Color.parseColor("#000000"));
        mTextStroke.setTextAlign(Paint.Align.CENTER);
        mTextStroke.setStrokeWidth(4.0f);
        mTextStroke.setStrokeMiter(4.0f);

        mTextStroke2 = new Paint();
        mTextStroke2.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextStroke2.setAntiAlias(true);
        mTextStroke2.setColor(Color.parseColor((String)"#000000"));
        mTextStroke2.setTextAlign(Paint.Align.CENTER);
        mTextStroke2.setStrokeWidth(4.0f);
        mTextStroke2.setStrokeMiter(4.0f);
        mTextStroke2.setTypeface(Typeface.DEFAULT_BOLD);

    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void DrawText3(Canvas cvs, int a, int r, int g, int b, float stroke, String txt, float posX, float posY, float size) {
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
        cvs.drawRect(x, y, width, height, mStrokePaint);
    }

    public void DrawFilledRect(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
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
        cvs.drawRect(x - 45, y - 5, width + 45, (height + 1.2f), mFilledPaint2);
    }

    public void DrawFilledRect33(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
        mFilledPaint2.setColor(Color.rgb(r, g, b));
        mFilledPaint2.setAlpha(a);
        cvs.drawRect(x, y, width, height, mFilledPaint2);
    }

    public void DrawRect2(Canvas cvs, int a, int r, int g, int b, float stroke, float x, float y, float width, float height) {
        mStrokePaint2.setStrokeWidth(stroke);
        mStrokePaint2.setColor(Color.rgb(r, g, b));
        mStrokePaint2.setAlpha(a);
        cvs.drawRect(x - 45, y - 5, width + 45, (height + 1.2f), mStrokePaint2);
    }

    public void DebugText(String s) {
        System.out.println(s);
    }

    public void DrawText(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    public void DrawText1(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mStrokePaint3.setARGB(a, r, g, b);
        mStrokePaint3.setTextSize(size);
        cvs.drawText(txt, posX, posY, mStrokePaint3);
    }


    public void DrawTextDistance(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(0, 0, 0, 0);
        mTextPaint.setAlpha(180);
        cvs.drawRect(posX - 44, posY - 18, posX - 79, posY + 6, mTextPaint);
        mStrokePaint.setColor(Color.rgb(0, 0, 0));
        cvs.drawRect(posX - 44, posY - 18, posX - 79, posY + 6, mStrokePaint);
        mTextPaint.setTextSize(13);
        mTextPaint.setARGB(255, 255, 255, 255);
        cvs.drawText(txt, posX - 62, posY, mTextPaint);
    }

    public void DrawTextAltert(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    public void DrawName(Canvas cvs, int a, int r, int g, int b, String nametxt, int teamid, float posX, float posY, float size) {
        String[] namesp = nametxt.split(":");
        char[] nameint = new char[namesp.length];
        for (int i = 0; i < namesp.length; i++)
            nameint[i] = (char) Integer.parseInt(namesp[i]);
        String realname = new String(nameint);
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setStrokeWidth(0.5f);
        mTextPaint.setTextSize(size);
        cvs.drawText("" + realname, posX, posY + 5.0f, mTextPaint);
        mTextPaint3.setARGB(200, 255, 165, 0);
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
        mFilledPaint.setARGB(255, 255, 0, 0);
        cvs.drawCircle(posX, posY, radius, mFilledPaint);
    }

    public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float) bitmap.getWidth() / maxWidth;
        float heightRatio = (float) bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int) (((float) width / bitmap.getWidth()) * bitmap.getHeight());
        }
        // Height constrained.
        else {
            height = maxHeight;
            width = (int) (((float) height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        float ratioX = (float) width / bitmap.getWidth();
        float ratioY = (float) height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }

    public void DrawItems(Canvas cvs, String itemName, float distance, float posX, float posY, float size) {
        String realItemName = getItemName(itemName);
        mTextStroke.setTextSize((float) (size));
        if (realItemName != null && !realItemName.equals(""))
            cvs.drawText(realItemName + " (" + Math.round(distance) + "m)", posX, posY, mTextStroke);
        mTextPaint.setTextSize(size);
        mTextPaint.setStrokeWidth(1.0f);
        mTextPaint.setStrokeMiter(1.0f);
        mTextPaint.setTextSize(size);
        if (realItemName != null && !realItemName.equals(""))
            cvs.drawText(realItemName + " (" + Math.round(distance) + "m)", posX, posY, mTextPaint);
    }

    public void DrawVehicles(Canvas cvs, String itemName, float distance, float posX, float posY, float size) {
        String realVehicleName = getVehicleName(itemName);
        mTextStroke.setTextSize((float) (size));
        if (realVehicleName != null && !realVehicleName.equals(""))
            cvs.drawText(realVehicleName + ": " + Math.round(distance) + "m", posX, posY, mTextStroke);
        mTextPaint.setTextSize(size);
        if (realVehicleName != null && !realVehicleName.equals(""))
            cvs.drawText(realVehicleName + ": " + Math.round(distance) + "m", posX, posY, mTextPaint);
    }

    private String getItemName(String s) {
        //Scopes
        if (s.contains("MZJ_8X") && getConfig("8x")) {
            mTextPaint.setColor(Color.rgb(179,61,81));
            return "8x";
        }

        if (s.contains("MZJ_2X") && getConfig("2x")) {
            mTextPaint.setColor(Color.rgb(179,61,81));

            return "2x";
        }

        if (s.contains("MZJ_HD") && getConfig("Red-Dot")) {
            mTextPaint.setColor(Color.rgb(179,61,81));

            return "Red Dot";
        }

        if (s.contains("MZJ_3X") && getConfig("3x")) {
            mTextPaint.setColor(Color.rgb(179,61,81));

            return "3X";
        }

        if (s.contains("MZJ_QX") && getConfig("Hollow")) {
            mTextPaint.setColor(Color.rgb(179,61,81));

            return "Hollow Sight";
        }

        if (s.contains("MZJ_6X") && getConfig("6x")) {
            mTextPaint.setColor(Color.rgb(179,61,81));

            return "6x";
        }

        if (s.contains("MZJ_4X") && getConfig("4x")) {
            mTextPaint.setColor(Color.rgb(179,61,81));

            return "4x";
        }

        if (s.contains("MZJ_SideRMR") && getConfig("Canted")) {
            mTextPaint.setColor(Color.rgb(179,61,81));
            return "Canted Sight";
        }


        //AR and smg
        if (s.contains("AUG") && getConfig("AUG")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "AUG";
        }

        if (s.contains("M762") && getConfig("M762")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "M762";
        }

        if (s.contains("SCAR") && getConfig("SCAR-L")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "SCAR-L";
        }

        if (s.contains("M416") && getConfig("M416")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "M416";
        }

        if (s.contains("M16A4") && getConfig("M16A4")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "M16A-4";
        }

        if (s.contains("Mk47") && getConfig("Mk47 Mutant")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Mk47 Mutant";
        }

        if (s.contains("G36") && getConfig("G36C")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "G36C";
        }

        if (s.contains("QBZ") && getConfig("QBZ")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "QBZ";
        }

        if (s.contains("AKM") && getConfig("AKM")) {
           mTextPaint.setColor(Color.rgb(255,127,80));
            return "AKM";
        }

        if (s.contains("Groza") && getConfig("Groza")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Groza";
        }

        if (s.contains("PP19") && getConfig("Bizon")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Bizon";
        }

        if (s.contains("TommyGun") && getConfig("Tommy Gun")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Tommy Gun";
        }

        if (s.contains("MP5K") && getConfig("Mp5K")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Mp5K";
        }

        if (s.contains("UMP9") && getConfig("Ump9")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Ump-9";
        }

        if (s.contains("Vector") && getConfig("Vector")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Vector";
        }

        if (s.contains("Uzi") && getConfig("Uzi")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Uzi";
        }

        if (s.contains("DP28") && getConfig("Dp-28")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Dp-28";
        }

        if (s.contains("M249") && getConfig("M249")) {
             mTextPaint.setColor(Color.rgb(255,127,80));

            return "M249";
        }

        //snipers

        if (s.contains("AWM") && getConfig("AWM")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "AWM";
        }

        if (s.contains("QBU") && getConfig("QBU")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "QBU";
        }

        if (s.contains("SLR") && getConfig("SLR")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "SLR";
        }

        if (s.contains("SKS") && getConfig("SKS")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "SKS";
        }

        if (s.contains("Mini14") && getConfig("Mini-14")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Mini-14";
        }

        if (s.contains("M24") && getConfig("M24")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "M24";
        }

        if (s.contains("Kar98k") && getConfig("Kar98k")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Kar98k";
        }

        if (s.contains("VSS") && getConfig("Vss")) {
              mTextPaint.setColor(Color.rgb(255,127,80));
            return "Vss";
        }

        if (s.contains("FAMAS") && getConfig("Famas")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Famas";
        }

        if (s.contains("Mosin") && getConfig("Mosin Nagant")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Mosin Nagant";
        }

        if (s.contains("Mk14") && getConfig("Mk14")) {
             mTextPaint.setColor(Color.rgb(255,127,80));
            return "Mk14";
        }
////shotguns and hand weapons
//        if (s.contains("S12K") && getConfig("S12K")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "S12K";
//        }
//
//        if (s.contains("DBS") && getConfig("DBS")) {
//            mTextPaint.setColor(Color.RED);
//            return "DBS";
//        }
//
//        if (s.contains("S686") && getConfig("S686")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "S686";
//        }
//
//        if (s.contains("S1897") && getConfig("S1897")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "S1897";
//        }
//
//        if (s.contains("Sickle") && getConfig("Sickle")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Sickle";
//        }
//
//        if (s.contains("Machete") && getConfig("Machete")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Machete";
//        }
//
//        if (s.contains("Cowbar") && getConfig("Cowbar")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Cowbar";
//        }
//
//        if (s.contains("CrossBow") && getConfig("CrossBow")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "CrossBow";
//        }
//
//        if (s.contains("Pan") && getConfig("Pan")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Pan";
//        }

//        //pistols
//
//        if (s.contains("SawedOff") && getConfig("SawedOff")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "SawedOff";
//        }
//
//        if (s.contains("R1895") && getConfig("R1895")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "R1895";
//        }
//
//        if (s.contains("Vz61") && getConfig("Vz61")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Vz61";
//        }
//
//        if (s.contains("P92") && getConfig("P92")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "P92";
//        }
//
//        if (s.contains("P18C") && getConfig("P18C")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "P18C";
//        }
//
//        if (s.contains("R45") && getConfig("R45")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "R45";
//        }
//
//        if (s.contains("P1911") && getConfig("P1911")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "P1911";
//        }
//
//        if (s.contains("DesertEagle") && getConfig("Desert Eagle")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "DesertEagle";
//        }


        //Ammo
        if (s.contains("Ammo_762mm") && getConfig("7.62mm")) {
            mTextPaint2.setColor(Color.rgb(255,190,0));
            return "7.62mm";
        }

        if (s.contains("Ammo_45AC") && getConfig("45ACP")) {
            mTextPaint2.setColor(Color.rgb(255,190,0));

            return "45ACP";
        }

        if (s.contains("Ammo_556mm") && getConfig("5.56mm")) {
            mTextPaint2.setColor(Color.rgb(255,190,0));
            return "5.56 mm";
        }

        if (s.contains("Ammo_9mm") && getConfig("9mm")) {
            mTextPaint2.setColor(Color.rgb(255,190,0));

            return "9mm";
        }

        if (s.contains("Ammo_300Magnum") && getConfig("300 Magnum")) {
            mTextPaint2.setColor(Color.rgb(255,190,0));

            return "300 Magnum";
        }

        if (s.contains("Ammo_12Guage") && getConfig("12 Gauge")) {
            mTextPaint2.setColor(Color.rgb(255,190,0));
            return "12 Guage";
        }

        if (s.contains("Ammo_Bolt") && getConfig("Arrow")) {
            mTextPaint.setColor(Color.rgb(255,190,0));
            return "Arrow";
        }

        //bag helmet vest
        if (s.contains("Bag_Lv3") && getConfig("Bag(3)")) {
            mTextPaint2.setARGB(255, 77, 115, 255);
            return "Bag lvl 3";
        }

        if (s.contains("Bag_Lv1") && getConfig("Bag(1)")) {
            mTextPaint2.setARGB(255, 127, 154, 250);
            return "Bag lvl 1";
        }

        if (s.contains("Bag_Lv2") && getConfig("Bag(2)")) {
            mTextPaint2.setARGB(255, 77, 115, 255);
            return "Bag lvl 2";
        }

        if (s.contains("Armor_Lv2") && getConfig("Armors(2)")) {
            mTextPaint2.setARGB(255, 77, 115, 255);
            return "Vest lvl 2";
        }


        if (s.contains("Armor_Lv1") && getConfig("Armors(1)")) {
            mTextPaint2.setARGB(255, 127, 154, 250);
            return "Vest lvl 1";
        }


        if (s.contains("Armor_Lv3") && getConfig("Armors(3)")) {
            mTextPaint.setColor(Color.CYAN);
            return "Vest lvl 3";
        }


        if (s.contains("Helmet_Lv2") && getConfig("Helmet(2)")) {
            mTextPaint.setColor(Color.rgb(25,0,15));
            return "Helmet lvl 2";
        }

        if (s.contains("Helmet_Lv1") && getConfig("Helmet(2)")) {
            mTextPaint.setColor(Color.rgb(25,0,15));
            return "Helmet lvl 1";
        }

        if (s.contains("Helmet_Lv3") && getConfig("Helmet(3)")) {
            mTextPaint.setColor(Color.rgb(25,0,15));
            return "Helmet lvl 3";
        }

        //Healthkits
        if (s.contains("Pills") && getConfig("PainKiller")) {
            mTextPaint.setColor(Color.rgb(130,220,5));
            return "PainKiller";
        }

        if (s.contains("Injection") && getConfig("Adrenaline")) {
            mTextPaint.setColor(Color.rgb(130,220,5));
            return "Adrenaline";
        }

        if (s.contains("Drink") && getConfig("Energy Drink")) {
            mTextPaint.setColor(Color.rgb(130,220,5));
            return "Energy Drink";
        }

        if (s.contains("Firstaid") && getConfig("FirstAid")) {
            mTextPaint.setColor(Color.rgb(130,220,5));
            return "FirstAid";
        }

        if (s.contains("Bandage") && getConfig("Bandage")) {
            mTextPaint.setColor(Color.rgb(130,220,5));
            return "Bandage";
        }

        if (s.contains("FirstAidbox") && getConfig("Medkit")) {
            mTextPaint.setColor(Color.rgb(130,220,5));
            return "Medkit";
        }

        //Throwables
        if (s.contains("Grenade_Stun") && getConfig("Stung")) {
            mTextPaint.setColor(Color.WHITE);
            return "Stung";
        }

        if (s.contains("Grenade_Shoulei") && getConfig("Grenade")) {
            mTextPaint.setColor(Color.RED);
            return "Grenade";
        }

        if (s.contains("Grenade_Smoke") && getConfig("Smoke")) {
            mTextPaint.setColor(Color.GRAY);
            return "Smoke";
        }

        if (s.contains("Grenade_Burn") && getConfig("Molotov")) {
            mTextPaint.setColor(Color.RED);
            return "Molotov";
        }
//
//
//        //others
//        if (s.contains("Large_FlashHider") && getConfig("MidNight Hider Ar")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "MidNight Hider Ar";
//        }
//
//        if (s.contains("QK_Large_C") && getConfig("Ar Compensator")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ar Compensator";
//        }
//
//        if (s.contains("Mid_FlashHider") && getConfig("MidNight Hider SMG")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "MidNight Hider SMG";
//        }
//
//        if (s.contains("QT_A_") && getConfig("Tactical Stock")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Tactical Stock";
//        }
//
//        if (s.contains("DuckBill") && getConfig("Duckbill")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "DuckBill";
//        }
//
//        if (s.contains("Sniper_FlashHider") && getConfig("MidNight Hider Snp")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "MidNight Hider Sniper";
//        }
//
//        if (s.contains("Mid_Suppressor") && getConfig("Suppressor SMG")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Suppressor SMG";
//        }
//
//        if (s.contains("HalfGrip") && getConfig("Half Grip")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Half Grip";
//        }
//

//        if (s.contains("Choke") && getConfig("Choke")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Choke";
//        }

//        if (s.contains("QT_UZI") && getConfig("Stock Micro UZI")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Stock Micro UZI";
//        }

//        if (s.contains("QK_Sniper") && getConfig("SniperCompensator")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Sniper Compensator";
//        }
//
//        if (s.contains("Sniper_Suppressor") && getConfig("Sup Sniper")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Suppressor Sniper";
//        }
//
//        if (s.contains("Large_Suppressor") && getConfig("Suppressor Ar")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Suppressor Ar";
//        }
//
//
//        if (s.contains("Sniper_EQ_") && getConfig("Ex.Qd.Sniper")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ex.Qd.Sniper";
//        }
//
//        if (s.contains("Mid_Q_") && getConfig("Qd.SMG")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Qd.SMG";
//        }
//
//        if (s.contains("Mid_E_") && getConfig("Ex.SMG")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ex.SMG";
//        }
//
//        if (s.contains("Sniper_Q_") && getConfig("Qd.Sniper")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Qd.Sniper";
//        }
//
//        if (s.contains("Sniper_E_") && getConfig("Ex.Sniper")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ex.Sniper";
//        }
//
//        if (s.contains("Large_E_") && getConfig("Ex.Ar")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ex.Ar";
//        }
//
//        if (s.contains("Large_EQ_") && getConfig("Ex.Qd.Ar")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ex.Qd.Ar";
//        }
//
//        if (s.contains("Large_Q_") && getConfig("Qd.Ar")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Qd.Ar";
//        }
//
//        if (s.contains("Mid_EQ_") && getConfig("Ex.Qd.SMG")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Ex.Qd.SMG";
//        }
//
//        if (s.contains("Crossbow_Q") && getConfig("Quiver CrossBow")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Quiver CrossBow";
//        }
//
//        if (s.contains("ZDD_Sniper") && getConfig("Bullet Loop")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Bullet Loop";
//        }
//
//
//        if (s.contains("ThumbGrip") && getConfig("Thumb Grip")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Thumb Grip";
//        }
//
//        if (s.contains("Lasersight") && getConfig("Laser Sight")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Laser Sight";
//        }
//
//        if (s.contains("Angled") && getConfig("Angled Grip")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Angled Grip";
//        }
//
//        if (s.contains("LightGrip") && getConfig("Light Grip")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Light Grip";
//        }
//
//        if (s.contains("Vertical") && getConfig("Vertical Grip")) {
//            mTextPaint.setColor(Color.WHITE);
//            return "Vertical Grip";
//        }

        if (s.contains("GasCan") && getConfig("GasCan")) {
            mTextPaint.setColor(Color.RED);
            return "GasCan";
        }

        if (s.contains("Mid_Compensator") && getConfig("Compensator SMG")) {
            mTextPaint.setColor(Color.RED);
            return "Compensator SMG";
        }


        //special
        if (s.contains("Flare") && getConfig("FlareGun")) {
            mTextPaint.setColor(Color.RED);
            return "FlareGun";
        }

        if (s.contains("Ghillie") && getConfig("Gilli-Suit")) {
            mTextPaint.setColor(Color.RED);
            return "Gilli-Suit";
        }
//           if (s.contains("CheekPad") && getConfig("CheekPad")) {
//                mTextPaint.setColor(Color.RED);
//            return "CheekPad";
//        }
        if (s.contains("PickUpListWrapperActor") && getConfig("Death-Crate")) {
            mTextPaint.setColor(Color.RED);
            return "Crate";
        }
        if ((s.contains("AirDropPlane")) && getConfig("Airplane")) {
            mTextPaint.setColor(Color.RED);
            return "Airplane";
        }
        if ((s.contains("AirDrop")) && getConfig("Air-Drop")) {
            mTextPaint.setColor(Color.RED);
            return "AirDrop";
        }
        //return s;
        return null;

    }

    private String getVehicleName(String s) {
        if (s.contains("Buggy") && getConfig("Buggy")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Buggy";
        }
        if (s.contains("UAZ") && getConfig("UAZ")) {
            mTextPaint.setColor(Color.YELLOW);
            return "UAZ";
        }

        if (s.contains("MotorcycleC") && getConfig("Trike")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Trike";
        }
        if (s.contains("Motorcycle") && getConfig("Bike")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Bike";
        }
        if (s.contains("Dacia") && getConfig("Dacia")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Dacia";
        }
        if (s.contains("AquaRail") && getConfig("Jet")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Jet";
        }
        if (s.contains("PG117") && getConfig("Boat")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Boat";
        }
        if (s.contains("MiniBus") && getConfig("Bus")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Bus";
        }
        if (s.contains("Mirado") && getConfig("Mirado")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Mirado";
        }
        if (s.contains("Scooter") && getConfig("Scooter")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Scooter";
        }
        if (s.contains("Rony") && getConfig("Rony")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Rony";
        }
        if (s.contains("Snowbike") && getConfig("Snowbike")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Snowbike";
        }
        if (s.contains("Snowmobile") && getConfig("Snowmobile")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Snowmobile";
        }
        if (s.contains("Tuk") && getConfig("Tempo")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Tempo";
        }
        if (s.contains("PickUp") && getConfig("Truck")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Truck";
        }
        if (s.contains("BRDM") && getConfig("BRDM")) {
            mTextPaint.setColor(Color.YELLOW);
            return "BRDM";
        }
        if (s.contains("LadaNiva") && getConfig("LadaNiva")) {
            mTextPaint.setColor(Color.YELLOW);
            return "LadaNiva";
        }
        if (s.contains("Bigfoot") && getConfig("Monster Truck")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Monster Truck";
        }
        if (s.contains("FerrisWheelCar_C") && getConfig("CoupleRB")) {
            mTextPaint.setColor(Color.YELLOW);

            return "CoupleRB";
        }
        if (s.contains("Motorglider") && getConfig("Motar-Glider")) {
            mTextPaint.setColor(Color.YELLOW);
            return "Motor Glider";
        }
        return "";
    }
}
