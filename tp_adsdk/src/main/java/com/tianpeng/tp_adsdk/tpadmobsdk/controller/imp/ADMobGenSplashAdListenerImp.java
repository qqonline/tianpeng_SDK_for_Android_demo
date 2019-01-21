package com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp;

import android.content.res.Resources;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.R;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.splash.ADMobGenSplashView;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.http.RequestParam;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.progress.DonutProgress;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_BAIDU;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_TOUTIAO;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_SPLASH;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ADMobGenSplashAdListenerImp extends ADMobGenAdListenerImp<ADMobGenSplashView> implements ADMobGenSplashAdListener {
    private CountDownTimer countDownTimer;
    private DonutProgress donutProgress;
    private boolean adClosed;
    private boolean exposureRequested;

    public ADMobGenSplashAdListenerImp(ADMobGenSplashView var1, IADMobGenConfiguration var2, boolean var3) {
        super(var1, var2, var3, STR_TYPE_SPLASH);
    }

    public void onADExposure() {
        LogTool.show(this.sdkName + "_onADExposure");
        if (this.listenerNotNull()) {
            if (!PLAFORM_BAIDU.equalsIgnoreCase(this.configuration.getSdkName()) && !PLAFORM_TOUTIAO.equalsIgnoreCase(this.configuration.getSdkName())) {
                this.splashStart();
            }

            if (((ADMobGenSplashView)this.weakReference.get()).getListener() != null) {
                ((ADMobGenSplashView)this.weakReference.get()).getListener().onADExposure();
            }
        }

        if (this.configuration != null && !this.exposureRequested) {
            this.exposureRequested = true;
            RequestParam.request(this.configuration.getSdkName(), STR_TYPE_SPLASH, this.isWeb ? 0 : 1, "display", (String)null);
        }

    }

    public void onADClick() {
        super.onADClick();
    }

    public void onAdClose() {
        if (!this.adClosed) {
            this.adClosed = true;
            super.onAdClose();
        }

        this.cacelTimer();
    }

    private void splashStart() {
        this.donutProgress = (DonutProgress) LayoutInflater.from(((ADMobGenSplashView)this.weakReference.get()).getApplicationContext()).inflate(R.layout.ad_mob_gen_default_jump, (ViewGroup)null, false);
        Resources var1 = ((ADMobGenSplashView)this.weakReference.get()).getApplicationContext().getResources();
        int var2 = (int)(var1.getDisplayMetrics().density * 43.0F);
        RelativeLayout.LayoutParams var3 = new RelativeLayout.LayoutParams(var2, var2);
        var3.addRule(11);
        var3.topMargin = var2 / 3;
        var3.rightMargin = var2 / 3;
        ((ADMobGenSplashView)this.weakReference.get()).addView(this.donutProgress, var3);
        this.cacelTimer();
        this.donutProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                var1.setClickable(false);
                ADMobGenSplashAdListenerImp.this.onAdClose();
            }
        });
        this.countDownTimer = new CountDownTimer(5000L, 200L) {
            public void onTick(long var1) {
                if (ADMobGenSplashAdListenerImp.this.referenceNotNull() && ADMobGenSplashAdListenerImp.this.countDownTimer != null && ADMobGenSplashAdListenerImp.this.donutProgress.getParent() != null) {
                    ADMobGenSplashAdListenerImp.this.donutProgress.setProgress(ADMobGenSplashAdListenerImp.this.donutProgress.getProgress() - 4.0F);
                } else {
                    ADMobGenSplashAdListenerImp.this.cacelTimer();
                }

            }

            public void onFinish() {
                if (ADMobGenSplashAdListenerImp.this.referenceNotNull() && ADMobGenSplashAdListenerImp.this.donutProgress != null && ADMobGenSplashAdListenerImp.this.donutProgress.getParent() != null) {
                    ADMobGenSplashAdListenerImp.this.donutProgress.setProgress(0.0F);
                }

                ADMobGenSplashAdListenerImp.this.cacelTimer();
            }
        };
        this.countDownTimer.start();
    }

    private void cacelTimer() {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
            this.countDownTimer = null;
        }

    }
}
