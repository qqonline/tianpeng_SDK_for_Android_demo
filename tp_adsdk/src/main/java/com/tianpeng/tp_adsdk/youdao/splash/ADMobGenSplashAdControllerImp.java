package com.tianpeng.tp_adsdk.youdao.splash;

import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenSplashAdController;
import com.tianpeng.tp_adsdk.youdao.base.ADMobGenSplashCustom;
import com.tianpeng.tp_adsdk.youdao.listener.YouDaoNativeNetworkListenerImp;
import com.youdao.sdk.nativeads.RequestParameters;
import com.youdao.sdk.nativeads.YouDaoNative;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenSplashAdControllerImp implements IADMobGenSplashAdController {
    private RelativeLayout layout;
    private YouDaoNative youDaoNative;

    public ADMobGenSplashAdControllerImp() {
    }

    public RelativeLayout createSplashContainer(IADMobGenAd var1, boolean var2) {
        if (var1 != null && !var1.isDestroy() && this.layout == null) {
            this.layout = new ADMobGenSplashCustom(var1.getActivity(), var2);
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

            if (var1 != null && !var1.isDestroy() && var3 != null) {
                this.youDaoNative = new YouDaoNative(var1.getActivity(), var3.getSplashId(), new YouDaoNativeNetworkListenerImp(var1, this.layout, var4,var1,var3));
                RequestParameters var6 = (new RequestParameters.Builder()).location((Location)null).build();
                this.youDaoNative.makeRequest(var6);
                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        if (this.youDaoNative != null) {
            this.youDaoNative.destroy();
            this.youDaoNative = null;
        }

        if (this.layout != null && this.layout instanceof ADMobGenSplashCustom) {
            ((ADMobGenSplashCustom)this.layout).destroy();
        }

    }
}