package com.tianpeng.tp_adsdk.mine.utils;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PackageUtils {
    public PackageUtils() {
    }

    public static boolean isAppInstalled(Context var0, String var1) {
        PackageManager var2 = var0.getPackageManager();
        boolean var3 = false;

        try {
            var2.getPackageInfo(var1, PackageManager.GET_ACTIVITIES);
            var3 = true;
        } catch (PackageManager.NameNotFoundException var5) {
            var3 = false;
        }

        return var3;
    }

    public static boolean isInstalled(Context var0, String var1) {
        PackageManager var2 = var0.getPackageManager();
        List var3 = var2.getInstalledPackages(0);
        Iterator var4 = var3.iterator();

        String var6;
        do {
            if (!var4.hasNext()) {
                return false;
            }

            PackageInfo var5 = (PackageInfo)var4.next();
            var6 = var5.packageName;
        } while(!var6.equals(var1));

        return true;
    }

    public static List<String> getInstallPackageList(Context var0) {
        ArrayList var1 = new ArrayList();
        PackageManager var2 = var0.getPackageManager();
        List var3 = var2.getInstalledPackages(0);
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            PackageInfo var5 = (PackageInfo)var4.next();
            var1.add(var5.packageName);
        }

        return var1;
    }

    public static List<PackageInfo> getInstallPackageInfoList(Context var0) {
        PackageManager var1 = var0.getPackageManager();
        List var2 = var1.getInstalledPackages(0);
        return var2;
    }

    public static String getApplicationName(Context var0, String var1) {
        PackageManager var2 = null;
        ApplicationInfo var3 = null;

        try {
            var2 = var0.getPackageManager();
            var3 = var2.getApplicationInfo(var1, 0);
        } catch (Exception var5) {
            var3 = null;
        }

        String var4 = "未获取到应用名称";
        var4 = (String)var2.getApplicationLabel(var3);
        return var4;
    }

    public static List<String> getAllTask(Context var0) {
        ArrayList var1 = new ArrayList();
        ActivityManager var2 = (ActivityManager)var0.getSystemService(Context.ACTIVITY_SERVICE);
        List var3 = var2.getRunningAppProcesses();

        try {
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
                ActivityManager.RunningAppProcessInfo var5 = (ActivityManager.RunningAppProcessInfo)var4.next();
                String var6 = var5.processName;
                var1.add(var6);
            }
        } catch (Exception var7) {
            ;
        }

        return var1;
    }

    public static String getTopApp(Context var0) {
        if (Build.VERSION.SDK_INT >= 21) {
            UsageStatsManager var1 = (UsageStatsManager)var0.getSystemService(Context.USAGE_STATS_SERVICE);
            if (var1 != null) {
                long var2 = System.currentTimeMillis();
                List var4 = var1.queryUsageStats(4, var2 - 60000L, var2);
                MyLog.i("foregroundApp", "Running app number in last 60 seconds : " + var4.size());
                String var5 = "";
                if (var4 != null && !var4.isEmpty()) {
                    int var6 = 0;

                    for(int var7 = 0; var7 < var4.size(); ++var7) {
                        if (((UsageStats)var4.get(var7)).getLastTimeUsed() > ((UsageStats)var4.get(var6)).getLastTimeUsed()) {
                            var6 = var7;
                        }
                    }

                    var5 = ((UsageStats)var4.get(var6)).getPackageName();
                }

                return var5;
            }
        }

        return "";
    }

    public static boolean hasPermission(Context var0) {
        AppOpsManager var1 = (AppOpsManager)var0.getSystemService(Context.APP_OPS_SERVICE);
        int var2 = 0;
        if (Build.VERSION.SDK_INT > 19) {
            var2 = var1.checkOpNoThrow("android:get_usage_stats", Process.myUid(), var0.getPackageName());
        }

        return var2 == 0;
    }
}

