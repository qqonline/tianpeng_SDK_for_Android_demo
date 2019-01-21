package com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp;

import android.view.ViewGroup;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.ADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.IADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.information.ADMobGenInformationImp;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;
import com.tianpeng.tp_adsdk.tpadmobsdk.http.RequestParam;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.information.InfomationView;

import java.lang.ref.WeakReference;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_GDT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class IADMobGenInformationAdCallBackIml implements IADMobGenInformationAdCallBack {
    private WeakReference<ADMobGenInformation> weakReference;
    private boolean isWeb;
    private IADMobGenInformation iadMobGenInformation;
    private IADMobGenConfiguration configuration;
    private String sdkName;
    private boolean isRequest;
    private boolean isWebRequest;

    public IADMobGenInformationAdCallBackIml(ADMobGenInformation var1, IADMobGenInformation var2, boolean var3, IADMobGenConfiguration var4) {
        this.weakReference = new WeakReference(var1);
        this.iadMobGenInformation = var2;
        this.isWeb = var3;
        this.configuration = var4;
        if (var4 != null) {
            this.sdkName = var4.getSdkName();
        }

    }

    public void onADFailed(String var1) {
        LogTool.show(this.sdkName + "_onADFailed_" + var1);
        if (this.listenerNotNull()) {
            ((ADMobGenInformation)this.weakReference.get()).getListener().onADFailed(var1);
        }

    }

    public void onADReceiv(IADMobGenInformationView var1) {
        LogTool.show(this.sdkName + "_onADReceiv");
        if (var1 != null && var1.getInformationAdView() != null && !this.iadMobGenInformation.isDestroy()) {
            if (this.weakReference != null && this.weakReference.get() != null
                    && !((ADMobGenInformation)this.weakReference.get()).isDestroy() && this.iadMobGenInformation != null
                    && this.iadMobGenInformation.getInformationAdView() != null && !this.iadMobGenInformation.isDestroy()) {
                if (PLAFORM_GDT.equalsIgnoreCase(var1.getSdkName())) {
                    var1.getInformationAdView().setAlpha(this.isWeb ? 1.0F : 0.0F);
                    if (this.iadMobGenInformation.getInformationAdView() instanceof ViewGroup) {
                        if (this.isWeb) {
                            ((ViewGroup)this.iadMobGenInformation.getInformationAdView()).addView(var1.getInformationAdView());
                        } else {
                            InfomationView var2 = new InfomationView(((ADMobGenInformation)this.weakReference.get()).getApplicationContext());
                            var2.addView(var1.getInformationAdView());
                            var2.setAlpha(0.0F);
                            ((ViewGroup)this.iadMobGenInformation.getInformationAdView()).addView(var2, 0);
                        }
                    }
                } else if (this.isWeb && this.iadMobGenInformation.getInformationAdView() instanceof ViewGroup) {
                    ((ViewGroup)this.iadMobGenInformation.getInformationAdView()).addView(var1.getInformationAdView());
                }

                if (this.iadMobGenInformation instanceof ADMobGenInformationImp) {
                    ADMobGenInformationImp var3 = (ADMobGenInformationImp)this.iadMobGenInformation;
                    var3.addIADMobGenInformationView(var1);
                }

                if (this.listenerNotNull()) {
                    ((ADMobGenInformation)this.weakReference.get()).getListener().onADReceiv(this.iadMobGenInformation);
                }
            } else if (this.listenerNotNull()) {
                ((ADMobGenInformation)this.weakReference.get()).getListener().onADFailed("empty view!!");
            }

        } else {
            if (this.listenerNotNull()) {
                ((ADMobGenInformation)this.weakReference.get()).getListener().onADFailed("empty data!!");
            }

        }
    }

    public void onADExposure() {
        LogTool.show(this.sdkName + "_onADExposure");
        if (this.listenerNotNull()) {
            ((ADMobGenInformation)this.weakReference.get()).getListener().onADExposure(this.iadMobGenInformation);
        }

        if (this.configuration != null && !this.isRequest) {
            this.isRequest = true;
            RequestParam.request(this.configuration.getSdkName(), STR_TYPE_INFORMATION, this.isWeb ? 1 : 0, "display", (String)null);
        }

    }

    public void onADClick() {
        LogTool.show(this.sdkName + "_onADClick");
        if (this.listenerNotNull()) {
            ((ADMobGenInformation)this.weakReference.get()).getListener().onADClick(this.iadMobGenInformation);
        }

        if (this.isWeb && this.configuration != null && !this.isWebRequest) {
            this.isWebRequest = true;
            RequestParam.request(this.configuration.getSdkName(), STR_TYPE_INFORMATION, this.isWeb ? 1 : 0, "click", (String) null);
        }

    }

    public boolean isWeb() {
        return this.isWeb;
    }

    public ADMobGenInformation getAdMobGenInformation() {
        return this.weakReference == null ? null : (ADMobGenInformation)this.weakReference.get();
    }

    public IADMobGenInformation getIadMobGenInformation() {
        return this.iadMobGenInformation;
    }

    public IADMobGenConfiguration getConfiguration() {
        return this.configuration;
    }

    public boolean listenerNotNull() {
        return this.weakReferenceIsRunning() && ((ADMobGenInformation)this.weakReference.get()).getListener() != null && this.iadMobGenInformation != null && !this.iadMobGenInformation.isDestroy();
    }

    public boolean weakReferenceIsRunning() {
        return this.isWeb && this.weakReference != null && this.weakReference.get() != null && !((ADMobGenInformation)this.weakReference.get()).isDestroy();
    }
}
