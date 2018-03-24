package com.button.notice.Discover.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivitysEnroll extends AppCompatActivity {

    private   ListView listView;
    private List list =new ArrayList();
    String ID;
    private   ArrayAdapter<List> arrayAdapter;
    private   String[] lists;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addHotelNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys_enroll);

        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);

        listView=findViewById(R.id.listview);

        Intent intent = getIntent();
       ID= intent.getStringExtra("ID");

       lists = StringUtil.ChangetoString( intent.getStringExtra("info") );
        for(int i=0;i<lists.length;i++) {
            addViewItem(i);
            list.add(lists[i]);
        }

    }






    public void more(View view){


        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            View childAt = addHotelNameView.getChildAt(i);
            EditText hotelName = (EditText) childAt.findViewById(R.id.enrollinfo);
            if(hotelName.getText().toString()!=null&&!hotelName.getText().toString().equals(""))
            list.add(hotelName.getText().toString());
            else{
                list.add("未填写");
            }
            Log.e(TAG, lists[i] + ":"+hotelName.getText().toString());

            }

        ACache aCache =ACache.get(this);
        String name =(aCache.getAsString("name"));  //设置用户名

        CommonRequest request = new CommonRequest();
        request.CreateExecl(StringUtil.listToString(list), ID, name,ActivitysEnroll.this, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {


                CommonRequest request1 = new CommonRequest();
                request1.setTable("table_activity_info");
                request1.setList("activityEnterPerson");
                request1.setId(ID);
                request1.setText(request1.getCurrentId(ActivitysEnroll.this));
                request1.Connect("1",new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {

                        Toast.makeText(ActivitysEnroll.this, "报名表提交成功", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void fail(String failCode, String failMsg) {
                        Toast.makeText(ActivitysEnroll.this, "报名表提交成功，但信息未录入，正在为您重新录入", Toast.LENGTH_SHORT).show();

                        CommonRequest request1 = new CommonRequest();
                        request1.setTable("table_activity_info");
                        request1.setList("activityEnterPerson");
                        request1.setId(ID);
                        request1.setText(request1.getCurrentId(ActivitysEnroll.this));
                        request1.Connect("1",new ResponseHandler() {
                            @Override
                            public void success(CommonResponse response) {
                                Toast.makeText(ActivitysEnroll.this, "报名表提交成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void fail(String failCode, String failMsg) {
                                Toast.makeText(ActivitysEnroll.this, "再次失败，请进行反馈", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });


                    }
                });





            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(ActivitysEnroll.this, "报名表提交失败", Toast.LENGTH_SHORT).show();
            }
        });


    }



    //添加ViewItem
    private void addViewItem(int i) {

            View hotelEvaluateView = View.inflate(this, R.layout.list_item_enroll, null);
            TextView btn_add =  hotelEvaluateView.findViewById(R.id.enrollname);
            EditText editText = hotelEvaluateView.findViewById(R.id.enrollinfo);





        ACache aCache = ACache.get(this);
        HashMap<String,String> map =  (HashMap<String,String>)aCache.getAsObject("userdata");
        if(map!=null){

        switch (lists[i]) {
            case "姓名":
                editText.setText(map.get("userName"));
                break;
            case "年龄":
                editText.setText(map.get("userAge"));
                break;
            case "民族":
                editText.setText(map.get("userNation"));
                break;
            case "学院":
                editText.setText(map.get("userCollege"));
                break;
            case "专业":
                editText.setText(map.get("userMajor"));
                break;
            case "班级":
                editText.setText(map.get("userClass"));
                break;
            case "学号":
                editText.setText(map.get("userSchoolcode"));
                break;
            case "手机号":
                editText.setText(map.get("userPhone"));
                break;
            case "QQ":
                editText.setText(map.get("userQq"));
                break;
            case "邮箱":
                editText.setText(map.get("userEmail"));
                break;
            default:
                break;

        }

        }

            btn_add.setText(lists[i]);
            addHotelNameView.addView(hotelEvaluateView);
    }

public void back(View view){
        finish();
}


}
