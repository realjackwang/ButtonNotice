package com.button.notice.Notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.button.notice.R;

public class noticeDetialQA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial_q);

        Button back = (findViewById(R.id.back));
        back.setOnClickListener((view -> {
            finish();
        }));

    }
}
