package com.tianpeng.tp_adsdk.gdt.view;

import android.view.View;

import com.qq.e.ads.nativ.NativeExpressADView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationView implements IADMobGenInformationView {
    private final NativeExpressADView view;
    private IADMobGenAd iadMobGenAd;

    public ADMobGenInformationView(NativeExpressADView var1) {
        this.view = var1;
    }

    public ADMobGenInformationView(NativeExpressADView var1, IADMobGenAd var2) {
        this.view = var1;
        this.iadMobGenAd = var2;
    }

    public View getInformationAdView() {
        return this.view;
    }

    public void render() {
        if (this.vieNotNull()) {
            this.view.render();
        }

    }

    public void onExposured() {
    }

    public void destroy() {
        if (this.vieNotNull()) {
            this.view.destroy();
        }

    }

    public String getSdkName() {
        return "gdt";
    }

    public boolean vieNotNull() {
        return this.view != null;
    }
}

