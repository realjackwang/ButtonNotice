package com.button.notice.User;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.R;
import com.button.notice.User.CircleFragment.associationFragment;
import com.button.notice.User.CircleFragment.organizationFragment;
import com.button.notice.User.CircleFragment.schoolFragment;
import com.button.notice.User.MyNoticeFragment.ActivityFragment;
import com.button.notice.User.MyNoticeFragment.LostandFoundFragment;
import com.button.notice.User.MyNoticeFragment.NoticeFragment;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyNotice extends AppCompatActivity {
    private ListView a;
    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<Fragment> tabFragments;
    private List<String> tabIndicators;
    private ContentPagerAdapter contentAdapter;


    com.button.notice.util.ProgressBar b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_user_mynotice);
        a=findViewById(android.R.id.list);
        b=findViewById(R.id.progressBar);
//        noticeget();
//        noticegeto();
        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initContent();
        initTab();



    }


    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.lightwhite), ContextCompat.getColor(this, R.color.white));  //没选中和选中时字的颜色
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));  //Tab底部横线的颜色
        ViewCompat.setElevation(mTabTl, 10);  //高度
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();

        tabIndicators.add("通知");   //添加Tab
        tabIndicators.add("活动");
        tabIndicators.add("寻物");
        tabIndicators.add("问卷");
        tabIndicators.add("竞赛");
        tabIndicators.add("广告");
        tabIndicators.add("心声");

        tabFragments = new ArrayList<>();

        tabFragments.add(NoticeFragment.newInstance("通知"));    //添加fragment
        tabFragments.add(ActivityFragment.newInstance("活动"));
        tabFragments.add(LostandFoundFragment.newInstance("寻物"));
        tabFragments.add(associationFragment.newInstance("问卷"));
        tabFragments.add(associationFragment.newInstance("竞赛"));
        tabFragments.add(associationFragment.newInstance("广告"));
        tabFragments.add(associationFragment.newInstance("心声"));

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());

        mContentVp.setOffscreenPageLimit(3);//防止 fragment重新加载

        mContentVp.setAdapter(contentAdapter);



    }






    public void noticeget(){

        ACache aCache = ACache.get(MyNotice.this);
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("mynotice");
        if(list!=null){
        SimpleAdapter adapter =new SimpleAdapter(MyNotice.this, list,R.layout.notice_listviewitem,new String[]{"noticeTitle", "noticeText","noticeDate","noticeTime"},new int[]{R.id.title, R.id.info,R.id.date,R.id.time});
        a.setAdapter(adapter);
        }
    }

    public void noticegeto(){
        ACache aCache = ACache.get(MyNotice.this);
        CommonRequest request = new CommonRequest();
        request.setTable("table_notice_info");
        request.setWhereEqualTo("noticeUser",request.getCurrentId(MyNotice.this));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    ArrayList<HashMap<String, String>> list = response.getDataList();

                    if( aCache.getAsObject("mynotice")!=null){

                    if (((ArrayList<HashMap<String,String>>) aCache.getAsObject("mynotice")).size()!=list.size()) {

                        aCache.put("mynotice", list);
                        SimpleAdapter adapter = new SimpleAdapter(MyNotice.this, list, R.layout.notice_listviewitem, new String[]{"noticeTitle", "noticeText", "noticeDate", "noticeTime"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                        a.setAdapter(adapter);
                        new Handler().postDelayed(() -> b.setVisibility(View.GONE), 1000);  //延时操作，使动画消失
                    }
                    else{
                        new Handler().postDelayed(() -> b.setVisibility(View.GONE), 1000);  //延时操作，使动画消失
                    }

                }
                    else {

                        aCache.put("mynotice", list);
                        SimpleAdapter adapter = new SimpleAdapter(MyNotice.this, list, R.layout.notice_listviewitem, new String[]{"noticeTitle", "noticeText", "noticeDate", "noticeTime"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                        a.setAdapter(adapter);
                        new Handler().postDelayed(() -> b.setVisibility(View.GONE), 1000);  //延时操作，使动画消失
                    }

                }
            }
            @Override
            public void fail(String failCode, String failMsg) {

            }
        });


    }

    public void back(View view){
        finish();
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }




}
