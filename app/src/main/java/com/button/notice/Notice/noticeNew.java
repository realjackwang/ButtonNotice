package com.button.notice.Notice;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.button.notice.Fragment.MainActivity;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
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
    private String newnoticedate;
    private String newnoticetime;
    private Button datepicker;//点击选择日期钮
    private Button timepicker;//点击选择时间钮
    private TextView tvProcessName;//用来展示选中日期的tv
    private TextView mText;//显示选择的时间
    private Calendar mCalendar;//这到底是个啥啊
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_new);

        Button Back = findViewById(R.id.back);//一大坨findViewById
        Button Submit = findViewById(R.id.submit);
        datepicker = findViewById(R.id.datepicker);
        timepicker=findViewById(R.id.timepicker);

        Back.setOnClickListener(this);//集成一下很多按钮的点击事件
        Submit.setOnClickListener(this);
        timepicker.setOnClickListener(this);
        datepicker.setOnClickListener(this);

        calendar = Calendar.getInstance();//我也不知道是用来干嘛的
    }

    //点击事件的集合
    @Override
    public void onClick(View v){

        switch (v.getId()){
            //前两个真是太好欺负了！！！
            case R.id.back:
                //返回按钮的监听事件
                {Intent intent = new Intent(noticeNew.this,MainActivity.class);
                startActivity(intent);break;}
            case R.id.submit://可以作为怎么往上传数据的例子 再问键键又该凶我了
                {
                //提交按钮的监听事件
                CommonRequest request = new CommonRequest();
                userId = request.getCurrentId(noticeNew.this);
                EditText NewNoticeTitle = findViewById(R.id.NewNoticeTitle);
                EditText NewNoticeText = findViewById(R.id.NewNoticeText);
                TextView NewNoticeDate = findViewById(R.id.tvProcessName);
                TextView NewNoticeTime = findViewById(R.id.timeset);

                newnoticetitle= NewNoticeTitle.getText().toString();
                newnoticetext= NewNoticeText.getText().toString();
                newnoticedate=NewNoticeDate.getText().toString();
                newnoticetime=NewNoticeTime.getText().toString();

                request.setTable("table_notice_info");

                request.addRequestParam("noticeTitle",newnoticetitle);
                request.addRequestParam("noticeText",newnoticetext);
                request.addRequestParam("noticeUser",userId);
                request.addRequestParam("noticeDate",newnoticedate);
                request.addRequestParam("noticeTime",newnoticetime);
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
            case R.id.datepicker://选择日期钮
                {
                DatePicker();
                break;
                }
            case R.id.timepicker://选择时间钮
                {
                showTimePickerDialog();
                break;
                }
        }
    }

    private void DatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(noticeNew.this, 3, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // TODO Auto-generated method stub
                int mYear = year;
                int mMonth = month;
                int mDay = day;
                //更新EditText控件日期 小于10加0
                tvProcessName.setText(new StringBuilder()
                        .append(mYear)
                        .append("-")
                        .append((mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1))
                        .append("-")
                        .append((mDay < 10) ? 0 + mDay : mDay));
            }
        },
//                AlertDialog.THEME_HOLO_LIGHT,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));


        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        tvProcessName = findViewById(R.id.tvProcessName);
        //设置时间范围
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }//拒绝面对里面的代码 实现选择时间用的

    private void showTimePickerDialog() {
        mCalendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(noticeNew.this, 3, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                int mHour = hour;
                int mMinute = minute;

                mText.setText(new StringBuilder()
                        .append((mHour ) < 10 ? ("0" + (mHour)) : (mHour ))
                        .append(":")
                        .append((mMinute ) < 10 ?( "0" + (mMinute)) : (mMinute)));
            }
        }, mCalendar.get(Calendar.HOUR), mCalendar.get(Calendar.MINUTE), true);
        mText =findViewById(R.id.timeset);
        dialog.show();

    }

}
