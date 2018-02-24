package com.button.notice.Button;

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
import android.widget.Button;
import android.widget.Toast;

import com.button.notice.Button.firstLogin.firstLoginFragment1;
import com.button.notice.Button.firstLogin.firstLoginFragment2;
import com.button.notice.Button.firstLogin.firstLoginFragment3;
import com.button.notice.Button.firstLogin.firstLoginFragment4;
import com.button.notice.Fragment.MainActivity;
import com.button.notice.R;
import com.button.notice.User.Circle;
import com.button.notice.User.CircleFragment.associationFragment;
import com.button.notice.User.CircleFragment.organizationFragment;
import com.button.notice.User.CircleFragment.schoolFragment;

import java.util.ArrayList;
import java.util.List;

public class FirstLogin extends AppCompatActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<Fragment> tabFragments;
    private List<String> tabIndicators;
    private ContentPagerAdapter contentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initContent();
        initTab();




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

        tabIndicators.add("");   //添加Tab
        tabIndicators.add("");
        tabIndicators.add("");
        tabIndicators.add("");

        tabFragments = new ArrayList<>();

        tabFragments.add(firstLoginFragment1.newInstance("1"));    //添加fragment
        tabFragments.add(firstLoginFragment2.newInstance("2"));
        tabFragments.add(firstLoginFragment3.newInstance("3"));
        tabFragments.add(firstLoginFragment4.newInstance("4"));

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());


        mContentVp.setOffscreenPageLimit(4);//防止 fragment重新加载

        mContentVp.setAdapter(contentAdapter);



    }

    public void sign(View view){
        Intent intent = new Intent(FirstLogin.this, buttonLogin.class);
        startActivity(intent);
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
