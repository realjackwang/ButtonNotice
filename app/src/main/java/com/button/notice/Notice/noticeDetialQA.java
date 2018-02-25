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

public class noticeDetialQA extends AppCompatActivity implements AdapterView.OnItemClickListener {


   private ListView QAList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial_q);

        //假装自己是只ios的返回按钮
        Button back = (findViewById(R.id.back));
        back.setOnClickListener((view -> {
            finish();
        }));

        //本地存储缓存的列表
        ACache aCache = ACache.get(noticeDetialQA.this);
        ArrayList<HashMap<String,String>> arrayList =(ArrayList<HashMap<String,String>>) aCache.getAsObject("QALoad");
        if(arrayList!=null) {
            SimpleAdapter adapter = new SimpleAdapter(noticeDetialQA.this,
                    arrayList, R.layout.qa_list,
                    new String[]{"QATitle", "QAInfo"},
                    new int[]{R.id.title, R.id.info});
            QAList.setAdapter(adapter);

        }

        CommonRequest request = new CommonRequest();
        request.setTable("table_question_info");
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> arrayList1 = response.getDataList();
                ACache aCache1 = ACache.get(noticeDetialQA.this);
                aCache1.put("QALoad", arrayList1);
                SimpleAdapter adapter =new SimpleAdapter(noticeDetialQA.this, arrayList1,R.layout.qa_list,new String[]{"QATitle", "QAInfo"},new int[]{R.id.title, R.id.info});
                QAList.setAdapter(adapter);
            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });




//新问题跳转按钮
        Button Quest = (findViewById(R.id.newQA));
        Quest.setOnClickListener((view -> {
//接收noticeDetailActivity传来的问题所属的通知的id
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");

            //传父通知id给发布新提问的活动
            Intent intent2 = new Intent();
            intent2.putExtra("fatherId", id);
            intent2.setClass(noticeDetialQA.this, noticeDetialQAnew.class);
            noticeDetialQA.this.startActivity(intent2);
        }));




    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        HashMap<String,String> map=(HashMap<String,String>) QAList.getItemAtPosition(position);
        String Text=map.get("Id");//获取点的那个item的id

        //接收noticeDetailActivity发来的父通知的作者的id
        Intent intent3 = getIntent();
        String Author = intent3.getStringExtra("author");

        Intent intent = new Intent();
        intent.putExtra("QAid", Text);
        intent.putExtra("author",Author);
        intent.setClass(noticeDetialQA.this, noticeDetialQADetial.class);
        noticeDetialQA.this.startActivity(intent);


    }
}
