package com.tianpeng.tp_adsdk.gdt.listener;

import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
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
public class GDTSplashADListener implements SplashADListener {
    private final ADMobGenSplashAdListener listener;
    private  IADMobGenConfiguration configuration;
    private UploadDataBean bean;
    private IADMobGenAd ad;
    public GDTSplashADListener(ADMobGenSplashAdListener var1) {
        this.listener = var1;
    }
    public GDTSplashADListener(ADMobGenSplashAdListener var1, IADMobGenConfiguration configuration, IADMobGenAd ad) {
        this.listener = var1;
        this.ad =  ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getSplashId());
        bean.setAdType("open");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("GDT");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }
    public void onADDismissed() {
        if (this.checkNotNull()) {
            this.listener.onAdClose();
        }

    }
    public void onNoAD(AdError var1) {
        if (this.checkNotNull()) {
            this.listener.onADFailed(var1.getErrorMsg());
        }

    }

    public void onADPresent() {
        if (this.checkNotNull()) {
            this.listener.onADReceiv();
            this.listener.onADExposure();
        }
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);

    }

    public void onADClicked() {
        if (this.checkNotNull()) {
            this.listener.onADClick();
        }
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    public void onADTick(long var1) {
    }

    public void onADExposure() {
    }

    private boolean checkNotNull() {
        return this.listener != null;
    }

    private void sendRequest(){

    }

}

