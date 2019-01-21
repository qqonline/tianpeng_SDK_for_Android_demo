package com.tianpeng.tp_adsdk.youdao.base;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.splash.ADMobGenSplashCustomBase;
import com.youdao.sdk.nativeads.NativeResponse;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenSplashCustom extends ADMobGenSplashCustomBase<NativeResponse> {

    public ADMobGenSplashCustom(Context var1, boolean var2) {
        super(var1, var2);
    }

    public void destroy() {
        super.destroy();
        NativeResponse var1 = (NativeResponse)this.getData();
        if (var1 != null) {
            var1.destroy();
        }

    }

    public void onADExposureImp(NativeResponse var1) {
        if (var1 != null && this.getAdMobGenAd() != null) {
            var1.recordImpression((View)this.getAdMobGenAd());
        }

    }

    public String showImage(NativeResponse var1) {
        return var1 != null ? var1.getMainImageUrl() : null;
    }

    public boolean clickAdImp(NativeResponse var1, View var2) {
        boolean var3 = false;
        if (var1 != null && this.getAdMobGenAd() != null) {
            if (var1.isDownloadApk()) {
                if (TPADMobSDK.instance().isWifi()) {
                    Toast.makeText(this.getContext().getApplicationContext(), "开始下载", Toast.LENGTH_SHORT).show();
                }
            } else {
                var3 = true;
            }

            var1.handleClick((View)this.getAdMobGenAd());
        }

        return var3;
    }

    protected String getLogTag() {
        return "Splash_youdao";
    }


}

