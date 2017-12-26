package com.notice.button.button.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.notice.button.button.Jellyrefresh.JellyRefreshLayout;
import com.notice.button.button.Jellyrefresh.PullToRefreshLayout;
import com.notice.button.button.Notice.noticeMain;
import com.notice.button.button.Notice.noticeNew;
import com.notice.button.button.R;

/**
 * Created by Jack on 2017/8/22.
 */

//, AbsListView.OnScrollListener


public class noticeFragment extends ListFragment {
    private ListView listView;
    private JellyRefreshLayout mJellyLayout;

@Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
    super.onActivityCreated(savedInstanceState);


    listView=(ListView)view.findViewById(android.R.id.list) ;
    mJellyLayout = (JellyRefreshLayout)view.findViewById(R.id.jelly_refresh);
    mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            pullToRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {


                    //在这个里面写查询功能，然后通过适配器给Listview 加油

                    mJellyLayout.setRefreshing(false);  //如果 成功就调用这个来使刷新中断 我猜的
                }
            }, 3000);  //超时时长，多少毫秒就不刷新，还是我猜的。
        }
    });

    View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, null);
    mJellyLayout.setLoadingView(loadingView);
    Button toNewNotice =(Button) view.findViewById (R.id.toNewNotice);//新通知跳转
    toNewNotice.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),noticeNew.class );
                    startActivity(intent);
                }
            })
    );
    return view;


}


    public static noticeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        noticeFragment fragment = new noticeFragment();
        fragment.setArguments(args);
        return fragment;
    }



}