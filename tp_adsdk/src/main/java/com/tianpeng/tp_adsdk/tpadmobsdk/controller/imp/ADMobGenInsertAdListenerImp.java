package com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.insert.ADMobGenInsertView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.http.RequestParam;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INSERT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_SPLASH;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ADMobGenInsertAdListenerImp extends ADMobGenAdListenerImp<ADMobGenInsertView> implements ADMobGenInsertitailAdListener {
    private boolean adClosed;
    private boolean exposureRequested;

    public ADMobGenInsertAdListenerImp(ADMobGenInsertView var1, IADMobGenConfiguration var2, boolean var3) {
        super(var1, var2, var3, STR_TYPE_SPLASH);
    }

    public void onADExposure() {
        LogTool.show(this.sdkName + "_onADExposure");
        if (this.listenerNotNull()) {
            if (((ADMobGenInsertView)this.weakReference.get()).getListener() != null) {
                ((ADMobGenInsertView)this.weakReference.get()).getListener().onADExposure();
            }
        }

        if (this.configuration != null && !this.exposureRequested) {
            this.exposureRequested = true;
            RequestParam.request(this.configuration.getSdkName(), STR_TYPE_INSERT, this.isWeb ? 0 : 1, "display", (String)null);
        }

    }

    @Override
    public void onADOpen() {
        LogTool.show(this.sdkName + "_onADOpen");
    }

    @Override
    public void onADLeftApplication() {
        LogTool.show(this.sdkName + "_onADLeftApplication");
    }

    public void onADClick() {
        super.onADClick();
    }

    public void onAdClose() {
        if (!this.adClosed) {
            this.adClosed = true;
            super.onAdClose();
        }
    }

}
