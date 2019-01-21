package com.tianpeng.tp_adsdk.gdt.listener;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.gdt.view.ADMobGenInformationView;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;

import java.util.List;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class GDTInformationADListener implements NativeExpressAD.NativeExpressADListener {
    private IADMobGenInformationAdCallBack callBack;
    IADMobGenAd iadMobGenAd;
    private  IADMobGenConfiguration configuration;
    private UploadDataBean bean;
    public GDTInformationADListener(IADMobGenInformationAdCallBack var1, IADMobGenConfiguration configuration,IADMobGenAd ad) {
        this.callBack = var1;
        this.iadMobGenAd = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getNativeId());
        bean.setAdType("native");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("GDT");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(iadMobGenAd.getApplicationContext().getPackageName());
    }
    public void onNoAD(AdError var1) {
        if (this.checkNotNull()) {
            this.callBack.onADFailed(var1.getErrorMsg());
        }

    }

    public void onADLoaded(List<NativeExpressADView> var1) {
        if (this.checkNotNull()) {
            if (var1 != null && !var1.isEmpty() && var1.get(0) != null) {
                NativeExpressADView var2 = (NativeExpressADView)var1.get(0);
                ADMobGenInformationView var3;
                if (this.iadMobGenAd != null) {
                    var3 = new ADMobGenInformationView(var2, this.iadMobGenAd);
                    this.callBack.onADReceiv(var3);
                } else {
                    var3 = new ADMobGenInformationView(var2);
                    this.callBack.onADReceiv(var3);
                }
            } else {
                this.callBack.onADFailed("empty data");
            }
        }

    }

    public void onRenderFail(NativeExpressADView var1) {
    }

    public void onRenderSuccess(NativeExpressADView var1) {
        if (this.checkNotNull()) {
            loadAD(var1);
        }

    }

    public void onADExposure(NativeExpressADView var1) {
        if (this.checkNotNull()) {
            this.callBack.onADExposure();
        }
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
    }

    public void onADClicked(NativeExpressADView var1) {
        if (this.checkNotNull()) {
            this.callBack.onADClick();
        }
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
    }

    public void onADClosed(NativeExpressADView var1) {
    }

    public void onADLeftApplication(NativeExpressADView var1) {
    }

    public void onADOpenOverlay(NativeExpressADView var1) {
    }

    public void onADCloseOverlay(NativeExpressADView var1) {
    }

    public boolean checkNotNull() {
        return this.callBack != null && this.callBack.getIadMobGenInformation() != null && !this.callBack.getIadMobGenInformation().isDestroy();
    }

    private static void loadAD(ViewGroup var0) {
        if (var0 != null) {
            int var1 = var0.getChildCount();

            for(int var2 = 0; var2 < var1; ++var2) {
                View var3 = var0.getChildAt(var2);
                if (var3 instanceof WebView) {
                    WebView var4 = (WebView)var3;
                    var4.loadUrl("javascript:document.getElementsByClassName(\"vda\")[0].removeChild(document.getElementsByClassName(\"del\")[0]);");
                    break;
                }

                if (var3 instanceof ViewGroup) {
                    loadAD((ViewGroup)var3);
                }
            }
        }

    }
}

