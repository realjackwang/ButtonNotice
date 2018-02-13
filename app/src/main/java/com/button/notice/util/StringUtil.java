package com.button.notice.util;

import java.io.File;

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

    public static String[] ChangetoString(String x){
        String[] y = x.split(",");
        return  y;
    }

    public static boolean isExist(String path) {
        File file = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
           return false;
        }
        else {
            return true;
        }
    }
}
