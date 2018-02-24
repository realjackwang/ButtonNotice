package com.button.notice.User.Setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.button.notice.Discover.Lostandfound.LostandfoundNew;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

public class Feedback extends AppCompatActivity {
    EditText a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);
        a= findViewById(R.id.feedback);
        b = findViewById(R.id.phone);
    }


    public void submit(View v){
        CommonRequest request = new CommonRequest();
        request.setTable("table_feedback_info");
        request.addRequestParam("feedbackInfo",a.getText().toString());
        request.addRequestParam("feedbackPhone",b.getText().toString());
        request.Create(request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                Toast.makeText(Feedback.this,"反馈成功",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(Feedback.this,"反馈失败，请稍后再试",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
