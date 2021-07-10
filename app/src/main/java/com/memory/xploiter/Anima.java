package com.memory.xploiter;


import android.animation.ArgbEvaluator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Process;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class Anima extends Activity {
    public String sGameActivity = "com.epicgames.ue4.GameActivity";

    Button Getkey ;
    CheckBox showChkBox;
    EditText mail, pass;
    Button init;
    private GradientDrawable gdAnimation = new GradientDrawable();
    private final GradientDrawable gdAnimation2 = new GradientDrawable();


    private void SetupForm() {
        float[] outerRadii = new float[]{20,20,20,20,20,20,20,20};
        float[] innerRadii = new float[]{20,20,20,20,20,20,20,20};
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        ShapeDrawable shape = new ShapeDrawable(new RoundRectShape(outerRadii, null, innerRadii));
        shape.getPaint().setColor(Color.parseColor("#000000"));
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(5);
        shape.setPadding(100,8,8,100);

        //gd1
        GradientDrawable gd = new GradientDrawable();
       // gd.setColor(Color.YELLOW);
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
        LinearLayout.LayoutParams name1 = new LinearLayout.LayoutParams(-10,-10);
        name1.gravity = 17;
        name1.setMargins(0,convertDipToPixels(0.0f),0,0);
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
        if (Build.VERSION.SDK_INT > 21) {
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
        rd2.setPadding(20,30,20,20);
        rd2.setText((Html.fromHtml("<font face='monospace'> <font color='#000000'>Remember Me</font></font>")));
        if (Build.VERSION.SDK_INT > 21) {
            rd2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));//setButtonTintList is accessible directly on API>19
        }

        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    prefs.write(USER, mail.getText().toString());
                   
                } else {
                   // pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
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
        rlp3.setMargins(225,50,225,40);
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
        rel4.setMargins(175,40,175,20);
        Getkey.setTextColor(Color.parseColor("#FFFFFF"));
        Getkey.setGravity(Gravity.CENTER);
        Getkey.setLayoutParams(rel4);
        Getkey.setBackgroundColor(Color.parseColor("#ffaa00"));
        Getkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new GetKey(Anima.this).execute();
            }
        });
        //Footer text
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
     //   linearLayoutc.addView(rd);
        linearLayoutc.addView(rd2);
        linearLayout2.addView(init);
        linearLayout2.addView(Getkey);
        linearLayout.addView(name);
        relativeLayout.addView(linearLayout);
      //  relativeLayout.addView(name);
        relativeLayout.addView(linearLayout2);

       // relativeLayout.addView(linearLayout);


        setContentView(relativeLayout);
        TryLoginPHP();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
				
						this.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION",
														 Uri.parse("package:" + this.getPackageName())));
						Process.killProcess(Process.myPid());
					}
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        System.loadLibrary("tersafe2");
//        Dialog2();
        SetupForm();
        startAnimation();
        startAnimation2();

    }

}
