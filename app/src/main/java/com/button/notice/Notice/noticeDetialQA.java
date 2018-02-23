package com.button.notice.Notice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.button.notice.R;
import com.button.notice.util.ACache;

import java.util.ArrayList;
import java.util.HashMap;

public class noticeDetialQA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial_q);

        Button back = (findViewById(R.id.back));
        back.setOnClickListener((view -> {
            finish();
        }));

        Button Quest = (findViewById(R.id.newQA));
        back.setOnClickListener((view -> {
            Intent intent = new Intent(noticeDetialQA.this,noticeDetialQAnew.class);
            startActivity(intent);
        }));




    }
}
