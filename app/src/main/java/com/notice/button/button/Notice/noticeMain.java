package com.notice.button.button.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.notice.button.button.Button.buttonLogin;
import com.notice.button.button.R;
import com.notice.button.button.User.userInfo;

import java.util.ArrayList;
import java.util.List;

public class noticeMain extends Activity {


    private List<noticeDetail> notice_list = new ArrayList<>();//声明列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_main);
        final Button btnLogin = (Button) findViewById(R.id.login);



        initNotice();//初始化通知数据

        noticeListAdapter adapter = new noticeListAdapter(noticeMain.this, R.layout.activity_notice_detail, notice_list);
        ListView listView =  findViewById(R.id.Notice_list);
        listView.setAdapter(adapter);


        Button toNewNotice =  findViewById(R.id.toNewNotice);//新通知跳转
        toNewNotice.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(noticeMain.this,noticeNew.class );
                        startActivity(intent);
                    }
                })
        );

    }



    private void initNotice() {
        noticeDetail no1 = new noticeDetail("Apple", "11.2", "fsef", "2e", "gvsdvg");
        notice_list.add(no1);
    }



}
