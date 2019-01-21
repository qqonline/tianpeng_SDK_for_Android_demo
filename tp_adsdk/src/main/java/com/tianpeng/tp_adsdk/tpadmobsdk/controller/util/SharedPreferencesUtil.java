package com.tianpeng.tp_adsdk.tpadmobsdk.controller.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tianpeng.tp_adsdk.tpadmobsdk.change.Md5Util;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.PackInfoTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil instance;

    private SharedPreferencesUtil() {
    }

    public static SharedPreferencesUtil getInstance() {
        if (instance == null) {
            Class var0 = SharedPreferencesUtil.class;
            synchronized(SharedPreferencesUtil.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil();
                }
            }
        }

        return instance;
    }

    public void commitValue(String var1, String var2) {
        try {
            this.getSharedPreferences().edit().putString(var1, var2).commit();
        } catch (Exception var4) {
            ;
        }

    }

    public String getValue(String var1) {
        try {
            return this.getSharedPreferences().getString(var1, (String)null);
        } catch (Exception var3) {
            return null;
        }
    }

    private SharedPreferences getSharedPreferences() {
        return TPADMobSDK.instance().getAdMobSdkContext().getSharedPreferences(this.getSharedPreferencesName(), 0);
    }

    private String getSharedPreferencesName() {
        String var1 = PackInfoTool.getPackName(TPADMobSDK.instance().getAdMobSdkContext());
        String var2 = PackInfoTool.getVersionName(TPADMobSDK.instance().getAdMobSdkContext());
        String var3 = Md5Util.md5(var1 + "_" + var2);
        return TextUtils.isEmpty(var3) ? var1 + "_" + var2 : var3;
    }
}
