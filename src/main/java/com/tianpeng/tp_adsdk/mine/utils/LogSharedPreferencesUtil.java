package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class LogSharedPreferencesUtil {
    private static String PREFS_NAME = "log.android.library";

    public LogSharedPreferencesUtil() {
    }

    public static void putStringPreference(Context var0, String var1, String var2) {
        if (var0 != null) {
            SharedPreferences.Editor var3 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
            var3.putString(var1, var2);
            var3.commit();
        }
    }

    public static String getStringPreference(Context var0, String var1) {
        if (var0 == null) {
            return "";
        } else {
            SharedPreferences var2 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            return var2.getString(var1, "");
        }
    }

    public static void putFloatPreference(Context var0, String var1, float var2) {
        if (var0 != null) {
            SharedPreferences.Editor var3 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
            var3.putFloat(var1, var2);
            var3.commit();
        }
    }

    public static float getFloatPreferenceData(Context var0, String var1) {
        if (var0 == null) {
            return 0.0F;
        } else {
            SharedPreferences var2 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            return var2.getFloat(var1, 0.0F);
        }
    }

    public static void putBooleanPreference(Context var0, String var1, boolean var2) {
        if (var0 != null) {
            SharedPreferences.Editor var3 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
            var3.putBoolean(var1, var2);
            var3.commit();
        }
    }

    public static boolean getBooleanPreferenceData(Context var0, String var1) {
        if (var0 == null) {
            return false;
        } else {
            SharedPreferences var2 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            return var2.getBoolean(var1, false);
        }
    }

    public static void putLongPreference(Context var0, String var1, long var2) {
        if (var0 != null) {
            SharedPreferences.Editor var4 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
            var4.putLong(var1, var2);
            var4.commit();
        }
    }

    public static long getLongPreference(Context var0, String var1) {
        if (var0 == null) {
            return 0L;
        } else {
            SharedPreferences var2 = var0.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            return var2.getLong(var1, 0L);
        }
    }
}

