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
import com.notice.button.button.service.ServiceUtil;

public class User_information extends AppCompatActivity implements ServiceUtil {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final CommonRequest request = new CommonRequest();

        sendHttpPostRequest(Constant.URL_User, request, new ResponseHandler() {
            @Override
            public String success(CommonResponse response) {
                return null;
            }
            @Override
            public  String fail(String failCode, String failMsg) {

                final CommonRequest request1 = new CommonRequest();//第一步 创建一个request对象

                request1.addRequestParam("table", "table_user_info");//第二步选择上传的表
                request1.addRequestParam("Id", failCode);  //选择上传的Id
                request1.addRequestParam("userName","莫小沫");
                request1.addRequestParam("userAge","18");
                request1.addRequestParam("userSchool","sdu");
                request1.addRequestParam("userCollege","信院");

                sendHttpPostRequest(Constant.URL_Updata, request1, new ResponseHandler() {  //固定写法，用来请求的。   第一个Constant.URL_Login是我的服务器的地址，你要用登录服务器就用代理服务器的地址，要用上传就用上传的服务器地址。

                    @Override
                    public String success(CommonResponse response) {     //如果成功了就在这里面写跳转啊，taost什么的。
                        Toast.makeText(User_information.this,"更改成功",Toast.LENGTH_SHORT).show();
                        return null;
                    }

                    @Override
                    public String fail(String failCode, String failMsg) {  //同理
                        Toast.makeText(User_information.this,"更改失败",Toast.LENGTH_SHORT).show();
                        return failCode;
                    }

                }, true);








                return failCode;
            }
        },true);






        setContentView(R.layout.user_main);
        Button all_circle = (Button) findViewById(R.id.all_circle);
        all_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_information.this, circle_list.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void sendHttpPostRequest(String url, CommonRequest request, ResponseHandler responseHandler, boolean showLoadingDialog) {
        new HttpPostTask(request, mHandler, responseHandler).execute(url);  //固定写法
    }
}
