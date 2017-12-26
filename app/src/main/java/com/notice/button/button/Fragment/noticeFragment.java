package com.notice.button.button.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.notice.button.button.Notice.noticeMain;
import com.notice.button.button.Notice.noticeNew;
import com.notice.button.button.R;

/**
 * Created by Jack on 2017/8/22.
 */

//, AbsListView.OnScrollListener


public class noticeFragment extends Fragment {


@Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);


    super.onActivityCreated(savedInstanceState);
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