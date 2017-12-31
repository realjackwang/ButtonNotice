package com.button.notice.Notice;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.button.notice.Fragment.MainActivity;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import java.util.Calendar;

/**
 * Created by Jill on 2017/12/25.,,
 */

/**
 * Created by Jill on 2017/12/25.,,
 */

public class noticeNew extends AppCompatActivity implements View.OnClickListener{
    private String newnoticetitle;
    private String newnoticetext;
    private String userId;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private Button datepicker;
    private Button timepicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_new);
        timepicker = findViewById(R.id.timepicker);
        datepicker = findViewById(R.id.datepicker);


        Button Back = findViewById(R.id.back);
        Button Submit = findViewById(R.id.submit);

        timepicker.setOnClickListener(this);
        datepicker.setOnClickListener(this);
        calendar = Calendar.getInstance();
        Back.setOnClickListener(this);
        Submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.back:  //返回按钮的监听事件
            {Intent intent = new Intent(noticeNew.this,MainActivity.class);
                startActivity(intent);break;}
            case R.id.submit:
            {
                //提交按钮的监听事件
                CommonRequest request = new CommonRequest();
                userId = request.getCurrentId(noticeNew.this);
                EditText NewNoticeTitle = findViewById(R.id.NewNoticeTitle);
                EditText NewNoticeText = findViewById(R.id.NewNoticeText);

                newnoticetitle= NewNoticeTitle.getText().toString();
                newnoticetext= NewNoticeText.getText().toString();
                request.setTable("table_notice_info");

                request.addRequestParam("noticeTitle",newnoticetitle);
                request.addRequestParam("noticeText",newnoticetext);
                request.addRequestParam("noticeUser",userId);
                request.Create(request, new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {

                        Toast.makeText(noticeNew.this,"发送成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void fail(String failCode, String failMsg) {
                        Toast.makeText(noticeNew.this,"发送失败",Toast.LENGTH_SHORT).show();
                    }
                });
                break;}
            case R.id.datepicker:
            {
                DatePicker();
                break;
            }
            case R.id.timepicker:
            {
                TimePicker();
                break;
            }



        }






    }

    private void DatePicker() {
        datePickerDialog = new DatePickerDialog(
                this, (view, year, monthOfYear, dayOfMonth) -> {
//            @Override
//            public void onDateSet(DatePicker view, int year,
//            int monthOfYear, int dayOfMonth) {
//                // 此处得到选择的时间，可以进行你想要的操作
//                tv.setText("您选择了：" + year + "年" + monthOfYear
//                        + "月" + dayOfMonth + "日");
//            }


                    //monthOfYear 得到的月份会减1所以我们要加1
                     String time = String.valueOf(year) + "　" + String.valueOf(monthOfYear + 1) + "  " + Integer.toString(dayOfMonth);
                    Log.d("测试", time);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    private void TimePicker() {
        timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            Log.d("测试", Integer.toString(hourOfDay));
            Log.d("测试", Integer.toString(minute));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }







}
