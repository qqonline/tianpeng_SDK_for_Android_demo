package com.tianpeng.tp_adsdk.toutiao.splash;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.tianpeng.tp_adsdk.toutiao.SdkInitImp;
import com.tianpeng.tp_adsdk.toutiao.listener.TTSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenSplashAdController;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenSplashAdControllerImp implements IADMobGenSplashAdController {
    private RelativeLayout layout;
    TTAdNative ttAdNative;

    public ADMobGenSplashAdControllerImp() {
    }

    public RelativeLayout createSplashContainer(IADMobGenAd var1, boolean var2) {
        if (var1 != null && !var1.isDestroy() && this.layout == null) {
            this.layout = new RelativeLayout(var1.getActivity());
            this.layout.setBackgroundColor(-1);
            ViewGroup.LayoutParams var3 = new ViewGroup.LayoutParams(-1, -1);
            this.layout.setLayoutParams(var3);
            this.layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View var1) {
                }
            });
        }

        return this.layout;
    }

    public boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, ADMobGenSplashAdListener var4, View var5) {
        if (var2 != null) {
            if (var2 != this.layout) {
                this.layout = var2;
            }

            this.destroyAd();
            if (var1 != null && !var1.isDestroy() && var2 != null && var3 != null) {
                this.ttAdNative = SdkInitImp.getInstance(var1.getApplicationContext()).createAdNative(var1.getApplicationContext());
                AdSlot var6 = (new AdSlot.Builder()).setCodeId(var3.getSplashId()).setSupportDeepLink(true).setImageAcceptedSize(1080, 1920).build();
                this.ttAdNative.loadSplashAd(var6, new TTSplashAdListener(var1, var2, var4,var3), 3000);
                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        if (this.ttAdNative != null) {
            this.ttAdNative = null;
        }

    }
}

