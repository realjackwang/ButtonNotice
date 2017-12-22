package com.notice.button.button;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bzdell on 2017/12/14.
 */

public class noticeAdapter extends ArrayAdapter {

        private final int resourceId;

        public noticeAdapter(Context context, int textViewResourceId, List<Notice_detial> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Notice_detial notice_detial = (Notice_detial) getItem(position); // 获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

            TextView notice_title = (TextView) view.findViewById(R.id.notice_title);
            TextView notice_lable = (TextView) view.findViewById(R.id.notice_label);
            TextView notice_deadline_date = (TextView) view.findViewById(R.id.notice_deadline_date);
            TextView notice_release_date = (TextView) view.findViewById(R.id.notice_release_date);
            TextView notice_publisher = (TextView) view.findViewById(R.id.notice_publisher);//获取该布局内的文本视图

            notice_title.setText(notice_detial.getTitle());//为文本视图设置文本内容

            return view;
        }
    }

