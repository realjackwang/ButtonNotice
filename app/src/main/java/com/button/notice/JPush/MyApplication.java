package com.button.notice.JPush;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class MyApplication extends Application {
    private static final String TAG = "JIGUANG-Example";
    private static Context context;

    @Override
    public void onCreate() {    	     
    	 Logger.d(TAG, "[MyApplication] onCreate");
         super.onCreate();
        MyApplication.context = getApplicationContext();

//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//         JPushInterface.init(this);     		// 初始化 JPush

    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}
