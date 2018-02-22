package com.button.notice.User;



import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.button.notice.Fragment.noticeFragment;
import com.button.notice.R;
import com.button.notice.User.CircleFragment.associationFragment;
import com.button.notice.User.CircleFragment.organizationFragment;
import com.button.notice.User.CircleFragment.schoolFragment;

import java.util.ArrayList;
import java.util.List;

public class Circle extends AppCompatActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<Fragment> tabFragments;
    private List<String> tabIndicators;
    private ContentPagerAdapter contentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_circle);

        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initContent();
        initTab();

    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.text_clo), ContextCompat.getColor(this, R.color.blue));

        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.blue));
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();

            tabIndicators.add("校园");
            tabIndicators.add("组织");
            tabIndicators.add("社团");

            tabFragments = new ArrayList<>();

            tabFragments.add(schoolFragment.newInstance("校园"));
            tabFragments.add(organizationFragment.newInstance("组织"));
            tabFragments.add(associationFragment.newInstance("社团"));

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
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
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }

}



