package com.button.notice.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.button.notice.Button.buttonLogin;
import com.button.notice.JPush.MyJPushMessageReceiver;
import com.button.notice.R;
import com.button.notice.User.Setting.Appinfo;
import com.button.notice.User.Setting.Feedback;
import com.button.notice.util.ACache;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.service.JPushMessageReceiver;

public class Settings extends AppCompatActivity  {
    private static final String TAG = "JIGUANG-Example";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
    }

    public void appinfo(View view){
        startActivity(new Intent(Settings.this,Appinfo.class));
    }
    public void sendback(View view){
        startActivity(new Intent(Settings.this,Feedback.class));
    }
    public void tuichu(View view){
        final SharedPreferences sp=this.getSharedPreferences("DODODO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        ACache aCache =ACache.get(Settings.this);
        aCache.clear();
        startActivity(new Intent(Settings.this,buttonLogin.class));
        finish();
    }



}
