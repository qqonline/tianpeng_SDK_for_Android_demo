package com.tianpeng.tp_adsdk.tpadmobsdk.widget.splash;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.R;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.splash.ADMobGenSplashView;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseInfomationView;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public abstract class ADMobGenSplashCustomBase<T> extends BaseInfomationView<T, ADMobGenSplashView, ADMobGenSplashAdListener> {
    protected Handler handler = new Handler(Looper.getMainLooper());
    private boolean isShow;

    public ADMobGenSplashCustomBase(Context var1, boolean var2) {
        super(var1, var2, true);
    }

    public void destroy() {
        LogTool.show(this.getLogTag() + "Splash_destroy...");
        super.destroy();
        this.handler.removeCallbacksAndMessages((Object)null);
    }

    public void showAd(T var1) {
        if (this.getAdMobGenAd() != null && !((ADMobGenSplashView)this.getAdMobGenAd()).isDestroy()) {
            if (var1 == null) {
                if (this.getAdMobGenAdListener() != null) {
                    ((ADMobGenSplashAdListener)this.getAdMobGenAdListener()).onADFailed(this.getLogTag() + "_createSplash_onADFailed...init error!!");
                }

            } else {
                this.setData(var1);
                this.handler.removeCallbacksAndMessages((Object)null);
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if (ADMobGenSplashCustomBase.this.getAdMobGenAd() != null && !((ADMobGenSplashView)ADMobGenSplashCustomBase.this.getAdMobGenAd()).isDestroy() && ADMobGenSplashCustomBase.this.getAdMobGenAdListener() != null) {
                           LogTool.show(ADMobGenSplashCustomBase.this.getLogTag() + "_createSplash_onADClose...");
                            ((ADMobGenSplashAdListener)ADMobGenSplashCustomBase.this.getAdMobGenAdListener()).onAdClose();
                        }

                    }
                }, 5000L);
                String var2 = this.showImage(var1);
                if (!TextUtils.isEmpty(var2)) {
                    this.loadWebView(var2);
                }

                this.view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View var1) {
                        LogTool.show(ADMobGenSplashCustomBase.this.getLogTag() + "_createSplash_onADClick...");
                        if (ADMobGenSplashCustomBase.this.getAdMobGenAdListener() != null) {
                            ((ADMobGenSplashAdListener)ADMobGenSplashCustomBase.this.getAdMobGenAdListener()).onADClick();
                        }

                        boolean var2 = ADMobGenSplashCustomBase.this.clickAdImp(ADMobGenSplashCustomBase.this.getData(), var1);
                        if (var2 && ADMobGenSplashCustomBase.this.handler != null) {
                            ADMobGenSplashCustomBase.this.handler.removeCallbacksAndMessages((Object)null);
                        }

                    }
                });
                if (!this.isWeb) {
                    this.showAD();
                }

            }
        }
    }

    public void onRenderFinish() {
        super.onRenderFinish();
        if (this.isWeb) {
            this.showAD();
        }

    }

    private void showAD() {
        if (!this.isShow) {
            this.isShow = true;
            this.onADExposureImp(this.getData());
            this.addLogo();
            LogTool.show(this.getLogTag() + "_createSplash_onADExposure...");
            if (this.getAdMobGenAdListener() != null) {
                ((ADMobGenSplashAdListener)this.getAdMobGenAdListener()).onADExposure();
            }
        }

    }

    private void addLogo() {
        if (this.isWeb) {
            View var1 = LayoutInflater.from(this.getContext()).inflate(R.layout.ad_mob_gen_ad_logo_text_layout, (ViewGroup)null, false);
            Resources var2 = this.getContext().getApplicationContext().getResources();
            int var3 = (int)(var2.getDisplayMetrics().density * 18.0F);
            RelativeLayout.LayoutParams var4 = new RelativeLayout.LayoutParams(var3 * 43 / 18, var3);
            var4.addRule(11);
            var4.addRule(12);
            var1.setLayoutParams(var4);
            this.addView(var1);
        }
    }

    public abstract void onADExposureImp(T var1);
}

