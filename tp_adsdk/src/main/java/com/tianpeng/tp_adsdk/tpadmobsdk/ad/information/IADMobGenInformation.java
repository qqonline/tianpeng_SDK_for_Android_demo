package com.tianpeng.tp_adsdk.tpadmobsdk.ad.information;

import android.view.View;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenInformation {
    View getInformationAdView();

    void render();

    void onExposured();

    void destroy();

    boolean isDestroy();

    int getInformationAdType();
}