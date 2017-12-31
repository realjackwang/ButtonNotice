package com.button.notice.Fragment;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.button.notice.JPush.JpushUtil;
import com.button.notice.JPush.MyApplication;
import com.button.notice.JPush.MyReceiver;
import com.button.notice.JPush.RegisterService;
import com.button.notice.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


//导致页面一直无法切换的原因是build.gradle里的ashokvarma版本太低

public class MainActivity extends AppCompatActivity  {
    public static MainActivity test_a = null;

    private ArrayList<Fragment> fragments;

    private int clickTime=0;
    private int times=-1;
    private Timer timer=null;
    int i =0;
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        test_a =this;
        startService(new Intent(MainActivity.this, RegisterService.class));
    BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
    bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
    bottomNavigationBar
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
            );
    bottomNavigationBar       //定义下面图标及名称及按压颜色
            .addItem(new BottomNavigationItem(R.drawable.notice, "通知").setActiveColorResource(R.color.blue))
            .addItem(new BottomNavigationItem(R.drawable.discover, "发现").setActiveColorResource(R.color.blue))
            .addItem(new BottomNavigationItem(R.drawable.user, "个人").setActiveColorResource(R.color.blue))
            .setFirstSelectedPosition(0)
            .initialise();

    fragments = getFragments();
    setDefaultFragment();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (fragments != null) {

                    if (position < fragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment from = fm.findFragmentById(R.id.layFrame);   //获取当前的fragment
                        Fragment fragment = fragments.get(position);

                        if(i==0){
                            ft.replace(R.id.layFrame, noticeFragment.newInstance("通知"));
                            i++;
                        }

                        if (fragment.isAdded()) {
                            ft.replace(R.id.layFrame, fragment);
                        } else {
                            ft.add(R.id.layFrame, fragment);
                        }
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {
//                if (fragments != null) {
//                    if (position < fragments.size()) {
//                        FragmentManager fm = getSupportFragmentManager();
//                        FragmentTransaction ft = fm.beginTransaction();
//                        Fragment fragment = fragments.get(position);
//                        ft.remove(fragment);
//                        ft.commitAllowingStateLoss();
//                    }
//                }
            }

            @Override
            public void onTabReselected(int position) {

            }
        });

}


private void setDefaultFragment() {     //设定默认的主页
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.replace(R.id.layFrame, noticeFragment.newInstance("通知"));
    transaction.commit();
}

private ArrayList<Fragment> getFragments() {
    ArrayList<Fragment> fragments = new ArrayList<>();
    fragments.add(noticeFragment.newInstance("通知"));
    fragments.add(discoverFragment.newInstance("发现"));
    fragments.add(userFragment.newInstance("个人"));
    return fragments;
}



    public void onBackPressed() {    //按两次返回退出程序

        clickTime=clickTime+1;

        if(clickTime==1&&timer==null){
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            timer=new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    times=times+1;
                    if(times==2){
                        clickTime=0;
                        times=-1;
                        timer.cancel();
                        timer=null;
                    }
                }
            }, 0,1000);
        }else if(clickTime==2){
            if(timer!=null){
                timer.cancel();
                timer=null;
                super.onBackPressed();
            }else{
                super.onBackPressed();
            }
        }
    }

    private void initm() {
        JPushInterface.init(getApplicationContext());
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.zerom.clothesnews.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }





    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!JpushUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

    public static void setNotification() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
                MyApplication.getAppContext(),
                R.layout.customer_notitfication_layout, R.id.icon, R.id.title,
                R.id.text);
        builder.layoutIconDrawable = R.mipmap.ic_launcher;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
        }

    }






