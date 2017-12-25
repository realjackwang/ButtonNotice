package com.notice.button.button.Button;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notice.button.button.Fragment.MainActivity;
import com.notice.button.button.Notice.noticeMain;
import com.notice.button.button.R;
import com.notice.button.button.service.CommonRequest;
import com.notice.button.button.service.CommonResponse;
import com.notice.button.button.service.ResponseHandler;
import com.notice.button.button.util.MD5;
import com.notice.button.button.util.StringUtil;

import java.security.NoSuchAlgorithmException;

public class buttonLogin extends AppCompatActivity  {

    private EditText etAccount;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_login);
        final SharedPreferences sp=this.getSharedPreferences("DODODO",Context.MODE_PRIVATE);

        etAccount = (EditText) findViewById(R.id.UserId);
        etPassword = (EditText) findViewById(R.id.UserKey);

        final Button btnLogin = (Button) findViewById(R.id.login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonRequest request = new CommonRequest();

                        if (!StringUtil.isEmpty(etAccount.getText().toString())
                                && !StringUtil.isEmpty(etPassword.getText().toString())) {


                            request.setUserName(etAccount.getText().toString());

                            try {
                                String passmd=MD5.md5(etPassword.getText().toString());
                                request.setPassWord(passmd);

                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }

                            request.Login(new ResponseHandler() {
                                @Override
                                public void success(CommonResponse response) {

                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("Id",  response.getResMsg());
                                    editor.commit();

                                    Toast.makeText(buttonLogin.this, "登录成功"+response.getResCode(), Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(buttonLogin.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                                @Override
                                public void fail(String failCode, String failMsg) {
                                    Toast.makeText(buttonLogin.this, "登录失败", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {

                            Toast.makeText(buttonLogin.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();

                        }

                    }



        });


        Button bttest = (Button) findViewById(R.id.test); //测试用跳转

        bttest.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(buttonLogin.this,noticeMain.class );
                        startActivity(intent);
                    }
                })
        );

    }


}