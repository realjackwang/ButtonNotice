package com.notice.button.button.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.notice.button.button.R;

/**
 * Created by Jack on 2017/8/22.
 */

public class userFragment extends Fragment {
    private FragmentActivity mContext;
    private static final int REQUESTCODE = 101;
    private FrameLayout layout;
    private TextView ea, eb;
    private ImageView ec;
    private LinearLayout ed, ee,ef,eg,eh;
    private SharedPreferences sp, sb;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;


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


}

