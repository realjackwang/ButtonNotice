package com.notice.button.button;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notice.button.button.service.CommonRequest;
import com.notice.button.button.service.CommonResponse;
import com.notice.button.button.service.Constant;
import com.notice.button.button.service.HttpPostTask;
import com.notice.button.button.service.ResponseHandler;
import com.notice.button.button.util.MD5;
import com.notice.button.button.util.StringUtil;

import java.security.NoSuchAlgorithmException;

public class Notice_Login extends AppCompatActivity  {

    private EditText etAccount;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_login);

        etAccount = (EditText) findViewById(R.id.UserId);
        etPassword = (EditText) findViewById(R.id.UserKey);

//        Button btnRegister = (Button) findViewById(R.id.login);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                        if (!StringUtil.isEmpty(etAccount.getText().toString())
//                                && !StringUtil.isEmpty(etPassword.getText().toString())) {
//
//
//                            final CommonRequest request = new CommonRequest();
//                            request.addRequestParam("name", etAccount.getText().toString());
//
//                            try {
//                                String passmd= MD5.md5(etPassword.getText().toString());
//
//                                request.addRequestParam("password", passmd );
//
//                            } catch (NoSuchAlgorithmException e) {
//
//                                e.printStackTrace();
//                            }
//
//
//
//                           sendHttpPostRequest(Constant.URL_Register, request, new ResponseHandler() {
//
//                                @Override
//                                public String success(CommonResponse response) {
//                                    Toast.makeText(Notice_Login.this, "注册成功"+response.getResCode(), Toast.LENGTH_SHORT).show();
//                                    return null;
//                                }
//
//                                @Override
//                                public String fail(String failCode, String failMsg) {
//                                    Toast.makeText(Notice_Login.this, failMsg, Toast.LENGTH_SHORT).show();
//                                    return failCode;
//                                }
//                            }, true);
//
//
//                        } else {
//                            Toast.makeText(Notice_Login.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//
//
//
//
//        });


        Button btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (!StringUtil.isEmpty(etAccount.getText().toString())
                                && !StringUtil.isEmpty(etPassword.getText().toString())) {

                            final CommonRequest request = new CommonRequest();
                            request.setUserName(etAccount.getText().toString());

                            try {
                                String passmd=MD5.md5(etPassword.getText().toString());
                                request.setPassWord(passmd);

                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }

                       request.Updata(request, new ResponseHandler() {
                           @Override
                           public void success(CommonResponse response) {

                           }

                           @Override
                           public void fail(String failCode, String failMsg) {

                           }
                       });
                            request.Login(new ResponseHandler() {
                                @Override
                                public void success(CommonResponse response) {
                                    Toast.makeText(Notice_Login.this, "登录成功"+response.getResCode(), Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(Notice_Login.this,Notice_List.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void fail(String failCode, String failMsg) {
                                    Toast.makeText(Notice_Login.this, "登录失败", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {

                            Toast.makeText(Notice_Login.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();

                        }

                    }



        });

    }


}