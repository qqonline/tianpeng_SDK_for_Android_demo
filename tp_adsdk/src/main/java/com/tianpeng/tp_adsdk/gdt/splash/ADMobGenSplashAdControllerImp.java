package com.tianpeng.tp_adsdk.gdt.splash;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qq.e.ads.splash.SplashAD;
import com.tianpeng.tp_adsdk.gdt.listener.GDTSplashADListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenSplashAdController;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenSplashAdControllerImp implements IADMobGenSplashAdController {
    private RelativeLayout relativeLayout;
    private SplashAD splashAD;

    public ADMobGenSplashAdControllerImp() {
    }

    public RelativeLayout createSplashContainer(IADMobGenAd var1, boolean var2) {
        if (var1 != null && !var1.isDestroy() && this.relativeLayout == null) {
            this.relativeLayout = new RelativeLayout(var1.getActivity());
            this.relativeLayout.setBackgroundColor(-1);
            ViewGroup.LayoutParams var3 = new ViewGroup.LayoutParams(-1, -1);
            this.relativeLayout.setLayoutParams(var3);
            this.relativeLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View var1) {
                }
            });
        }

        return this.relativeLayout;
    }

    public boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, ADMobGenSplashAdListener var4, View var5) {
        if (var2 != null) {
            if (var2 != this.relativeLayout) {
                this.relativeLayout = var2;
            }

            if (var1 != null && !var1.isDestroy() && var2 != null && var3 != null) {
                this.splashAD = new SplashAD(var1.getActivity(), var2, var5, var3.getAppId(), var3.getSplashId(), new GDTSplashADListener(var4,var3,var1), 0);
                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        this.splashAD = null;
    }
}
