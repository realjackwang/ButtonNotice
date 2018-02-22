package com.button.notice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.button.notice.Notice.noticeNew;
import com.button.notice.R;
import com.button.notice.User.Circle;
import com.button.notice.User.Collection;
import com.button.notice.User.Drafts;
import com.button.notice.User.History;
import com.button.notice.User.Identification;
import com.button.notice.User.MyNotice;
import com.button.notice.User.Settings;
import com.button.notice.User.UserInfo;
import com.button.notice.util.ACache;


/**
 * Created by Jack on 2017/8/22.
 */

public class userFragment extends Fragment {

    TextView setting,username;
    LinearLayout circle,collection,drafts,history,identification,mynotice;
    FrameLayout userinfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setting  = view.findViewById (R.id.settings);
        circle =  view.findViewById (R.id.user_circle);
        collection =  view.findViewById (R.id.user_collection);
        drafts =  view.findViewById (R.id.user_drafts);
        history =  view.findViewById (R.id.user_history);
        identification =  view.findViewById (R.id.renzhen);
        mynotice =  view.findViewById (R.id.user_mynotice);
        userinfo =  view.findViewById (R.id.user_info);
        username = view.findViewById(R.id.User_name);



        setting.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getActivity(),Settings.class );
              startActivity(intent);
          }
      });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Circle.class );
                startActivity(intent);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Collection.class );
                startActivity(intent);
            }
        });
        drafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Drafts.class );
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),History.class );
                startActivity(intent);
            }
        });
        identification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Identification.class );
                startActivity(intent);
            }
        });
        mynotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyNotice.class );
                startActivity(intent);
            }
        });
        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserInfo.class );
                startActivity(intent);
            }
        });


        return view;
    }

    public static userFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        userFragment fragment = new userFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public  void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ACache aCache =ACache.get(getActivity());
        username.setText(aCache.getAsString("name"));  //设置用户名


    }
    }

