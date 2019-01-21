package com.tianpeng.tp_adsdk.tpadmobsdk.ad.information;

import android.app.Activity;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.SimpleADMobGenInformationAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.BaseADinitController;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public final class ADMobGenInformation extends BaseADinitController<SimpleADMobGenInformationAdListener, ADMobGenInformation> {
    private int onlyImageHeight;
    private int informationAdType;

    public ADMobGenInformation(Activity var1) {
        super(var1, TYPE_INFORMATION);
    }

    public ADMobGenInformation(Activity var1, int var2) {
        this(var1);
        this.informationAdType = var2;
    }

    public int getInformationAdType() {
        return this.informationAdType;
    }

    public int getOnlyImageHeight() {
        return this.onlyImageHeight;
    }

    public void setOnlyImageHeight(int var1) {
        this.onlyImageHeight = var1;
    }

    public void setListener(SimpleADMobGenInformationAdListener var1) {
        super.setListener(var1);
    }

    public ADMobGenInformation getParam() {
        return this;
    }

    public SimpleADMobGenInformationAdListener getListener() {
        return (SimpleADMobGenInformationAdListener)super.getListener();
    }

    public void loadAd() {
        super.loadAd();
        if (this.config != null) {
            this.config.adDestroy();
        } else if (this.getListener() != null) {
            this.getListener().onADFailed("create mobAdHelper error");
        }

    }

    public final boolean isDestroy() {
        return super.isDestroy();
    }

    public final Activity getActivity() {
        return super.getActivity();
    }

    public void destroy() {
        super.destroy();
        this.setListener((SimpleADMobGenInformationAdListener)null);
    }
}
