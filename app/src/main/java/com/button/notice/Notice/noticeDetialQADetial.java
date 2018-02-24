package com.button.notice.Notice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;

public class noticeDetialQADetial extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial_qadetial);

        //接收父通知的作者的id
        Intent intent = getIntent();
        String author = intent.getStringExtra("author");

        if (userId==author){
            Toast.makeText(noticeDetialQADetial.this,"你是通知的发送者",Toast.LENGTH_SHORT).show();

        }


        Intent intent1 = getIntent();
        String id = intent.getStringExtra("QAid");


    }
}
