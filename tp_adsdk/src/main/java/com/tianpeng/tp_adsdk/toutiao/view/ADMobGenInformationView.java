package com.tianpeng.tp_adsdk.toutiao.view;

import android.view.View;

import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.tianpeng.tp_adsdk.toutiao.base.ADMobGenInformationCustom;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;

import java.util.ArrayList;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationView implements IADMobGenInformationView {
    private ADMobGenInformationCustom custom;
    DownloadStatusController controller;
    boolean b = false;

    public ADMobGenInformationView(TTFeedAd var1, final IADMobGenInformationAdCallBack var2) {
        if (var2 != null && var2.getAdMobGenInformation() != null && !var2.getAdMobGenInformation().isDestroy() && var2.getIadMobGenInformation() != null && !var2.getIadMobGenInformation().isDestroy()) {
            this.custom = new ADMobGenInformationCustom(var2.getAdMobGenInformation(), var2.isWeb());
            this.custom.setInfromationAdType(var2.getAdMobGenInformation().getInformationAdType());
            this.custom.setPlatfromstr("toutiao");
            this.custom.setAdMobGenAd(var2.getAdMobGenInformation().getParam());
            this.custom.setADMobGenInformationAdCallBack(var2);
            this.custom.setData(var1);
            this.custom.addView();
            this.controller = null;
            if (var1.getInteractionType() == 4) {
                this.controller = var1.getDownloadStatusController();
            }

            ArrayList var3 = new ArrayList();
            var3.add(this.custom.getCustomClickView());
            ArrayList var4 = new ArrayList();
            var4.add(this.custom.getCustomClickView());
            var1.registerViewForInteraction(this.custom.getCustomClickView(), var3, var4, new TTNativeAd.AdInteractionListener() {
                public void onAdClicked(View var1, TTNativeAd var2x) {
                    if (var2x != null && var2 != null) {
                        var2.onADClick();
                    }

                }

                public void onAdCreativeClick(View var1, TTNativeAd var2x) {
                    if (var2x != null && var2 != null) {
                        if (ADMobGenInformationView.this.controller != null) {
                            ADMobGenInformationView.this.controller.changeDownloadStatus();
                        }

                        var2.onADClick();
                    }

                }

                public void onAdShow(TTNativeAd var1) {
                    if (var1 != null && var2 != null && !ADMobGenInformationView.this.b) {
                        ADMobGenInformationView.this.b = true;
                        var2.onADExposure();
                    }

                }
            });
        }

    }

    public View getInformationAdView() {
        return this.custom;
    }

    public void render() {
        if (this.custom != null) {
            this.custom.render();
        }

    }

    public void onExposured() {
        if (this.custom != null) {
            this.custom.exposure();
        }

    }

    public void destroy() {
        if (this.custom != null) {
            this.custom.destroy();
        }

    }

    public String getSdkName() {
        return "toutiao";
    }
}

