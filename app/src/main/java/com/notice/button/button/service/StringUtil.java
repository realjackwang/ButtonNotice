package com.notice.button.button.service;

/**
 * Created by Jack on 2017/12/7.
 */

public class StringUtil {

  public static boolean isEmpty(String x){
      boolean y=false;
        if(x.equals(""))
        {
            y=true;
        }
      return y;
    }

//   1,2,3,50,56,1000,656456,

    public static String[] ChangetoString(String x){
        String[] y = x.split(",");
        return  y;
    }
}
