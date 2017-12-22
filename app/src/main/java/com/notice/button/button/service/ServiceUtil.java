package com.notice.button.button.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Jack on 2017/12/9.
 */

public interface ServiceUtil{

    void sendHttpPostRequest(String url, CommonRequest request, ResponseHandler responseHandler, boolean showLoadingDialog);

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == Constant.HANDLER_HTTP_SEND_FAIL) {
                //   LogUtil.logErr(msg.obj.toString());

                //LoadingDialogUtil.cancelLoading();
                //   DialogUtil.showHintDialog(BaseActivity.this, "请求发送失败，请重试", true);
            } else if (msg.what == Constant.HANDLER_HTTP_RECEIVE_FAIL) {
                //  LogUtil.logErr(msg.obj.toString());

                // LoadingDialogUtil.cancelLoading();
                // DialogUtil.showHintDialog(BaseActivity.this, "请求接受失败，请重试", true);
            }
        }
    };





}


