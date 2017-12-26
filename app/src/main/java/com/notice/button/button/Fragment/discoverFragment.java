package com.notice.button.button.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notice.button.button.R;


/**
 * Created by Jack on 2017/8/22.
 */

public class discoverFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
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
