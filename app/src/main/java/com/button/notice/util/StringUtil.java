package com.button.notice.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

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

    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException(
                        "Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }
}

