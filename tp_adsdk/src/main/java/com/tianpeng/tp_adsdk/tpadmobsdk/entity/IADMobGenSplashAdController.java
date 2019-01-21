package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import android.view.View;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;

/**
 * Created by YuHong on 2019/1/4 0004.
 */

public interface IADMobGenSplashAdController {
    RelativeLayout createSplashContainer(IADMobGenAd var1, boolean var2);

    boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, ADMobGenSplashAdListener var4, View var5);

    void destroyAd();
}