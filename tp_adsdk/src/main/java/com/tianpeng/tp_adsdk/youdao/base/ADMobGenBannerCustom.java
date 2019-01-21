package com.tianpeng.tp_adsdk.youdao.base;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.banner.ADMobGenBannerCustomBase;
import com.youdao.sdk.nativeads.NativeResponse;
import com.youdao.sdk.nativeads.YouDaoNative;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenBannerCustom extends ADMobGenBannerCustomBase<NativeResponse> {
    private YouDaoNative youDaoNative;

    public ADMobGenBannerCustom(Context var1, boolean var2) {
        super(var1, var2);
    }

    public void setYoudaoNative(YouDaoNative var1) {
        youDaoNative = var1;
    }

    public String showImage(NativeResponse var1) {
        return var1 != null ? var1.getMainImageUrl() : null;
    }

    public boolean clickAdImp(NativeResponse var1, View var2) {
        if (var1 != null && this.getAdMobGenAd() != null) {
            if (var1.isDownloadApk() && TPADMobSDK.instance().isWifi()) {
                Toast.makeText(this.getContext().getApplicationContext(), "开始下载", Toast.LENGTH_SHORT).show();
            }

            var1.handleClick((View)this.getAdMobGenAd());
        }

        return false;
    }

    public void destroy() {
        super.destroy();
        this.destory();
    }

    public void onADExposureImp(NativeResponse var1) {
        if (var1 != null && this.getAdMobGenAd() != null) {
            var1.recordImpression((View)this.getAdMobGenAd());
        }

    }

    protected String getLogTag() {
        return "Banner_youdao";
    }


    private void destory() {
        if (youDaoNative != null) {
            youDaoNative.destroy();
            youDaoNative = null;
        }

        if (this.getData() != null) {
            ((NativeResponse)this.getData()).destroy();
        }

    }
}

