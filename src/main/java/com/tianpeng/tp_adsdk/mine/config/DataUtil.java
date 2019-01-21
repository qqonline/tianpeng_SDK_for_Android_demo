package com.tianpeng.tp_adsdk.mine.config;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;

import com.tianpeng.tp_adsdk.mine.utils.LogSharedPreferencesUtil;
import com.tianpeng.tp_adsdk.mine.utils.WebViewUtil;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class DataUtil {
    private static long currMachineId = 0L;
    private static String currLat;
    private static String currLng;
    private static String userAgent;

    public DataUtil() {
    }

    public static void saveLngAndLat(Context var0, String var1, String var2) {
        saveLng(var0, var1);
        saveLat(var0, var2);
    }

    public static void saveLat(Context var0, String var1) {
        currLat = var1;
        LogSharedPreferencesUtil.putStringPreference(var0, "lat", var1);
    }

    public static String getLat(Context var0) {
        if (TextUtils.isEmpty(currLat)) {
            currLat = LogSharedPreferencesUtil.getStringPreference(var0, "lat");
        }

        return currLat;
    }

    public static void saveLng(Context var0, String var1) {
        currLng = var1;
        LogSharedPreferencesUtil.putStringPreference(var0, "lng", var1);
    }

    public static String getLng(Context var0) {
        if (TextUtils.isEmpty(currLng)) {
            currLng = LogSharedPreferencesUtil.getStringPreference(var0, "lng");
        }

        return currLng;
    }

    public static void saveMachineId(Context var0, long var1) {
        currMachineId = var1;
        LogSharedPreferencesUtil.putLongPreference(var0, "machineId", var1);
    }

    public static long getMachineId(Context var0) {
        if (currMachineId == 0L) {
            currMachineId = LogSharedPreferencesUtil.getLongPreference(var0, "machineId");
        }

        return currMachineId;
    }

    public static String getUserAgent(Context var0) {
        if (var0 == null) {
            return userAgent;
        } else {
            if (TextUtils.isEmpty(userAgent)) {
                WebView var1 = new WebView(var0.getApplicationContext());
                userAgent = var1.getSettings().getUserAgentString();
                WebViewUtil.destroy(var1);
            }

            return userAgent;
        }
    }
}

