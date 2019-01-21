package com.tianpeng.tp_adsdk.tpadmobsdk.change;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PackInfoTool {
    public static String getPackName(Context var0) {
        return var0 == null ? null : var0.getPackageName();
    }

    public static String getVersionName(Context var0) {
        String var1 = "";

        try {
            PackageInfo var2 = var0.getPackageManager().getPackageInfo(getPackName(var0), 0);
            var1 = var2.versionName;
        } catch (Exception var3) {
            ;
        }

        return var1;
    }
}
