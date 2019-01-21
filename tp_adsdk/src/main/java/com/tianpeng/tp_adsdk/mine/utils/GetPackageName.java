package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class GetPackageName {
    private Context context;

    public GetPackageName(Context var1) {
        this.context = var1;
    }

    public String getVersionName() {
        if (this.context == null) {
            return "";
        } else {
            PackageManager var1 = this.context.getPackageManager();

            String var2;
            try {
                PackageInfo var3 = var1.getPackageInfo(this.context.getPackageName(), 0);
                var2 = var3.versionName;
            } catch (Exception var4) {
                var2 = "";
            }

            return var2;
        }
    }

    public PackageInfo getPackageInfo() {
        if (this.context == null) {
            return null;
        } else {
            PackageManager var1 = this.context.getPackageManager();

            PackageInfo var2;
            try {
                var2 = var1.getPackageInfo(this.context.getPackageName(), 0);
            } catch (Exception var4) {
                var2 = null;
            }

            return var2;
        }
    }

    public static String getApplicationName(Context var0) {
        try {
            PackageManager var1 = null;
            ApplicationInfo var2 = null;
            var1 = var0.getPackageManager();
            var2 = var1.getApplicationInfo(var0.getPackageName(), 0);
            String var3 = (String)var1.getApplicationLabel(var2);
            return var3;
        } catch (Exception var4) {
            return "";
        }
    }

    public static int getVersionCode(Context var0) {
        int var1 = 0;

        try {
            var1 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException var3) {
            ;
        }

        return var1;
    }

    public static String getVersionName(Context var0) {
        String var1 = "未获取到";

        try {
            var1 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException var3) {
            ;
        }

        return var1;
    }
}

