package com.button.notice.Notice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class noticeDetialQA extends AppCompatActivity  {


   private ListView QAList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //接收noticeDetailActivity传来的问题所属的通知的id
        Intent intent = getIntent();
        String id = intent.getStringExtra("noticeId");
        setContentView(R.layout.activity_notice_detial_q);

        //假装自己是只ios的返回按钮
        Button back = (findViewById(R.id.back));
        back.setOnClickListener((view -> {
            finish();
        }));

        //本地存储缓存的列表
        QAList=findViewById(R.id.listView1);

        ACache aCache = ACache.get(noticeDetialQA.this);
        ArrayList<HashMap<String,String>> arrayList =(ArrayList<HashMap<String,String>>) aCache.getAsObject("qaList");
        if(arrayList!=null) {
            SimpleAdapter adapter = new SimpleAdapter(noticeDetialQA.this,
                    arrayList, R.layout.qa_list,
                    new String[]{"questionTitle", "questionInfo"},
                    new int[]{R.id.QAtitle, R.id.QAinfo});
            QAList.setAdapter(adapter);

        }


        //加载列表
        CommonRequest request = new CommonRequest();
        request.setTable("table_question_info");
        request.setWhereEqualTo("questionNotice",id);
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> arrayList1 = response.getDataList();
                SimpleAdapter adapter =new SimpleAdapter(noticeDetialQA.this, arrayList1,R.layout.qa_list,new String[]{"questionTitle", "questionInfo"},new int[]{R.id.QAtitle, R.id.QAinfo});
                QAList.setAdapter(adapter);
            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });

        //****************************************//
        //新问题跳转按钮
        Button Quest = (findViewById(R.id.newQA));
        Quest.setOnClickListener((view -> {


            //传父通知id给发布新提问的活动
            Intent intent2 = new Intent();
            intent2.putExtra("noticeId", id);
            intent2.setClass(noticeDetialQA.this, noticeDetialQAnew.class);
            noticeDetialQA.this.startActivity(intent2);

        }));
        QAList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map=(HashMap<String,String>) QAList.getItemAtPosition(i);
                String QAid=map.get("Id");//获取点的那个item的id

                //查询父通知的发送者的id
                CommonRequest request1 = new  CommonRequest();
                request1.setTable("table_notice_info");
                Intent intent = getIntent();
                String fatherNoticeId = intent.getStringExtra("noticeId");
                request1.setWhereEqualTo("Id",fatherNoticeId);
                request1.Query(new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {
                        ArrayList<HashMap<String, String>> list = response.getDataList();
                        HashMap<String, String> map = list.get(0);
                        String Author = map.get("noticeUser");
                        //把通知的发送者id和问题的ID和通知的ID传给qaDetail
                        Intent intent2 = new Intent();
                        intent2.putExtra("author", Author);
                        intent2.putExtra("QAid",QAid);
                        intent2.putExtra("fatherNoticeId",fatherNoticeId);
                        intent2.setClass(noticeDetialQA.this, noticeDetialQADetial.class);
                        noticeDetialQA.this.startActivity(intent2);
                    }

                    @Override
                    public void fail(String failCode, String failMsg) {

                    }
                });

            }
        });

    }


}
