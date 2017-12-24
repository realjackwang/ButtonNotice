package com.notice.button.button;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.notice.button.button.service.CommonRequest;
import com.notice.button.button.service.CommonResponse;
import com.notice.button.button.service.Constant;
import com.notice.button.button.service.HttpPostTask;
import com.notice.button.button.service.ResponseHandler;

public class User_information extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

                final CommonRequest request1 = new CommonRequest();//第一步 创建一个request对象

                request1.addRequestParam("table", "table_user_info");//第二步选择上传的表
                request1.addRequestParam("Id", request1.getCurrentId(User_information.this));  //选择上传的Id
                request1.addRequestParam("userName","莫小沫");
                request1.addRequestParam("userAge","18");
                request1.addRequestParam("userSchool","sdu");
                request1.addRequestParam("userCollege","信院");

                request1.Updata(request1,new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {     //如果成功了就在这里面写跳转啊，taost什么的。
                        Toast.makeText(User_information.this,"更改成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void fail(String failCode, String failMsg) {  //同理
                        Toast.makeText(User_information.this,"更改失败",Toast.LENGTH_SHORT).show();

                    }

                });



        Button all_circle = (Button) findViewById(R.id.all_circle);
        all_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_information.this, circle_list.class);
                startActivity(intent);
            }
        });

    }


}
