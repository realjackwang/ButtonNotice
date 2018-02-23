package com.button.notice.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.button.notice.R;
import com.button.notice.User.Setting.Appinfo;
import com.button.notice.User.Setting.Feedback;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
    }

    public void appinfo(View view){
        startActivity(new Intent(Settings.this,Appinfo.class));
    }
    public void sendback(View view){
        startActivity(new Intent(Settings.this,Feedback.class));
    }


}
