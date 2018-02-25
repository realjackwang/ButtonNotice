package com.button.notice.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.button.notice.Discover.Activitys.ActivitysNew;
import com.button.notice.Discover.LostandfoundActivity;
import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.HashMap;

import static com.button.notice.service.Constant.URL_Files;


/**
 * Created by Jack on 2017/8/22.
 */

public class discoverFragment extends Fragment {

    LinearLayout xunwu,wenjuan;

    private ViewPager viewPager;
    private ViewGroup group;
    private ImageView[] imageViews;//提示性点点数组
    private ImageView imageView,more,v;
    private int[] images;//图片ID数组
    private int currentPage=0;//当前展示的页码
    String[] imageid ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TEXT","onCreateView");
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        viewPager =view.findViewById(R.id.viewpager);
        xunwu = view.findViewById(R.id.xunwu);
        wenjuan = view.findViewById(R.id.wenjuan);
        group = (ViewGroup)view.findViewById(R.id.viewGroup);
        more = view.findViewById(R.id.more);




        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getContext());  //初始化图片加载器
        ImageLoader.getInstance().init(configuration);

        getimage();
        getimageo();


        viewPager.setOverScrollMode(viewPager.OVER_SCROLL_NEVER);//去除左右滑动光晕效果
        viewPager.setPageMargin(0);  //设置间距



        xunwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LostandfoundActivity.class );
                startActivity(intent);
            }
        });
        wenjuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ActivitysNew.class );
                startActivity(intent);
            }
        });


        return view;

    }

    private void getimage(){
        ACache aCache = ACache.get(getActivity());
        if(!(aCache.getAsString("images")==null||aCache.getAsString("images").equals(""))){
            imageload(aCache.getAsString("images"));
        }
    }


    private void getimageo(){
        CommonRequest request = new CommonRequest();
        request.setTable("table_activity_info");
        request.setList("Id");
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if(!isVisible()){       //这样一串小小的代码解决了切换页面后，异步任务仍然运行，导致的错误

                }

                else{
                    ACache aCache = ACache.get(getActivity());
                    imageid = StringUtil.ChangetoString(response.getResMsg());
                    if(!aCache.getAsString("images").equals(response.getResMsg())) {
                        aCache.put("images", response.getResMsg());
                        group.removeAllViews();
                        imageload("0");
                    }

                    }
            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        });




    }


    private void imageload(String x){

        if(x!="0"){
            imageid=StringUtil.ChangetoString(x);
        }

        //绑定监听事件
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        images=new int[imageid.length];

        //有多少张图就有多少个点点
        imageViews = new ImageView[imageid.length];
        for(int i =0;i<imageid.length;i++){
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(30,30));
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;

            //默认第一张图显示为选中状态
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.dot_blues);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.dot_grey);
            }

            group.addView(imageViews[i]);

        }





        PagerAdapter adapter=new PagerAdapter(){
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return images.length;
            }
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                // TODO Auto-generated method stub

                return arg0==arg1;

            }
            @Override
            public void destroyItem(ViewGroup container,int position,Object o){
//                container.removeViewAt(position);
            }
            @Override
            public Object instantiateItem(ViewGroup container,int position){

                RoundedImageView im=new RoundedImageView(getContext());

                    im.setPadding(100,10,100,10);
                    im.setCornerRadius((float)50);
                    im.mutateBackground(true);

                    im.setScaleType(ImageView.ScaleType.FIT_XY);

                String imageUrl = URL_Files+"/activitys/"+imageid[position]+"/poster.jpg";

                ImageSize mImageSize = new ImageSize(100, 200);

                //显示图片的配置
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.dot_blues)
                        .showImageOnFail(R.drawable.wrong)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                ImageLoader.getInstance().displayImage(imageUrl,im,options);

                container.addView(im);
                return im;

            }





//            @Override
//            public float getPageWidth(int position){
//
//                return 0.9f;
//            }


        };

        viewPager.setAdapter(adapter);



    }


    public static discoverFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        discoverFragment fragment = new discoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //pageView监听器
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        //如果切换了，就把当前的点点设置为选中背景，其他设置未选中背景
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            for(int i=0;i<imageViews.length;i++){
                imageViews[arg0].setBackgroundResource(R.drawable.dot_blues);
                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.drawable.dot_grey);
                }
            }

        }

    }


    @Override
    public void onAttach(Context context) {
        Log.d("TEXT","onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("TEXT","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("TEXT","onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("TEXT","onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("TEXT","onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("TEXT","onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("TEXT","onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("TEXT","onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("TEXT","onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("TEXT","onDetach");
        super.onDetach();
    }

}
