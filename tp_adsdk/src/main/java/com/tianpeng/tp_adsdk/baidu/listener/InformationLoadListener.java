package com.tianpeng.tp_adsdk.baidu.listener;

import android.text.TextUtils;

import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.feeds.NativeErrorCode;
import com.baidu.mobad.feeds.NativeResponse;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.baidu.view.ADMobGenInformationView;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;

import java.util.List;
import java.util.Map;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class InformationLoadListener implements BaiduNative.BaiduNativeNetworkListener,BaiduNative.BaiduNativeEventListener {
    private final IADMobGenInformationAdCallBack callBack;
    private final Map<String, BaiduNative> map;
    IADMobGenAd iadMobGenAd;
    private  IADMobGenConfiguration configuration;
    private UploadDataBean bean;
    public InformationLoadListener(IADMobGenInformationAdCallBack var1, Map<String, BaiduNative> var2,IADMobGenAd ad,IADMobGenConfiguration configuration) {
        this.callBack = var1;
        this.map = var2;
        this.iadMobGenAd = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getNativeId());
        bean.setAdType("native");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("baidu");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(iadMobGenAd.getApplicationContext().getPackageName());
    }

    public void onNativeFail(NativeErrorCode var1) {
        if (this.checkCallBackNotNull()) {
            this.callBack.onADFailed(TextUtils.isEmpty(var1.name()) ? "get ad error" : var1.name());
            this.onDestroy(this.callBack.hashCode() + "");
        }

    }

    public void onNativeLoad(List<NativeResponse> var1) {
        if (this.checkCallBackNotNull()) {
            if (var1 != null && !var1.isEmpty() && var1.get(0) != null) {
                NativeResponse var2 = (NativeResponse)var1.get(0);
                ADMobGenInformationView var3 = new ADMobGenInformationView(var2, this.callBack);
                this.callBack.onADReceiv(var3);
            } else {
                this.callBack.onADFailed("empty data");
            }

            this.onDestroy(this.callBack.hashCode() + "");
        }

    }

    public boolean checkCallBackNotNull() {
        return this.callBack != null;
    }

    private void onDestroy(String var1) {
        try {
            if (this.map == null) {
                return;
            }

            BaiduNative var2 = (BaiduNative)this.map.get(var1);
            if (var2 != null) {
                var2.destroy();
                var2 = null;
                this.map.remove(var1);
            }
        } catch (Exception var3) {
            ;
        }

    }

    @Override
    public void onImpressionSended() {
        bean.setSdkAction("show");
        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
    }

    @Override
    public void onClicked() {
        bean.setSdkAction("click");
        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
    }
}

