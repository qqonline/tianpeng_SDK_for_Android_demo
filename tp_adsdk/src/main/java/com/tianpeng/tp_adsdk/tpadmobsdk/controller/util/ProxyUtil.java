package com.tianpeng.tp_adsdk.tpadmobsdk.controller.util;

import android.content.pm.ApplicationInfo;
import android.net.Proxy;

import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ProxyUtil {
    public static boolean sdkIsRunning() {
        try {
            ApplicationInfo var0 = TPADMobSDK.instance().getAdMobSdkContext().getApplicationInfo();
            return (var0.flags & 2) != 0;
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean hasHost() {
        String var0 = Proxy.getDefaultHost();
        int var1 = Proxy.getDefaultPort();
        return var0 != null && var1 != -1;
    }

    public static boolean isSleep() {
        if (618 == SDKUtil.getInstance().getFlag()) {
            return true;
        } else {
            return !sdkIsRunning() && !hasHost();
        }
    }
}
