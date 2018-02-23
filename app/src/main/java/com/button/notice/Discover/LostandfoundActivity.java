package com.button.notice.Discover;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.button.notice.Button.FirstLogin;
import com.button.notice.Button.buttonLogin;
import com.button.notice.Discover.Lostandfound.FoundFragment;
import com.button.notice.Discover.Lostandfound.LostFragment;
import com.button.notice.Discover.Lostandfound.LostandfoundNew;
import com.button.notice.Discover.Lostandfound.TopPopWindow;
import com.button.notice.Notice.noticeDetailActivity;
import com.button.notice.R;
import com.button.notice.User.Circle;
import com.button.notice.User.CircleFragment.associationFragment;
import com.button.notice.User.CircleFragment.organizationFragment;
import com.button.notice.User.CircleFragment.schoolFragment;

import java.util.ArrayList;
import java.util.List;

public class LostandfoundActivity extends AppCompatActivity implements View.OnClickListener {


    private TabLayout mTabTl;
    private ViewPager mContentVp;


    ImageView more;

    private List<Fragment> tabFragments;
    private List<String> tabIndicators;
    private LostandfoundActivity.ContentPagerAdapter contentAdapter;
    private TopPopWindow topPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_lostandfound);

        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);
        more = findViewById(R.id.more);

        more.setVisibility(View.VISIBLE);
        initContent();
        initTab();
        more.setOnClickListener(this);
    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.text_clo), ContextCompat.getColor(this, R.color.blue));  //没选中和选中时字的颜色
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.blue));  //Tab底部横线的颜色
        ViewCompat.setElevation(mTabTl, 10);  //高度
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();

        tabIndicators.add("寻物");   //添加Tab
        tabIndicators.add("寻主");


        tabFragments = new ArrayList<>();

        tabFragments.add(LostFragment.newInstance("寻物"));    //添加fragment
        tabFragments.add(FoundFragment.newInstance("寻主"));


        contentAdapter = new LostandfoundActivity.ContentPagerAdapter(getSupportFragmentManager());

        mContentVp.setOffscreenPageLimit(2);//防止 fragment重新加载

        mContentVp.setAdapter(contentAdapter);



    }

    private void showTopRightPopMenu() {
        if (topPopWindow == null) {
            //(activity,onclicklistener,width,height)
            topPopWindow = new TopPopWindow(LostandfoundActivity.this, (View.OnClickListener) this, 360, 240);
            //监听窗口的焦点事件，点击窗口外面则取消显示
            topPopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        topPopWindow.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        topPopWindow.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        topPopWindow.showAsDropDown(more, 30, 30);
        //如果窗口存在，则更新
        topPopWindow.update();
    }

    public void back(View view){
        finish();
    }  //返回

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
        /**
         * 发布按钮
         */
        case R.id.more:
            showTopRightPopMenu();
        break;

        /**
         * popwindow引入的方法的onclick的listener引入到this
         * popwindow的点击事件
         */
        case R.id.ll_lost:



        Intent intent = new Intent(getApplicationContext(), LostandfoundNew.class);
        intent.putExtra("type", "lost");
        startActivity(intent);
        topPopWindow.dismiss();
        break;

        case R.id.ll_found:
            Intent intent2 = new Intent(getApplicationContext(), LostandfoundNew.class);
            intent2.putExtra("type", "found");
            startActivity(intent2);
            topPopWindow.dismiss();
        break;

        default:
        break;

    }

    }

//    public void more(View v){
//        Intent intent = new Intent(LostandfoundActivity.this, LostandfoundNew.class);
//        startActivity(intent);
//    }

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
