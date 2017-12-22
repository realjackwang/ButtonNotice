package com.notice.button.button;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class Notice_List extends Activity {

    private List<Notice_detial> notice_list = new ArrayList<Notice_detial>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);


        initNotice();//初始化通知数据

        noticeAdapter adapter = new noticeAdapter(Notice_List.this, R.layout.notice_detial, notice_list);
        ListView listView = (ListView) findViewById(R.id.Notice_list);
        listView.setAdapter(adapter);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice_List.this, User_information.class);
                startActivity(intent);
            }
        });
    }


    private void initNotice() {
        Notice_detial no1 = new Notice_detial("Apple", "11.2", "fsef", "2e", "gvsdvg");
        notice_list.add(no1);

    }
}
