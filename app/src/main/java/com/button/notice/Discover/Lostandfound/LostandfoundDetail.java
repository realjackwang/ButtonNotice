package com.button.notice.Discover.Lostandfound;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.button.notice.R;
import com.button.notice.util.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.button.notice.service.Constant.URL_Files;

public class LostandfoundDetail extends AppCompatActivity {

    String id =null;

    ImageView im;
    FrameLayout all;
    TextView type,time,place,title,info,phone;
    ProgressBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_lostandfound_detail);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        init();
        infoget();


    }

    private void init(){

        type =findViewById(R.id.type);
        time =findViewById(R.id.time);
        place =findViewById(R.id.place);
        title =findViewById(R.id.title);
        info =findViewById(R.id.info);
        phone = findViewById(R.id.phone);
        im =findViewById(R.id.image);
        all= findViewById(R.id.all);
        bar= findViewById(R.id.progressBar);


        all.setVisibility(View.GONE);
        String imageUrl = URL_Files+"/lostandfound/"+id+"/photo.jpg";
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






    }

}
