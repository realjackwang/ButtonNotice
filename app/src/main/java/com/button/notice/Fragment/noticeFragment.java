package com.button.notice.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.Jellyrefresh.JellyRefreshLayout;
import com.button.notice.Jellyrefresh.PullToRefreshLayout;
import com.button.notice.Notice.noticeNew;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 2017/8/22.
 */

//, AbsListView.OnScrollListener


public class noticeFragment extends ListFragment {
    private ListView listView;
    private JellyRefreshLayout mJellyLayout;
    int i=1;
    Button toNewNotice;

@Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
    super.onActivityCreated(savedInstanceState);



    ACache aCache =ACache.get(getActivity());
    ArrayList<HashMap<String,String>> arrayList =(ArrayList<HashMap<String,String>>) aCache.getAsObject("listacache1");

    if(arrayList!=null) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, R.layout.listview_item, new String[]{"noticeTitle", "noticeText",}, new int[]{R.id.title, R.id.info});
        setListAdapter(adapter);
    }

    listView=(ListView)view.findViewById(android.R.id.list) ;
    mJellyLayout = (JellyRefreshLayout)view.findViewById(R.id.jelly_refresh);
    mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {


                    CommonRequest request = new CommonRequest();
                    request.setTable("table_notice_info");
                    request.Query(new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            mJellyLayout.setRefreshing(false);
                            ArrayList<HashMap<String, String>> arrayList = response.getDataList();
                            ACache aCache = ACache.get(getActivity());
                            aCache.put("listacache1",arrayList);
                            SimpleAdapter adapter =new SimpleAdapter(getContext(),arrayList,R.layout.listview_item,new String[]{"noticeTitle","noticeText",},new int[]{R.id.title,R.id.info});
                            setListAdapter(adapter);

                        }

                        @Override
                        public void fail(String failCode, String failMsg) {

                        }
                    });

                    //在这个里面写查询功能，然后通过适配器给Listview             加油哦
                  //如果 成功就调用这个来使刷新中断 我猜的
                }
            });  //超过多少毫秒就停止刷新，还是我猜的。







    View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, null);
    mJellyLayout.setLoadingView(loadingView);
    toNewNotice = view.findViewById (R.id.toNewNotice);//新通知跳转
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