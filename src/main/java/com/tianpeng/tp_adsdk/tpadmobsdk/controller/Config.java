package com.tianpeng.tp_adsdk.tpadmobsdk.controller;

import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public abstract class Config<T>{
    private IADMobGenConfiguration configuration;
    private boolean isDestrory = false;

    public Config(IADMobGenConfiguration var1) {
        this.configuration = var1;
        LogTool.show("AbsADMobAdHelper_configuration_" + (var1 == null ? "" : var1.getSdkName()));
    }

    public void destrory() {
        this.isDestrory = true;
        this.destroy();
    }

    public abstract void setView(T var1);

    public abstract void adDestroy();

    protected abstract void destroy();
}
