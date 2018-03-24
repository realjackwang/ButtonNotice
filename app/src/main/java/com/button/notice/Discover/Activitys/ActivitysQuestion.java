package com.button.notice.Discover.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivitysQuestion extends AppCompatActivity {
    String Id;
    private LinearLayout addHotelNameView;
    ScrollView all;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys_question);
        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);


        Intent intent =getIntent();
        Id= intent.getStringExtra("ID");
        init();
        getinfo();


    }


    @Override
    protected void onRestart() {
        addHotelNameView.removeAllViews();
        getinfo();
        super.onRestart();
    }


    public void init(){
        all =findViewById(R.id.all);
        progressBar = findViewById(R.id.progressBar);
        all.setVisibility(View.GONE);
    }

    public void getinfo(){
        CommonRequest request = new CommonRequest();
        request.setTable("table_activity_comment");
        request.setWhereEqualTo("commentActivity",Id);
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> arrayList = response.getDataList();

                if(arrayList!=null&&arrayList.size()>0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        HashMap<String, String> map = arrayList.get(i);
                        addComment(map.get("commentInfo"),map.get("Id"));
                        String[] comment = map.get("commentAnswer").split("@@@");
                        if(comment[0]!=null&&!comment[0].equals("")&&!comment[0].equals("null"))
                        for (int j = 0, k=0;(j <comment.length)&&(k<2); j++,k++) {
                            addCommentComment(comment[j],map.get("Id"));
                        }
                    }
                }

                progressBar.setVisibility(View.GONE);
                all.setVisibility(View.VISIBLE);
            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });


    }

    private void addComment(String x,String y){
        View commentview = View.inflate(this, R.layout.list_item_comment, null);
        TextView commentname =  commentview.findViewById(R.id.commentname);
        commentname.setText(x);
        commentname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitysQuestion.this, ActivitysQuestionComment.class);
                intent.putExtra("questionid",y);
                startActivity(intent);
            }
        });
        addHotelNameView.addView(commentview);
    }



    private void addCommentComment(String x,String y){
        View commentview = View.inflate(this, R.layout.list_item_comment_2, null);
        TextView commentname =  commentview.findViewById(R.id.commentname);
        commentname.setText(x);
        commentname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitysQuestion.this, ActivitysQuestionComment.class);
                intent.putExtra("questionid",y);
                startActivity(intent);
            }
        });
        addHotelNameView.addView(commentview);
    }


    public void more(View view){



        final EditText editText = new EditText(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("请填写问题内容：");
        builder.setView(editText);
        builder.setPositiveButton("确定提问", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CommonRequest request = new CommonRequest();
                request.setTable("table_activity_comment");
                request.addRequestParam("commentActivity",Id);
                request.addRequestParam("commentUser",request.getCurrentId(ActivitysQuestion.this));
                request.addRequestParam("commentInfo",editText.getText().toString());
                request.Create(request,new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {
                        Toast.makeText(ActivitysQuestion.this, "提问成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String failCode, String failMsg) {
                        Toast.makeText(ActivitysQuestion.this, "提问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();





    }


    public void back(View view){
        finish();
    }

}
