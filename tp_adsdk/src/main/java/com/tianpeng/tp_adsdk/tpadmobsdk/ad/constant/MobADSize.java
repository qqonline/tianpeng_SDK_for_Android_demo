package com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class MobADSize {
    public static final int FULL_WIDTH = -1;
    public static final int AUTO_HEIGHT = -2;
    private int height;
    private int wdith;

    public MobADSize(int wdith, int height) {
        this.height = height;
        this.wdith = wdith;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.wdith;
    }
}