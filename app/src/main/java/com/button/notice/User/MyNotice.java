package com.button.notice.User;

import android.app.ListActivity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class MyNotice extends ListActivity {
    private ListView a;
    com.button.notice.util.ProgressBar b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mynotice);
        a=findViewById(android.R.id.list);
        b=findViewById(R.id.progressBar);
        noticeget();
        noticegeto();

    }



    public void noticeget(){

        ACache aCache = ACache.get(MyNotice.this);
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("mynotice");
        if(list!=null){
        SimpleAdapter adapter =new SimpleAdapter(MyNotice.this, list,R.layout.notice_listviewitem,new String[]{"noticeTitle", "noticeText","noticeDate","noticeTime"},new int[]{R.id.title, R.id.info,R.id.date,R.id.time});
        a.setAdapter(adapter);
        }
    }

    public void noticegeto(){
        ACache aCache = ACache.get(MyNotice.this);
        CommonRequest request = new CommonRequest();
        request.setTable("table_notice_info");
        request.setWhereEqualTo("noticeUser",request.getCurrentId(MyNotice.this));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    ArrayList<HashMap<String, String>> list = response.getDataList();

                    if( aCache.getAsObject("mynotice")!=null){

                    if (((ArrayList<HashMap<String,String>>) aCache.getAsObject("mynotice")).size()!=list.size()) {

                        aCache.put("mynotice", list);
                        SimpleAdapter adapter = new SimpleAdapter(MyNotice.this, list, R.layout.notice_listviewitem, new String[]{"noticeTitle", "noticeText", "noticeDate", "noticeTime"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                        a.setAdapter(adapter);
                        new Handler().postDelayed(() -> b.setVisibility(View.GONE), 1000);  //延时操作，使动画消失
                    }
                    else{
                        new Handler().postDelayed(() -> b.setVisibility(View.GONE), 1000);  //延时操作，使动画消失
                    }

                }
                    else {

                        aCache.put("mynotice", list);
                        SimpleAdapter adapter = new SimpleAdapter(MyNotice.this, list, R.layout.notice_listviewitem, new String[]{"noticeTitle", "noticeText", "noticeDate", "noticeTime"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                        a.setAdapter(adapter);
                        new Handler().postDelayed(() -> b.setVisibility(View.GONE), 1000);  //延时操作，使动画消失
                    }

                }
            }
            @Override
            public void fail(String failCode, String failMsg) {

            }
        });


    }

    public void back(View view){
        finish();
    }
}
