package com.tianpeng.tp_adsdk.baidu.view;

import android.view.View;

import com.baidu.mobad.feeds.NativeResponse;
import com.tianpeng.tp_adsdk.baidu.base.ADMobGenInformationCustom;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationView implements IADMobGenInformationView {
    private ADMobGenInformationCustom adMobGenInformationCustom;

    public ADMobGenInformationView(NativeResponse var1, IADMobGenInformationAdCallBack var2) {
        if (var2 != null && var2.getAdMobGenInformation() != null && !var2.getAdMobGenInformation().isDestroy() && var2.getIadMobGenInformation() != null && !var2.getIadMobGenInformation().isDestroy()) {
            this.adMobGenInformationCustom = new ADMobGenInformationCustom(var2.getAdMobGenInformation().getApplicationContext(), var2.isWeb());
            this.adMobGenInformationCustom.setAdMobGenAd(var2.getAdMobGenInformation().getParam());
            this.adMobGenInformationCustom.setADMobGenInformationAdCallBack(var2);
            this.adMobGenInformationCustom.setData(var1);
        }

    }

    public View getInformationAdView() {
        return this.adMobGenInformationCustom;
    }

    public void render() {
        if (this.isCustomNotNull()) {
            this.adMobGenInformationCustom.render();
        }

    }

    public void onExposured() {
        if (this.isCustomNotNull()) {
            this.adMobGenInformationCustom.exposure();
        }

    }

    public void destroy() {
        if (this.adMobGenInformationCustom != null) {
            this.adMobGenInformationCustom.destroy();
        }

    }

    public String getSdkName() {
        return "baidu";
    }

    public boolean isCustomNotNull() {
        return this.adMobGenInformationCustom != null;
    }
}
