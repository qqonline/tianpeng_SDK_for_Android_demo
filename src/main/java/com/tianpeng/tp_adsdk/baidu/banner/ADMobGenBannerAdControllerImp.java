package com.tianpeng.tp_adsdk.baidu.banner;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;
import com.tianpeng.tp_adsdk.baidu.listener.BannerLoadListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenBannerAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenBannerAdControllerImp implements IADMobGenBannerAdController {
    private RelativeLayout relativeLayout;
    private AdView baiduAD;

    public ADMobGenBannerAdControllerImp() {
    }

    public RelativeLayout createBannerContainer(IADMobGenAd var1) {
        if (var1 != null && !var1.isDestroy() && this.relativeLayout == null) {
            this.relativeLayout = new RelativeLayout(var1.getActivity());
            this.relativeLayout.setBackgroundColor(-1);
            ViewGroup.LayoutParams var2 = new ViewGroup.LayoutParams(-1, -1);
            this.relativeLayout.setLayoutParams(var2);
            this.relativeLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View var1) {
                }
            });
        }

        return this.relativeLayout;
    }

    public boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, boolean var4, ADMobGenBannerAdListener var5) {
        if (var2 != null) {
            if (var2 != this.relativeLayout) {
                this.relativeLayout = var2;
            }

            ViewParent var6 = this.relativeLayout.getParent();
            if (var6 != null && var6 instanceof ViewGroup) {
                ((ViewGroup)var6).removeView(this.relativeLayout);
            }

            this.destroyAd();
            if (var1 != null && !var1.isDestroy() && var3 != null) {
                this.baiduAD = new AdView(var1.getActivity(), var3.getBannerId());
                this.baiduAD.setListener(new BannerLoadListener(var5,var3,var1));
                var2.addView(this.baiduAD);
                if (var1.getParam() instanceof ViewGroup) {
                    ((ViewGroup)var1.getParam()).addView(var2, 0);
                }

                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        try {
            if (this.baiduAD != null) {
                this.baiduAD.destroy();
                this.baiduAD = null;
            }
        } catch (Exception var2) {
            ;
        }

    }
}
