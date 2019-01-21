package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenInformationAdController {
    boolean loadAd(IADMobGenAd var1, IADMobGenConfiguration var2, IADMobGenInformationAdCallBack var3);

    void destroyAd();
}