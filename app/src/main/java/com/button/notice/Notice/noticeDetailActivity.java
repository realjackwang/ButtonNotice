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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;

import static cn.jpush.android.api.JPushInterface$a.v;
import static cn.jpush.android.api.JPushInterface$a.w;


public class noticeDetailActivity extends AppCompatActivity {

    private int whetherCollect=0;




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
                    Button ifcollect =(findViewById(R.id.collect));
                    ifcollect.setVisibility(View.VISIBLE);
                    Button ifcollect1 = (findViewById(R.id.collect1));
                    ifcollect1.setVisibility(View.GONE);
                   }

            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(noticeDetailActivity.this,"收藏失败，请重试",Toast.LENGTH_SHORT).show();

            }
        });


        ifcollect1.setOnClickListener((view -> {
            ifcollect1.setVisibility(View.GONE);
            ifcollect.setVisibility(View.VISIBLE);
            CommonRequest request1 = new CommonRequest();
            request1.setTable("table_user_info");
            request1.setList("userCollection");
            request1.setText(noticeId);
            request1.Connect(noticeDetailActivity.this);
            Toast.makeText(noticeDetailActivity.this, noticeId + "，取消收藏成功", Toast.LENGTH_SHORT).show();

        }));

        ifcollect.setOnClickListener(view -> {
            ifcollect.setVisibility(View.GONE);
            ifcollect1.setVisibility(View.VISIBLE);
            CommonRequest request5 = new CommonRequest();
            request5.setTable("table_user_info");
            request5.setList("userCollection");
            request5.setText(noticeId);
            request5.Connect(noticeDetailActivity.this);
            Toast.makeText(noticeDetailActivity.this, noticeId + "，收藏成功", Toast.LENGTH_SHORT).show();

        });





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
        //收藏
        collect.setOnClickListener((view -> {





        }));


    }








}

