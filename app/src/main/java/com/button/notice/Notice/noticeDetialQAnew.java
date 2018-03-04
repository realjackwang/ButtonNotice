package com.button.notice.Notice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

public class noticeDetialQAnew extends AppCompatActivity {

    private String QAtitle ;
    private String QAinfo ;
    private String userId;
    private CheckBox Hide;
    private String hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial_qanew);

        //*****************************//
        //返回按钮
        Button back = (findViewById(R.id.back));
        back.setOnClickListener((view -> {
            finish();
        }));
        //*****************************//




        //*****************************//
        //提交按钮点击监听
        Button submit = (findViewById(R.id.save));
        submit.setOnClickListener((view -> {
            //获取用户输入的内容
            EditText NewQATitle = findViewById(R.id.QAtitle);
            EditText NewQAinfo = findViewById(R.id.QAinfo);
            QAtitle= NewQATitle.getText().toString();
            QAinfo =NewQAinfo.getText().toString();
            //获取noticeDetailQA活动传来的父通知的id
            Intent intent = getIntent();
            String fatherNoticeId = intent.getStringExtra("noticeId");
            //******************************************************
            //是否匿名判断
            Hide = findViewById(R.id.hide);
            Hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        hide="true";
                        Toast.makeText(noticeDetialQAnew.this,"匿名发布",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        hide="false";
                        Toast.makeText(noticeDetialQAnew.this,"不匿名发布",Toast.LENGTH_SHORT).show();}

                }
            });
            //******************************************************
            //上传数据
            CommonRequest request = new CommonRequest();
            request.setTable("table_question_info");
            userId = request.getCurrentId(noticeDetialQAnew.this);
            request.addRequestParam("questionTitle",QAtitle);
            request.addRequestParam("questionInfo",QAinfo);
            request.addRequestParam("activityUser","");
            request.addRequestParam("questionNotice",fatherNoticeId);
            request.addRequestParam("questionHide",hide);
            request.addRequestParam("questionUser",userId);
            request.Create(request, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast.makeText(noticeDetialQAnew.this,"发送成功",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void fail(String failCode, String failMsg) {
                    Toast.makeText(noticeDetialQAnew.this,"发送失败",Toast.LENGTH_SHORT).show();
                }
            });
            //******************************************************
        }));
    }

}
