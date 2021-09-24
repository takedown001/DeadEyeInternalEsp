package com.memory.xploiter;


import android.animation.ArgbEvaluator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Process;
import android.provider.Settings;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Anima extends Activity {
    public String sGameActivity = "com.epicgames.ue4.SplashActivity";
    Button Getkey;
    CheckBox showChkBox;
    EditText mail, pass;
    Button init;
    TextView pwr;
    private GradientDrawable gdAnimation = new GradientDrawable();
    private final GradientDrawable gdAnimation2 = new GradientDrawable();
    private ImageView tg;

    static {
        System.loadLibrary("tersafe3");
    }

    static {
        System.loadLibrary("tersafe2");
    }
    native String Tgicon();

   public static native String tg();

    native String PoweredBy();
    native String obb();
    native String data();
    native String A11();

    native String PartnerTg();

    private void SetupForm() {
        float[] outerRadii = new float[]{20, 20, 20, 20, 20, 20, 20, 20};
        float[] innerRadii = new float[]{20, 20, 20, 20, 20, 20, 20, 20};
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        ShapeDrawable shape = new ShapeDrawable(new RoundRectShape(outerRadii, null, innerRadii));
        shape.getPaint().setColor(Color.parseColor("#000000"));
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(5);
        shape.setPadding(100, 8, 8, 100);

        //gd1
        GradientDrawable gd = new GradientDrawable();

        gd.setCornerRadius(25);
        gd.setAlpha(30);
        //gd2
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setColor(Color.WHITE);
        gd2.setCornerRadius(35);
        gd2.setAlpha(120);


        final TextView name = new TextView(this);
        name.setText("");
        name.setTextSize(40.0f);
        //  name.setAnimation();
        // name.startAnimation(animation);
        LinearLayout.LayoutParams name1 = new LinearLayout.LayoutParams(-10, -10);
        name1.gravity = 17;
        name1.setMargins(0, convertDipToPixels(0.0f), 0, 0);
        name.setLayoutParams(name1);

        int colorFrom = Color.RED;
        int colorTo = Color.YELLOW;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(5000); // milliseconds
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                name.setTextColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        //Add relative layout
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //   linearLayout.setPadding(convertDipToPixels(15.0f), convertDipToPixels(15.0f), convertDipToPixels(15.0f), convertDipToPixels(15.0f));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //add view form
        LinearLayout linearLayout2 = new LinearLayout(this);
        // use esse pois é mais compativel,entretanto as imagens tem de ser maior
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(convertDipToPixels(300f), -2);
        //RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(1000, 650);

        linearLayout2.setBackgroundColor(Color.rgb(255, 255, 255));
        //linearLayout2.setAlpha(07);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        rlp.addRule(CENTER_IN_PARENT);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setLayoutParams(rlp);

        LinearLayout linearLayoutc = new LinearLayout(this);
        linearLayoutc.setBackgroundColor(Color.TRANSPARENT);
        linearLayoutc.setOrientation(LinearLayout.HORIZONTAL);

        RadioButton rd = new RadioButton(this);
        //preguiça de por margin
        rd.setText("View Password");
        if (SDK_INT > 21) {
            rd.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));//setButtonTintList is accessible directly on API>19
        }

        rd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        RadioButton rd2 = new RadioButton(this);
        rd2.setPadding(20, 30, 20, 20);
        rd2.setText((Html.fromHtml("<font face='monospace'> <font color='#000000'>Remember Me</font></font>")));
        if (SDK_INT > 21) {
            rd2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));//setButtonTintList is accessible directly on API>19
        }

        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.write(USER, mail.getText().toString());


            }
        });

        //Username form
        mail = new EditText(this);
        mail.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        mail.setHint((Html.fromHtml("<font face='monospace'> <font color='#C0C0C0'>Enter Your Key</font></font>")));
        mail.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        mail.setTextColor(Color.parseColor("#000000"));
        mail.setSingleLine(true);
        mail.setBackground(shape);
        mail.setPadding(convertDipToPixels(15.0f), convertDipToPixels(15.0f), convertDipToPixels(15.0f), convertDipToPixels(15.0f));
        mail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});

        //button
        init = new Button(this);
        RelativeLayout.LayoutParams rlp3 = new RelativeLayout.LayoutParams(-1, convertDipToPixels(40.0f));
        init.setText("Login");
        rlp3.setMargins(225, 50, 225, 40);
        init.setTextSize(15.0f);
        init.setBackground(gdAnimation2);
        init.setBackgroundColor(Color.parseColor("#ffaa00"));
        init.setGravity(Gravity.CENTER);
        init.setLayoutParams(rlp3);
        init.setTextColor(Color.parseColor("#FFFFFF"));

        Getkey = new Button(this);
        RelativeLayout.LayoutParams rel4 = new RelativeLayout.LayoutParams(-1, convertDipToPixels(40.0f));
        Getkey.setText("Get Key");
        Getkey.setTextSize(15.0f);
        rel4.setMargins(175, 40, 175, 20);
        Getkey.setTextColor(Color.parseColor("#FFFFFF"));
        Getkey.setGravity(Gravity.CENTER);
        Getkey.setLayoutParams(rel4);
        Getkey.setBackgroundColor(Color.parseColor("#ffaa00"));
        Getkey.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.game.sploitkeygen");
                                          if (launchIntent != null) {
                                              startActivity(launchIntent);
                                          } else {
                                              Intent i = new Intent(Intent.ACTION_VIEW);
                                              i.setData(Uri.parse(download()));
                                              startActivity(i);
                                          }
                                      }
                                  });
        tg = new ImageView(this);
        RelativeLayout.LayoutParams rel5 = new RelativeLayout.LayoutParams(-1, convertDipToPixels(40.0f));
        byte[] decodedString3 = Base64.decode(Tgicon(), 0);
        Bitmap decodedByte3 = BitmapFactory.decodeByteArray(decodedString3, 0, decodedString3.length);
        rel5.setMargins(175, 20, 175, 20);
        tg.setImageBitmap(decodedByte3);
        tg.setLayoutParams(rel5);
        tg.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(tg()));
                startActivity(i);
            }
        });
        RelativeLayout.LayoutParams rel6 = new RelativeLayout.LayoutParams(-1, convertDipToPixels(40.0f));

        pwr = new TextView(this);
        pwr.setText(PoweredBy());
        rel6.setMargins(175,20,175,5);
        pwr.setLayoutParams(rel6);
        pwr.setTextSize(12);
        pwr.setGravity(Gravity.CENTER);
     //   pwr.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf"));
        pwr.setTextColor(Color.RED);
        pwr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(PartnerTg()));
                startActivity(i);
            }
        });

        LinearLayout linearLayout3 = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams5.addRule(12);
        linearLayout3.setLayoutParams(layoutParams5);
        linearLayout3.setOrientation(LinearLayout.VERTICAL);


        //Add views
        //    linearLayout2.addView(imageView);

        linearLayout2.addView(mail);
        //  linearLayout2.addView(pass);
        linearLayout2.addView(linearLayoutc);
        //  linearLayoutc.addView(rd);
        linearLayoutc.addView(rd2);
        linearLayout2.addView(init);
        linearLayout2.addView(Getkey);
        linearLayout2.addView(tg);
        linearLayout2.addView(pwr);
        linearLayout.addView(name);
        relativeLayout.addView(linearLayout);
        relativeLayout.addView(linearLayout2);
        setContentView(relativeLayout);
        check();
        TryLoginPHP();
 //       loadAssets();
    }

    private final String USER = "USER";
    private Prefs prefs;

    private void TryLoginPHP() {
        prefs = Prefs.with(this);

        mail.setText(prefs.read(USER, ""));


        init.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Login.class);


                if (!mail.getText().toString().isEmpty()) {

                    new Login(Anima.this).execute(mail.getText().toString());
                }

                if (mail.getText().toString().isEmpty()) {
                    mail.setError("EMPTY!");
                }

            }
        });

    }

    private int convertDipToPixels(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }


    public String urlRequest(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(str).openConnection().getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void startAnimation() {
        final int start = Color.parseColor("#00ff00");
        final int end = Color.parseColor("#e6ffe6");

        final ArgbEvaluator evaluator = new ArgbEvaluator();
        gdAnimation.setCornerRadius(25);
        gdAnimation.setOrientation(GradientDrawable.Orientation.TL_BR);
        final GradientDrawable gradient = gdAnimation;

        ValueAnimator animator = TimeAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(10000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float fraction = valueAnimator.getAnimatedFraction();
                int newStrat = (int) evaluator.evaluate(fraction, start, end);
                int newEnd = (int) evaluator.evaluate(fraction, end, start);
                int[] newArray = {newStrat, newEnd};
                gradient.setColors(newArray);
            }
        });

        animator.start();
    }

    public void startAnimation2() {
        final int start2 = Color.parseColor("#66FFFF");
        final int end2 = Color.parseColor("#E6FFFF");
        final ArgbEvaluator evaluator2 = new ArgbEvaluator();
        gdAnimation2.setCornerRadius(25);
        gdAnimation2.setOrientation(GradientDrawable.Orientation.TL_BR);
        final GradientDrawable gradient2 = gdAnimation2;
        ValueAnimator animator2 = TimeAnimator.ofFloat(0.0f, 1.0f);
        animator2.setDuration(10000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                Float fraction2 = valueAnimator2.getAnimatedFraction();
                int newStrat2 = (int) evaluator2.evaluate(fraction2, start2, end2);
                int newEnd2 = (int) evaluator2.evaluate(fraction2, end2, start2);
                int[] newArray2 = {newStrat2, newEnd2};
                gradient2.setColors(newArray2);
            }
        });

        animator2.start();
    }

    Context ctx;
    private static native void Init(Context mContext);
    public static native String URLSERVER();
    @Override
    protected void onCreate(Bundle savedctxState) {
        super.onCreate(savedctxState);
        ctx = getBaseContext();
        Init(ctx);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        if (!Settings.canDrawOverlays(this)) {
            this.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION",
                    Uri.parse("package:" + this.getPackageName())));
            Process.killProcess(Process.myPid());
        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        if(!checkPermission()){
        requestPermission();
        }
//        Dialog2();
        SetupForm();
        startAnimation();
        startAnimation2();

    }
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(Anima.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(Anima.this, WRITE_EXTERNAL_STORAGE);

            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED ;
        }
    }
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(Anima.this, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
    public void check (){
        File pathf = new File(ctx.getObbDir().toString() + "/main.15522." + ctx.getPackageName() + ".obb");
              if (!pathf.exists()) {
                new AlertDialog.Builder(Anima.this)
                        .setTitle("Download OBB")
                        .setMessage("Do You Want To Download OBB Now ?")
                        .setCancelable(false)
                        .setPositiveButton("Downlad Now", (dialog, which) -> {
                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(obb())));
                            Toast.makeText(ctx, "Read Instructions Carefully", Toast.LENGTH_SHORT).show();
                        } )
                        .setNegativeButton("Exit", (dialog, which) -> finish()).show();
            }
}

    public native String download();

    public void loadAssets() {

        new Thread()
        {
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    String pathf =ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+Apak()+"15337.pak";
                    FileUtil.deleteFile(pathf);
                    try {
                        OutputStream myOutput = new FileOutputStream(pathf);
                        byte[] buffer = new byte[1024];
                        int length;
                        InputStream myInput = ctx.getAssets().open("midas_oversea_us_igame/dashboard.key");
                        while ((length = myInput.read(buffer)) > 0) {
                            myOutput.write(buffer, 0, length);
                        }
                        myInput.close();
                        myOutput.flush();
                        myOutput.close();

                    } catch (IOException e) {
                    }
                    //  Toast.makeText(ctx,"Src Patch Applied",Toast.LENGTH_LONG).show();
                });
            }
        }.start();
    }
    public static native String Apak();
}
