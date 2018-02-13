package com.button.notice.Notice;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.button.notice.Fragment.MainActivity;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.w3c.dom.Text;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

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
    private Button quanzipicker,filepicker;
    private TextView tvProcessName;//用来展示选中日期的tv
    private TextView mText;//显示选择的时间
<<<<<<< HEAD


    private AppCompatActivity mActivity;
    private final int EX_FILE_PICKER_RESULT = 0xfa01;
    private String startDirectory = null;// 记忆上一次访问的文件目录路径

=======
>>>>>>> 383cb035f27881a60e55844beee6f006b99ff999
    private Calendar mCalendar;//这到底是个啥啊
    private Calendar calendar;

    private String quanziids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_new);

        Button Back = findViewById(R.id.back);//一大坨findViewById
        Button Submit = findViewById(R.id.submit);
        datepicker = findViewById(R.id.datepicker);
        timepicker=findViewById(R.id.timepicker);
        quanzipicker=findViewById(R.id.circleChoose);
        filepicker=findViewById(R.id.fileChoose);
        Back.setOnClickListener(this);//集成一下很多按钮的点击事件
        Submit.setOnClickListener(this);
        timepicker.setOnClickListener(this);
        datepicker.setOnClickListener(this);
        filepicker.setOnClickListener(this);
        quanzipicker.setOnClickListener(this);
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
                request.setIspush(true);
                request.addRequestParam("noticeTitle",newnoticetitle);
                request.addRequestParam("noticeText",newnoticetext);
                request.addRequestParam("noticeUser",userId);
                request.addRequestParam("noticeDate",newnoticedate);
                request.addRequestParam("noticeTime",newnoticetime);
                request.addRequestParam("noticeCommunity",quanziids);
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
                    Dataset();
//                DatePicker();
                break;
                }
            case R.id.timepicker://选择时间钮
                {
                    Timeset();
//                showTimePickerDialog();
                break;

                }

            case R.id.fileChoose:{
                fileChoose();
            break;
            }
            case R.id.circleChoose:{
                circleChoose();
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

    private  void Dataset()  {


        String month =Integer.toHexString(calendar.get(Calendar.MONTH)+1); //

        String str=calendar.get(Calendar.YEAR)+"-"+month+"-"+calendar.get(Calendar.DATE);
        Log.d("初试时间：",str);
        String str2=calendar.get(Calendar.YEAR)+5+"-"+month+"-"+calendar.get(Calendar.DATE);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date2 = null;
        try {
            date2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(date);
        calendar2.setTime(date2);

        TimePickerView pvTime = new TimePickerView.Builder(noticeNew.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调

                TextView btn = findViewById(R.id.tvProcessName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                btn.setText(sdf.format(date2));
            }
        })

                .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setRangDate(calendar,calendar2)//起始终止年月日设定

//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void Timeset(){
        TimePickerView pvTime = new TimePickerView.Builder(noticeNew.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调

                TextView btn = findViewById(R.id.timeset);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                btn.setText(sdf.format(date2));

            }

        })
                .setType(TimePickerView.Type.HOURS_MINS)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年

//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    private void circleChoose(){

        AlertDialog.Builder builder = new AlertDialog.Builder(noticeNew.this);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择圈子");
        //    指定下拉列表的显示数据

        ACache aCache =ACache.get(noticeNew.this);
        final String[] quanzi = (String[]) aCache.getAsObject("quanziacache");
        final String[] quanziid = (String[]) aCache.getAsObject("quanziidacache");
        //    设置一个下拉的列表选择项
        if(quanzi!=null){
        builder.setItems(quanzi, (dialog, which) -> {

            quanziids=quanziid[which];
            Toast.makeText(getApplicationContext(), quanziid[which],Toast.LENGTH_LONG).show();

        });

        builder.show();

    }
    else {
            Toast.makeText(getApplicationContext(), "你还没有加入任何圈子，请进行反馈",Toast.LENGTH_LONG).show();
        }

    }

    private void fileChoose(){



            AlertDialog.Builder builder = new AlertDialog.Builder(noticeNew.this);
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("选择查找方式");
            //    指定下拉列表的显示数据
            final String[] cities = {"QQ文件", "微信文件", "TIM文件", "自行查找"};
            //    设置一个下拉的列表选择项
            builder.setItems(cities, (dialog, which) -> {

                switch (which){
                    case 0:{
                        if(StringUtil.isExist(Environment.getExternalStorageDirectory().getPath()+"/tencent/QQfile_recv")){
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath()+"/tencent/QQfile_recv");
//                        if (TextUtils.isEmpty(startDirectory)) {
//                            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
//                        } else {
//                            exFilePicker.setStartDirectory(startDirectory);
//                        }

                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(noticeNew.this, EX_FILE_PICKER_RESULT);}
                        else {
                            Toast.makeText(getApplicationContext(), "未找到有关文件夹",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case 1:{
                        if(StringUtil.isExist(Environment.getExternalStorageDirectory().getPath()+"/tencent/MicroMsg/Download")){
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath()+"/tencent/MicroMsg/Download");
//                        if (TextUtils.isEmpty(startDirectory)) {
//                            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
//                        } else {
//                            exFilePicker.setStartDirectory(startDirectory);
//                        }
                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(noticeNew.this, EX_FILE_PICKER_RESULT);}
                        else {
                            Toast.makeText(getApplicationContext(), "未找到有关文件夹",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case 2:{
                        if(StringUtil.isExist(Environment.getExternalStorageDirectory().getPath()+"/tencent/TIMfile_recv")){
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath()+"/tencent/TIMfile_recv");
//                        if (TextUtils.isEmpty(startDirectory)) {
//                            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
//                        } else {
//                            exFilePicker.setStartDirectory(startDirectory);
//                        }

                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(noticeNew.this, EX_FILE_PICKER_RESULT);}
                        else {
                            Toast.makeText(getApplicationContext(), "未找到有关文件夹",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case 3:{
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(noticeNew.this, EX_FILE_PICKER_RESULT);
                        break;
                    }

                }


            });

            builder.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EX_FILE_PICKER_RESULT) {
            ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
            if (result != null && result.getCount() > 0) {
                String path = result.getPath();

                List<String> names = result.getNames();
                for (int i = 0; i < names.size(); i++) {
                    File f = new File(path, names.get(i));
                    try {
                        Uri uri = Uri.fromFile(f); //这里获取了真实可用的文件资源

                        CommonRequest request = new CommonRequest();
                        request.setId(request.getCurrentId(noticeNew.this));
                        if(request.getCurrentId(noticeNew.this).isEmpty()){
                            Toast.makeText(getApplicationContext(), "登录失效，请重新登录",Toast.LENGTH_LONG).show();
                        }
                        else {
                                request.Upload(uri.getPath(), new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        if(statusCode==255)
                                            Toast.makeText(getApplicationContext(), "登录失效，请重新登录",Toast.LENGTH_LONG).show();

                                        if(statusCode==200)
                                            Toast.makeText(getApplicationContext(), "附件上传成功",Toast.LENGTH_LONG).show();
                                    }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(getApplicationContext(), "附件上传失败"+statusCode,Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onProgress(long bytesWritten, long totalSize) {
                                    // TODO Auto-generated method stub
                                    super.onProgress(bytesWritten, totalSize);
                                    int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                                    // 下载进度显示
                                }

                            });
                        }
                        Toast.makeText(mActivity, "选择文件:" + uri.getPath(), Toast.LENGTH_SHORT)
                                .show();

                        startDirectory = path;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
