package com.notice.button.button.Notice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notice.button.button.R;
import com.notice.button.button.service.CommonRequest;
import com.notice.button.button.service.CommonResponse;
import com.notice.button.button.service.ResponseHandler;

/**
 * Created by Jill on 2017/12/25.,,
 */

public class noticeNew extends AppCompatActivity {
    private String newnoticetitle;
    private String newnoticetext;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_new);



        Button Back =(Button)findViewById(R.id.back);
        Button Submit = (Button)findViewById(R.id.submit);

        //返回按钮的监听事件
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noticeNew.this,noticeMain.class);
                startActivity(intent);
            }
        });
        /////

        //提交按钮的监听事件
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonRequest request = new CommonRequest();
                userId = request.getCurrentId(noticeNew.this);
                EditText NewNoticeTitle = (EditText)findViewById(R.id.NewNoticeTitle);
                EditText NewNoticeText = (EditText)findViewById(R.id.NewNoticeText);

                newnoticetitle= NewNoticeTitle.getText().toString();
                newnoticetext= NewNoticeText.getText().toString();
                request.setTable("table_notice_info");

                request.addRequestParam("noticeTitle",newnoticetitle);
                request.addRequestParam("noticeText",newnoticetext);
                request.addRequestParam("noticeUser",userId);
                request.Create(request, new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {

                        Toast.makeText(noticeNew.this,"发送成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void fail(String failCode, String failMsg) {
                        Toast.makeText(noticeNew.this,"发送失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //////














    }
}
