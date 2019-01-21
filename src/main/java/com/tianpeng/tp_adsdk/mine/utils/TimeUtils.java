package com.tianpeng.tp_adsdk.mine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class TimeUtils {
    public TimeUtils() {
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime(String var0) {
        Date var1 = new Date();
        SimpleDateFormat var2 = new SimpleDateFormat(var0, Locale.getDefault());
        String var3 = var2.format(var1);
        return var3;
    }

    public static String getTargetTime(long var0) {
        Date var2 = new Date(var0);
        SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String var4 = var3.format(var2);
        return var4;
    }
}
