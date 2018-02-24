package com.button.notice.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 添加按钮的单击事件

        // 设置显示信息
        // 单击事件
        builder.setTitle("退出当前账号");
        builder.setMessage("退出后你将无法获取最新的通知与消息，你的本地信息将一并清除，确定退出？")
                .
                // 设置确定按钮
                        setPositiveButton("确定退出",
                        (dialog, which) -> {

                            final SharedPreferences sp = this.getSharedPreferences("DODODO", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.commit();
                            ACache aCache = ACache.get(Settings.this);
                            aCache.clear();
                            startActivity(new Intent(Settings.this, buttonLogin.class));
                            finish();

                        }   ).
                            // 设置取消按钮
                            setNegativeButton("取消",
                                    (dialog, which) -> {

                                    });
                            // 创建对话框
                            AlertDialog ad = builder.create();
                            // 显示对话框
                            ad.show();


    }



}
