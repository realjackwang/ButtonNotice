package com.button.notice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.button.notice.R;
import com.button.notice.User.settings;


/**
 * Created by Jack on 2017/8/22.
 */

public class userFragment extends Fragment {

    TextView setting;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        setting  = view.findViewById (R.id.settings);






      setting.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getActivity(),settings.class );
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




    }
    }

