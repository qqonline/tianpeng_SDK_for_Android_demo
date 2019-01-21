package com.tianpeng.tp_adsdk.youdao.view;

import android.view.View;

import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;
import com.tianpeng.tp_adsdk.youdao.base.ADMobGenInformationCustom;
import com.youdao.sdk.nativeads.NativeResponse;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenInformationView implements IADMobGenInformationView {
    private ADMobGenInformationCustom custom;

    public ADMobGenInformationView(NativeResponse var1, IADMobGenInformationAdCallBack var2) {
        if (var2 != null && var2.getAdMobGenInformation() != null && !var2.getAdMobGenInformation().isDestroy() && var2.getIadMobGenInformation() != null && !var2.getIadMobGenInformation().isDestroy()) {
            custom = new ADMobGenInformationCustom(var2.getAdMobGenInformation().getApplicationContext(), var2.isWeb());
            custom.setAdMobGenAd(var2.getAdMobGenInformation().getParam());
            custom.setADMobGenInformationAdCallBack(var2);
            custom.setData(var1);
        }

    }

    public View getInformationAdView() {
        return custom;
    }

    public void render() {
        if (custom != null) {
            custom.render();
        }

    }

    public void onExposured() {
        if (custom != null) {
            custom.exposure();
        }

    }

    public void destroy() {
        if (custom != null) {
            custom.destroy();
        }

    }

    public String getSdkName() {
        return "youdao";
    }
}

