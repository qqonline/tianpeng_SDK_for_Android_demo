package com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

import java.lang.ref.WeakReference;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ADMobGenAdListenerImp <T extends IADMobGenAd> implements ADMobGenAdListener {
    protected WeakReference<T> weakReference;
    protected IADMobGenConfiguration configuration;
    protected boolean isWeb;
    private String step;
    protected String sdkName;
    protected boolean showedFail;
    protected boolean nativeClicked;

    public ADMobGenAdListenerImp(T var1, IADMobGenConfiguration var2, boolean var3, String var4) {
        this.weakReference = new WeakReference(var1);
        this.configuration = var2;
        this.isWeb = var3;
        this.step = var4;
        if (var2 != null) {
            this.sdkName = var2.getSdkName();
        }

    }

    public void onADFailed(String var1) {
        LogTool.show(this.sdkName + "_onADFailed_" + var1);
        if (this.listenerNotNull()) {
            ((IADMobGenAd)this.weakReference.get()).getListener().onADFailed(var1);
        }

        if (this.configuration != null && !this.showedFail) {
            this.showedFail = true;
        }

    }

    public void onADReceiv() {
        LogTool.show(this.sdkName + "_onADReceiv");
        if (this.listenerNotNull()) {
            ((IADMobGenAd)this.weakReference.get()).getListener().onADReceiv();
        }

    }

    public void onADClick() {
        LogTool.show(this.sdkName + "_onADClick");
        if (this.listenerNotNull()) {
            ((IADMobGenAd)this.weakReference.get()).getListener().onADClick();
        }

        if (!this.isWeb && this.configuration != null && !this.nativeClicked) {
            this.nativeClicked = true;
        }

    }

    public void onAdClose() {
        LogTool.show(this.sdkName + "_onADClose");
        if (this.listenerNotNull()) {
            ((IADMobGenAd)this.weakReference.get()).getListener().onAdClose();
        }

    }

    public boolean listenerNotNull() {
        return this.referenceNotNull() && ((IADMobGenAd)this.weakReference.get()).getListener() != null;
    }

    public boolean referenceNotNull() {
        return !this.isWeb && this.weakReference != null && this.weakReference.get() != null && !((IADMobGenAd)this.weakReference.get()).isDestroy();
    }
}
