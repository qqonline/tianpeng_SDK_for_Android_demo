package com.tianpeng.tp_adsdk.tpadmobsdk.entity;


import com.tianpeng.tp_adsdk.BuildConfig;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class ADMobGenCommon {
    public ADMobGenCommon() {
    }

    public static String getSdkVersion() {
        return BuildConfig.VERSION_NAME;
    }
}

