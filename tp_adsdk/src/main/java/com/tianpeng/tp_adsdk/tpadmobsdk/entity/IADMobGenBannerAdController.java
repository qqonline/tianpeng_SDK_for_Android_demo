package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenBannerAdController {
    RelativeLayout createBannerContainer(IADMobGenAd var1);

    boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, boolean var4, ADMobGenBannerAdListener var5);

    void destroyAd();
}
