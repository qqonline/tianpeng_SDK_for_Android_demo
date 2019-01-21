package com.tianpeng.tp_adsdk.baidu.listener;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

import org.json.JSONObject;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class BannerLoadListener implements AdViewListener {
    private final ADMobGenBannerAdListener listener;
    private IADMobGenConfiguration configuration;
    private IADMobGenAd ad;
    private UploadDataBean bean;
    public BannerLoadListener(ADMobGenBannerAdListener var1, IADMobGenConfiguration configuration, IADMobGenAd ad) {
        this.listener = var1;
        this.ad = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("banner");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("baidu");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }

    public void onAdReady(AdView var1) {
        if (this.listenerNotNull()) {
            this.listener.onADReceiv();
        }

    }

    public void onAdShow(JSONObject var1) {
        if (this.listenerNotNull()) {
            this.listener.onADExposure();
        }
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    public void onAdClick(JSONObject var1) {
        if (this.listenerNotNull()) {
            this.listener.onADClick();
        }
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    public void onAdFailed(String var1) {
        if (this.listenerNotNull()) {
            this.listener.onADFailed(var1);
        }

    }

    public void onAdSwitch() {
    }

    public void onAdClose(JSONObject var1) {
        if (this.listenerNotNull()) {
            this.listener.onAdClose();
        }

    }

    public boolean listenerNotNull() {
        return this.listener != null;
    }
}

