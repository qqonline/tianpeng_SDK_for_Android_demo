package com.tianpeng.tp_adsdk.baidu.splash;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.tianpeng.tp_adsdk.baidu.listener.SplashLoadListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenSplashAdController;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenSplashAdControllerImp implements IADMobGenSplashAdController {
    private RelativeLayout relativeLayout;
    private SplashAd baiduSplashAD;

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

            if (var1 != null && !var1.isDestroy() && var3 != null) {
                this.baiduSplashAD = new SplashAd(var1.getActivity(), this.relativeLayout, new SplashLoadListener(var4,var1,var3), var3.getSplashId());
                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        if (this.baiduSplashAD != null) {
            this.baiduSplashAD.destroy();
            this.baiduSplashAD = null;
        }

    }
}

