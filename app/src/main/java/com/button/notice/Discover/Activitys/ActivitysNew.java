package com.button.notice.Discover.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.button.notice.Button.buttonLogin;
import com.button.notice.Notice.noticeNew;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.LoadUtils;
import com.button.notice.util.RoundProgressBar;
import com.button.notice.util.StringUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

public class ActivitysNew extends AppCompatActivity {

    CheckBox enter;
    EditText title, info,mean,place;
    TextView cricle, time,enterxi;
    FrameLayout enterform,xxx;
//    ProgressBar filebar;
//    RoundProgressBar imagebar;



    private Calendar calendar;
    private String quanziids = null;
    ArrayList<String> photoPaths = new ArrayList<>();
    ArrayList<String> docPaths = new ArrayList<>();

    String URL=null;



    Boolean backs =true;

    private Dialog mLoad;

    private static final int REQUESTCODE = 101;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int ORIGINAL_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;

    Uri uritempFile;

    private AppCompatActivity mActivity;
    private final int EX_FILE_PICKER_RESULT = 0xfa01;
    private String startDirectory = null;// 记忆上一次访问的文件目录路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_activitysnew);
        calendar = Calendar.getInstance();

        init(); //初始化控件

        enter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    enterform.setVisibility(View.GONE);
                } else {
                    enterform.setVisibility(View.VISIBLE);
                }

            }
        });




        xxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> filePaths = new ArrayList<>();
                filePaths.add(Environment.getExternalStorageDirectory().getPath() + "/tencent/QQfile_recv");
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, ORIGINAL_REQUEST_CODE);


//                FilePickerBuilder.getInstance().setMaxCount(1)
//                        .setActivityTheme(R.style.AppTheme)
//                        .setSelectedFiles(filePaths)
//                        .pickPhoto(ActivitysNew.this);


//                ArrayList<String> filePaths = new ArrayList<>();
//                filePaths.add(Environment.getExternalStorageDirectory().getPath() + "/tencent/QQfile_recv");
//                FilePickerBuilder.getInstance().setMaxCount(1)
//                        .setSelectedFiles(filePaths)
//                        .setActivityTheme(R.style.AppTheme)
//                        .addFileSupport("ZIP",zipTypes, R.drawable.ic_zip)
//                        .pickFile(ActivitysNew.this);



            }
        });
    }


    private void init() {
        time = findViewById(R.id.timeset);
        cricle = findViewById(R.id.circleText);
        enterform = findViewById(R.id.enterform);
        title = findViewById(R.id.activityTitle);
        info = findViewById(R.id.activityText);
        enter = findViewById(R.id.enter);
        xxx =findViewById(R.id.imageChoose);
        mean =findViewById(R.id.activityMean);
        place =findViewById(R.id.activityPlace);
        enterxi =findViewById(R.id.enterxi);
//        imagebar = findViewById(R.id.imagebar);

        ACache aCache = ACache.get(this);
        if(aCache.getAsString("enter")!=null){
        enterxi.setVisibility(View.VISIBLE);}
        else {
            enterxi.setVisibility(View.GONE);
        }
        enterform.setVisibility(View.GONE);
    }

    public void more(View view) {


        mLoad = LoadUtils.createLoadingDialog(this, "发布中...");
        mLoad.setCancelable(false);
        mHandler.sendEmptyMessageDelayed(1, 20000);
        backs=false;
        CommonRequest request = new CommonRequest();
        request.setTable("table_activity_info");
        request.addRequestParam("activityTitle", title.getText().toString());
        request.addRequestParam("activityInfo", info.getText().toString());
        request.addRequestParam("activityCommunity", quanziids);
        request.addRequestParam("activityTime", time.getText().toString());
        if(URL!=null&&!URL.equals(""))
        request.addRequestParam("activityImage", "1");
        request.addRequestParam("activityFileurl", "");
        request.addRequestParam("activityPlace", place.getText().toString());
        request.addRequestParam("activityMean", mean.getText().toString());
        request.addRequestParam("activityUser",request.getCurrentId(this));




        if (enter.isChecked()) {
            request.addRequestParam("activityEnter", "1");
            ACache aCache = ACache.get(this);
            request.addRequestParam("activityEnterInfo",aCache.getAsString("enter"));
        } else {
            request.addRequestParam("activityEnter", "0");
        }


        request.Create(request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

               String ID= response.getResMsg();

               if(URL!=null&&!URL.equals(""))
                upload(URL,ID);

               else{
                   mLoad.dismiss();
                   ACache aCache = ACache.get(ActivitysNew.this);
                   aCache.remove("selected");
                   aCache.remove("enter");
                   Toast.makeText(ActivitysNew.this, "活动发布成功", Toast.LENGTH_SHORT).show();
                   finish();
               }

            }

            @Override
            public void fail(String failCode, String failMsg) {
                mLoad.dismiss();
                backs=true;
                ACache aCache = ACache.get(ActivitysNew.this);
                aCache.remove("selected");
                aCache.remove("enter");
                Toast.makeText(ActivitysNew.this, "活动发布失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void timepicker(View view) {


        String month = Integer.toHexString(calendar.get(Calendar.MONTH) + 1); //

        String str = calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DATE);
        Log.d("初试时间：", str);
        String str2 = calendar.get(Calendar.YEAR) + 5 + "-" + month + "-" + calendar.get(Calendar.DATE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                time.setText(sdf.format(date2));
            }
        })

                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)//默认全部显示
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
                .setRangDate(calendar, calendar2)//起始终止年月日设定

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

    public void circleChoose(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择圈子");
        //    指定下拉列表的显示数据

        ACache aCache = ACache.get(this);
        final String[] quanzi = (String[]) aCache.getAsObject("quanziacache");
        final String[] quanziid = (String[]) aCache.getAsObject("quanziidacache");
        //    设置一个下拉的列表选择项
        if (quanzi != null) {
            builder.setItems(quanzi, (dialog, which) -> {

                quanziids = quanziid[which];
                cricle.setText(quanzi[which]);

            });

            builder.show();

        } else {
            Toast.makeText(getApplicationContext(), "你还没有加入任何圈子，请进行反馈", Toast.LENGTH_LONG).show();
        }


    }

    public void enterform(View view) {
        Intent intent = new Intent(this, ActivitysEnter.class);
        startActivity(intent);
    }

    public void imageChoose(View view) {



    }

    public void fileChoose(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitysNew.this);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择查找方式");
        //    指定下拉列表的显示数据
        final String[] cities = {"QQ文件", "微信文件", "TIM文件", "自行查找"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, (dialog, which) -> {

            switch (which) {
                case 0: {
                    if (StringUtil.isExist(Environment.getExternalStorageDirectory().getPath() + "/tencent/QQfile_recv")) {
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath() + "/tencent/QQfile_recv");
//                        if (TextUtils.isEmpty(startDirectory)) {
//                            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
//                        } else {
//                            exFilePicker.setStartDirectory(startDirectory);
//                        }

                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(ActivitysNew.this, EX_FILE_PICKER_RESULT);
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到有关文件夹", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case 1: {
                    if (StringUtil.isExist(Environment.getExternalStorageDirectory().getPath() + "/tencent/MicroMsg/Download")) {
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath() + "/tencent/MicroMsg/Download");
//                        if (TextUtils.isEmpty(startDirectory)) {
//                            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
//                        } else {
//                            exFilePicker.setStartDirectory(startDirectory);
//                        }
                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(ActivitysNew.this, EX_FILE_PICKER_RESULT);
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到有关文件夹", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case 2: {
                    if (StringUtil.isExist(Environment.getExternalStorageDirectory().getPath() + "/tencent/TIMfile_recv")) {
                        ExFilePicker exFilePicker = new ExFilePicker();
                        exFilePicker.setNewFolderButtonDisabled(true);
                        exFilePicker.setQuitButtonEnabled(true);
                        exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath() + "/tencent/TIMfile_recv");
//                        if (TextUtils.isEmpty(startDirectory)) {
//                            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
//                        } else {
//                            exFilePicker.setStartDirectory(startDirectory);
//                        }

                        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                        exFilePicker.start(ActivitysNew.this, EX_FILE_PICKER_RESULT);
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到有关文件夹", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case 3: {
                    ExFilePicker exFilePicker = new ExFilePicker();
                    exFilePicker.setNewFolderButtonDisabled(true);
                    exFilePicker.setQuitButtonEnabled(true);
                    exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
                    exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
                    exFilePicker.start(ActivitysNew.this, EX_FILE_PICKER_RESULT);
                    break;
                }

            }


        });

        builder.show();

    }


    public void upload (String url,String Id){

        CommonRequest request = new CommonRequest();
        request.setId(Id);
        request.setType("2");
       request.Upload(url, new AsyncHttpResponseHandler() {
           @Override
           public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               mLoad.dismiss();
               ACache aCache = ACache.get(ActivitysNew.this);
               aCache.remove("selected");
               aCache.remove("enter");
               Toast.makeText(ActivitysNew.this, "活动发布成功", Toast.LENGTH_SHORT).show();
               finish();
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               mLoad.dismiss();
               backs=true;
               ACache aCache = ACache.get(ActivitysNew.this);
               aCache.remove("selected");
               aCache.remove("enter");
               Toast.makeText(ActivitysNew.this, "活动发布成功，但海报上传失败" , Toast.LENGTH_LONG).show();
           }

       });







    }


    public void resizeImage(Uri uri) {//重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);

        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());



        startActivityForResult(intent, RESIZE_REQUEST_CODE);


    }


    public void back(View view) {
        finish();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else

            switch (requestCode) {

                case EX_FILE_PICKER_RESULT:

                    ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
                    if (result != null && result.getCount() > 0) {
                        String path = result.getPath();

                        List<String> names = result.getNames();
                        for (int i = 0; i < names.size(); i++) {
                            File f = new File(path, names.get(i));
                            try {
                                Uri uri = Uri.fromFile(f); //这里获取了真实可用的文件资源

                                CommonRequest request = new CommonRequest();
                                request.setId(request.getCurrentId(ActivitysNew.this));
                                if (request.getCurrentId(ActivitysNew.this).isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "登录失效，请重新登录", Toast.LENGTH_LONG).show();
                                } else {
                                    request.Upload(uri.getPath(), new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            if (statusCode == 255)
                                                Toast.makeText(getApplicationContext(), "登录失效，请重新登录", Toast.LENGTH_LONG).show();

                                            if (statusCode == 200)
                                                Toast.makeText(getApplicationContext(), "附件上传成功", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            Toast.makeText(getApplicationContext(), "附件上传失败" + statusCode, Toast.LENGTH_LONG).show();
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
                break;


                case ORIGINAL_REQUEST_CODE:
                    Uri originaluri=data.getData();
                    URL=StringUtil.getRealFilePath(this,originaluri);
                    break;

                case IMAGE_REQUEST_CODE:
                    Uri originalUri=data.getData();
                    resizeImage(originalUri);

                    break;

                case RESIZE_REQUEST_CODE:

                        Bitmap photo = null;
                        try {
                            photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream stream =new ByteArrayOutputStream();

                        photo.compress(Bitmap.CompressFormat.JPEG,100,stream);

                        File fimage=new File(Environment.getExternalStorageDirectory(),"headforus.png");

                        try {
//                            fimage.createNewFile();
                            FileOutputStream iStream = new FileOutputStream(fimage);
                            photo.compress(Bitmap.CompressFormat.PNG, 100, iStream);
                            iStream.flush();
                            iStream.close();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }


                        Toast.makeText(ActivitysNew.this, "附件上传" , Toast.LENGTH_LONG).show();
//                        upload(Environment.getExternalStorageDirectory()+"/headforus.png");


                    break;


                case FilePickerConst.REQUEST_CODE_PHOTO:
                    if(resultCode== Activity.RESULT_OK && data!=null)
                    {
                        photoPaths = new ArrayList<>();
                        photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    }
                    break;
                case FilePickerConst.REQUEST_CODE_DOC:
                    if(resultCode== Activity.RESULT_OK && data!=null)
                    {
                        docPaths = new ArrayList<>();
                        docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    }
                    break;

                    default:
                    break;

            }
            }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeDialog(mLoad);
                    break;
            }
        }
    };

    public void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }


    public void onBackPressed() {   //防止错按返回退出

        if(backs){
            super.onBackPressed();
        }

    }



    }



