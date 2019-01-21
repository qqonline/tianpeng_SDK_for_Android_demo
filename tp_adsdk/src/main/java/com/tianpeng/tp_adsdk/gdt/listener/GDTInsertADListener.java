package com.tianpeng.tp_adsdk.gdt.listener;

import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.ads.interstitial.InterstitialADListener;
import com.qq.e.comm.util.AdError;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class GDTInsertADListener implements InterstitialADListener {
    private final ADMobGenInsertitailAdListener listener;
    private InterstitialAD ad;
    private  IADMobGenConfiguration configuration;
    private UploadDataBean bean;
    private IADMobGenAd ad2;
    public GDTInsertADListener(ADMobGenInsertitailAdListener var1,InterstitialAD ad, IADMobGenConfiguration configuration, IADMobGenAd ad2) {
        this.listener = var1;
        this.ad = ad;
        this.ad2 = ad2;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getInsertId());
        bean.setAdType("flow");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("GDT");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad2.getApplicationContext().getPackageName());
    }

    @Override
    public void onADReceive() {
        if (this.checkNotNull()) {
            this.listener.onADReceiv();
            ad.show();
        }
    }

    public void onNoAD(AdError var1) {
        if (this.checkNotNull()) {
            this.listener.onADFailed(var1.getErrorMsg());
        }

    }

    @Override
    public void onADOpened() {
        if (this.checkNotNull()) {
            this.listener.onADOpen();
        }
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(ad2.getApplicationContext(),bean);
    }

    public void onADClicked() {
        if (this.checkNotNull()) {
            this.listener.onADClick();
        }
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(ad2.getApplicationContext(),bean);
    }

    @Override
    public void onADLeftApplication() {
        if (this.checkNotNull()) {
            this.listener.onADLeftApplication();
        }
    }

    @Override
    public void onADClosed() {
        if (this.checkNotNull()) {
            this.listener.onAdClose();
        }
    }


    public void onADExposure() {
        if (this.checkNotNull()) {
            this.listener.onADExposure();
        }
    }

    public boolean checkNotNull() {
        return this.listener != null;
    }
}

