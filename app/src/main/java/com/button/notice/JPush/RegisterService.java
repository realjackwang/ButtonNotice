package com.button.notice.JPush;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.button.notice.Button.buttonLogin;
import com.button.notice.Fragment.MainActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Jack on 2017/12/30.
 */

public class RegisterService extends Service {

    public static boolean isForeground = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        isForeground = true;
        initm();
        registerMessageReceiver();
        MainActivity.setNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent localIntent = new Intent();
        localIntent.setClass(this, RegisterService.class);
        this.startService(localIntent);
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void initm() {
        JPushInterface.init(getApplicationContext());
    }

    private MyJPushMessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.taorouw.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MyJPushMessageReceiver();
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

}