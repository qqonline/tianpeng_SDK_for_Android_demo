package com.tianpeng.tp_adsdk.tpadmobsdk.change;

import android.util.Log;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class LogTool {
    private static boolean isNeedShow = false;

    public static void show(String var0) {
        show("ADMobGen_Log", var0);
    }

    public static void show(String var0, String var1) {
        if (isNeedShow) {
            Log.i(var0, var1);
        }

    }

    public static void showI(String var0) {
        Log.i("ADMobGen_Log", var0);
    }
}
