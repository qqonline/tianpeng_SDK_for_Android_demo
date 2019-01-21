package com.tianpeng.tp_adsdk.toutiao;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.tianpeng.tp_adsdk.toutiao.service.AppDownloadStatusListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.ISdkInit;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;


/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class SdkInitImp implements ISdkInit {
    private static boolean isInit;
    private static String appId;
    private static String sdkName;

    public SdkInitImp() {
    }

    public static TTAdManager getInstance(Context var0) {
        TTAdManager var1 = TTAdManagerFactory.getInstance(var0);
        if (!isInit) {
            Class var2 = SdkInitImp.class;
            synchronized(SdkInitImp.class) {
                if (!isInit) {
                    setData(var1, var0);
                    isInit = true;
                }
            }
        }

        return var1;
    }

    private static void setData(TTAdManager var0, Context var1) {
        if (appId != null && appId.length() != 0) {
            var0.setAppId(appId).setName(sdkName == null ? "toutiao" : sdkName).setTitleBarTheme(1).setAllowShowNotifiFromSDK(true).setAllowLandingPageShowWhenScreenLock(true).openDebugMode().setGlobalAppDownloadListener(new AppDownloadStatusListener(var1)).setDirectDownloadNetworkType(new int[]{4});
        }
    }

    public static synchronized String getAppName(Context var0) {
        try {
            PackageManager var1 = var0.getPackageManager();
            PackageInfo var2 = var1.getPackageInfo(var0.getPackageName(), 0);
            int var3 = var2.applicationInfo.labelRes;
            return var0.getResources().getString(var3);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public void init(IADMobGenConfiguration var1) {
        if (var1 != null) {
            String var2 = var1.getAppId();
            if (var2 != null && var2.length() != 0) {
                String[] var3 = var2.split("\\|");
                if (var3.length != 0) {
                    appId = var3[0];
                    if (var3.length > 1) {
                        sdkName = var3[1];
                    }

                }
            }
        }
    }

    public String getPlatform() {
        return "toutiao";
    }
}