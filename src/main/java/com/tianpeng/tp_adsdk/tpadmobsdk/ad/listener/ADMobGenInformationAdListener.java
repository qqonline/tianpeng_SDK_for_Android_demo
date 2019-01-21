package com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.IADMobGenInformation;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface ADMobGenInformationAdListener extends ADMobGenAdListener {
    void onADExposure(IADMobGenInformation var1);

    void onADReceiv(IADMobGenInformation var1);

    void onADClick(IADMobGenInformation var1);
}
