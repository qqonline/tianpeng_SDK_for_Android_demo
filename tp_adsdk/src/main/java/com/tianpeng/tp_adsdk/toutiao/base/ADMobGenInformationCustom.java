package com.tianpeng.tp_adsdk.toutiao.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.ADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.ADMobGenInformationEntity;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.information.ADMobGenInformationCustomBase;

import java.util.List;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationCustom extends ADMobGenInformationCustomBase<TTFeedAd> {
    private FrameLayout frameLayout;

    public ADMobGenInformationCustom(ADMobGenInformation var1, boolean var2) {
        super(var1.getApplicationContext(), var2);
    }

    public void addView() {
        this.frameLayout = new FrameLayout(this.getContext());
        this.frameLayout.setBackgroundColor(0);
        RelativeLayout.LayoutParams var1 = new RelativeLayout.LayoutParams(-1, -2);
        var1.addRule(6, this.getWebView().getId());
        var1.addRule(8, this.getWebView().getId());
        this.frameLayout.setLayoutParams(var1);
        this.addView(this.frameLayout);
    }

    public void setData(TTFeedAd var1) {
        super.setData(var1);
    }

    public void callExposure() {
    }

    @Override
    public void onExposure(TTFeedAd var1) {

    }

    public ADMobGenInformationEntity getInformationEntity(TTFeedAd var1) {
        if (var1 == null) {
            return null;
        } else {
            String var2 = var1.getTitle();
            String var3 = var1.getDescription();
            int var4 = var1.getInteractionType();
            List var5 = var1.getImageList();
            return var5 != null && !var5.isEmpty() ? new ADMobGenInformationEntity(!TextUtils.isEmpty(var2) && !"null".equals(var2) ? var2 : "", !TextUtils.isEmpty(var3) && !"null".equals(var3) ? var3 : "", ((TTImage)var5.get(0)).getImageUrl(), var4 == 4) : null;
        }
    }

    protected String getLogTag() {
        return "Information_toutiao";
    }

    @Override
    public boolean clickAdImp(TTFeedAd var1, View var2) {
        return false;
    }

    public FrameLayout getCustomClickView() {
        return this.frameLayout;
    }
}

