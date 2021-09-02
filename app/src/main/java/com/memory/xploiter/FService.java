package com.memory.xploiter;

import static com.memory.xploiter.Anima.Apak;
import static com.memory.xploiter.Login.isfree;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androtrainer.Flags;
import androtrainer.MemoryScanner;
import androtrainer.Ranges;

public class FService extends Service  {

    private WindowManager mWindowManager;
    private FrameLayout rootFrame;
    private ImageView mMenuHeadImageView;
    private LinearLayout mRootContainer;
    private RelativeLayout mCollapsed;
    private LinearLayout mExpanded;
    private LinearLayout mMenuBody;
    Loader loader = new Loader();
    private LinearLayout Seprateitem;
    private  LinearLayout memorytab;
    private LinearLayout item;
    private LinearLayout itemlayout;
    private LinearLayout mainlayout;
    private LinearLayout itemtab;
    private LinearLayout PlayerBody;
    private LinearLayout aimbot;
    private LinearLayout premium;
    static Context ctx;
    private String settime;
    private String gamename;
    SharedPreferences configPrefs;
    private ImageView playerimg,itemimg,vehicalimg;
    private LinearLayout weapon,ammo, armors,health,scope,vehical,special;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getBaseContext();
        loader.Init(this, this);
        configPrefs = getSharedPreferences("config", MODE_PRIVATE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        initFloatingView();
                    });
                }
            }).start();
            loadAssets();
    }
    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        gamename = intent.getStringExtra("gamename");
        if(!gamename.equals(Login.Check())) {
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    native String Title();
    native String Icon();
    native String PlayerEsp();
    native String ItemEsp();
    native String VehicalEsp();

    private void UpdateConfiguration(String s, Object v) {
        try {
            onSendConfig(s, v.toString());
            SharedPreferences.Editor configEditor = configPrefs.edit();
            configEditor.putString(s, v.toString());
            configEditor.apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private native void onSendConfig(String s, String v);

    public static native void onCanvasDraw(Canvas canvas, int w, int h, float d);

    public static native void Switch(int i, boolean jboolean1);
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMenuHeadImageView != null) mWindowManager.removeView(mMenuHeadImageView);
        loader.Destroy();
        stopSelf();
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clearcache();
            }
        }).start();
        onDestroy();
    }

    private void createMenu() {
        // Menu Header
        TextView heading = new TextView(getBaseContext());
        heading.setText(Title());
        heading.setTextColor(Color.WHITE);
        heading.setTypeface(null,Typeface.BOLD);
        heading.setBackgroundColor(Color.parseColor("#FF102030"));
        heading.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        heading.setTextSize(20);
        heading.setPadding(0, 20, 30, 20);
        mExpanded.addView(heading);
        createMenuBody();
        Features();
        ScrollView scroll = new ScrollView(getBaseContext());
        scroll.setPadding(0,0,0,20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 5.0f;
        PlayerBody.setLayoutParams(params);
        PlayerBody.addView(mMenuBody);
        PlayerBody.addView(itemtab);
        PlayerBody.addView(aimbot);
        scroll.addView(PlayerBody);
        mainlayout.addView(scroll);
        mExpanded.addView(mainlayout);

    }
    static boolean getConfig(String key){
        SharedPreferences sp= ctx.getSharedPreferences("espValue",Context.MODE_PRIVATE);
        return  sp.getBoolean(key,false);
    }

    private void Features(){
        mainlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
        itemlayout.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
        itemlayout.setOrientation(LinearLayout.VERTICAL);
        byte[] decode = Base64.decode(PlayerEsp(), 0);
        playerimg.setLayoutParams(new LinearLayout.LayoutParams(100,120));
        playerimg.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        playerimg.setPadding(0,2,0,2);
        playerimg.setImageAlpha(255);
        playerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuBody.setVisibility(View.VISIBLE);
                itemtab.setVisibility(View.GONE);
                aimbot.setVisibility(View.GONE);
            }
        });
        byte[] iecode = Base64.decode(ItemEsp(), 0);
        itemimg.setLayoutParams(new LinearLayout.LayoutParams(100,120));
        itemimg.setImageBitmap(BitmapFactory.decodeByteArray(iecode, 0, decode.length));
        itemimg.setVisibility(View.GONE);
        itemimg.setImageAlpha(255);
        itemimg.setPadding(0,0,0,0);
        itemimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuBody.setVisibility(View.GONE);
                itemtab.setVisibility(View.VISIBLE);
                aimbot.setVisibility(View.GONE);
            }
        });
        vehicalimg.setLayoutParams(new LinearLayout.LayoutParams(100 ,120));
        byte[] vecode = Base64.decode(VehicalEsp(), 0);
        vehicalimg.setImageBitmap(BitmapFactory.decodeByteArray(vecode, 0, decode.length));
        vehicalimg.setImageAlpha(255);
        vehicalimg.setPadding(0,0,0,0);
        vehicalimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuBody.setVisibility(View.GONE);
                itemtab.setVisibility(View.GONE);
                aimbot.setVisibility(View.VISIBLE);
            }
        });

        itemlayout.addView(playerimg);
        itemlayout.addView(itemimg);
        itemlayout.addView(vehicalimg);
        mainlayout.addView(itemlayout);
    }

    private void clearcache(){
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Logs");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/GameErrorNoRecords");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/StartEvenReportedFlag");
        FileUtil.deleteFile(ctx.getCacheDir()+"/*");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+Apak()+".pak");

    }
    private void applysrc(){

        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferEifs0");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferEifs1");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/SrcVersion.ini");
        FileUtil.writeFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/SrcVersion.ini",sendsrcconfig()+configPrefs.getString("src","15339"));
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferEifs0");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferEifs1");
        FileUtil.deleteFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/LightData");
        FileUtil.makeDir(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/LightData");
        FileUtil.writeFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+"/ShadowTrackerExtra/ShadowTrackerExtra/Saved/LightData/LightData3036393187.ltz","kpk3o");

    }
    public native String sendsrcconfig();

    public native String modify ();

    private void createMenuBody() {
       addSubtitle("Bypass MTP Protection",mMenuBody);
       adddescription("Bypasses Normal Integrity Scans And Security Patches On Logo", mMenuBody);
       addSwitch("Inject", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FileUtil.writeFile(ctx.getExternalFilesDir("UE4Game").getAbsolutePath() + "/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Config/Android/Updater.ini", modify());
                            clearcache();
                            loader.SwitchMemory(11);
                           }
                    }).start();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            applysrc();
//                        }
//                    }).start();
                Toast.makeText(ctx,"MTP Protection Bypassed",Toast.LENGTH_LONG).show();
                }else{
                    clearcache();
                }
            }
        },mMenuBody);
//        addSubtitle("Src Patch",mMenuBody);
//       adddescription("Src Patch Sync Your Game Version To Server & Avoid Detection ( Only First Time Use)", mMenuBody);
//        addSwitch("Apply Patch", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//
//                }else{
//                    clearcache();
//                }
//            }
//        },mMenuBody);

        addSubtitle("Render FrameRate",mMenuBody);
        String [] FPS = {"30 FPS","45 FPS", "60 FPS", "90 FPS", "120 FPS"};
        addRadioGroup(FPS, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0 :
                        Toast.makeText(getBaseContext(),"30 FrameRate Activated",Toast.LENGTH_SHORT).show();
                        ESPView.ChangeFps(30);
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(),"45 FrameRate Activated",Toast.LENGTH_SHORT).show();
                        ESPView.ChangeFps(45);
                        break;
                    case 2:
                        Toast.makeText(getBaseContext(),"60 FrameRate Activated",Toast.LENGTH_SHORT).show();
                        ESPView.ChangeFps(60);
                        break;
                    case 3:
                        Toast.makeText(getBaseContext(),"90 FrameRate Activated",Toast.LENGTH_SHORT).show();
                        ESPView.ChangeFps(90);
                        break;
                    case 4 :
                        Toast.makeText(getBaseContext(),"120 FrameRate Activated",Toast.LENGTH_SHORT).show();
                        ESPView.ChangeFps(120);
                        break;
                }
            }
        },mMenuBody);
        addSubtitle("Bounding Box Display",mMenuBody);
        adddescription("Show The Bounding Box On Player",mMenuBody);
        String [] box = {"OFF","2-D Box"};
        addRadioGroup(box, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0 :
                        loader.Switch(1, false);
                        Toast.makeText(getBaseContext(),"OFF",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        loader.Switch(1, true);
                        Toast.makeText(getBaseContext(),"2-D Box Activated",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, mMenuBody);
        addSubtitle("Line Display",mMenuBody);
        adddescription("Show The Line On Player",mMenuBody);
        String [] Line = {"OFF","Upwards","Centered"};
        addRadioGroup(Line, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0 :
                        loader. Switch(2, false);
                        loader.Switch(146, false);
                        Toast.makeText(getBaseContext(),"OFF",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        loader.Switch(2, true);
                        loader.Switch(146, false);
                        Toast.makeText(getBaseContext(),"Upwards Line Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        loader. Switch(146, true);
                        loader. Switch(2, false);
                        Toast.makeText(getBaseContext(),"Centered Line Activated",Toast.LENGTH_SHORT).show();
                }
            }
        }, mMenuBody);
        addSubtitle("Health Display",mMenuBody);
        adddescription("Show The Health Box On Player",mMenuBody);
        String [] health = {"OFF","Horizontal"};
        addRadioGroup(health, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0 :
                        loader.Switch(4,false);
                        Toast.makeText(getBaseContext(),"OFF",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        loader.Switch(4,true);
                        Toast.makeText(getBaseContext(),"Horizontal Health Activated",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, mMenuBody);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout horiz = new LinearLayout(getBaseContext());
        horiz.setOrientation(LinearLayout.HORIZONTAL);
        horiz.setPadding(47,0,0,0);
        addSubtitle("ESP Generic Adjustment",item);
        additem("Distance", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loader.Switch(3, isChecked);

            }
        },horiz,1);
        additem("Skeleton", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loader.Switch(8, isChecked);
            }
        },horiz,2);
        item.addView(horiz);
        LinearLayout horiz1 = new LinearLayout(getBaseContext());
        horiz1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        horiz.setOrientation(LinearLayout.HORIZONTAL);
        horiz1.setPadding(47,0,0,0);
        additem("Name", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loader.Switch(5, isChecked);
            }
        },horiz1,3);
        additem("Throwable Warn", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loader.Switch(9, isChecked);
            }
        },horiz1,4);
        item.addView(horiz1);
        addi(new String[]{"360 Alert"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loader.Switch(149,isChecked);
            }
        },item);
        addSubtitle("Items Generic Adjustment",item);
        AddSeekbarng(" Size Adjustment",10,25,15,"","",new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loader.Size(1,seekBar.getProgress());
                loader.Size(3,seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        },item);
        //weapon start
        addi(new String[]{"AKM", "M416","Dp-28"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);
            }
        },item);
        addi(new String[]{"SCAR-L", "M762"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);
            }
        },item);
        addi(new String[]{"Buggy", "UAZ","Dacia"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },item);

        addi(new String[]{"3x", "FirstAid","FlareGun"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },item);
//        addi(new String[]{"3x", "6x","FlareGun"}, new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                setValue((String)buttonView.getText(),isChecked);
//            }
//        },item);

        mMenuBody.addView(item);
        addSwitch("Memory Features", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    memorytab.setVisibility(View.VISIBLE);
                }else {
                    memorytab.setVisibility(View.GONE);
                }
            }
        },mMenuBody);

        Memorylayout();
        mMenuBody.addView(memorytab);
        setSeprateitem();
        items();
        aimbot();;
    }
    private void aimbot(){
        aimbot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        aimbot.setOrientation(LinearLayout.VERTICAL);
        addSubtitle("Bullet Tracking", aimbot);
        adddescription("Bullets Will Have its Own Aim While Firing", aimbot);
        addSwitch("Enable Bullet Tracking", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UpdateConfiguration("AIM::AIMBULLET", isChecked ? 1 : 0);
                loader.Switch(14,isChecked);
            }
        }, aimbot);
        premium.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        premium.setOrientation(LinearLayout.VERTICAL);
        if(!isfree)
        {    
            premium.setVisibility(View.VISIBLE);
        }
        addSubtitle("Aim at the Body Part",premium);
        addRadioGroup(new String[]{"Head","Stomach"}, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:  loader.SetAim(2,1);
                        UpdateConfiguration("AIM::AIMBULLET",1);
                        
                    break;
//                    case 1:  loader.SetAim(2,2); break;
//                    case 2 : loader.SetAim(23);
//                        UpdateConfiguration("AIM::AIMBULLET",2);
//                    break;
                    case 2 : loader.SetAim(2,4);
                        
                        UpdateConfiguration("AIM::AIMBULLET",2);
                        break;
                }

            }
        },premium);
        addSwitch("Ignore Knocked Out Player", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loader.Switch(16,isChecked);
                UpdateConfiguration("AIM::KNOCKED",isChecked ?1:0);
                
            }
        },premium);
        addSubtitle("Target Selection Mode",premium);
        addRadioGroup(new String[]{"CrossHair Priority", "Distance Priority"}, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:loader.SetAim(3,1);
                        
                        break;
                    case 1:loader.SetAim(3,0);
                        
                        break;
                }
            }
        },premium);

        addSwitch(" Aim Prediction", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UpdateConfiguration("AIM::PREDICTION",isChecked ? 1:0);
            }
        },premium);
        adddescription("Predicts Aim On Basis Player Location",premium);
        addSubtitle("Toggle Mode",premium);
        addRadioGroup(new String[]{"Firing & Aiming down Sight", "Aiming Down Sight", "Firing"}, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                UpdateConfiguration("AIM::TRIGGER",0);
                switch (checkedId){
                    case 0:  loader.SetAim(4,3);
                        
                            UpdateConfiguration("AIM::TRIGGER",3);
                    break;
                    case 1:  loader.SetAim(4,2);
                        
                            UpdateConfiguration("AIM::TRIGGER",2);
                        break;
                    case 2:  loader.SetAim(4,1);
                        
                            UpdateConfiguration("AIM::TRIGGER",1);
                    break;
                }
            }
        },premium);
        addSubtitle("FOV",premium);
        int range =100;
        AddSeekbarng( "Bullet aim with in the range", 0, 200, range, "", "", new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar seekBar, int range, boolean isChecked) {
                loader.Range(seekBar.getProgress());
                
                UpdateConfiguration("AIM::SIZE",seekBar.getProgress());
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        },premium);
        aimbot.addView(premium);
//        LinearLayout tv = new LinearLayout(getBaseContext());
//        tv.setOrientation(LinearLayout.VERTICAL);
//        tv.setVisibility(View.GONE);
//        addSwitch("Aim Prediction", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    tv.setVisibility(View.VISIBLE);
//                }else {
//                    tv.setVisibility(View.GONE);
//                }
//            }
//        }, aimbot);
//        adddescription("Based on the Speed & Direction Of The Enenmy",aimbot);
//        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        addsubheading("Bullet Speed",tv);
//        int BSpeed = 8100;
//        AddSeekbarng( "Adjust the bullet speed ", 8000, 10000, BSpeed, "", "", new SeekBar.OnSeekBarChangeListener(){
//            public void onProgressChanged(SeekBar seekBar, int BSpeed, boolean isChecked) {
//                loader.BSpeed(seekBar.getProgress());
//            }
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        },tv);
//        aimbot.addView(tv);

    }

    private void items(){
        weapon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        weapon.setOrientation(LinearLayout.VERTICAL);
        //weapon start
        addi(new String[]{"AKM", "M416"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);

            }
        },weapon);
        addi(new String[]{"AUG", "M762"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);


            }
        },weapon);
        addi(new String[]{"SCAR", "Groza"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);

            }
        },weapon);
        addi(new String[]{"Mk14", "SKS"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);

            }
        },weapon);
        addi(new String[]{"Kar98k", "Win94"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"M16A4", "G36C"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"QBZ", "Uzi"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String) buttonView.getText(),isChecked);

            }
        },weapon);
        addi(new String[]{"Mp5K", "Ump9"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"M249", "Tommy Gun"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"Vector", "Dp-28"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);

        addi(new String[]{"Awm", "QBU"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"SLR", "Mini-14"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"M24", "Mosin Nagant"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);
        addi(new String[]{"Famas", "Vss"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },weapon);
        itemtab.addView(weapon);
        //wepon end
        ammo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ammo.setOrientation(LinearLayout.VERTICAL);

        addi(new String[]{"7.62mm", "5.56mm"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },ammo);
        addi(new String[]{"9mm", "300 Magnum"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        },ammo);
        addi(new String[]{"Arrow", "12 Gauge"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },ammo);
        itemtab.addView(ammo);
        armors.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        armors.setOrientation(LinearLayout.VERTICAL);
        //armor
        addi(new String[]{"Bag(1)", "Bag(2)"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },armors);
        addi(new String[]{"Bag(3)", "Helmet(1)"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },armors);
        addi(new String[]{"Helmet(2)", "Helmet(3)"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },armors);
        addi(new String[]{"Armors(1)", "Armors(2)"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },armors);
        addi(new String[]{"Armors(3)"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },armors);
        itemtab.addView(armors);
        health.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        health.setOrientation(LinearLayout.VERTICAL);
        //armor
        addi(new String[]{"PainKiller", "Adrenaline"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },health);
        addi(new String[]{"FirstAid", "Medkit"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },health);
        addi(new String[]{"Drink", "Bandage"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },health);
        itemtab.addView(health);

        scope.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        scope.setOrientation(LinearLayout.VERTICAL);
        //armor
        addi(new String[]{"Hollow", "Canted"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },scope);
        addi(new String[]{"Red-Dot", "8x","4x"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },scope);
        addi(new String[]{"3x","2x","6x"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },scope);
        itemtab.addView(scope);
        vehical.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        vehical.setOrientation(LinearLayout.VERTICAL);
        addi(new String[]{"Buggy", "UAZ","Trike"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"Bike", "Dacia","Jet"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"Boat", "Bus","Truck"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"Scooter", "Rony"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"SnowBike", "Tempo"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"LadaNiva", "Mirado"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"Motar-Glider", "CoupleRB"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);
        addi(new String[]{"Monster Truck","BRDM"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },vehical);

        itemtab.addView(vehical);
        special.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        special.setOrientation(LinearLayout.VERTICAL);
        //armor
        addi(new String[]{"FlareGun", "Gilli-Suit"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },special);
        addi(new String[]{"Air-Drop", "Airplane"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },special);
        addi(new String[]{"Death-Crate","GasCan"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue((String)buttonView.getText(),isChecked);
            }
        },special);
        itemtab.addView(special);
    }
    private void setSeprateitem(){
        itemtab.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Seprateitem.setLayoutParams(new LinearLayout.LayoutParams(130, LinearLayout.LayoutParams.MATCH_PARENT));
        Seprateitem.setOrientation(LinearLayout.VERTICAL);
        TextView Weapon = new TextView(getBaseContext());
        TextView Armors = new TextView(getBaseContext());
        TextView Health = new TextView(getBaseContext());
        TextView Special = new TextView(getBaseContext());
        TextView Ammo = new TextView(getBaseContext());
        TextView Vehical = new TextView(getBaseContext());
        TextView Scope = new TextView(getBaseContext());
        Weapon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Weapon.setPadding(2,5,0,2);
        Weapon.setText("Weapon");
        Weapon.setTypeface(Typeface.DEFAULT_BOLD);
        Weapon.setBackgroundColor(Color.BLACK);
        Weapon.setTextColor(Color.WHITE);
        Weapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weapon.setBackgroundColor(Color.BLACK);
                Ammo.setBackgroundColor(Color.GRAY);
                Health.setBackgroundColor(Color.GRAY);
                Armors.setBackgroundColor(Color.GRAY);
                Special.setBackgroundColor(Color.GRAY);
                Vehical.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.GRAY);
                weapon.setVisibility(View.VISIBLE);
                ammo.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
                armors.setVisibility(View.GONE);
                special.setVisibility(View.GONE);
                vehical.setVisibility(View.GONE);
                scope.setVisibility(View.GONE);
            }
        });
        Seprateitem.addView(Weapon);

        Ammo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Ammo.setPadding(2,5,0,2);
        Ammo.setTypeface(Typeface.DEFAULT_BOLD);
        Ammo.setText("Ammo");
        Ammo.setBackgroundColor(Color.GRAY);
        Ammo.setTextColor(Color.WHITE);
        Ammo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ammo.setBackgroundColor(Color.BLACK);
                Weapon.setBackgroundColor(Color.GRAY);
                Health.setBackgroundColor(Color.GRAY);
                Armors.setBackgroundColor(Color.GRAY);
                Special.setBackgroundColor(Color.GRAY);
                Vehical.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.GRAY);
                weapon.setVisibility(View.GONE);
                ammo.setVisibility(View.VISIBLE);
                health.setVisibility(View.GONE);
                armors.setVisibility(View.GONE);
                special.setVisibility(View.GONE);
                vehical.setVisibility(View.GONE);
                scope.setVisibility(View.GONE);
            }
        });
        Seprateitem.addView(Ammo);

        Armors.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Armors.setPadding(2,5,0,2);
        Armors.setText("Armors");
        Armors.setTypeface(Typeface.DEFAULT_BOLD);
        Armors.setBackgroundColor(Color.GRAY);
        Armors.setTextColor(Color.WHITE);
        Armors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Armors.setBackgroundColor(Color.BLACK);
                Weapon.setBackgroundColor(Color.GRAY);
                Ammo.setBackgroundColor(Color.GRAY);
                Health.setBackgroundColor(Color.GRAY);
                Special.setBackgroundColor(Color.GRAY);
                Vehical.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.GRAY);
                weapon.setVisibility(View.GONE);
                ammo.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
                armors.setVisibility(View.VISIBLE);
                special.setVisibility(View.GONE);
                vehical.setVisibility(View.GONE);
                scope.setVisibility(View.GONE);
            }
        });
        Seprateitem.addView(Armors);

        Health.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Health.setPadding(2,5,0,2);
        Health.setText("Health");
        Health.setTypeface(Typeface.DEFAULT_BOLD);
        Health.setBackgroundColor(Color.GRAY);
        Health.setTextColor(Color.WHITE);
        Health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Health.setBackgroundColor(Color.BLACK);
                Weapon.setBackgroundColor(Color.GRAY);
                Ammo.setBackgroundColor(Color.GRAY);
                Armors.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.GRAY);
                Special.setBackgroundColor(Color.GRAY);
                Vehical.setBackgroundColor(Color.GRAY);
                weapon.setVisibility(View.GONE);
                ammo.setVisibility(View.GONE);
                health.setVisibility(View.VISIBLE);
                vehical.setVisibility(View.GONE);
                armors.setVisibility(View.GONE);
                special.setVisibility(View.GONE);
                scope.setVisibility(View.GONE);
            }
        });
        Seprateitem.addView(Health);

        Scope.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Scope.setPadding(2,5,0,2);
        Scope.setText("Scope");
        Scope.setTypeface(Typeface.DEFAULT_BOLD);
        Scope.setBackgroundColor(Color.GRAY);
        Scope.setTextColor(Color.WHITE);
        Scope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Special.setBackgroundColor(Color.GRAY);
                Weapon.setBackgroundColor(Color.GRAY);
                Ammo.setBackgroundColor(Color.GRAY);
                Health.setBackgroundColor(Color.GRAY);
                Armors.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.BLACK);
                Vehical.setBackgroundColor(Color.GRAY);
                weapon.setVisibility(View.GONE);
                ammo.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
                armors.setVisibility(View.GONE);
                scope.setVisibility(View.VISIBLE);
                vehical.setVisibility(View.GONE);
                special.setVisibility(View.GONE);
            }
        });
        Seprateitem.addView(Scope);

        Vehical.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Vehical.setPadding(2,5,0,2);
        Vehical.setText("Vehical");
        Vehical.setTypeface(Typeface.DEFAULT_BOLD);
        Vehical.setBackgroundColor(Color.GRAY);
        Vehical.setTextColor(Color.WHITE);
        Vehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Special.setBackgroundColor(Color.GRAY);
                Weapon.setBackgroundColor(Color.GRAY);
                Ammo.setBackgroundColor(Color.GRAY);
                Health.setBackgroundColor(Color.GRAY);
                Armors.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.GRAY);
                Vehical.setBackgroundColor(Color.BLACK);
                weapon.setVisibility(View.GONE);
                ammo.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
                armors.setVisibility(View.GONE);
                scope.setVisibility(View.GONE);
                vehical.setVisibility(View.VISIBLE);
                special.setVisibility(View.GONE);

            }
        });
        Seprateitem.addView(Vehical);

        Special.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
        Special.setPadding(2,5,0,2);
        Special.setText("Special");
        Special.setTypeface(Typeface.DEFAULT_BOLD);
        Special.setBackgroundColor(Color.GRAY);
        Special.setTextColor(Color.WHITE);
        Special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Special.setBackgroundColor(Color.BLACK);
                Weapon.setBackgroundColor(Color.GRAY);
                Ammo.setBackgroundColor(Color.GRAY);
                Health.setBackgroundColor(Color.GRAY);
                Armors.setBackgroundColor(Color.GRAY);
                Scope.setBackgroundColor(Color.GRAY);
                Vehical.setBackgroundColor(Color.GRAY);
                weapon.setVisibility(View.GONE);
                ammo.setVisibility(View.GONE);
                scope.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
                armors.setVisibility(View.GONE);
                vehical.setVisibility(View.GONE);
                special.setVisibility(View.VISIBLE);
            }
        });

        Seprateitem.addView(Special);
        itemtab.addView(Seprateitem);

    }

    private void addi(String [] name, CompoundButton.OnCheckedChangeListener on, LinearLayout parent){
        LinearLayout hori = new LinearLayout(getBaseContext());
        hori.setOrientation(LinearLayout.HORIZONTAL);
        hori.setPadding(47,0,0,0);
        hori.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        CheckBox[] checkBox = new CheckBox[name.length];
        for (int i = 0 ; i<name.length;i++) {
            checkBox[i] = new CheckBox(getBaseContext());
            checkBox[i].setTextSize(14);
            checkBox[i].setId(i);
            checkBox[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf"));
            checkBox[i].setTextColor(Color.WHITE);
            checkBox[i].setText(name[i]);
            checkBox[i].setChecked(getConfig(name[i]));
            checkBox[i].setButtonTintList(ColorStateList.valueOf(Color.WHITE));
            checkBox[i].setOnCheckedChangeListener(on);
            hori.addView(checkBox[i]);
        }

        parent.addView(hori);
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void additem(String name, CompoundButton.OnCheckedChangeListener on, LinearLayout parent,int id ){
        CheckBox checkBox = new CheckBox(getBaseContext());
        checkBox.setTextSize(14);
        checkBox.setId(id);
        checkBox.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf"));
        checkBox.setTextColor(Color.WHITE);
        checkBox.setText(name);
        checkBox.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
        checkBox.setOnCheckedChangeListener(on);
        parent.addView(checkBox);
    }
    private void Memorylayout(){
        memorytab.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        memorytab.setOrientation(LinearLayout.VERTICAL);
        adddescription("Runtime Memory Modificaiton (You Might Be At Risk)",mMenuBody);
        addSubtitle("Aimbot (Line Of Sight)",memorytab);
        adddescription("Increases Aim Support On Visible Enemies",memorytab);
        String [] aim = {"OFF","Head","Body"};
        addRadioGroup(aim, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0 :
                        loader.SwitchMemory(6);
                        Toast.makeText(getBaseContext(),"OFF",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        loader.SwitchMemory(5);
                        Toast.makeText(getBaseContext(),"Head Aim Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        loader.SwitchMemory(5);
                        Toast.makeText(getBaseContext(),"Body Aim Activated",Toast.LENGTH_SHORT).show();
                }
            }
        }, memorytab);
        addSubtitle("Magic Bullet",memorytab);
        adddescription("Bullet Auto Follows Enemy",memorytab);
        addSwitch("Activate Magic Bullet", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loader.SwitchMemory(7);
                }
                else{
                    loader.SwitchMemory(8);
                }
            }
        },memorytab);
        addSubtitle("Recoil Compensation",memorytab);
        adddescription("Reduces Weapon Recoil On Firing",memorytab);
       addRadioGroup(new String[]{"OFF", "Vertical", "Horizontal","Both"}, 0, new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case 0 :
                       loader.SwitchMemory(2);
                       loader.SwitchMemory(21);
                       break;
                   case 1:
                       loader.SwitchMemory(1);
                       break;
                   case 2:
                       loader.SwitchMemory(20);
                       break;
                   case 3:
                       loader.SwitchMemory(1);
                       loader.SwitchMemory(20);
                       break;
               }
           }
       },memorytab);

        addSubtitle("Custom Crosshair",memorytab);
        adddescription("Reduces Bullet Spread On Hip Fire Or Improves Raw Aim",memorytab);
        String [] crosshair = {"OFF","Graphical Crosshair","Memory Crosshair"};
//        addSwitch("Medium Speed", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    loader.SwitchMemory(9);
//                }
//                else{
//                    loader.SwitchMemory(10);
//                }
//            }
//        },memorytab);
        addRadioGroup(crosshair, 0, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0 :
                        loader.SwitchMemory(4);
                        loader.Switch(148, false);
                        Toast.makeText(getBaseContext(),"OFF",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        loader.Switch(148, true);
                        loader.SwitchMemory(4);
                        Toast.makeText(getBaseContext(),"Graphical Crosshair Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        loader.SwitchMemory(3);
                        loader.Switch(148, false);
                        Toast.makeText(getBaseContext(),"Memory Crosshair Activated",Toast.LENGTH_SHORT).show();
                }
            }
        }, memorytab);

        addSwitch("Midnight Enviornment", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loader.SwitchMemory(9);
                    Toast.makeText(getBaseContext(),buttonView.getText()+" Activated",Toast.LENGTH_SHORT).show();
                }else{
                    loader.SwitchMemory(10);
                }
            }
        },memorytab);
        adddescription("Turn Surrounding into Midnight View For Better Visibility",memorytab);
//        addSubtitle("Player Scope Adjustment",memorytab);
//        adddescription("Gives You advantage To shoot Through Wall(Can't Be Turn Off & Highly Risky)",memorytab);
//        addi(new String[]{"SitScope", "SitLeftScope"}, new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                switch (buttonView.getId()){
//                    case 0:
//                        sitscope();
//                    case 1:
//                        sitleft();
//                }
//            }
//        },memorytab);


//        addSwitch("Speed Knock", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    loader.SwitchMemory(14);
//                    Toast.makeText(getBaseContext(),buttonView.getText()+" Activated",Toast.LENGTH_SHORT).show();
//                }else{
//                    loader.SwitchMemory(15);
//                }
//            }
//        },memorytab);


        adddescription("Increases And Improves Player Knock Speed", memorytab);
        addSubtitle("Accelerate Player Speed", memorytab);
        adddescription("Increases Player Speed On Tap & Makes You SuperMan With Double-Tap(200m)", memorytab);
        addRadioGroup(new String[]{"OFF", "ON"}, 0, new
                RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case 0:
                                stopService(new Intent(ctx,Flogo.class));
                                loader.SwitchMemory(26);
                                loader.SwitchMemory(17);
                                break;
                            case 1:
                                startService(new Intent(ctx,Flogo.class));
                                Toast.makeText(getBaseContext(),"Player Speed Menu Open",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, memorytab);
        addSubtitle("Car Generic Adjustments", memorytab);
        addi(new String[]{"Car Speed", "Car Jump"}, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case 0:
                        if(isChecked){
                            loader.SwitchMemory(12);
                            Toast.makeText(getBaseContext(),buttonView.getText()+" Activated",Toast.LENGTH_SHORT).show();
                        }else {
                            loader.SwitchMemory(13);
                        }
                        break;
                    case 1:
                        if(isChecked){
                            startService(new Intent(ctx,logo.class));
                            Toast.makeText(getBaseContext(),"Car jump Menu Open",Toast.LENGTH_SHORT).show();
                        }else {
                            stopService(new Intent(ctx,logo.class));
                        }
                        break;
                }
            }
        },memorytab);
    }

//    private void sitscope(){
//            xploit.clearResults();
//            xploit.setRanges(new int[]{Ranges.ANONYMOUS});
//            xploit.searchNumber("18.38787841797", Flags.FLOAT);
//            xploit.getResultsCount(1);
//            xploit.editAll("130.5419921875", Flags.FLOAT, 0x0);
//            xploit.clearResults();
//    }
//    private void sitleft(){
//            xploit.clearResults();
//            xploit.setRanges(new int[]{Ranges.ANONYMOUS});
//            xploit.searchNumber("13.27983283997", Flags.FLOAT);
//            xploit.getResultsCount(500);
//            xploit.editAll("-250", Flags.FLOAT, 0x0);
//            xploit.clearResults();
//    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initFloatingView() {
        AssetManager assetManager = getBaseContext().getAssets();
        rootFrame = new FrameLayout(getBaseContext());
        mRootContainer = new LinearLayout(getBaseContext());
        mCollapsed = new RelativeLayout(getBaseContext());
        mExpanded = new LinearLayout(getBaseContext());
        mMenuBody = new LinearLayout(getBaseContext());
        memorytab =  new LinearLayout(getBaseContext());
        item = new LinearLayout(getBaseContext());
        itemlayout = new LinearLayout(getBaseContext());
        mainlayout = new LinearLayout(getBaseContext());
        playerimg = new ImageView(getBaseContext());
        itemimg = new ImageView(getBaseContext());
        vehicalimg = new ImageView(getBaseContext());
        Seprateitem = new LinearLayout(getBaseContext());
        scope = new LinearLayout(getBaseContext());
        vehical = new LinearLayout(getBaseContext());
        itemtab = new LinearLayout(getBaseContext());
        aimbot = new LinearLayout(getBaseContext());
        premium = new LinearLayout(getBaseContext());
        // items
        weapon = new LinearLayout(getBaseContext());
        ammo = new LinearLayout(getBaseContext());
        health = new LinearLayout(getBaseContext());
        armors = new LinearLayout(getBaseContext());
        special = new LinearLayout(getBaseContext());
        PlayerBody = new LinearLayout(getBaseContext());
        itemtab.setOrientation(LinearLayout.HORIZONTAL);
        itemtab.setVisibility(View.GONE);
        aimbot.setVisibility(View.GONE);
        memorytab.setVisibility(View.GONE);
        premium.setVisibility(View.GONE);
        /*
            When -1 or -2 is applied, the view fills the screen
            just as it would for match_parent or fill_parent.
            When any number -3 or lower is applied, the behavior is the same
            as wrap_content.
         */
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        rootFrame.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        mRootContainer.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mRootContainer.setOrientation(LinearLayout.HORIZONTAL);
        mCollapsed.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mMenuHeadImageView = new ImageView(getBaseContext());
        mMenuHeadImageView.setLayoutParams(new LinearLayout.LayoutParams(150, 130));

        byte[] decode = Base64.decode(Icon(), 0);
        mMenuHeadImageView.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        mMenuHeadImageView.setImageAlpha(255);

        ((ViewGroup.MarginLayoutParams) mMenuHeadImageView.getLayoutParams()).topMargin = convertDipToPixels(10);
        mCollapsed.addView(mMenuHeadImageView);

        mExpanded.setLayoutParams(new LinearLayout.LayoutParams(750, ViewGroup.LayoutParams.WRAP_CONTENT));
        mExpanded.setOrientation(LinearLayout.VERTICAL);
        mExpanded.setBackgroundColor(Color.parseColor("#FF102030"));
        PlayerBody.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        PlayerBody.setOrientation(LinearLayout.VERTICAL);
        PlayerBody.setBackgroundColor(Color.parseColor("#FF102030"));
        mMenuBody.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mMenuBody.setOrientation(LinearLayout.VERTICAL);
        mMenuBody.setBackgroundColor(Color.parseColor("#FF102030"));
        //mMenuBody.setBackgroundColor(Color.parseColor("#171E24"));
        //children of layout2 LinearLayout
        createMenu();
        // set visibility
        mMenuHeadImageView.setVisibility(View.VISIBLE);
        mCollapsed.setVisibility(View.VISIBLE);
        mExpanded.setVisibility(View.GONE);


            /*
            ScrollView scroll = new ScrollView(getBaseContext());
            scroll.setLayoutParams(new LinearLayout.LayoutParams(mExpanded.getLayoutParams()));
            scroll.addView(mExpanded);
            */

        // add views


        mRootContainer.addView(mCollapsed);
        mRootContainer.addView(mExpanded);
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

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(rootFrame, params);
        mMenuHeadImageView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = motionEvent.getRawX();
                        initialTouchY = motionEvent.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (motionEvent.getRawX() - initialTouchX);
                        int Ydiff = (int) (motionEvent.getRawY() - initialTouchY);

                        if (Xdiff < 10 && Ydiff < 10) {
                            if (mExpanded.getVisibility() == View.GONE) {
                                Log.d("ACTION_UP", "Expanding View...");
                                mCollapsed.setVisibility(View.VISIBLE);
                                mExpanded.setVisibility(View.VISIBLE);
                            } else {
                                Log.d("ACTION_UP", "Collapsing View...");
                                mExpanded.setVisibility(View.GONE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (motionEvent.getRawX() - initialTouchX);
                        params.y = initialY + (int) (motionEvent.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(rootFrame, params);
                        return true;
                }
                return false;
            }
        });
    }

       private int convertDipToPixels(int i) {
            return (int) ((((float) i) * getResources().getDisplayMetrics().density) + 0.5f);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void addSwitch(String text, Switch.OnCheckedChangeListener on,LinearLayout parent) {
        Switch switchBtn = new Switch(getBaseContext());
        switchBtn.setTextSize(12);
        switchBtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"));
        switchBtn.setLayoutParams(new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.WRAP_CONTENT));
        switchBtn.setBackgroundColor(Color.parseColor("#FF102030"));
        switchBtn.setTextColor(Color.WHITE);
        switchBtn.setPadding(47, 5, 20,5);
        switchBtn.setSwitchPadding(10);
        switchBtn.setText(text);
        switchBtn.setOnCheckedChangeListener(on);
        if(switchBtn.getParent() != null) {
            ((ViewGroup)switchBtn.getParent()).removeView(switchBtn); // <- fix
        }
        else {
            parent.addView(switchBtn);
        }
    }
    public void loadAssets() {

        new Thread()
        {
            public void run() {
                clearcache();
                new Handler(Looper.getMainLooper()).post(() -> {
                    String pathf =ctx.getExternalFilesDir("UE4Game").getAbsolutePath()+Apak()+configPrefs.getString("src","15339")+".pak";
                    try {
                        OutputStream myOutput = new FileOutputStream(pathf);
                        byte[] buffer = new byte[1024];
                        int length;
                        InputStream myInput = ctx.getAssets().open("midas_oversea_us_igame/debug.key");
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

    @TargetApi(Build.VERSION_CODES.O)
    private void adddescription(String text, LinearLayout parent) {
        TextView label = new TextView(getBaseContext());
        label.setTextSize(10);
        label.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf"));
        label.setBackgroundColor(Color.parseColor("#FF102030"));
        label.setPadding(47, 0, 0, 5);
        label.setTextColor(Color.WHITE);
        label.setText(text);
        if(label.getParent() != null) {
            ((ViewGroup)label.getParent()).removeView(label); // <- fix
        }
        else {
            parent.addView(label);
        }
    }
    private void addsubheading(String text,LinearLayout parent) {
        TextView label = new TextView(getBaseContext());
        label.setBackgroundColor(Color.parseColor("#FF102030"));
        label.setPadding(47, 0, 0, 5);
        label.setText(text);
        label.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf"));
        label.setTextColor(Color.WHITE);
        if(label.getParent() != null) {
            ((LinearLayout)label.getParent()).removeView(label); // <- fix
        }
        parent.addView(label);
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void addSubtitle(String text, LinearLayout parent) {
        TextView label = new TextView(getBaseContext());
        label.setTextColor(Color.WHITE);
        label.setTextSize(14);
        label.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"));
        label.setTypeface(null,Typeface.BOLD);
        label.setBackgroundColor(Color.parseColor("#FF102030"));
        label.setPadding(45,0 , 0, 5);
        label.setText(text);
        if(label.getParent() != null) {
            ((ViewGroup)label.getParent()).removeView(label); // <- fix
        }else {
            parent.addView(label);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void addRadioGroup(String [] text, int n, RadioGroup.OnCheckedChangeListener on,LinearLayout parent) {
        RadioGroup radioGroup = new RadioGroup(getBaseContext());
        radioGroup.setPadding(47,0,0,0);
        RadioButton[] radioButton = new RadioButton[text.length];
        for (int i = 0 ; i< text.length; i++) {
            radioButton[i] = new RadioButton(getBaseContext());
            if(i ==n){
                radioButton[i].setChecked(true);
            }
            radioButton[i].setText(text[i]);
            radioButton[i].setId(i);
            radioButton[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf"));
            radioButton[i].setTextColor(Color.WHITE);
            radioButton[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButton[i].setButtonTintList(ColorStateList.valueOf(Color.WHITE));
            radioButton[i].setTextSize(14);
            radioButton[i].setBackgroundColor(Color.parseColor("#FF102030"));
            radioGroup.addView(radioButton[i]);
        }
        radioGroup.setOnCheckedChangeListener(on);
        radioGroup.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-1,-2));

        parent.addView(radioGroup);

    }
    void AddSeekbarng(String string, final int n, int n2, int n3, final String string2, final String string3, final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener, LinearLayout parent) {
        int n4 = n3;
        LinearLayout linearLayout = new LinearLayout((Context)this);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(":");
        textView.setText((CharSequence)stringBuilder.toString());
        textView.setTextSize(1, 12.5f);
        textView.setPadding(this.convertSizeToDp(10.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(10.0f), this.convertSizeToDp(5.0f));
        textView.setTextColor(-1);
        textView.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, -2));
        textView.setGravity(3);
        textView.setPadding(47,0,0,0);
        SeekBar seekBar = new SeekBar((Context)this);
        seekBar.setMax(n2);
        if (Build.VERSION.SDK_INT >= 26) {
            seekBar.setMin(n);
            seekBar.setProgress(n);
        }
        seekBar.setThumbTintList(ColorStateList.valueOf((int)-1));
        seekBar.setProgressTintList(ColorStateList.valueOf((int)-1));
        seekBar.setPadding(this.convertSizeToDp(15.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(15.0f), this.convertSizeToDp(5.0f));
        final TextView textView2 = new TextView((Context)this);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(string2);
        stringBuilder2.append(n);
        stringBuilder2.append(string3);
        textView2.setText((CharSequence)stringBuilder2.toString());
        textView2.setGravity(5);
        textView2.setTextSize(1, 12.5f);
        textView2.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        textView2.setPadding(this.convertSizeToDp(15.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(15.0f), this.convertSizeToDp(5.0f));
        textView2.setTextColor(-1);
        if (n4 != 0) {
            if (n4 < n) {
                n4 = n;
            }
            if (n4 > n2) {
                n4 = n2;
            }
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(string2);
            stringBuilder3.append(n4);
            stringBuilder3.append(string3);
            textView2.setText((CharSequence)stringBuilder3.toString());
            seekBar.setProgress(n4);
        }
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int n2, boolean bl) {
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2;
                if (n2 < n) {
                    n2 = n;
                    seekBar.setProgress(n2);
                }
                if ((onSeekBarChangeListener2 = onSeekBarChangeListener) != null) {
                    onSeekBarChangeListener2.onProgressChanged(seekBar, n2, bl);
                }
                TextView textView = textView2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(n2);
                stringBuilder.append(string3);
                textView.setText(stringBuilder.toString());


            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = onSeekBarChangeListener;
                if (onSeekBarChangeListener2 != null) {
                    onSeekBarChangeListener2.onStartTrackingTouch(seekBar);
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = onSeekBarChangeListener;
                if (onSeekBarChangeListener2 != null) {
                    onSeekBarChangeListener2.onStopTrackingTouch(seekBar);
                }
            }
        };
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener2);
        linearLayout.addView((View)textView);
        linearLayout.addView((View)textView2);
        if(linearLayout.getParent() != null) {
            ((ViewGroup)linearLayout.getParent()).removeView(linearLayout); // <- fix
        }
        if(seekBar.getParent() != null) {
            ((ViewGroup)seekBar.getParent()).removeView(seekBar); // <- fix
        }
        parent.addView(linearLayout);
        parent.addView(seekBar);
    }
    int convertSizeToDp(float f) {
        return Math.round((float) TypedValue.applyDimension((int)1, (float)f, (DisplayMetrics)this.getResources().getDisplayMetrics()));
    }
    private void setValue(String key,boolean b) {
        SharedPreferences sp=this.getSharedPreferences("espValue",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putBoolean(key,b);
        ed.apply();
    }

}