package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenInsertitailAdController {
//    RelativeLayout createBannerContainer(IADMobGenAd var1);

    boolean loadAd(IADMobGenAd var1,  IADMobGenConfiguration var3, boolean var4, ADMobGenInsertitailAdListener var5);

    void destroyAd();
//    void showAD();
}
