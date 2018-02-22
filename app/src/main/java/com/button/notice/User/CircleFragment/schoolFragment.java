package com.button.notice.User.CircleFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.R;
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
public class schoolFragment extends ListFragment {
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_circle_school, null);
        listView= view.findViewById(android.R.id.list);


        circleget();
        circlegeto();



        return view;
    }


    public void circleget(){

        ACache aCache = ACache.get(getActivity());
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("circle");
        if(list!=null){
            SimpleAdapter adapter =new SimpleAdapter(getContext(), list,R.layout.circle_listviewitem,new String[]{"communityName", "communityInfo"},new int[]{R.id.title, R.id.info});
            listView.setAdapter(adapter);
        }


    }
    public void circlegeto(){

        CommonRequest request = new CommonRequest();
        request.setTable("table_user_info");
        request.setWhereEqualTo("userId",request.getCurrentId(getActivity()));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                ArrayList<HashMap<String, String>> list = response.getDataList();

                if(list.size()>0) {


                    HashMap<String, String> map = list.get(0);
                    String[] circle = StringUtil.ChangetoString(map.get("userCommunity"));


                    CommonRequest request1 = new CommonRequest();
                    request1.setTable("table_community_info");
                    request1.setWhereEqualMoreTo("Id", circle);
                    request1.Query(new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            if (response.getDataList().size() > 0) {
                                ACache aCache = ACache.get(getActivity());
                                ArrayList<HashMap<String, String>> list = response.getDataList();

                                if (aCache.getAsObject("circle") != null) {

                                    if (((ArrayList<HashMap<String, String>>) aCache.getAsObject("circle")).size() != list.size()) {

                                        aCache.put("circle", list);
                                        SimpleAdapter adapter =new SimpleAdapter(getContext(), list,R.layout.circle_listviewitem,new String[]{"communityName", "communityInfo"},new int[]{R.id.title, R.id.info});
                                        listView.setAdapter(adapter);
                                    }

                                } else {

                                    aCache.put("circle", list);
                                    SimpleAdapter adapter =new SimpleAdapter(getContext(), list,R.layout.circle_listviewitem,new String[]{"communityName", "communityInfo"},new int[]{R.id.title, R.id.info});
                                    listView.setAdapter(adapter);
                                }

                            }
                        }

                        @Override
                        public void fail(String failCode, String failMsg) {

                        }
                    });
                }
            }
            @Override
            public void fail(String failCode, String failMsg) {

            }
        });

    }



    public static schoolFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        schoolFragment fragment = new schoolFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
