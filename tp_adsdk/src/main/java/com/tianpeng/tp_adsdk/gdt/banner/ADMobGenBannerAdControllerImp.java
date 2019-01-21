package com.tianpeng.tp_adsdk.gdt.banner;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerView;
import com.tianpeng.tp_adsdk.gdt.listener.GDTBannerADListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenBannerAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenBannerAdControllerImp implements IADMobGenBannerAdController {
    private RelativeLayout layout;
    private BannerView bannerView;

    public ADMobGenBannerAdControllerImp() {
    }

    public RelativeLayout createBannerContainer(IADMobGenAd var1) {
        if (var1 != null && !var1.isDestroy() && this.layout == null) {
            this.layout = new RelativeLayout(var1.getActivity());
            this.layout.setBackgroundColor(-1);
            ViewGroup.LayoutParams var2 = new ViewGroup.LayoutParams(-1, -1);
            this.layout.setLayoutParams(var2);
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
            if (var1 != null && !var1.isDestroy() && var3 != null) {
                this.bannerView = new BannerView(var1.getActivity(), ADSize.BANNER, var3.getAppId(), var3.getBannerId());
                this.bannerView.setADListener(new GDTBannerADListener(var5,var3,var1));
                this.bannerView.setRefresh(0);
                var2.addView(this.bannerView);
                if (var1.getParam() instanceof ViewGroup) {
                    ((ViewGroup)var1.getParam()).addView(var2, 0);
                }

                this.bannerView.loadAD();
                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        if (this.bannerView != null) {
            this.bannerView.destroy();
            ViewParent var1 = this.bannerView.getParent();
            if (var1 != null && var1 instanceof ViewGroup) {
                ((ViewGroup)var1).removeView(this.bannerView);
            }

            this.bannerView = null;
        }

    }
}

