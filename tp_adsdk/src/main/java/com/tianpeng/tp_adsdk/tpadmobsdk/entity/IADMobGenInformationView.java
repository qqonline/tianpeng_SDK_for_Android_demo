package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import android.view.View;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenInformationView {
    View getInformationAdView();

    void render();

    void onExposured();

    void destroy();

    String getSdkName();
}
