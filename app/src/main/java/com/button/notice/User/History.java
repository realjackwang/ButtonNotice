package com.button.notice.User;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.button.notice.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);



        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);  //初始化图片加载器
        ImageLoader.getInstance().init(configuration);


        final ImageView mImageView = (ImageView) findViewById(R.id.image);
        String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519549979492&di=7cf2ddcafe5f212aeaab68df4a4e9793&imgtype=0&src=http%3A%2F%2Fimgm.cnmo-img.com.cn%2Fappimg%2Fscreenpic%2Fmiddle%2F787%2F786480.jpg";
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.dot_blues)
                .showImageOnFail(R.drawable.beijing)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);




    }
}
