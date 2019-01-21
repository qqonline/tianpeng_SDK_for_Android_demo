package com.tianpeng.tp_adsdk.tpadmobsdk.widget;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.ChannelChange;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.Config;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public abstract class BaseBannerView<T extends ADMobGenAdListener, E extends IADMobGenAd> extends RelativeLayout implements IADMobGenAd<T, E> {
    private  int type;
    private  Context context;
    protected Config config;
    private T listener;
    protected Activity activity;
    private boolean isDestroy;

    protected BaseBannerView(Activity var1, int var2) {
        super(var1);
        this.type = var2;
        this.activity = var1;
        this.context = var1.getApplicationContext();
    }

    public void loadAd() {
        if (this.config == null) {
            this.config = ChannelChange.chooseChannel(this.type, (IADMobGenConfiguration)null);
            if (this.config != null) {
                this.config.setView(this.getParam());
                this.config.adDestroy();
            } else if (this.listener != null) {
                this.listener.onADFailed("create mobAdHelper error");
            }
        }

    }

    public void destroy() {
        this.isDestroy = true;
        if (this.config != null) {
            this.config.destrory();
            this.config = null;
        }

    }

    public boolean isDestroy() {
        return this.isDestroy || activity == null || activity.isFinishing();
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

