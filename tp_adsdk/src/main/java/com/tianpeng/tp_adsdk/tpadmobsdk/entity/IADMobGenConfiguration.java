package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenConfiguration {
    String getSdkName();

    String getAppId();

    String getSplashId();

    String getBannerId();

    String getNativeId();

    String getInsertId();

    int getTurn();

    void setFlowAdType(int var1);
}