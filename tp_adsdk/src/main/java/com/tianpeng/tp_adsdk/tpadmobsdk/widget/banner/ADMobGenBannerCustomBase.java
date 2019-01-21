package com.tianpeng.tp_adsdk.tpadmobsdk.widget.banner;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.R;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.banner.ADMobGenBannerView;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseInfomationView;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public abstract class ADMobGenBannerCustomBase<T> extends BaseInfomationView<T, ADMobGenBannerView, ADMobGenBannerAdListener> {
    private boolean isADshowing;

    public ADMobGenBannerCustomBase(Context var1, boolean var2) {
        super(var1, var2, true);
    }

    protected void onMeasure(int var1, int var2) {
        this.setMeasuredDimension(getDefaultSize(0, var1), getDefaultSize(0, var2));
        int var3 = this.getMeasuredWidth();
        var1 = MeasureSpec.makeMeasureSpec(var3, MeasureSpec.EXACTLY);
        var2 = MeasureSpec.makeMeasureSpec(Math.max(var3 * 5 / 32, (int)(this.getResources().getDisplayMetrics().density * 50.0F)),  MeasureSpec.EXACTLY);
        super.onMeasure(var1, var2);
    }

    public void showAd(T var1) {
        if (this.getAdMobGenAd() != null && !((ADMobGenBannerView)this.getAdMobGenAd()).isDestroy()) {
            if (var1 == null) {
                if (this.getAdMobGenAdListener() != null) {
                    ((ADMobGenBannerAdListener)this.getAdMobGenAdListener()).onADFailed(this.getLogTag() + "_createBanner_onADFailed...init error!!");
                }

            } else {
                this.setData(var1);
                String var2 = this.showImage(var1);
                if (!TextUtils.isEmpty(var2)) {
                    this.loadWebView(var2);
                }

                this.view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View var1) {
                        if (ADMobGenBannerCustomBase.this.getAdMobGenAdListener() != null) {
                            ((ADMobGenBannerAdListener)ADMobGenBannerCustomBase.this.getAdMobGenAdListener()).onADClick();
                        }

                        ADMobGenBannerCustomBase.this.clickAdImp(ADMobGenBannerCustomBase.this.getData(), var1);
                    }
                });
                if (!this.isWeb) {
                    this.showWebView();
                }

            }
        }
    }

    public void destroy() {
        LogTool.show(this.getLogTag() + "Banner_destroy...");
        super.destroy();
    }

    public void onRenderFinish() {
        super.onRenderFinish();
        this.showWebView();
    }

    private void showWebView() {
        if (!this.isADshowing) {
            this.onADExposureImp(this.getData());
            LogTool.show(this.getLogTag() + "_createBanner_onADExposure...");
            this.showView();
            this.isADshowing = true;
            if (this.getAdMobGenAdListener() != null) {
                ((ADMobGenBannerAdListener)this.getAdMobGenAdListener()).onADExposure();
            }

        }
    }

    private void showView() {
        if (this.isWeb) {
            View var1 = LayoutInflater.from(this.getContext()).inflate(R.layout.ad_mob_gen_ad_logo_text_layout, (ViewGroup)null, false);
            Resources var2 = this.getContext().getApplicationContext().getResources();
            int var3 = (int)(var2.getDisplayMetrics().density * 16.0F);
            RelativeLayout.LayoutParams var4 = new RelativeLayout.LayoutParams(var3 * 43 / 18, var3);
            var4.addRule(11);
            var4.addRule(12);
            var1.setLayoutParams(var4);
            this.addView(var1);
        }
    }

    public abstract void onADExposureImp(T var1);
}

