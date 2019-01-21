package com.tianpeng.tp_adsdk.tpadmobsdk.widget.information;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.R;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.ADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInformationAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.ADMobGenInformationEntity;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseInfomationView;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public abstract class ADMobGenInformationCustomBase<T> extends BaseInfomationView<T, ADMobGenInformation, ADMobGenInformationAdListener> {
    private Handler handler = new Handler();
    private IADMobGenInformationAdCallBack callBack;
    private boolean h;
    private boolean i;
    public View informationAdView;

    public ADMobGenInformationCustomBase(Context var1, boolean var2) {
        super(var1, var2, false);
        if (this.webView != null) {
            RelativeLayout.LayoutParams var3 = new RelativeLayout.LayoutParams(-1, -2);
            this.webView.setLayoutParams(var3);
            this.webView.setId(R.id.admobgensdk_web_id);
            this.view = new View(this.getContext());
            RelativeLayout.LayoutParams var4 = new RelativeLayout.LayoutParams(-1, -2);
            this.view.setLayoutParams(var4);
            var4.addRule(6, this.webView.getId());
            var4.addRule(8, this.webView.getId());
            this.addView(this.view);
        }

    }

    public void setADMobGenInformationAdCallBack(IADMobGenInformationAdCallBack var1) {
        this.callBack = var1;
        if (var1 != null && var1.getIadMobGenInformation() != null) {
            this.informationAdView = var1.getIadMobGenInformation().getInformationAdView();
        }

    }

    public  void render() {
        if (!this.h) {
            this.showAd(null);
        }

    }

    public  void showAd(T var1) {
        if (this.getAdMobGenAd() != null && !((ADMobGenInformation)this.getAdMobGenAd()).isDestroy() && !this.h) {
            if (this.getData() == null) {
                if (((ADMobGenInformation)this.getAdMobGenAd()).getListener() != null) {
                    ((ADMobGenInformation)this.getAdMobGenAd()).getListener().onADFailed(this.getLogTag() + "_createInformation_onADFailed...init error!!");
                }

            } else if (!this.h) {
                this.showImage(this.getData());
                this.addLogo(this.getInformationEntity(this.getData()));
            }
        }
    }

    public void setAdMobGenAd(ADMobGenInformation var1) {
        super.setAdMobGenAd(var1);
        if (this.webView != null) {
            RelativeLayout.LayoutParams var2 = new RelativeLayout.LayoutParams(-1, -2);
            this.webView.setLayoutParams(var2);
            if (this.view != null) {
                this.removeView(this.view);
                this.addView(this.view);
                this.view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View var1) {
                        if (ADMobGenInformationCustomBase.this.getAdMobGenAd() != null && !((ADMobGenInformation)ADMobGenInformationCustomBase.this.getAdMobGenAd()).isDestroy() && ADMobGenInformationCustomBase.this.getData() != null) {
                            if (ADMobGenInformationCustomBase.this.callBack != null) {
                                ADMobGenInformationCustomBase.this.callBack.onADClick();
                            }

                            ADMobGenInformationCustomBase.this.clickAdImp(ADMobGenInformationCustomBase.this.getData(), ADMobGenInformationCustomBase.this);
                        }

                    }
                });
            }
        }

    }

    public String showImage(T var1) {
        return null;
    }

    public void destroy() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages((Object)null);
            this.handler = null;
        }

        this.callBack = null;
        this.informationAdView = null;
        LogTool.show(this.getLogTag() + "information_destroy...");
        super.destroy();
    }

    public  void onRenderFinish() {
        super.onRenderFinish();
    }

    public void exposure() {
        if (!this.i && this.getAdMobGenAd() != null && !((ADMobGenInformation)this.getAdMobGenAd()).isDestroy() && this.callBack != null && this.getData() != null) {
            LogTool.show(this.getLogTag() + "_createInformation_onADExposure...");
            this.onExposure(this.getData());
            this.i = true;
            this.callExposure();
        }

    }

    public void callExposure() {
        if (this.getAdMobGenAd() != null && !((ADMobGenInformation)this.getAdMobGenAd()).isDestroy() && this.callBack != null) {
            this.callBack.onADExposure();
        }

    }

    public abstract void onExposure(T var1);

    public abstract ADMobGenInformationEntity getInformationEntity(T var1);
}

