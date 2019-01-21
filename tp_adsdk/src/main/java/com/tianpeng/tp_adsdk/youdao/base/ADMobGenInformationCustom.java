package com.tianpeng.tp_adsdk.youdao.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tianpeng.tp_adsdk.tpadmobsdk.entity.ADMobGenInformationEntity;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.information.ADMobGenInformationCustomBase;
import com.youdao.sdk.nativeads.NativeResponse;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenInformationCustom extends ADMobGenInformationCustomBase<NativeResponse> {
    public ADMobGenInformationCustom(Context var1, boolean var2) {
        super(var1, var2);
    }


    protected String getLogTag() {
        return "Information_youdao";
    }


    public void onExposure(NativeResponse var1) {
        if (var1 != null && this.informationAdView != null) {
            var1.recordImpression(this.informationAdView);
        }

    }

    public ADMobGenInformationEntity getInformationEntity(NativeResponse var1) {
        if (var1 != null) {
            String var2 = var1.getTitle();
            String var3 = var1.isDownloadApk() ? var1.getDownloadTitle() : var1.getText();
            return new ADMobGenInformationEntity(!TextUtils.isEmpty(var2) && !"null".equals(var2) ? var2 : "", !TextUtils.isEmpty(var3) && !"null".equals(var3) ? var3 : "", var1.getMainImageUrl(), var1.isDownloadApk());
        } else {
            return null;
        }
    }

    public boolean clickAdImp(NativeResponse var1, View var2) {
        boolean var3 = false;
        if (var1 != null && this.informationAdView != null) {
            if (var1.isDownloadApk()) {
                Toast.makeText(this.informationAdView.getContext(), "开始下载", Toast.LENGTH_SHORT).show();
            } else {
                var3 = true;
            }

            var1.handleClick(this.informationAdView);
        }

        return var3;
    }
}

