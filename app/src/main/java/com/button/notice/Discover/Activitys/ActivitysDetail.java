package com.button.notice.Discover.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.button.notice.Notice.noticeDetailActivity;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ProgressBar;
import com.button.notice.util.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static com.button.notice.service.Constant.URL_Files;

public class ActivitysDetail extends AppCompatActivity {
    String id =null;

    ImageView im;
    FrameLayout all;
    TextView mean,time,place,title,number,info,baoming,bubaoming,yibaoming;
    ProgressBar bar;
    LinearLayout baomingrenshu,wenda;
    String enterinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("activitysId");

        init();
        infoget();
    }

      private void init(){

        mean =findViewById(R.id.mean);
        time =findViewById(R.id.time);
        place =findViewById(R.id.place);
        title =findViewById(R.id.title);
        number =findViewById(R.id.number);
        info =findViewById(R.id.info);
        im =findViewById(R.id.image);
        all= findViewById(R.id.all);
        bar= findViewById(R.id.progressBar);
        wenda =findViewById(R.id.wenda);
        yibaoming = findViewById(R.id.yibaoming);
        baoming = findViewById(R.id.baoming);
        bubaoming = findViewById(R.id.bubaoming);
        baomingrenshu = findViewById(R.id.baomingrenshu);

        all.setVisibility(View.GONE);
        String imageUrl = URL_Files+"/activitys/"+id+"/poster.jpg";
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.wrong)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl,im,options);


    }

    private void infoget(){
        CommonRequest request = new CommonRequest();
        request.setTable("table_activity_info");
        request.setWhereEqualTo("Id",id);
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                ArrayList<HashMap<String,String>> arrayList = response.getDataList();
                HashMap<String,String> map  = arrayList.get(0);
                time.setText(map.get("activityTime").substring(0,16));
                place.setText(map.get("activityPlace"));
                title.setText(map.get("activityTitle"));
                info.setText(map.get("activityInfo"));
                mean.setText(map.get("activityMean"));

                if(map.get("activityEnter").equals("1")){
                    bubaoming.setVisibility(View.GONE);
                    yibaoming.setVisibility(View.GONE);
                    baomingrenshu.setVisibility(View.VISIBLE);
                    baoming.setVisibility(View.VISIBLE);
                    enterinfo=map.get("activityEnterInfo");
                    String[] bao = StringUtil.ChangetoString(map.get("activityEnterPerson"));
                    if(!bao[0].equals("")){
                    number.setText(bao.length+"");
                    for(int i=0;i<bao.length;i++) {
                        if (bao[i].equals(request.getCurrentId(ActivitysDetail.this)))
                        {
                            baoming.setVisibility(View.GONE);
                            yibaoming.setVisibility(View.VISIBLE);
                        }
                    }
                }
                }
                else{
                    baoming.setVisibility(View.GONE);
                    baomingrenshu.setVisibility(View.GONE);
                    bubaoming.setVisibility(View.VISIBLE);
                    yibaoming.setVisibility(View.GONE);
                }


                all.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(ActivitysDetail.this, "活动加载失败请重试", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.GONE);
            }
        });

    }

    public void baoming(View view){
        Intent intent = new Intent(this, ActivitysEnroll.class);
        intent.putExtra("ID",id);
        intent.putExtra("info",enterinfo);
        startActivity(intent);
        Toast.makeText(this, "部分信息已为您自动填写", Toast.LENGTH_SHORT).show();
    }

    public void wenda(View view){
        Intent intent = new Intent(this, ActivitysQuestion.class);
        intent.putExtra("ID",id);
        startActivity(intent);

    }



    public  void back(View view){
        finish();
    }

}
