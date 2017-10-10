package com.example.tan.finalproject.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tan on 5/7/2017.
 */

public class TimeUtils {
    public static String longDateToString_TypeA(long mili) {
        SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy hh:mm a");
        Date date = new Date(mili);
        return sdf.format(date);
    }
    public static String longDateToString_TypeB(long mili) {
        SimpleDateFormat sdf = new SimpleDateFormat("M dd yyyy hh:mm a");
        Date date = new Date(mili);
        return sdf.format(date);
    }
    public static String longDateToString_TypeC(long mili) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = new Date(mili);
        return sdf.format(date);
    }
    public static long getCurrentTimeInLong() {
        return new Date().getTime();
    }

    public static String longDateToString_TypeD(long mili) {
        SimpleDateFormat sdf = new SimpleDateFormat("E dd:MM");
        Date date = new Date(mili);
        return sdf.format(date);
    }
}
