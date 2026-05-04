package com.assessment.ewallet.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtil {


    public static Long getTimeNowLongFormat(){
        return System.currentTimeMillis() / 1000;
    }



    public static String getFmtDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String getNowDateForTransaction(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }

    public static LocalDateTime getLocalDateTime(){
        ZoneId z = ZoneId.of("Asia/Ho_Chi_Minh");
        return LocalDateTime.now(z);
    }




}
