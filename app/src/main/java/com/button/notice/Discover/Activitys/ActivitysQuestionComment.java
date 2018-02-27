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

public class ActivitysQuestionComment extends AppCompatActivity {
    private LinearLayout addHotelNameView;
    ScrollView all;
    ProgressBar progressBar;



    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys_question_comment);
        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);


        Intent intent = getIntent();
        id =  intent.getStringExtra("questionid");
        init();
        getinfo();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

    }


    public void init(){
        all =findViewById(R.id.all);
        progressBar = findViewById(R.id.progressBar);
        all.setVisibility(View.GONE);
    }


    public void getinfo(){
        addHotelNameView.removeAllViews();
        CommonRequest request = new CommonRequest();
        request.setTable("table_activity_comment");
        request.setWhereEqualTo("Id",id);
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> arrayList = response.getDataList();
                if(arrayList!=null&&arrayList.size()>0) {
                        HashMap<String, String> map = arrayList.get(0);
                        addComment(map.get("commentInfo"));
                        String[] comment = map.get("commentAnswer").split("@@@");
                        if(comment[0]!=null&&!comment[0].equals("")&&!comment[0].equals("null"))
                            for (int j=0;(j <comment.length); j++) {
                                addComment(comment[j]);
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


    public void more(View view){

        final EditText editText = new EditText(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("请填写回答内容：");
        builder.setView(editText);
        builder.setPositiveButton("确定回答", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CommonRequest request = new CommonRequest();
                request.setTable("table_activity_comment");
                request.setId(id);
                request.setList("commentAnswer");
                request.setText(editText.getText().toString());
                request.AddAll("2",new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {
                        Toast.makeText(ActivitysQuestionComment.this, "回答成功", Toast.LENGTH_SHORT).show();
                        getinfo();
                    }

                    @Override
                    public void fail(String failCode, String failMsg) {
                        Toast.makeText(ActivitysQuestionComment.this, "回答失败，请稍后再试", Toast.LENGTH_SHORT).show();
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


    private void addComment(String x){
        View commentview = View.inflate(this, R.layout.comment_list_item, null);
        TextView commentname =  commentview.findViewById(R.id.commentname);
        commentname.setText(x);
        addHotelNameView.addView(commentview);
    }

}
