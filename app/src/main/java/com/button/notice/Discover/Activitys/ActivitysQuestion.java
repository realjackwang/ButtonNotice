package com.button.notice.Discover.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivitysQuestion extends AppCompatActivity {
    private LinearLayout addHotelNameView;
    String Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys_question);
        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);


        Intent intent =getIntent();
        Id= intent.getStringExtra("ID");

        getinfo();


    }


    @Override
    protected void onRestart() {
        addHotelNameView.removeAllViews();
        getinfo();
        super.onRestart();
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
                        addComment(map.get("commentInfo"));
                        String[] comment = map.get("commentAnswer").split("$$$", 2);
                        for (int j = 0; j < comment.length; j++) {
                            addCommentComment(comment[j]);
                        }
                    }

                }
            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });


    }

    private void addComment(String x){
        View commentview = View.inflate(this, R.layout.comment_list_item, null);
        TextView commentname =  commentview.findViewById(R.id.commentname);
        commentname.setText(x);
        addHotelNameView.addView(commentview);
    }



    private void addCommentComment(String x){
        View commentview = View.inflate(this, R.layout.comment_list_item_2, null);
        TextView commentname =  commentview.findViewById(R.id.commentname);
        commentname.setText(x);
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

}
