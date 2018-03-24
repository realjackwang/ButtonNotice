package com.button.notice.User.MyActivitys;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.BuildConfig;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.Constant;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class EnrollDetail extends AppCompatActivity {
    private ListView a;
    private TextView c,d;
    String Id = null;
    String ID = null;
    com.button.notice.util.ProgressBar b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_myactivitys_enrolldetail);

        init();
        namegeto();

    }
    public void init(){
        a= findViewById(R.id.listview);
        b= findViewById(R.id.progressBar);
        c= findViewById(R.id.renshu);
        d= findViewById(R.id.download);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Id = intent.getStringExtra("Id");


    }


    public void namegeto(){


        CommonRequest request = new CommonRequest();
        request.setTable("table_user_info");
        if(ID!=null&&!ID.equals("")) {
            String[] bao = StringUtil.ChangetoString(ID);
            c.setText("已有"+bao.length+"人报名");
            d.setClickable(true);
            request.setWhereEqualMoreTo("userId", bao);
            request.Query(new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {

                    if (response.getDataList().size() > 0) {
                         ArrayList<HashMap<String, String>> list = response.getDataList();

                                SimpleAdapter adapter = new SimpleAdapter(EnrollDetail.this, list, android.R.layout.simple_list_item_1, new String[]{"userName"}, new int[]{android.R.id.text1});
                                a.setAdapter(adapter);
                                b.setVisibility(View.GONE);
                            }



                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });
        }

        else{

            d.setText("暂无人报名");
            d.setClickable(false);
            b.setVisibility(View.GONE);
        }




    }



    public void download(View view){


        File files =new File(Environment.getExternalStorageDirectory()+"/Button/files/activitys/"+Id);
        if(!files.exists()) {
            files.mkdirs();


            CommonRequest request = new CommonRequest();
            String path = "G:\\uploads\\application\\" + Id + "\\";
            String filepath = Environment.getExternalStorageDirectory() + "/Button/files/activitys/" + Id + "/";


            request.Download(path, filepath, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EnrollDetail.this);
                    builder.setTitle("下载完成");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            String x = Environment.getExternalStorageDirectory() + "/Button/files/activitys/" + Id + "/" + response.getResMsg();
                            String Path = null;
                            try {
                                Path = new String(x.getBytes("UTF-8"), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            File file = new File(x);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_VIEW);
                            String type = getMIMEType(file);
                            //设置intent的data和Type属性。

                            try {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Uri contentUri = FileProvider.getUriForFile(EnrollDetail.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                                    intent.setDataAndType(contentUri, type);
//                                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                                } else {
                                    intent.setDataAndType(Uri.fromFile(file), type);
//                                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                }


                                EnrollDetail.this.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(EnrollDetail.this, "您没有安装Office文件", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });

        }
        else {

            TextView title = new TextView(this);
            title.setText("温馨提示");
            title.setPadding(10, 10, 10, 10);
            title.setGravity(Gravity.CENTER);
// title.setTextColor(getResources().getColor(R.color.greenBG));
            title.setTextSize(28);
            int color = EnrollDetail.this.getResources().getColor(R.color.text_clo);
            title.setTextColor(color);

            TextView msg = new TextView(this);
            msg.setText("你已经下载过一次汇总表了，请问是否需要重新下载最新的汇总表，或是打开之前的汇总表");
            msg.setPadding(10, 10, 10, 10);
            msg.setGravity(Gravity.CENTER);
            msg.setTextColor(color);
            msg.setTextSize(16);


            AlertDialog.Builder builder2 = new AlertDialog.Builder(EnrollDetail.this);
            builder2.setCustomTitle(title);
            builder2.setView(msg);
            builder2.setNegativeButton("打开已有", (dialog, which) -> {

                Intent intent = new Intent();
                String x = Environment.getExternalStorageDirectory() + "/Button/files/activitys/" + Id + "/报名表.xls" ;
                File file = new File(x);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String type = getMIMEType(file);
                //设置intent的data和Type属性。

                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(EnrollDetail.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                        intent.setDataAndType(contentUri, type);

                    } else {
                        intent.setDataAndType(Uri.fromFile(file), type);

                    }


                    EnrollDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(EnrollDetail.this, "您没有安装Office文件", Toast.LENGTH_SHORT).show();
                }

            });
            builder2.setPositiveButton("继续下载", (dialog, which) -> {

                CommonRequest request = new CommonRequest();
                String path = "G:\\uploads\\application\\" + Id + "\\";
                String filepath = Environment.getExternalStorageDirectory() + "/Button/files/activitys/" + Id + "/";


                request.Download(path, filepath, new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EnrollDetail.this);
                        builder.setTitle("下载完成");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                String x = Environment.getExternalStorageDirectory() + "/Button/files/activitys/" + Id + "/" + response.getResMsg();
                                String Path = null;
                                try {
                                    Path = new String(x.getBytes("UTF-8"), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                File file = new File(x);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction(Intent.ACTION_VIEW);
                                String type = getMIMEType(file);
                                //设置intent的data和Type属性。

                                try {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        Uri contentUri = FileProvider.getUriForFile(EnrollDetail.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                                        intent.setDataAndType(contentUri, type);
//                                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                                    } else {
                                        intent.setDataAndType(Uri.fromFile(file), type);
//                                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    }


                                    EnrollDetail.this.startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(EnrollDetail.this, "您没有安装Office文件", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void fail(String failCode, String failMsg) {

                    }


                });


            });
            builder2.show();
        }

    }





    /**
     * 根据文件后缀名匹配MIMEType
     *
     * @param file
     * @return
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String name = file.getName();
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return type;
        }
        String end = name.substring(index, name.length()).toLowerCase();
        if (TextUtils.isEmpty(end)) return type;

        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };


    public void back(View view){
        finish();
    }




}
