package com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.banner.ADMobGenBannerView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_BANNER;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ADMobGenBannerAdListenerImp extends ADMobGenAdListenerImp<ADMobGenBannerView> implements ADMobGenBannerAdListener {
    public ADMobGenBannerAdListenerImp(ADMobGenBannerView var1, IADMobGenConfiguration var2, boolean var3) {
        super(var1, var2, var3, STR_TYPE_BANNER);
    }

    public void onADExposure() {
        LogTool.show(this.sdkName + "_onADExposure");
        this.nativeClicked = false;
        if (this.listenerNotNull()) {
            ((ADMobGenBannerView)this.weakReference.get()).getListener().onADExposure();
        }

    }
}
