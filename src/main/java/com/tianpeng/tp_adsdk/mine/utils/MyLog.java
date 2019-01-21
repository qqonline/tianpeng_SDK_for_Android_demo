package com.tianpeng.tp_adsdk.mine.utils;

import android.util.Log;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class MyLog {
    public static boolean ENABLE = false;
    public static String FLAG = "8899";

    public MyLog() {
    }

    public static void i(String var0, String var1) {
        if (ENABLE) {
            Log.i(var0, var1);
        }

    }

    public static void w(String var0, String var1) {
        if (ENABLE) {
            Log.w(var0, var1);
        }

    }

    public static void e(String var0, String var1) {
        if (ENABLE) {
            Log.e(var0, var1);
        }

    }

    public static void d(String var0, String var1) {
        Log.d(var0, var1);
    }
}

