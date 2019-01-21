package com.tianpeng.tp_adsdk.baidu.base;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.baidu.mobad.feeds.NativeResponse;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.ADMobGenInformationEntity;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.information.ADMobGenInformationCustomBase;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationCustom extends ADMobGenInformationCustomBase<NativeResponse> {
    public ADMobGenInformationCustom(Context var1, boolean var2) {
        super(var1, var2);
    }

    public void onExposure(NativeResponse var1) {
        if (var1 != null && this.informationAdView != null) {
            var1.recordImpression(this.informationAdView);
        }

    }

    protected String getLogTag() {
        return "Information_BaiDu";
    }


    public ADMobGenInformationEntity getInformationEntity(NativeResponse var1) {
        return var1 != null ? new ADMobGenInformationEntity(var1.getTitle(), var1.getDesc(), var1.getIconUrl(), var1.isDownloadApp()) : null;
    }

    public boolean clickAdImp(NativeResponse var1, View var2) {
        boolean var3 = false;
        if (var1 != null && this.informationAdView != null) {
            if (var1.isDownloadApp()) {
                if (TPADMobSDK.instance().isWifi()) {
                    Toast.makeText(this.informationAdView.getContext(), "开始下载", Toast.LENGTH_SHORT).show();
                }
            } else {
                var3 = true;
            }

            var1.handleClick(this.informationAdView);
        }

        return var3;
    }
}
