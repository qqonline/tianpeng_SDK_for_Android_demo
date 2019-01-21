package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class DisplayTool {
    private int wDip;
    private int hDip;
    private int wScreen;
    private int hScreen;
    public Context con;
    private int mDensityDpi;

    public int getwDip() {
        return this.wDip;
    }

    public int gethDip() {
        return this.hDip;
    }

    public int getwScreen() {
        return this.wScreen;
    }

    public int gethScreen() {
        return this.hScreen;
    }

    public int getDensityDpi() {
        return this.mDensityDpi;
    }

    public DisplayTool(Context var1) {
        this.con = var1;
        DisplayMetrics var2 = var1.getResources().getDisplayMetrics();
        this.wScreen = var2.widthPixels;
        this.hScreen = var2.heightPixels;
        this.wDip = this.px2dip((double)this.wScreen);
        this.hDip = this.px2dip((double)this.hScreen);
        this.mDensityDpi = var2.densityDpi;
    }

    public int dip2px(double var1) {
        float var3 = this.con.getResources().getDisplayMetrics().density;
        return (int)(var1 * (double)var3 + 0.5D);
    }

    public int px2dip(double var1) {
        float var3 = this.con.getResources().getDisplayMetrics().density;
        return (int)(var1 / (double)var3 + 0.5D);
    }

    public int px2sp(float var1) {
        float var2 = this.con.getResources().getDisplayMetrics().scaledDensity;
        return (int)(var1 / var2 + 0.5F);
    }

    public int sp2px(float var1) {
        float var2 = this.con.getResources().getDisplayMetrics().scaledDensity;
        return (int)(var1 * var2 + 0.5F);
    }
}

