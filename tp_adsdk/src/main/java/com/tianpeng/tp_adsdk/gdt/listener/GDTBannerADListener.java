package com.tianpeng.tp_adsdk.gdt.listener;

import com.qq.e.ads.banner.BannerADListener;
import com.qq.e.comm.util.AdError;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class GDTBannerADListener implements BannerADListener {
    private final ADMobGenBannerAdListener listener;
    private  IADMobGenConfiguration configuration;
    private UploadDataBean bean;
    private IADMobGenAd ad;
    public GDTBannerADListener(ADMobGenBannerAdListener var1) {
        this.listener = var1;
    }
    public GDTBannerADListener(ADMobGenBannerAdListener var1, IADMobGenConfiguration configuration, IADMobGenAd ad) {
        this.listener = var1;
        this.ad = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("banner");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("GDT");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }

    public void onNoAD(AdError var1) {
        if (this.listener != null) {
            this.listener.onADFailed(var1.getErrorMsg());
        }

    }

    public void onADReceiv() {
        if (this.listener != null) {
            this.listener.onADReceiv();
        }

    }

    public void onADExposure() {
        if (this.listener != null) {
            this.listener.onADExposure();
        }
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);

    }

    public void onADClosed() {
        if (this.listener != null) {
            this.listener.onAdClose();
        }

    }

    public void onADClicked() {
        if (this.listener != null) {
            this.listener.onADClick();
        }
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);

    }

    public void onADLeftApplication() {
    }

    public void onADOpenOverlay() {
//        bean.setSdkAction("show");
//        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    public void onADCloseOverlay() {
    }
}

