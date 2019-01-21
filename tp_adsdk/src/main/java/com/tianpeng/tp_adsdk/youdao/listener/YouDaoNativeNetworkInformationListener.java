package com.tianpeng.tp_adsdk.youdao.listener;

import android.view.View;

import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.youdao.view.ADMobGenInformationView;
import com.youdao.sdk.nativeads.NativeErrorCode;
import com.youdao.sdk.nativeads.NativeResponse;
import com.youdao.sdk.nativeads.YouDaoNative;

import java.util.Map;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class YouDaoNativeNetworkInformationListener implements YouDaoNative.YouDaoNativeNetworkListener,YouDaoNative.YouDaoNativeEventListener {
    private final Map<String, YouDaoNative> map;
    private final IADMobGenInformationAdCallBack callBack;
    private IADMobGenAd ad;
    private IADMobGenConfiguration configuration;
    private UploadDataBean bean;

    public YouDaoNativeNetworkInformationListener(Map<String, YouDaoNative> var1, IADMobGenInformationAdCallBack var2, IADMobGenAd ad, IADMobGenConfiguration configuration) {
        this.map = var1;
        callBack = var2;
        this.ad = ad;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("native");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("youdao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(ad.getApplicationContext().getPackageName());
    }

    public void onNativeLoad(NativeResponse var1) {
        if (callBack != null) {
            if (var1 == null) {
                callBack.onADFailed("get ad empty!!");
            } else {
                ADMobGenInformationView var2 = new ADMobGenInformationView(var1, callBack);
                callBack.onADReceiv(var2);
            }

            this.destory(callBack.hashCode() + "");
        }

    }

    public void onNativeFail(NativeErrorCode var1) {
        if (callBack != null) {
            callBack.onADFailed(var1.name());
            this.destory(callBack.hashCode() + "");
        }

    }

    private void destory(String var1) {
        try {
            if (this.map == null) {
                return;
            }

            YouDaoNative var2 = (YouDaoNative)this.map.get(var1);
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

