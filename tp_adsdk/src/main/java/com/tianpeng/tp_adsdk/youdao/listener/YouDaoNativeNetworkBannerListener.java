package com.tianpeng.tp_adsdk.youdao.listener;

import android.view.View;

import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.youdao.base.ADMobGenBannerCustom;
import com.youdao.sdk.nativeads.NativeErrorCode;
import com.youdao.sdk.nativeads.NativeResponse;
import com.youdao.sdk.nativeads.YouDaoNative;

import java.lang.ref.WeakReference;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class YouDaoNativeNetworkBannerListener implements YouDaoNative.YouDaoNativeNetworkListener,YouDaoNative.YouDaoNativeEventListener {
    private final WeakReference<IADMobGenAd> iadMobGenAdWeakReference;
    private final WeakReference<ADMobGenBannerCustom> adMobGenBannerCustomWeakReference;
    private final ADMobGenBannerAdListener listener;
    private IADMobGenAd ad;
    private IADMobGenConfiguration configuration;
    private UploadDataBean bean;

    public YouDaoNativeNetworkBannerListener(IADMobGenAd var1, ADMobGenBannerCustom var2, ADMobGenBannerAdListener var3, IADMobGenAd ad, IADMobGenConfiguration configuration) {
        iadMobGenAdWeakReference = new WeakReference(var1);
        adMobGenBannerCustomWeakReference = new WeakReference(var2);
        listener = var3;
        this.ad = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("banner");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("youdao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }

    public void onNativeLoad(NativeResponse var1) {
        if (checkNotNull()) {
            if (var1 == null) {
                listener.onADFailed("get ad empty!!");
            } else {
                listener.onADReceiv();
                if (adMobGenBannerCustomWeakReference != null && adMobGenBannerCustomWeakReference.get() != null) {
                    ((ADMobGenBannerCustom)adMobGenBannerCustomWeakReference.get()).showAd(var1);
                }
            }
        }

    }

    public void onNativeFail(NativeErrorCode var1) {
        if (checkNotNull()) {
            listener.onADFailed(var1.name());
        }

    }

    public boolean checkNotNull() {
        return iadMobGenAdWeakReference != null && iadMobGenAdWeakReference.get() != null && !((IADMobGenAd)iadMobGenAdWeakReference.get()).isDestroy() && listener != null;
    }

    @Override
    public void onNativeImpression(View view, NativeResponse nativeResponse) {
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }

    @Override
    public void onNativeClick(View view, NativeResponse nativeResponse) {
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(ad.getApplicationContext(),bean);
    }
}

