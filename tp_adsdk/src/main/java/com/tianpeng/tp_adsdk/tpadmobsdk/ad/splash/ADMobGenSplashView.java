package com.tianpeng.tp_adsdk.tpadmobsdk.ad.splash;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseBannerView;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_SPLASH;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public class ADMobGenSplashView extends BaseBannerView<ADMobGenSplashAdListener, ADMobGenSplashView> {
    private static double height = 0.75D;
    private int realHeight;

    public ADMobGenSplashView(Activity var1) {
        this(var1, height);
    }

    public ADMobGenSplashView(Activity var1, double var2) {
        super(var1, TYPE_SPLASH);
        if (var2 >= height && var2 <= 1.0D) {
            height = var2;
        }

        this.realHeight = (int)((double)this.getResources().getDisplayMetrics().heightPixels * height);
        RelativeLayout.LayoutParams var4 = new RelativeLayout.LayoutParams(-1, this.realHeight);
        this.setLayoutParams(var4);
    }

    protected void onMeasure(int var1, int var2) {
        this.setMeasuredDimension(getDefaultSize(0, var1), getDefaultSize(0, var2));
        int var3 = this.getMeasuredWidth();
        var1 = MeasureSpec.makeMeasureSpec(var3, MeasureSpec.EXACTLY);
        var2 = MeasureSpec.makeMeasureSpec(this.realHeight, MeasureSpec.EXACTLY);
        super.onMeasure(var1, var2);
    }

    public void setListener(ADMobGenSplashAdListener var1) {
        super.setListener(var1);
    }

    public  ADMobGenSplashView getParam() {
        return this;
    }

    public ADMobGenSplashAdListener getListener() {
        return (ADMobGenSplashAdListener)super.getListener();
    }

    public void loadAd() {
        super.loadAd();
    }

    public  boolean isDestroy() {
        return super.isDestroy();
    }

    public  Activity getActivity() {
        return super.getActivity();
    }

    public void destroy() {
        this.setListener((ADMobGenSplashAdListener)null);
        super.destroy();
        this.removeAllViews();
    }
}

