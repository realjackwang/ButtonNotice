package com.button.notice.Notice;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.renderscript.Int2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.Fragment.MainActivity;
import com.button.notice.Fragment.noticeFragment;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;

import static cn.jpush.android.api.JPushInterface$a.v;
import static cn.jpush.android.api.JPushInterface$a.w;


public class noticeDetailActivity extends AppCompatActivity {

    private int whetherCollect=0;
     String author;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Button back = findViewById(R.id.back);
        Button query = findViewById(R.id.query);
        Button collect = findViewById(R.id.collect);
        Button read = findViewById(R.id.read);

        //**********************//
        // 返回按钮点击事件
        back.setOnClickListener((view -> {
                    finish();
                })
        );
        //**********************//



        //**********************//
        //通知详情的内容
        TextView title = (findViewById(R.id.noticeTitle));
        TextView author = (findViewById(R.id.Author));
        TextView createTime = (findViewById(R.id.noticeCreateTime));
        TextView text = (findViewById(R.id.Text));
        //**********************//

        //**********************//
        //接收intent传来的notice的id的值
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        //查询数据库  获取点击的通知的内容
        CommonRequest request2 = new CommonRequest();
        request2.setTable("table_notice_info");
        request2.setWhereEqualTo("Id", id);
        request2.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String, String> map = list.get(0);
                String Title = map.get("noticeTitle");
                String CreateDate = map.get("noticeCreateTime");
                String Author = map.get("noticeUser");
                String Text = map.get("noticeText");

                title.setText("标题：" + Title);
                createTime.setText("发布日期：" + CreateDate);
                author.setText("作者：" + Author);
                text.setText("内容：" + Text);
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(noticeDetailActivity.this, "加载失败 请重试", Toast.LENGTH_SHORT).show();

            }
        });
        //**********************//

        //**********************//
        //问询按钮点击跳转页面
        query.setOnClickListener((view -> {
            //传父通知id
            Intent intent3 = new Intent();
            intent3.putExtra("id", id);
            intent3.setClass(noticeDetailActivity.this, noticeDetialQA.class);
            noticeDetailActivity.this.startActivity(intent3);
//查询数据库看通知的最初发送者并想把他通过noticeDetailQA传给noticeDetailQADetail
        CommonRequest request1 = new CommonRequest();
        request1.setTable("table_notice_info");
        request1.setWhereEqualTo("Id", id);
        request1.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String, String> map = list.get(0);
                String Author = map.get("noticeUser");
                Intent intent = new Intent();
                intent.putExtra("author",Author);
                intent.setClass(noticeDetailActivity.this,noticeDetialQA.class);
                noticeDetailActivity.this.startActivity(intent);

            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(noticeDetailActivity.this, "失败", Toast.LENGTH_SHORT).show();

            }
        }); }));

        //**********************//
        //收藏
        collect.setOnClickListener((view -> {

            if (whetherCollect==0) {
                whetherCollect=1;

                CommonRequest request = new CommonRequest();
                request.setTable("table_user_info");
                request.setList("userCollection");
                request.setText(id);

                request.Connect(noticeDetailActivity.this);
                Toast.makeText(noticeDetailActivity.this, id + "，收藏成功", Toast.LENGTH_SHORT).show();
            }
            else if (whetherCollect==1){
                whetherCollect=0;


                CommonRequest request = new CommonRequest();
                request.setTable("table_user_info");
                request.setList("userCollection");
                request.setText(id);

                request.Connect(noticeDetailActivity.this);
                Toast.makeText(noticeDetailActivity.this, id + "，收藏成功", Toast.LENGTH_SHORT).show();
            }
            //**********************//

        }));


    }

}

