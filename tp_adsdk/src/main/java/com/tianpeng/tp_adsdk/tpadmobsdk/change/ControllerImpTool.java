package com.tianpeng.tp_adsdk.tpadmobsdk.change;

import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class ControllerImpTool {
    public static <T> T getClassInstance(String var0) {
        try {
            Class var1 = Class.forName(var0);
            return (T) var1.newInstance();
        } catch (Exception var2) {
            return null;
        }
    }

//    public static String getADMobSDKImp() {
//        return TPADMobSDK.instance().getSdkName() + ".admob.show.AdmobShowControllerImp";
//    }

    public static String getSdkInitImp(String var0) {
        if (var0 == null) {
            var0 = "";
        }

        return TPADMobSDK.instance().getSdkName() + "." + var0.toLowerCase() + ".SdkInitImp";
    }

    public static String getSplashAdControllerImp(String var0) {
        if (var0 == null) {
            var0 = "";
        }

        return TPADMobSDK.instance().getSdkName() + "." + var0.toLowerCase() + ".splash.ADMobGenSplashAdControllerImp";
    }

    public static String getBannerAdControllerImp(String var0) {
        if (var0 == null) {
            var0 = "";
        }

        return TPADMobSDK.instance().getSdkName() + "." + var0.toLowerCase() + ".banner.ADMobGenBannerAdControllerImp";
    }
    public static String getInsertAdControllerImp(String var0) {
        if (var0 == null) {
            var0 = "";
        }

        return TPADMobSDK.instance().getSdkName() + "." + var0.toLowerCase() + ".interstitial.ADMobGenInsertitailAdControllerImp";
    }

    public static String getInformationAdControllerImp(String var0) {
        if (var0 == null) {
            var0 = "";
        }

        return TPADMobSDK.instance().getSdkName() + "." + var0.toLowerCase() + ".information.ADMobGenInformationAdControllerImp";
    }
}
