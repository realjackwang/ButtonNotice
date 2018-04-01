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
import com.button.notice.util.StringUtil;
import com.button.notice.Notice.what_is_not_activity.myToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;

import static android.widget.Toast.makeText;
import static cn.jpush.android.api.JPushInterface$a.m;
import static cn.jpush.android.api.JPushInterface$a.u;
import static cn.jpush.android.api.JPushInterface$a.v;
import static cn.jpush.android.api.JPushInterface$a.w;


public class noticeDetailActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Button back = findViewById(R.id.back);
        Button query = findViewById(R.id.query);
        Button ifread = findViewById(R.id.read);
        Button ifread1=findViewById(R.id.read1);
        TextView ddline =findViewById(R.id.Ddline);

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
        String noticeId = intent.getStringExtra("id");
        //查询数据库  获取点击的通知的内容
        CommonRequest request2 = new CommonRequest();
        request2.setTable("table_notice_info");
        request2.setWhereEqualTo("Id", noticeId);
        request2.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String, String> map = list.get(0);
                String Title = map.get("noticeTitle");
                String CreateDate = map.get("createTime").substring(5,map.get("createTime").length()-2);

                String Author = map.get("noticeUser");
                String Text = map.get("noticeText");
                String Ddline =(map.get("noticeDate")+" "+ map.get("noticeTime")).substring(0,map.get("createTime").length()-5);

                title.setText( Title);
                createTime.setText( CreateDate);
                ddline.setText("事件截止时间："+Ddline);
                author.setText("作者：" + Author);
                text.setText(Text);
            }

            @Override
            public void fail(String failCode, String failMsg) {
                makeText(noticeDetailActivity.this, "加载失败 请重试", Toast.LENGTH_SHORT).show();

            }
        });
        //**********************//

        //**********************//
        //查询数据库获取收藏状态以正确显示按钮
        Button ifcollect1 = (findViewById(R.id.collect1));
        Button ifcollect =(findViewById(R.id.collect));
        CommonRequest request = new CommonRequest();
        request.setTable("table_user_info");
        String userId = request.getCurrentId(noticeDetailActivity.this);
        request.setWhereEqualTo("userId",userId);
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String, String> map = list.get(0);
                String collect = map.get("userCollection");
                String ifCollect ="false";
                StringUtil stringUtil = new StringUtil();
                String[] currentCollection = stringUtil.ChangetoString(collect);
                for(int i=0;i<currentCollection.length;i++){
                    if (currentCollection[i].equals(noticeId)){
                        ifCollect="true";
                        break;
                    }
                }

                if (ifCollect.equals("true")){
                    ifcollect.setVisibility(View.GONE);
                    ifcollect1.setVisibility(View.VISIBLE); }

                else if (ifCollect.equals("false")){
                    ifcollect.setVisibility(View.VISIBLE);
                    ifcollect1.setVisibility(View.GONE);
                }

            }

            @Override
            public void fail(String failCode, String failMsg) {
                makeText(noticeDetailActivity.this,"请刷新",Toast.LENGTH_SHORT).show();

            }
        });
        //**********************//

        //**********************//
        //查询数据库获取已读状态以正确显示按钮
        CommonRequest requestRead = new CommonRequest();
        requestRead.setTable("table_user_info");
        requestRead.setWhereEqualTo("userId",userId);
        //Toast.makeText(noticeDetailActivity.this,"查询了",Toast.LENGTH_SHORT).show();
        requestRead.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String, String> map = list.get(0);
                String read = map.get("userRead");
                String ifRead ="false";
                StringUtil stringUtil1 = new StringUtil();
                String[] currentRead = stringUtil1.ChangetoString(read);
                for(int i=0;i<currentRead.length;i++){
                    if (currentRead[i].equals(noticeId)){
                        ifRead="true";
                        break;
                    }
                }

                if (ifRead.equals("true")){
                    ifread.setVisibility(View.GONE);
                    ifread1.setVisibility(View.VISIBLE); }

                else if (ifRead.equals("false")){
                    ifread.setVisibility(View.VISIBLE);
                    ifread1.setVisibility(View.GONE);
                }

            }

            @Override
            public void fail(String failCode, String failMsg) {
                makeText(noticeDetailActivity.this,"请刷新",Toast.LENGTH_SHORT).show();
            }
        });
        //**********************//


        //**********************//
        //收藏按钮功能 向数据库写入或删除部分
        //删除
        ifcollect1.setOnClickListener((view -> {
            ifcollect1.setVisibility(View.GONE);
            ifcollect.setVisibility(View.VISIBLE);
            CommonRequest request5 = new CommonRequest();
            request5.setTable("table_user_info");
            request5.setList("userCollection");
            request5.setId(userId);
            request5.setText(noticeId);
            request5.Connect("0", new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast toast1=Toast.makeText(noticeDetailActivity.this, noticeId + "，取消收藏成功", Toast.LENGTH_SHORT);
                    myToast.showMyToast(toast1, 1*1000);
                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });

        }));
        //写入
        ifcollect.setOnClickListener(view -> {
            ifcollect.setVisibility(View.GONE);
            ifcollect1.setVisibility(View.VISIBLE);
            CommonRequest request6 = new CommonRequest();
            request6.setTable("table_user_info");
            request6.setList("userCollection");
            request6.setId(userId);
            request6.setText(noticeId);
            request6.Connect("0", new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast toast=Toast.makeText(noticeDetailActivity.this, noticeId + "，收藏成功", Toast.LENGTH_LONG);
                    myToast.showMyToast(toast, 1*1000);
                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });


        });
        //**********************//

        //**********************//
        //已读按钮功能 向数据库写入或删除部分
        //删除
        ifread1.setOnClickListener((view -> {
            ifread1.setVisibility(View.GONE);
            ifread.setVisibility(View.VISIBLE);
            CommonRequest request6 = new CommonRequest();
            request6.setTable("table_user_info");
            request6.setList("userRead");
            request6.setId(userId);
            request6.setText(noticeId);
            request6.Disconnect("3", new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast toast2=Toast.makeText(noticeDetailActivity.this, noticeId + "，取消已读成功", Toast.LENGTH_LONG);
                    myToast.showMyToast(toast2, 1*1000);
                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });

        }));
        //写入
        ifread.setOnClickListener(view -> {
            ifread.setVisibility(View.GONE);
            ifread1.setVisibility(View.VISIBLE);
            CommonRequest request7 = new CommonRequest();
            request7.setTable("table_user_info");
            request7.setList("userRead");
            request7.setId(userId);
            request7.setText(noticeId);
            request7.Connect("3", new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast toast1=Toast.makeText(noticeDetailActivity.this, noticeId + "，已读本条通知，将反馈给发布者", Toast.LENGTH_LONG);
                    myToast.showMyToast(toast1, 1*1000);
                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });


        });
        //**********************//


        //**********************//
        //问询按钮点击跳转页面
        query.setOnClickListener((view -> {
            //传父通知id
            Intent intent3 = new Intent();
            intent3.putExtra("noticeId", noticeId);
            intent3.setClass(noticeDetailActivity.this, noticeDetialQA.class);
            noticeDetailActivity.this.startActivity(intent3);

        }));
        //**********************//


    }

}

