package com.tianpeng.tp_adsdk.tpadmobsdk.ad;

import android.app.Activity;
import android.content.Context;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenAdListener;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface IADMobGenAd<T extends ADMobGenAdListener, E extends IADMobGenAd> {
    void loadAd();

    void destroy();

    boolean isDestroy();

    Activity getActivity();

    Context getApplicationContext();

    T getListener();

    void setListener(T var1);

    E getParam();
}

