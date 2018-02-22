package com.button.notice.User.CircleFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.button.notice.Fragment.noticeFragment;
import com.button.notice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class organizationFragment extends ListFragment {


    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_circle_organization, null);




        listView= view.findViewById(android.R.id.list);

        return view;
    }


    public static organizationFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        organizationFragment fragment = new organizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
