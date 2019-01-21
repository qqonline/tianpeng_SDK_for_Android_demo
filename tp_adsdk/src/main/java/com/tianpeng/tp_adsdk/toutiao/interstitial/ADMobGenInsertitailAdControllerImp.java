package com.tianpeng.tp_adsdk.toutiao.interstitial;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.tianpeng.tp_adsdk.toutiao.SdkInitImp;
import com.tianpeng.tp_adsdk.toutiao.listener.TTInsertAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInsertitailAdController;

/**
 * Created by YuHong on 2019/1/16 0016.
 */
public class ADMobGenInsertitailAdControllerImp implements IADMobGenInsertitailAdController {
    private TTAdNative ttAdNative;
    @Override
    public boolean loadAd(IADMobGenAd iadMobGenAd, IADMobGenConfiguration configuration, boolean var4, ADMobGenInsertitailAdListener listener) {
        if (iadMobGenAd != null && !iadMobGenAd.isDestroy() && configuration != null) {
            AdSlot adSlot = new AdSlot.Builder()
                    .setCodeId( configuration.getInsertId())
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(600, 600) //根据广告平台选择的尺寸，传入同比例尺寸
                    .build();
            this.getTtAdNative(iadMobGenAd).loadInteractionAd(adSlot,new TTInsertAdListener(iadMobGenAd,listener,configuration));
            return true;
        } else {
            return false;
        }
    }

    private TTAdNative getTtAdNative(IADMobGenAd var1) {
        if (this.ttAdNative == null) {
            this.ttAdNative = SdkInitImp.getInstance(var1.getActivity()).createAdNative(var1.getActivity());
            TTAdManagerFactory.getInstance(var1.getActivity()).requestPermissionIfNecessary(var1.getActivity());
        }

        return this.ttAdNative;
    }

    public void destroyAd() {
        if (this.ttAdNative != null) {
            this.ttAdNative = null;
        }

    }
}
