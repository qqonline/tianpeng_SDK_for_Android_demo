package com.tianpeng.tp_adsdk.youdao.banner;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.banner.ADMobGenBannerView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenBannerAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.youdao.base.ADMobGenBannerCustom;
import com.tianpeng.tp_adsdk.youdao.listener.YouDaoNativeNetworkBannerListener;
import com.youdao.sdk.nativeads.RequestParameters;
import com.youdao.sdk.nativeads.YouDaoNative;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenBannerAdControllerImp implements IADMobGenBannerAdController {
    private RelativeLayout layout;
    private ADMobGenBannerCustom custom;

    public ADMobGenBannerAdControllerImp() {
    }

    public RelativeLayout createBannerContainer(IADMobGenAd var1) {
        if (var1 != null && !var1.isDestroy() &&layout == null) {
           layout = new RelativeLayout(var1.getActivity());
           layout.setBackgroundColor(-1);
            ViewGroup.LayoutParams var2 = new ViewGroup.LayoutParams(-1, -1);
           layout.setLayoutParams(var2);
           layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View var1) {
                }
            });
        }

        return layout;
    }

    public boolean loadAd(IADMobGenAd var1, RelativeLayout var2, IADMobGenConfiguration var3, boolean var4, ADMobGenBannerAdListener var5) {
        if (var2 != null) {
            if (var2 !=layout) {
               layout = var2;
            }

            ViewParent var6 =layout.getParent();
            if (var6 != null && var6 instanceof ViewGroup) {
                ((ViewGroup)var6).removeView(this.layout);
            }

            this.destroyAd();
            if (var1 != null && !var1.isDestroy() && var3 != null) {
                custom = new ADMobGenBannerCustom(var1.getActivity(), var4);
                custom.setAdMobGenAd((ADMobGenBannerView)var1);
                custom.setAdMobGenAdListener(var5);
                YouDaoNative var7 = new YouDaoNative(var1.getActivity(), var3.getBannerId(), new YouDaoNativeNetworkBannerListener(var1, custom, var5,var1,var3));
                var2.addView(custom);
                if (var1.getParam() instanceof ViewGroup) {
                    ((ViewGroup)var1.getParam()).addView(var2, 0);
                }

                RequestParameters var8 = (new RequestParameters.Builder()).build();
                var7.makeRequest(var8);
                custom.setYoudaoNative(var7);
                return true;
            }
        }

        return false;
    }

    public void destroyAd() {
        if (custom != null) {
            custom.destroy();
            ViewParent var1 = custom.getParent();
            if (var1 != null && var1 instanceof ViewGroup) {
                ((ViewGroup)var1).removeView(custom);
            }

            custom = null;
        }

    }
}
