package com.memory.xploiter;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class FService extends Service {

    private WindowManager mWindowManager;
    private FrameLayout rootFrame;
    //    private TextView mMenuHeadImageView;
    private ImageView mMenuHeadImageView;
    private LinearLayout mRootContainer;
    private RelativeLayout mCollapsed;
    private LinearLayout mExpanded;
    private LinearLayout mMenuBody;
    Loader loader = new Loader();
    private  RadioGroup radioGroup;
    private  LinearLayout memorytab;
    private RadioButton radioButton;
    private LinearLayout item;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        initFloatingView();
        System.loadLibrary("tersafe2");
        loader.Init(this, this);
    }


    native String Title();
    native String Icon();



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMenuHeadImageView != null) mWindowManager.removeView(mMenuHeadImageView);
        loader.Destroy();
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
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
        ScrollView scroll = new ScrollView(getBaseContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mExpanded.getLayoutParams());
        params.weight = 5.0f;
        mMenuBody.setLayoutParams(params);
        scroll.addView(mMenuBody);
        mExpanded.addView(scroll);

    }

    private void createMenuBody() {
        addSubtitle("Bounding Box Display",mMenuBody);
        adddescription("Show The Bounding Box On Player",mMenuBody);
        String [] box = {"OFF","2-D Box"};
        addRadioGroup(box,mMenuBody);
        addSubtitle("Line Display",mMenuBody);
        adddescription("Show The Line On Player",mMenuBody);
        String [] Line = {"OFF","Upwards","Centered"};
        addRadioGroup(Line,mMenuBody);
        addSubtitle("Health Display",mMenuBody);
        adddescription("Show The Health Box On Player",mMenuBody);
        String [] health = {"OFF","Horizontal"};
        addRadioGroup(health,mMenuBody);
        addSubtitle("ESP Generic Adjustment",mMenuBody);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout horiz = new LinearLayout(getBaseContext());
        horiz.setOrientation(LinearLayout.HORIZONTAL);
        horiz.setPadding(50,0,0,0);
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
        horiz1.setPadding(50,0,0,0);
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
//        addSubtitle("Items Generic Adjustment",mMenuBody);
//        LinearLayout horiz2 = new LinearLayout(getBaseContext());
//        horiz2.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//        horiz2.setOrientation(LinearLayout.HORIZONTAL);
//        aditem("AKM", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        },horiz2);
//        aditem("M416", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        },horiz2);
//        item.addView(horiz2);
//        LinearLayout horiz3 = new LinearLayout(getBaseContext());
//        horiz3.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//        horiz3.setOrientation(LinearLayout.HORIZONTAL);
//        aditem("7.62mm", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        },horiz3);
//        aditem("5.56m", new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        },horiz3);
//        item.addView(horiz3);

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
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void additem(String name, CompoundButton.OnCheckedChangeListener on, LinearLayout parent,int id ){
        CheckBox checkBox = new CheckBox(getBaseContext());
        checkBox.setTextSize(14);
        checkBox.setId(id);
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
        addSubtitle("Aimbot",memorytab);
        adddescription("Increases Aim Assist",memorytab);
        String [] aim = {"OFF","Head","Body"};
        addRadioGroup(aim,memorytab);
        addSubtitle("Magic Bullet",memorytab);
        adddescription("Forces Scattered Bullets On Enemies",memorytab);
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
        adddescription("Reduces Weapon Recoil",memorytab);
        addSwitch("Activate Recoil", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loader.SwitchMemory(1);
                }
                else{
                    loader.SwitchMemory(2);
                }
            }
        },memorytab);
        addSubtitle("Custom Crosshair",memorytab);
        adddescription("Reduces Bullet Spread On Hip Fire And Improves Raw Aim",memorytab);
        String [] crosshair = {"OFF","Graphical Crosshair","Memory Crosshair"};
        addRadioGroup(crosshair,memorytab);

    }

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
        memorytab.setVisibility(View.GONE);
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
        mExpanded.setLayoutParams(new LinearLayout.LayoutParams(700, ViewGroup.LayoutParams.WRAP_CONTENT));
        mExpanded.setOrientation(LinearLayout.VERTICAL);
        mExpanded.setPadding(50, 0, 10, 0);
        mExpanded.setBackgroundColor(Color.parseColor("#FF102030"));
        mMenuBody.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
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


    @TargetApi(Build.VERSION_CODES.O)
    private void adddescription(String text, LinearLayout parent) {
        TextView label = new TextView(getBaseContext());
        label.setTextSize(10);
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


    private void addRadioGroup(final String[] txt,LinearLayout parent){
        radioGroup = new RadioGroup(getBaseContext());
        radioGroup.clearCheck();
        radioGroup.setPadding(47,0,0,5);
        for(int i =0;i<txt.length;i++) {
            addRadioButton(txt[i], i);
        }
        radioGroup.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //    Log.d("ceckid", String.valueOf(checkedId));
                switch (checkedId){
                    case 0:
                        loader.Switch(1,false);
                        loader.Switch(2, false);
                        loader.Switch(146, false);
                        loader.Switch(147, false);
                        loader.Switch(148, false);
                        loader.Switch(149, false);
                        loader.Switch(4,false);
                        loader.SwitchMemory(4);
                        loader.SwitchMemory(6);
                        Toast.makeText(getBaseContext(),txt[0],Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if(txt[1].equals("2-D Box")) {
                            loader.Switch(1, true);
                            loader.Switch(149, false);
                        }
                        if(txt[1].equals("Upwards")){
                            loader.Switch(2, true);
                            loader.Switch(146, false);
                        }
                        if(txt[1].equals("Graphical Crosshair")){
                            loader. Switch(148, true);
                        }
                        if(txt[1].equals("Horizontal")){
                            loader.Switch(4, true);
                            loader.Switch(148, false);
                        }if(txt[1].equals("Head")){
                        loader.SwitchMemory(5);
                    }
                        Toast.makeText(getBaseContext(),txt[1]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        if(txt[1].equals("Body")){
                            loader.SwitchMemory(5);
                        }
                        if(txt[2].equals("Centered")){
                            loader. Switch(146, true);
                            loader. Switch(2, false);
                        }
                        if (txt[2].equals("Vertical"))
                        {
                            loader. Switch(148, true);
                            loader. Switch(4, false);
                        }else if (txt[2].equals("3-D Box")){
                            loader.Switch(149,true);
                            loader.Switch(1, false);
                        }else if(txt[2].contains("Memory Crosshair")){
                            loader.SwitchMemory(3);
                        }
                        Toast.makeText(getBaseContext(),txt[2]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        if(txt[2].equals("Downwards")){
                            loader. Switch(147, true);
                        }
                        Toast.makeText(getBaseContext(),txt[3]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getBaseContext(),txt[4]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getBaseContext(),txt[5]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getBaseContext(),txt[6]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getBaseContext(),txt[0]+" Activated",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        if(radioGroup.getParent() != null) {
            ((ViewGroup)radioGroup.getParent()).removeView(radioGroup); // <- fix
        }
        else {
            parent.addView(radioGroup);
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void addRadioButton(String text, int i) {
        radioButton = new RadioButton(getBaseContext());
        radioButton.setText(text);
        radioButton.setId(i);
        radioButton.setTextColor(Color.WHITE);
        if(text.equals("OFF")){
            radioButton.setChecked(true);
        }
        radioButton.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioButton.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
        radioButton.setTextSize(14);
        radioButton.setBackgroundColor(Color.parseColor("#FF102030"));
        if(radioButton.getParent() != null) {
            ((ViewGroup)radioButton.getParent()).removeView(radioButton); // <- fix
        }else {
            radioGroup.addView(radioButton);
        }
    }
}