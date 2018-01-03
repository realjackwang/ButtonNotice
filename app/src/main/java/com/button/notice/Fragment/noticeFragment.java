package com.button.notice.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.button.notice.Jellyrefresh.JellyRefreshLayout;
import com.button.notice.Jellyrefresh.PullToRefreshLayout;
import com.button.notice.Notice.noticeDetail;
import com.button.notice.Notice.noticeNew;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 2017/8/22.
 */

//, AbsListView.OnScrollListener


public class noticeFragment extends ListFragment {
    private ListView listView;
    private JellyRefreshLayout mJellyLayout;
    private SimpleAdapter simpleAdapter;
    int i=1;
    Button toNewNotice;

@Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable
        ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_notice, null);

    ACache aCache =ACache.get(getActivity());
    ArrayList<HashMap<String,String>> arrayList =(ArrayList<HashMap<String,String>>) aCache.getAsObject("listacache1");

    if(arrayList!=null) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, R.layout.listview_item, new String[]{"noticeTitle", "noticeText",}, new int[]{R.id.title, R.id.info});
        setListAdapter(adapter);

    }
   // listView.setOnItemClickListener(new MyListener());
    listView= view.findViewById(android.R.id.list);
    mJellyLayout = view.findViewById(R.id.jelly_refresh);
    mJellyLayout.setPullToRefreshListener(pullToRefreshLayout -> {


                CommonRequest request = new CommonRequest();
                request.setTable("table_notice_info");
                request.Query(new ResponseHandler() {
                    @Override
                    public void success(CommonResponse response) {
                        mJellyLayout.setRefreshing(false);
                        ArrayList<HashMap<String, String>> arrayList1 = response.getDataList();
                        ACache aCache1 = ACache.get(getActivity());
                        aCache1.put("listacache1", arrayList1);
                        SimpleAdapter adapter =new SimpleAdapter(getContext(), arrayList1,R.layout.listview_item,new String[]{"noticeTitle","noticeText",},new int[]{R.id.title,R.id.info});
                        setListAdapter(adapter);

                    }

                    @Override
                    public void fail(String failCode, String failMsg) {

                    }
                });

                //在这个里面写查询功能，然后通过适配器给Listview             加油哦
              //如果 成功就调用这个来使刷新中断 我猜的
            });  //超过多少毫秒就停止刷新，还是我猜的。







    View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, null);
    mJellyLayout.setLoadingView(loadingView);

    {
        toNewNotice = view.findViewById (R.id.toNewNotice);//发送新通知跳转
        toNewNotice.setOnClickListener((v -> {
        Intent intent = new Intent(getActivity(),noticeNew.class );
        startActivity(intent);
        })
        );
    }

    return view;
}
//    private List<Map<String, Object>> getData() {
//        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
//        Map<String, Object> mMap = new HashMap<String, Object>();
//        mMap.put("image", R.drawable.discover);
//        mMap.put("data", "one");
//        mList.add(mMap);
//
//        mMap = new HashMap<String, Object>();
//        mMap.put("image", R.drawable.discover);
//        mMap.put("data", "two");
//        mList.add(mMap);
//        return mList;
//    }


    public static noticeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        noticeFragment fragment = new noticeFragment();
        fragment.setArguments(args);
        return fragment;
    }



//    class MyListener  implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//                                long id) {
//            Map<String, Object> mMap = (Map<String, Object>) simpleAdapter.getItem(position);
//            Toast.makeText(getActivity(), mMap.get("data").toString(), Toast.LENGTH_SHORT).show();
//        }
//    }



}