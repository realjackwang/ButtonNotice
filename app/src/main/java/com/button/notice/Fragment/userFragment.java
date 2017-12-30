package com.button.notice.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.button.notice.R;


/**
 * Created by Jack on 2017/8/22.
 */

public class userFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
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




    }
    }

