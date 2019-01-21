package com.tianpeng.tp_adsdk.tpadmobsdk.controller;

import android.app.Activity;
import android.content.Context;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public abstract class BaseADinitController<T extends ADMobGenAdListener, E extends BaseADinitController> implements IADMobGenAd<T, E> {
    private  int channelType;
    private  Context context;
    protected Config config;
    private T listener;
    protected  Activity activity;
    private boolean isDestrory;

    protected BaseADinitController(Activity var1, int var2) {
        this.channelType = var2;
        this.activity = var1;
        this.context = var1.getApplicationContext();
    }

    public void loadAd() {
        if (this.config == null) {
            this.config = ChannelChange.chooseChannel(this.channelType, (IADMobGenConfiguration)null);
            if (this.config != null) {
                this.config.setView(this.getParam());
            }
        }

    }

    public void destroy() {
        if (this.config != null) {
            this.config.destrory();
            this.config = null;
        }

    }

    public boolean isDestroy() {
        return this.isDestrory || this.activity == null || this.activity.isFinishing();
    }

    public Activity getActivity() {
        return this.activity;
    }

    public Context getApplicationContext() {
        return this.context;
    }

    public T getListener() {
        return this.listener;
    }

    public void setListener(T var1) {
        this.listener = var1;
    }
}
