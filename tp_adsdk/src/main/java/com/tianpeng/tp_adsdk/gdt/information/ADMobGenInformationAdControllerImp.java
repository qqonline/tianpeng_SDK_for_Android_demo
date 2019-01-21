package com.tianpeng.tp_adsdk.gdt.information;

import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.tianpeng.tp_adsdk.gdt.listener.GDTInformationADListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdController;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationAdControllerImp implements IADMobGenInformationAdController {

    public boolean loadAd(IADMobGenAd var1, IADMobGenConfiguration var2, IADMobGenInformationAdCallBack var3) {
        if (var1 != null && !var1.isDestroy() && var2 != null) {
            NativeExpressAD var4 = new NativeExpressAD(var1.getActivity(), new ADSize(-1, -2), var2.getAppId(), var2.getNativeId(), new GDTInformationADListener(var3, var2,var1));
            var4.loadAD(1);
            return true;
        } else {
            return false;
        }
    }

    public void destroyAd() {
    }
}

