package com.tianpeng.tp_adsdk.youdao.listener;

import android.view.View;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.splash.ADMobGenSplashView;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.youdao.base.ADMobGenSplashCustom;
import com.youdao.sdk.nativeads.NativeErrorCode;
import com.youdao.sdk.nativeads.NativeResponse;
import com.youdao.sdk.nativeads.YouDaoNative;

import java.lang.ref.WeakReference;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class YouDaoNativeNetworkListenerImp implements YouDaoNative.YouDaoNativeNetworkListener,YouDaoNative.YouDaoNativeEventListener {
    private final WeakReference<IADMobGenAd> iadMobGenAdWeakReference;
    private final WeakReference<RelativeLayout> relativeLayoutWeakReference;
    private final ADMobGenSplashAdListener listener;
    private IADMobGenAd ad;
    private IADMobGenConfiguration configuration;
    private UploadDataBean bean;
    public YouDaoNativeNetworkListenerImp(IADMobGenAd var1, RelativeLayout var2, ADMobGenSplashAdListener var3, IADMobGenAd ad, IADMobGenConfiguration configuration) {
        this.iadMobGenAdWeakReference = new WeakReference(var1);
        this.relativeLayoutWeakReference = new WeakReference(var2);
        this.listener = var3;
        this.ad = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("open");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("youdao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }

    public void onNativeLoad(NativeResponse var1) {
        if (this.notNull()) {
            if (var1 == null) {
                this.listener.onADFailed("get ad empty!!");
            } else {
                this.listener.onADReceiv();
                if (this.relativeLayoutWeakReference != null && this.relativeLayoutWeakReference.get() != null
                        && this.relativeLayoutWeakReference.get() instanceof ADMobGenSplashCustom) {
                    ADMobGenSplashCustom var2 = (ADMobGenSplashCustom)this.relativeLayoutWeakReference.get();
                    var2.setAdMobGenAd((ADMobGenSplashView)this.iadMobGenAdWeakReference.get());
                    var2.setAdMobGenAdListener(this.listener);
                    var2.showAd(var1);
                }
            }
        }

    }

    public void onNativeFail(NativeErrorCode var1) {
        if (this.notNull()) {
            this.listener.onADFailed(var1.name());
        }

    }

    public boolean notNull() {
        return this.iadMobGenAdWeakReference != null && this.iadMobGenAdWeakReference.get() != null && !((IADMobGenAd)this.iadMobGenAdWeakReference.get()).isDestroy() && this.listener != null;
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

