package com.button.notice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.button.notice.Discover.LostandfoundActivity;
import com.button.notice.R;
import com.button.notice.User.UserInfo;


/**
 * Created by Jack on 2017/8/22.
 */

public class discoverFragment extends Fragment {

    LinearLayout xunwu,wenjuan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        xunwu = view.findViewById(R.id.xunwu);
        wenjuan = view.findViewById(R.id.wenjuan);



        xunwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LostandfoundActivity.class );
                startActivity(intent);
            }
        });

        wenjuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }

    public static discoverFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        discoverFragment fragment = new discoverFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
