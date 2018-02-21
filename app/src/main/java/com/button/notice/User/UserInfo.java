package com.button.notice.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

public class UserInfo extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

                final CommonRequest request1 = new CommonRequest();//第一步 创建一个request对象

                request1.addRequestParam("table", "table_user_info");//第二步选择上传的表
                request1.addRequestParam("Id", request1.getCurrentId(UserInfo.this));  //选择上传的Id
                request1.addRequestParam("userName","莫小沫");
                request1.addRequestParam("userAge","18");
                request1.addRequestParam("userSchool","sdu");
                request1.addRequestParam("userCollege","信院");

                request1.Updata(request1,new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {     //如果成功了就在这里面写跳转啊，taost什么的。
                        Toast.makeText(UserInfo.this,"更改成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void fail(String failCode, String failMsg) {  //同理
                        Toast.makeText(UserInfo.this,"更改失败",Toast.LENGTH_SHORT).show();

                    }

                });





    }


}
