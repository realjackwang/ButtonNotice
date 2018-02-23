package com.button.notice.Discover.Lostandfound;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.button.notice.R;

/**
 * Created by Jack on 2018/2/23.
 */

public class TopPopWindow extends PopupWindow {
    private View mView;
    private LinearLayout ll_popmenu_lost, ll_popmenu_found;

    public TopPopWindow(Activity paramActivity, View.OnClickListener paramOnClickListener,
                        int paramInt1, int paramInt2) {
        mView = LayoutInflater.from(paramActivity).inflate(R.layout.popwindow_topright, null);
        ll_popmenu_lost = (LinearLayout) mView.findViewById(R.id.ll_lost);
        ll_popmenu_found = (LinearLayout) mView.findViewById(R.id.ll_found);

        if (paramOnClickListener != null) {
            //设置点击监听

            ll_popmenu_lost.setOnClickListener(paramOnClickListener);
            ll_popmenu_found.setOnClickListener(paramOnClickListener);

            setContentView(mView);
            //设置宽度
            setWidth(paramInt1);
            //设置高度
            setHeight(paramInt2);
            //设置显示隐藏动画
//            setAnimationStyle(R.style.AnimTools);
            //设置背景透明
            setBackgroundDrawable(new ColorDrawable(0));
        }
    }
}
