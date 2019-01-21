package com.tianpeng.tp_adsdk.tpadmobsdk.ad.insert;

import android.app.Activity;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseBannerView;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INSERT;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public class ADMobGenInsertView extends BaseBannerView<ADMobGenInsertitailAdListener, ADMobGenInsertView> {

    public ADMobGenInsertView(Activity var1) {
        super(var1, TYPE_INSERT);
    }

    public void setListener(ADMobGenInsertitailAdListener var1) {
        super.setListener(var1);
    }

    public ADMobGenInsertView getParam() {
        return this;
    }

    public ADMobGenInsertitailAdListener getListener() {
        return super.getListener();
    }

    public void loadAd() {
        super.loadAd();
    }
    public void show() {
        super.loadAd();
    }

    public  boolean isDestroy() {
        return super.isDestroy();
    }

    public  Activity getActivity() {
        return super.getActivity();
    }

    public void destroy() {
        this.setListener(null);
        super.destroy();
        this.removeAllViews();
    }
}

