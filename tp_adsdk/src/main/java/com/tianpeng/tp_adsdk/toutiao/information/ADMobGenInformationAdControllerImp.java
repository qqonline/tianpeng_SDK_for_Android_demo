package com.tianpeng.tp_adsdk.toutiao.information;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.tianpeng.tp_adsdk.toutiao.SdkInitImp;
import com.tianpeng.tp_adsdk.toutiao.listener.TTFeedAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdController;


/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationAdControllerImp implements IADMobGenInformationAdController {
    TTAdManager ttAdManager;
    private TTAdNative ttAdNative;

    public ADMobGenInformationAdControllerImp() {
    }

    public boolean loadAd(IADMobGenAd var1, IADMobGenConfiguration var2, IADMobGenInformationAdCallBack var3) {
        if (var1 != null && !var1.isDestroy() && var2 != null) {
            AdSlot var4 = (new AdSlot.Builder()).setCodeId(var2.getNativeId()).setSupportDeepLink(true).setImageAcceptedSize(699, 388).setAdCount(1).build();
            this.getTtAdNative(var1).loadFeedAd(var4, new TTFeedAdListener(var1, var3,var2));
            return true;
        } else {
            return false;
        }
    }

    private TTAdNative getTtAdNative(IADMobGenAd var1) {
        if (this.ttAdNative == null) {
            this.ttAdManager = SdkInitImp.getInstance(var1.getApplicationContext());
            this.ttAdNative = this.ttAdManager.createAdNative(var1.getApplicationContext());
            this.ttAdManager.requestPermissionIfNecessary(var1.getApplicationContext());
        }

        return this.ttAdNative;
    }

    public void destroyAd() {
        if (this.ttAdNative != null) {
            this.ttAdNative = null;
        }

    }
}
