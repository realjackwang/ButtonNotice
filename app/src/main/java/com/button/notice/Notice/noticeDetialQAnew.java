package com.button.notice.Notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.button.notice.R;

public class noticeDetialQAnew extends AppCompatActivity {

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
        

    }



}
