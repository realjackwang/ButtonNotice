package com.button.notice.Button;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.button.notice.Fragment.MainActivity;

import com.button.notice.JPush.JpushUtil;
import com.button.notice.JPush.MyApplication;
import com.button.notice.Notice.noticeNew;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.LoadUtils;
import com.button.notice.util.MD5;
import com.button.notice.util.StringUtil;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class buttonLogin extends AppCompatActivity  {

    private EditText etAccount;
    private EditText etPassword;

    private Dialog mLoad;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeDialog(mLoad);
                    break;
            }
        }
    };


    public void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_login);

//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(getApplicationContext());   //初始化推送服务




        final SharedPreferences sp=this.getSharedPreferences("DODODO",Context.MODE_PRIVATE);

        etAccount = (EditText) findViewById(R.id.UserId);
        etPassword = (EditText) findViewById(R.id.UserKey);

        final Button btnLogin = (Button) findViewById(R.id.login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonRequest request = new CommonRequest();

                if (checkNetwork()==false)
                {
                    Toast.makeText(buttonLogin.this, "无法连接到网络", Toast.LENGTH_SHORT).show();
                }
                else if (checkNetwork()==true){


                        if (!StringUtil.isEmpty(etAccount.getText().toString())
                                && !StringUtil.isEmpty(etPassword.getText().toString())) {


                            request.setUserName(etAccount.getText().toString());

                            try {
                                String passmd= MD5.md5(etPassword.getText().toString());
                                request.setPassWord(passmd);

                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            mLoad = LoadUtils.createLoadingDialog(buttonLogin.this, "登录中...");
                            mHandler.sendEmptyMessageDelayed(1, 10000);
                            request.Login(new ResponseHandler() {
                                @Override
                                public void success(CommonResponse response) {

                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("Id",  response.getResMsg());
                                    editor.commit();

                                    JPushInterface.setAlias(buttonLogin.this,1,response.getResMsg());


                                    CommonRequest request = new CommonRequest();
                                    request.setTable("table_user_info");
                                    request.setList("");
                                    request.setId(response.getResMsg());
                                    request.Query(new ResponseHandler() {
                                        @Override
                                        public void success(CommonResponse response) {
                                            ACache aCache =ACache.get(buttonLogin.this);
                                            ArrayList<HashMap<String, String>> list = response.getDataList();
                                            if(list.size()>0){
                                                HashMap<String, String> map = list.get(0);


                                              String[] quanziid =  StringUtil.ChangetoString(map.get("userCommunity"));
                                              String name = map.get("userName");
                                                aCache.put("name",name);

                                                List array = new ArrayList();

                                                for(int i=0;i<quanziid.length;i++)
                                                array.add(quanziid[i]);

                                                Set tags = new HashSet(Arrays.asList(array));
                                                JPushInterface.setTags(buttonLogin.this,1,tags);



                                              aCache.put("quanziidacache",quanziid);
                                                CommonRequest request = new CommonRequest();
                                                request.setTable("table_community_info");
                                                request.setWhereEqualMoreTo("Id",quanziid);
                                                request.Query(new ResponseHandler() {
                                                    @Override
                                                    public void success(CommonResponse response) {
                                                        ACache aCache =ACache.get(buttonLogin.this);

                                                        ArrayList<HashMap<String, String>> list = response.getDataList();
                                                        if(list.size()>0){
                                                            String[] quanzi = new String[list.size()];
                                                            for(int i=0;i<list.size();i++) {

                                                                HashMap<String, String> map = list.get(i);
                                                               quanzi[i]=map.get("communityName");

                                                            }

                                                            aCache.put("quanziacache",quanzi);
                                                            mLoad.dismiss();

                                                            Toast.makeText(buttonLogin.this, "登录成功", Toast.LENGTH_SHORT).show();

                                                            System.out.print("看这里看这里"+sp.getBoolean("Firstsignin",true));

                                                         if( sp.getBoolean("Firstsignin",true)){      //判断是否为第一次登陆



                                                             SharedPreferences.Editor editor=sp.edit();
                                                             editor.putBoolean("Firstsignin", false);
                                                             editor.commit();

                                                             Intent intent = new Intent(buttonLogin.this, FirstLogin.class);
                                                             startActivity(intent);
                                                             finish();

                                                            }

                                                            else {


                                                             Intent intent = new Intent(buttonLogin.this, MainActivity.class);
                                                             startActivity(intent);
                                                             finish();

                                                         }
                                                        }
                                                    }

                                                    @Override
                                                    public void fail(String failCode, String failMsg) {
                                                        mLoad.dismiss();
                                                    }
                                                });

                                            }

                                        }

                                        @Override
                                        public void fail(String failCode, String failMsg) {
                                            mLoad.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void fail(String failCode, String failMsg) {
                                    mLoad.dismiss();
                                    Toast.makeText(buttonLogin.this, "学号或密码错误", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {




                            Toast.makeText(buttonLogin.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();

                        }

                    }


                }
        });



        Button bttest = (Button) findViewById(R.id.test); //测试用跳转

        bttest.setOnClickListener((v -> {
            Intent intent = new Intent(buttonLogin.this,MainActivity.class );
            startActivity(intent);
        })
        );

    }






    boolean checkNetwork() {
        // 实例化ConnectivityManager
        ConnectivityManager manager = (ConnectivityManager) buttonLogin.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获得当前网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        // 判断是否连接
        if (info == null || !info.isConnected()) {
            return false;
        }
        return true;
    }   //检查网络


}