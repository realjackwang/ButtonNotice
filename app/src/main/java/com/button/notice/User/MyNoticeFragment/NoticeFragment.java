package com.button.notice.User.MyNoticeFragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.R;
import com.button.notice.User.MyNotice;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends ListFragment {
    private ListView a;
    com.button.notice.util.ProgressBar b;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_circle_school, null);
        a= view.findViewById(android.R.id.list);
        b=view.findViewById(R.id.progressBar);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {   //在Activity创建后再获取信息
        super.onActivityCreated(savedInstanceState);

        noticeget();
        noticegeto();

    }


    public void noticeget(){

        ACache aCache = ACache.get(getActivity());
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("mynotice");
        if(list!=null){
            SimpleAdapter adapter =new SimpleAdapter(getActivity(), list,R.layout.notice_listviewitem,new String[]{"noticeTitle", "noticeText","noticeDate","noticeTime"},new int[]{R.id.title, R.id.info,R.id.date,R.id.time});
            a.setAdapter(adapter);
        }
    }

    public void noticegeto(){
        ACache aCache = ACache.get(getActivity());
        CommonRequest request = new CommonRequest();
        request.setTable("table_notice_info");
        request.setWhereEqualTo("noticeUser",request.getCurrentId(getActivity()));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    ArrayList<HashMap<String, String>> list = response.getDataList();

                    if( aCache.getAsObject("mynotice")!=null){

                        if (((ArrayList<HashMap<String,String>>) aCache.getAsObject("mynotice")).size()!=list.size()) {

                            aCache.put("mynotice", list);
                            SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.notice_listviewitem, new String[]{"noticeTitle", "noticeText", "noticeDate", "noticeTime"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                            a.setAdapter(adapter);
                            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }
                        else{
                            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }

                    }
                    else {

                        aCache.put("mynotice", list);
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.notice_listviewitem, new String[]{"noticeTitle", "noticeText", "noticeDate", "noticeTime"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                        a.setAdapter(adapter);
                        getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }

                }
            }
            @Override
            public void fail(String failCode, String failMsg) {

            }
        });


    }



    public static NoticeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        NoticeFragment fragment = new NoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
