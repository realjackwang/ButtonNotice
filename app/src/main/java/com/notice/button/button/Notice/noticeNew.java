package com.notice.button.button.Notice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notice.button.button.R;

public class noticeNew extends AppCompatActivity {

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

            }
        });
        //////

        EditText NewNoticeTitle = (EditText)findViewById(R.id.NewNoticeTitle);
        EditText NewNoticeText = (EditText)findViewById(R.id.NewNoticeText);




    }
}
