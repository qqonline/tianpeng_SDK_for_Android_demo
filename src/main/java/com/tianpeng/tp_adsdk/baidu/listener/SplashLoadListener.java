package com.tianpeng.tp_adsdk.baidu.listener;

import com.baidu.mobads.SplashAdListener;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class SplashLoadListener implements SplashAdListener {
    private final ADMobGenSplashAdListener listener;
    private IADMobGenAd ad;
    private IADMobGenConfiguration configuration;
    private UploadDataBean bean;

    public SplashLoadListener(ADMobGenSplashAdListener var1, IADMobGenAd ad, IADMobGenConfiguration configuration) {
        this.listener = var1;
        this.ad = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("open");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("baidu");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }

    public void onAdPresent() {
        if (this.thisNotNull()) {
            this.listener.onADReceiv();
            this.listener.onADExposure();
        }
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    public void onAdDismissed() {
        if (this.thisNotNull()) {
            this.listener.onAdClose();
        }

    }

    public void onAdFailed(String var1) {
        if (this.thisNotNull()) {
            this.listener.onADFailed(var1);
        }

    }

    public void onAdClick() {
        if (this.thisNotNull()) {
            this.listener.onADClick();
        }
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    public boolean thisNotNull() {
        return this.listener != null;
    }
}

