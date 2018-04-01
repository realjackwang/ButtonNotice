package com.button.notice.User.MyNoticeFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.button.notice.Discover.Lostandfound.LostandfoundDetail;
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
public class LostandFoundFragment extends ListFragment {
    private ListView a;
    com.button.notice.util.ProgressBar b;
    ArrayList<HashMap<String, String>> list1 = new ArrayList<>();
    ArrayList<HashMap<String, String>> list2 = new ArrayList<>();
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

        Lostandfoundget();
        Lostandfoundgeto();

    }


    public void Lostandfoundget(){

        ACache aCache = ACache.get(getActivity());
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("mylostandfound");
        if(list!=null){
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.list_item_xunwu, new String[]{"foundType", "foundName", "foundDate", "foundState","lost"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.state,R.id.lost});
            a.setAdapter(adapter);
        }
    }

    public void Lostandfoundgeto(){
        ACache aCache = ACache.get(getActivity());
        CommonRequest request = new CommonRequest();
        request.setTable("table_lostandfound_found");
        request.setWhereEqualTo("foundUser",request.getCurrentId(getActivity()));
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    list1 = response.getDataList();

                    CommonRequest request = new CommonRequest();
                    request.setTable("table_lostandfound_lost");
                    request.setWhereEqualTo("lostUser",request.getCurrentId(getActivity()));
                    request.Query(new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {

                            if (response.getDataList().size() > 0) {
                                list2 = response.getDataList();

                                for (int i = 0; i < list2.size(); i++) {
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("foundName", list2.get(i).get("lostName"));
                                    map.put("foundPlace", list2.get(i).get("lostPlace"));
                                    map.put("foundName", list2.get(i).get("lostInfo"));
                                    map.put("foundPhone", list2.get(i).get("lostPhone"));
                                    map.put("foundType", list2.get(i).get("lostType"));
                                    map.put("foundDate", list2.get(i).get("lostDate"));
                                    map.put("foundState", list2.get(i).get("lostState"));
                                    map.put("lost","寻物：");
                                    list1.add(map);
                                }


                                if (aCache.getAsObject("mylostandfound") != null) {

                                    if (((ArrayList<HashMap<String, String>>) aCache.getAsObject("mylostandfound")).size() != list1.size()) {

                                        aCache.put("mylostandfound", list1);
                                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list1, R.layout.list_item_xunwu, new String[]{"foundType", "foundName", "foundDate", "foundState","lost"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.state,R.id.lost});
                                        a.setAdapter(adapter);
                                        getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                    } else {
                                        getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                    }

                                } else {

                                    aCache.put("mylostandfound", list1);
                                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), list1, R.layout.list_item_xunwu, new String[]{"foundType", "foundName", "foundDate", "foundState","lost"}, new int[]{R.id.title, R.id.info, R.id.date, R.id.state,R.id.lost});                        a.setAdapter(adapter);
                                    getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                }

                            }
                        }

                        @Override
                        public void fail(String failCode, String failMsg) {
                            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }
                    });


                }
            }
            @Override
            public void fail(String failCode, String failMsg) {
                getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });


    }



    public static LostandFoundFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        LostandFoundFragment fragment = new LostandFoundFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        HashMap<String,String> map=(HashMap<String,String>) l.getItemAtPosition(position);
        String Text=map.get("Id");
        Intent intent = new Intent();
        intent.putExtra("id", Text);
        intent.setClass(getActivity(), LostandfoundDetail.class);
        getActivity().startActivity(intent);
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
    }

}
