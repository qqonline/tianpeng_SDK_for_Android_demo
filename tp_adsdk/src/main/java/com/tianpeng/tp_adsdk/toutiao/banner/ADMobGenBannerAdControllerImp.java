package com.tianpeng.tp_adsdk.toutiao.banner;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.tianpeng.tp_adsdk.toutiao.SdkInitImp;
import com.tianpeng.tp_adsdk.toutiao.listener.TTBannerAdListener;
import com.tianpeng.tp_adsdk.toutiao.view.TTCommView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.banner.ADMobGenBannerView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenBannerAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;


/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenBannerAdControllerImp implements IADMobGenBannerAdController {
    private RelativeLayout layout;
    private TTAdNative ttAdNative;

    public ADMobGenBannerAdControllerImp() {
    }

    public RelativeLayout createBannerContainer(IADMobGenAd var1) {
        if (var1 != null && !var1.isDestroy() && this.layout == null) {
            ADMobGenBannerView var2 = null;

            try {
                var2 = (ADMobGenBannerView)var1;
            } catch (Exception var4) {
                ;
            }

            if (var2 != null && var2.getTTwidth() > 0 && var2.getTTheight() > 0) {
                this.layout = new TTCommView(var1.getActivity(), var2.getTTwidth(), var2.getTTheight());
            } else {
                this.layout = new TTCommView(var1.getActivity());
            }

            this.layout.setBackgroundColor(16777215);
            this.layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View var1) {
                }
            });
        }

        return this.layout;
    }

    public boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, boolean var4, ADMobGenBannerAdListener var5) {
        if (var2 != null) {
            if (var2 != this.layout) {
                this.layout = var2;
            }

            ViewParent var6 = this.layout.getParent();
            if (var6 != null && var6 instanceof ViewGroup) {
                ((ViewGroup)var6).removeView(this.layout);
            }

            this.destroyAd();
            ADMobGenBannerView var7 = null;
            if (var1 != null && !var1.isDestroy() && var3 != null) {
                try {
                    var7 = (ADMobGenBannerView)var1;
                } catch (Exception var9) {
                    ;
                }

                AdSlot var8 = null;
                if (var7 != null && var7.getTTwidth() > 0 && var7.getTTheight() > 0) {
                    var8 = (new AdSlot.Builder()).setCodeId(var3.getBannerId()).setSupportDeepLink(true).setImageAcceptedSize(var7.getTTwidth(), var7.getTTheight()).build();
                } else {
                    var8 = (new AdSlot.Builder()).setCodeId(var3.getBannerId()).setSupportDeepLink(true).setImageAcceptedSize(640, 100).build();
                }

                this.getTtAdNative(var1).loadBannerAd(var8, new TTBannerAdListener(var1, var2, var5,var3));
                if (var1.getParam() instanceof ViewGroup) {
                    ((ViewGroup)var1.getParam()).addView(var2, 0);
                }

                return true;
            }
        }

        return false;
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

