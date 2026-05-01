package com.assessment.ewallet.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {


    public static Long getTimeNowLongFormat(){
        return System.currentTimeMillis() / 1000;
    }

    public static String getNowDateForTransaction(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }


}
