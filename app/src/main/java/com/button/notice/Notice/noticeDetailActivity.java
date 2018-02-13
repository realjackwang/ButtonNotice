package com.button.notice.Notice;

import android.content.Intent;
import android.renderscript.Int2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.button.notice.Fragment.MainActivity;
import com.button.notice.Fragment.noticeFragment;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;

import static cn.jpush.android.api.JPushInterface$a.v;


public class noticeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Button back = findViewById(R.id.back);
        Button query = findViewById(R.id.query);
        Button collect = findViewById(R.id.collect);
        Button read = findViewById(R.id.read);

        back.setOnClickListener((view -> {
            Intent intent = new Intent(noticeDetailActivity.this,MainActivity.class);
            startActivity(intent);
        })
        );

        query.setOnClickListener((view -> {
            Intent intent = new Intent(noticeDetailActivity.this,noticeDetialQA.class);
            startActivity(intent);
        }));

        TextView title = (findViewById(R.id.noticeTitle));
        TextView author = (findViewById(R.id.Author));
        TextView createTime = (findViewById(R.id.noticeCreateTime));
        TextView text = (findViewById(R.id.Text));

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        CommonRequest request1 = new CommonRequest();
        request1.setTable("table_notice_info");
        request1.setWhereEqualTo("Id",id);
        request1.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String,String> map=list.get(0);
                String Title =map.get("noticeTitle");
                String CreateDate = map.get("noticeCreateTime");
                String Author = map.get("noticeUser");
                String Text=map.get("noticeText");

                title.setText("标题："+Title);
                createTime.setText("发布日期："+CreateDate);
                author.setText("作者："+Author);
                text.setText("内容："+Text);

            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });
    }


}
