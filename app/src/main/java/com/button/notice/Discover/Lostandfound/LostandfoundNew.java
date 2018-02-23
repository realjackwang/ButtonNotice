package com.button.notice.Discover.Lostandfound;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.button.notice.Discover.Lostandfound.bean.GetJsonDataUtil;
import com.button.notice.Discover.Lostandfound.bean.JsonBean;
import com.button.notice.Notice.noticeNew;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class LostandfoundNew extends AppCompatActivity{


    FrameLayout type;
    TextView typetext,title,date,typea;
    EditText name,phone,place,info;


    private List<String> types = Arrays.asList("钱包","校园卡","银行卡","证件","书籍","背包","自行车","衣服","钥匙","数码产品","首饰","其他");


//    private ArrayList<JsonBean> options1Items = new ArrayList<>();
//    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
//    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
//    private Thread thread;
//    private static final int MSG_LOAD_DATA = 0x0001;
//    private static final int MSG_LOAD_SUCCESS = 0x0002;
//    private static final int MSG_LOAD_FAILED = 0x0003;

    private Calendar calendar;
//    private boolean isLoaded = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_lostandfoundnew);

        type=findViewById(R.id.type);
        typetext = findViewById(R.id.typetext);
        title= findViewById(R.id.textView13);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        place = findViewById(R.id.place);
        info = findViewById(R.id.info);
        date =findViewById(R.id.datetext);
        typea =findViewById(R.id.typetext);


//        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        calendar = Calendar.getInstance();//我也不知道是用来干嘛的

        Intent intent = getIntent();

        String t = intent.getStringExtra("type");

        if(t.equals("lost")){
           title.setText("寻物启事");


        }
        if(t.equals("found")){
            title.setText("失物招领");



        }
//        initView();

    }



//
//    private void initView() {
//        findViewById(R.id.more).setOnClickListener(this);
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//
//            case R.id.more:
//                if (isLoaded){
//                    ShowPickerView();
//                }else {
//                    Toast.makeText(LostandfoundNew.this,"Please waiting until the data is parsed",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//        }
//
//    }

    public void date(View v){

        String month =Integer.toHexString(calendar.get(Calendar.MONTH)+1); //

        String str=calendar.get(Calendar.YEAR)-1+"-"+month+"-"+calendar.get(Calendar.DATE);
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

        TimePickerView pvTime = new TimePickerView.Builder(LostandfoundNew.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调

                TextView btn = findViewById(R.id.datetext);
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
    }  //选择日期

    public void type(View v){
    OptionsPickerView  pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {


                String tx = types.get(options1);
                typetext.setText(tx);
            }
        }).build();
        pvOptions.setPicker(types);
        pvOptions.show();
    } //选择类型

    public void more(View v){
        CommonRequest request = new CommonRequest();
        Intent intent = getIntent();
        String t = intent.getStringExtra("type");

        if(t.equals("lost")){
            request.setTable("table_lostandfound_lost");
            request.addRequestParam("lostName",name.getText().toString());
            request.addRequestParam("lostPhone",phone.getText().toString());
            request.addRequestParam("lostPlace",place.getText().toString());
            request.addRequestParam("lostDate",date.getText().toString());
            request.addRequestParam("lostType",typea.getText().toString());
            request.addRequestParam("lostInfo",info.getText().toString());
            request.Create(request, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast.makeText(LostandfoundNew.this,"发送成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void fail(String failCode, String failMsg) {
                    Toast.makeText(LostandfoundNew.this,"发送失败",Toast.LENGTH_SHORT).show();
                }
            });

        }
        if(t.equals("found")){
            request.setTable("table_lostandfound_found");
            request.addRequestParam("foundName",name.getText().toString());
            request.addRequestParam("foundPhone",phone.getText().toString());
            request.addRequestParam("foundPlace",place.getText().toString());
            request.addRequestParam("foundDate",date.getText().toString());
            request.addRequestParam("foundType",typea.getText().toString());
            request.addRequestParam("foundInfo",info.getText().toString());
            request.Create(request, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    Toast.makeText(LostandfoundNew.this,"发送成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void fail(String failCode, String failMsg) {
                    Toast.makeText(LostandfoundNew.this,"发送失败",Toast.LENGTH_SHORT).show();
                }
            });



        }




    }  //发送


//    private void ShowPickerView() {// 弹出选择器
//
//        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()+
//                        options2Items.get(options1).get(options2)+
//                        options3Items.get(options1).get(options2).get(options3);
//
//                Toast.makeText(LostandfoundNew.this,tx, Toast.LENGTH_SHORT).show();
//            }
//        })
//
//                .setTitleText("城市选择")
//                .setDividerColor(Color.BLACK)
//                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                .setContentTextSize(20)
//                .build();
//
//        /*pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.show();
//    }

//    private void initJsonData() {//解析数据
//
//        /**
//         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
//         * 关键逻辑在于循环体
//         *
//         * */
//        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据
//
//        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
//
//        /**
//         * 添加省份数据
//         *
//         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
//         * PickerView会通过getPickerViewText方法获取字符串显示出来。
//         */
//        options1Items = jsonBean;
//
//        for (int i=0;i<jsonBean.size();i++){//遍历省份
//            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
//
//            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
//                String CityName = jsonBean.get(i).getCityList().get(c).getName();
//                CityList.add(CityName);//添加城市
//
//                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//
//                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
//                if (jsonBean.get(i).getCityList().get(c).getArea() == null
//                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
//                    City_AreaList.add("");
//                }else {
//
//                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
//                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
//
//                        City_AreaList.add(AreaName);//添加该城市所有地区数据
//                    }
//                }
//                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
//            }
//
//            /**
//             * 添加城市数据
//             */
//            options2Items.add(CityList);
//
//            /**
//             * 添加地区数据
//             */
//            options3Items.add(Province_AreaList);
//        }
//
//        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
//
//    }


//    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
//        ArrayList<JsonBean> detail = new ArrayList<>();
//        try {
//            JSONArray data = new JSONArray(result);
//            Gson gson = new Gson();
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
//        }
//        return detail;
////    }



    public void back(View c){
        finish();
    }  //返回按键

//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_LOAD_DATA:
//                    if (thread==null){//如果已创建就不再重新创建子线程了
//
//                        Toast.makeText(LostandfoundNew.this,"Begin Parse Data",Toast.LENGTH_SHORT).show();
//                        thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 写子线程中的操作,解析省市区数据
//                                initJsonData();
//                            }
//                        });
//                        thread.start();
//                    }
//                    break;
//
//                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(LostandfoundNew.this,"Parse Succeed",Toast.LENGTH_SHORT).show();
//                    isLoaded = true;
//                    break;
//
//                case MSG_LOAD_FAILED:
//                    Toast.makeText(LostandfoundNew.this,"Parse Failed",Toast.LENGTH_SHORT).show();
//                    break;
//
//            }
//        }
//    };



}
