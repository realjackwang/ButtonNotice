package com.notice.button.button.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.notice.button.button.R;
import com.notice.button.button.User.userInfo;

import java.util.ArrayList;
import java.util.List;

public class noticeMain extends Activity {

    private List<noticeDetail> notice_list = new ArrayList<noticeDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);


        initNotice();//初始化通知数据

        noticeListAdapter adapter = new noticeListAdapter(noticeMain.this, R.layout.activity_notice_ditail, notice_list);
        ListView listView = (ListView) findViewById(R.id.Notice_list);
        listView.setAdapter(adapter);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noticeMain.this, userInfo.class);
                startActivity(intent);
            }
        });
    }


    private void initNotice() {
        noticeDetail no1 = new noticeDetail("Apple", "11.2", "fsef", "2e", "gvsdvg");
        notice_list.add(no1);

    }
}
