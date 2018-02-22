package com.button.notice.Button;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.button.notice.Fragment.MainActivity;
import com.button.notice.R;

public class FirstLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        Toast.makeText(FirstLogin.this, "第一次来呀！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FirstLogin.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
