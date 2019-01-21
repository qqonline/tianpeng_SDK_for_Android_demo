package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.ADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.IADMobGenInformation;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenInformationAdCallBack {
    void onADFailed(String var1);

    void onADReceiv(IADMobGenInformationView var1);

    void onADExposure();

    void onADClick();

    boolean isWeb();

    ADMobGenInformation getAdMobGenInformation();

    IADMobGenInformation getIadMobGenInformation();

    IADMobGenConfiguration getConfiguration();
}
