package com.tianpeng.tp_adsdk.tpadmobsdk.ad.banner;

import android.app.Activity;
import android.graphics.Rect;
import android.view.ViewTreeObserver;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseBannerView;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_BANNER;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public final class ADMobGenBannerView extends BaseBannerView<ADMobGenBannerAdListener, ADMobGenBannerView> implements ViewTreeObserver.OnScrollChangedListener {
    private Rect rect = new Rect();
    private long d;
    private int height;
    private int width;

    public ADMobGenBannerView(Activity var1) {
        super(var1, TYPE_BANNER);
        this.setMinimumHeight((int)(this.getResources().getDisplayMetrics().density * 50.0F));
    }

    public void setADSize(int var1, int var2) {
        if (var1 >= 0 && var2 >= 0) {
            this.height = var2;
            this.width = var1;
        }
    }

    public int getTTheight() {
        return this.height;
    }

    public int getTTwidth() {
        return this.width;
    }

    public void setListener(ADMobGenBannerAdListener var1) {
        super.setListener(var1);
    }

    public final ADMobGenBannerView getParam() {
        return this;
    }

    public ADMobGenBannerAdListener getListener() {
        return (ADMobGenBannerAdListener)super.getListener();
    }

    protected void onMeasure(int var1, int var2) {
        this.setMeasuredDimension(getDefaultSize(0, var1), getDefaultSize(0, var2));
        int var3 = this.getMeasuredWidth();
        var1 = MeasureSpec.makeMeasureSpec(var3, MeasureSpec.EXACTLY);//1073741824
        super.onMeasure(var1, var2);
    }

    public void loadAd() {
        this.removeListener();
        this.addListener();
        if (this.getViewTreeObserver() != null) {
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    int var1 = ADMobGenBannerView.this.getHeight();
                    int var2 = ADMobGenBannerView.this.getWidth();
                    if (var1 > 0 && var2 > 0) {
                        ADMobGenBannerView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        ADMobGenBannerView.this.onLocalVisible(false);
                    }

                }
            });
        }

    }

    public final boolean isDestroy() {
        return super.isDestroy();
    }

    public final Activity getActivity() {
        return super.getActivity();
    }

    public void destroy() {
        this.setListener((ADMobGenBannerAdListener)null);
        this.removeListener();
        super.destroy();
    }

    public void onScrollChanged() {
        this.onLocalVisible(true);
    }

    private void addListener() {
        if (this.getViewTreeObserver() != null) {
            this.getViewTreeObserver().addOnScrollChangedListener(this);
        }

    }

    private void removeListener() {
        if (this.getViewTreeObserver() != null) {
            this.getViewTreeObserver().removeOnScrollChangedListener(this);
        }

    }

    private void onLocalVisible(boolean var1) {
        if (!this.isDestroy()) {
            long var2 = System.currentTimeMillis();
            if (var2 - this.d >= 100L || !var1) {
                this.d = var2;
                rect.set(0, 0, 0, 0);
                this.getLocalVisibleRect(rect);
                int var4 = this.getMeasuredWidth();
                int var5 = this.getMeasuredHeight();
                if (var4 > 0 && var5 > 0) {
                    int var6 = rect.right - rect.left;
                    int var7 = rect.bottom - rect.top;
                    if (rect.left == 0 && var6 >= var4 / 2 && rect.top == 0 && var7 >= var5 / 2) {
                        this.removeListener();
                        super.loadAd();
                    }

                }
            }
        }
    }
}

