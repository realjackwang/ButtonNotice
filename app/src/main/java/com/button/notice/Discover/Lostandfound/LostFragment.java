package com.button.notice.Discover.Lostandfound;


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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LostFragment extends ListFragment {

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
        ArrayList<HashMap<String, String>> list =  (ArrayList<HashMap<String,String>>) aCache.getAsObject("lost");
        if(list!=null){
            SimpleAdapter adapter =new SimpleAdapter(getContext(), list,R.layout.list_item_circle,new String[]{"lostType", "lostInfo"},new int[]{R.id.title, R.id.info});
            listView.setAdapter(adapter);
        }


    }
    public void circlegeto(){
        ACache aCache = ACache.get(getActivity());

        CommonRequest request = new CommonRequest();
        request.setTable("table_lostandfound_lost");
        request.Query(new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                if (response.getDataList().size() > 0) {

                    ArrayList<HashMap<String, String>> list = response.getDataList();


                    if (aCache.getAsObject("lost") != null) {

                        if (((ArrayList<HashMap<String, String>>) aCache.getAsObject("lost")).size() != list.size()) {

                            aCache.put("lost", list);
                            SimpleAdapter adapter =new SimpleAdapter(getContext(), list,R.layout.list_item_circle,new String[]{"lostType", "lostInfo"},new int[]{R.id.title, R.id.info});
                            listView.setAdapter(adapter);
                                    }
                                }
                                else {

                                    aCache.put("lost", list);
                                    SimpleAdapter adapter =new SimpleAdapter(getContext(), list,R.layout.list_item_circle,new String[]{"lostType", "lostInfo"},new int[]{R.id.title, R.id.info});
                                    listView.setAdapter(adapter);
                                }

                            }
                        }

                        @Override
                        public void fail(String failCode, String failMsg) {

                        }
                    });
                }





    public static LostFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        LostFragment fragment = new LostFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
