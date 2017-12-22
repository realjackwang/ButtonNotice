package com.notice.button.button.service;

import android.os.Bundle;

/**
 * Created by Jack on 2017/12/10.
 */

public  class ServiceTools implements ServiceUtil {


  public  String  findId(String a){

      final String[] x = new String[1];

     CommonRequest request = new CommonRequest();

    sendHttpPostRequest(Constant.URL_User, request, new ResponseHandler() {

        @Override
        public String success(CommonResponse response) {

            x[0] = response.getResCode();
            return response.getResCode();
        }

        @Override
        public  String fail(String failCode, String failMsg) {

           x[0] = failCode;

            return failCode;
        }

    },true);


      return x[0];
  }

    @Override
    public  void sendHttpPostRequest(String url, CommonRequest request, ResponseHandler responseHandler, boolean showLoadingDialog) {
        new HttpPostTask(request, mHandler, responseHandler).execute(url);

    }






}
