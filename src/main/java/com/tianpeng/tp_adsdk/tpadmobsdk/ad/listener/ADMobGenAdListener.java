package com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface ADMobGenAdListener {
    void onADFailed(String var1);

    void onADReceiv();

    void onADClick();

    void onAdClose();
}