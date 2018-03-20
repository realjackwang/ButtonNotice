package com.button.notice.Notice;

import android.content.Intent;
import android.service.quicksettings.Tile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class noticeDetialQADetial extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial_qadetial);

        TextView title = (findViewById(R.id.title));
        TextView text= (findViewById(R.id.text));
        TextView author =findViewById(R.id.Author);
        TextView answered=findViewById(R.id.answeredText);


        EditText anwser = findViewById(R.id.answerText);
        Button submit = findViewById(R.id.submit);
        anwser.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);





        //获取noticeDetailQA传过来的父通知的作者和问题的id
        Intent intent1 = getIntent();
        String id = intent1.getStringExtra("QAid");
        String Author = intent1.getStringExtra("author");
        String fatherNoticeId = intent1.getStringExtra("fatherNoticeId");


        CommonRequest requestx = new CommonRequest();
        requestx.setTable("table_question_info");
        requestx.setWhereEqualTo("Id",id);
        requestx.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
             ArrayList<HashMap<String,String>> arrayList = response.getDataList();
             HashMap<String,String> map = arrayList.get(0);
             if(map.get("questionAnswer")!=null&&!map.get("questionAnswer").equals("")){
                 answered.setVisibility(View.VISIBLE);
                 answered.setText(map.get("questionAnswer"));

                 if (userId.equals(Author)){
                     Toast.makeText(noticeDetialQADetial.this,"原通知作者，请编辑回答",Toast.LENGTH_SHORT).show();
                     anwser.setVisibility(View.VISIBLE);
                     submit.setVisibility(View.VISIBLE);
                     submit.setText("修改回答");
                     answered.setVisibility(View.GONE);
                     anwser.setText(map.get("questionAnswer"));
                 }
                 else{
                     anwser.setVisibility(View.GONE);
                     submit.setVisibility(View.GONE);
                     answered.setText(map.get("questionAnswer"));
                 }

             }

             else{


                 if (userId.equals(Author)){
                     Toast.makeText(noticeDetialQADetial.this,"原通知作者，请编辑回答",Toast.LENGTH_SHORT).show();
                     anwser.setVisibility(View.VISIBLE);
                     submit.setVisibility(View.VISIBLE);
                     answered.setVisibility(View.GONE);
                 }


             }


            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });

        CommonRequest request0 = new CommonRequest();
        userId = request0.getCurrentId(noticeDetialQADetial.this);

//        if (userId.equals(Author)){
//            Toast.makeText(noticeDetialQADetial.this,"原通知作者，请编辑回答",Toast.LENGTH_SHORT).show();
//            anwser.setVisibility(View.VISIBLE);
//            submit.setVisibility(View.VISIBLE);
//            answered.setVisibility(View.GONE);
//        }

        CommonRequest request = new CommonRequest();
        request.setTable("table_question_info");
        request.setWhereEqualTo("Id",id);
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> list = response.getDataList();
                HashMap<String, String> map = list.get(0);
                String Title = map.get("questionTitle");
                String Text = map.get("questionInfo");
                String Author = map.get("questionUser");
                String Hide =map.get("questionHide");
                String Answer =map.get("questionAnswer");
                if (Hide.equals("false"))
                {
                    author.setText("作者："+Author);
                }


                title.setText("问题:"+Title);
                text.setText("问题描述："+Text);

            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });

        submit.setOnClickListener((view -> {
            String authorAnswer = anwser.getText().toString();
            CommonRequest request1=new CommonRequest();
            request1.setTable("table_question_info");
            request1.setId(id);
            request1.addRequestParam("questionAnswer",authorAnswer);
            request1.Updata(request1, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    anwser.setText(authorAnswer);
                    Toast.makeText(noticeDetialQADetial.this,"发送成功",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void fail(String failCode, String failMsg) {
                    Toast.makeText(noticeDetialQADetial.this,"发送失败",Toast.LENGTH_SHORT).show();
                }
            });
        }));

    }


    public  void back(View view){
        finish();
    }
}
