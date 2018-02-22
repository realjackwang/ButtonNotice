package com.button.notice.User;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.TimePickerView;
import com.button.notice.Notice.noticeNew;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserInfo extends AppCompatActivity  {


    EditText nickname, name,banji, mingzu, phone,qq,mail,school,xueyuan,xuehao,zhuanye;
    TextView birthdata,age;

    private Calendar calendar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        nickname=findViewById(R.id.editText10);
        name =findViewById(R.id.editText1);
        birthdata=findViewById(R.id.birthdata);
        mingzu =findViewById(R.id.editText3);
        phone =findViewById(R.id.editText4);
        qq=findViewById(R.id.editText8);
        mail=findViewById(R.id.editText9);
        school=findViewById(R.id.editText5);
        xueyuan=findViewById(R.id.editText6);
        xuehao=findViewById(R.id.editText7);
        zhuanye=findViewById(R.id.editText15);
        banji=findViewById(R.id.editText16);
        age=findViewById(R.id.age2);














        infoget();
        infogeto();




        calendar = Calendar.getInstance();
        birthdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dataset();
            }
        });



//        if(birthdata.getText().toString()!=null||!birthdata.getText().toString().equals("")) {
//            Date dbDate = null;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                dbDate = (Date) dateFormat.parse(birthdata.getText().toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            age.setText(StringUtil.getAge(dbDate));
//        }


    }

    private void infoget(){


        ACache aCache = ACache.get(UserInfo.this);
        HashMap<String,String> map =  (HashMap<String,String>)aCache.getAsObject("userdata");
        if(map!=null){
        name.setText(map.get("userName"));
        birthdata.setText(map.get("userBirth"));
        mingzu.setText(map.get("userNation"));
        phone.setText(map.get("userPhone"));
        qq.setText(map.get("userQq"));
        mail.setText(map.get("userEmail"));
        school.setText(map.get("userSchool"));
        xueyuan.setText(map.get("userCollege"));
        xuehao.setText(map.get("userSchoolcode"));
        zhuanye.setText(map.get("userMajor"));
        banji.setText(map.get("userClass"));
        age.setText(map.get("userAge"));
        }
    }  //本地个人信息

    private void infogeto(){
        CommonRequest request = new CommonRequest();
        request.setTable("table_user_info");
        request.setWhereEqualTo("userId",request.getCurrentId(UserInfo.this));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String, String>> arrayList = response.getDataList();
                HashMap<String,String> map = arrayList.get(0);

                ACache aCache = ACache.get(UserInfo.this);

                if((HashMap<String,String>)aCache.getAsObject("userdata")!=null) {

                    if (!((HashMap<String, String>) aCache.getAsObject("userdata")).equals(map)) {

                        name.setText(map.get("userName"));
                        birthdata.setText(map.get("userBirth"));
                        mingzu.setText(map.get("userNation"));
                        phone.setText(map.get("userPhone"));
                        qq.setText(map.get("userQq"));
                        mail.setText(map.get("userEmail"));
                        school.setText(map.get("userSchool"));
                        xueyuan.setText(map.get("userCollege"));
                        xuehao.setText(map.get("userSchoolcode"));
                        zhuanye.setText(map.get("userMajor"));
                        banji.setText(map.get("userClass"));
                        age.setText(map.get("userAge"));

                        aCache.put("userdata", map);
                        Toast.makeText(UserInfo.this, "更改成功", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    name.setText(map.get("userName"));
                    birthdata.setText(map.get("userBirth"));
                    mingzu.setText(map.get("userNation"));
                    phone.setText(map.get("userPhone"));
                    qq.setText(map.get("userQq"));
                    mail.setText(map.get("userEmail"));
                    school.setText(map.get("userSchool"));
                    xueyuan.setText(map.get("userCollege"));
                    xuehao.setText(map.get("userSchoolcode"));
                    zhuanye.setText(map.get("userMajor"));
                    banji.setText(map.get("userClass"));
                    age.setText(map.get("userAge"));

                    aCache.put("userdata", map);
                    Toast.makeText(UserInfo.this, "更改成功", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(UserInfo.this,"更改失败",Toast.LENGTH_SHORT).show();
            }
        });

    }  //网络个人信息

    public void infosave(View v){


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 添加按钮的单击事件

                // 设置显示信息
        // 单击事件
        builder.setTitle("温馨提示");
        builder.setMessage("请确认修改的信息的正确性，否则将影响通知及活动的准确性")
                        .
                        // 设置确定按钮
                                setPositiveButton("是",
                                (dialog, which) -> {
                                    final CommonRequest request1 = new CommonRequest();//第一步 创建一个request对象
                                    request1.setTable("table_user_info");
                                    request1.setId(request1.getCurrentId(UserInfo.this));
                                    request1.addRequestParam("userName",name.getText().toString());
                                    request1.addRequestParam("userAge",age.getText().toString());
                                    request1.addRequestParam("userSchool",school.getText().toString());
                                    request1.addRequestParam("userCollege",xueyuan.getText().toString());
                                    request1.addRequestParam("userSchoolcode",xuehao.getText().toString());
                                    request1.addRequestParam("userPhone",phone.getText().toString());
                                    request1.addRequestParam("userEmail",mail.getText().toString());
                                    request1.addRequestParam("userQq",qq.getText().toString());
                                    request1.addRequestParam("userClass",banji.getText().toString());
                                    request1.addRequestParam("userNation",mingzu.getText().toString());
                                    request1.addRequestParam("userBirth",birthdata.getText().toString());
                                    request1.addRequestParam("userMajor",zhuanye.getText().toString());

                                    request1.Updata(request1,new ResponseHandler() {
                                        @Override
                                        public void success(CommonResponse response) {     //如果成功了就在这里面写跳转啊，taost什么的。
                                            Toast.makeText(UserInfo.this,"修改成功",Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void fail(String failCode, String failMsg) {  //同理
                                            Toast.makeText(UserInfo.this,"修改失败，请稍后再试",Toast.LENGTH_SHORT).show();

                                        }

                                    });
                                }).
                        // 设置取消按钮
                                setNegativeButton("否",
                                (dialog, which) -> {

                                });
                // 创建对话框
                AlertDialog ad = builder.create();
                // 显示对话框
                ad.show();









    }

    private  void Dataset()  {


        String month =Integer.toHexString(calendar.get(Calendar.MONTH)+1); //

        String str=calendar.get(Calendar.YEAR)-100+"-"+month+"-"+calendar.get(Calendar.DATE);
        Log.d("初试时间：",str);
        String str2=calendar.get(Calendar.YEAR)+"-"+month+"-"+calendar.get(Calendar.DATE);

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

        TimePickerView pvTime = new TimePickerView.Builder(UserInfo.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调

                TextView btn = findViewById(R.id.birthdata);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                btn.setText(sdf.format(date2));
                int Age = StringUtil.getAge(date2);
                age.setText(Age+"");
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

    public void back(View v){
        finish();
    }

}
