package com.button.notice.User.MyNoticeFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.Notice.noticeDetailActivity;
import com.button.notice.R;
import com.button.notice.User.MyActivitys.ActivityDetail;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;
import com.button.notice.util.ACache;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends ListFragment {
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

        Activityget();
        Activitygeto();

    }


    public void Activityget(){

        ACache aCache = ACache.get(getActivity());
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("myactivity");
        if(list!=null){
            SimpleAdapter adapter =new SimpleAdapter(getActivity(), list,R.layout.notice_listviewitem,new String[]{"activityTitle", "activityInfo","createTime","activityUser"},new int[]{R.id.title, R.id.info,R.id.date,R.id.time});
            a.setAdapter(adapter);
        }
    }

    public void Activitygeto(){
        ACache aCache = ACache.get(getActivity());
        CommonRequest request = new CommonRequest();
        request.setTable("table_activity_info");
        request.setWhereEqualTo("activityUser",request.getCurrentId(getActivity()));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    ArrayList<HashMap<String, String>> list = response.getDataList();

                    if( aCache.getAsObject("myactivity")!=null){

                        if (((ArrayList<HashMap<String,String>>) aCache.getAsObject("myactivity")).size()!=list.size()) {

                            aCache.put("myactivity", list);
                            SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.notice_listviewitem, new String[]{"activityTitle", "activityInfo", "createTime", "activityUser"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
                            a.setAdapter(adapter);
                            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }
                        else{
                            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }

                    }
                    else {

                        aCache.put("myactivity", list);
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.notice_listviewitem, new String[]{"activityTitle", "activityInfo", "createTime", "activityUser"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.time});
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



    public static ActivityFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        HashMap<String,String> map=(HashMap<String,String>) l.getItemAtPosition(position);
        String Text=map.get("Id");
        Intent intent = new Intent();
        intent.putExtra("id", Text);
        intent.setClass(getActivity(), ActivityDetail.class);
        getActivity().startActivity(intent);
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
    }

}
