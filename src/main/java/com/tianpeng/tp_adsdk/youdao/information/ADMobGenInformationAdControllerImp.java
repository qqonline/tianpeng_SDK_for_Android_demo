package com.tianpeng.tp_adsdk.youdao.information;

import android.location.Location;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdController;
import com.tianpeng.tp_adsdk.youdao.listener.YouDaoNativeNetworkInformationListener;
import com.youdao.sdk.nativeads.RequestParameters;
import com.youdao.sdk.nativeads.YouDaoNative;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class ADMobGenInformationAdControllerImp implements IADMobGenInformationAdController {
    private Map<String, YouDaoNative> map = new ConcurrentHashMap();

    public ADMobGenInformationAdControllerImp() {
    }

    public boolean loadAd(IADMobGenAd var1, IADMobGenConfiguration var2, IADMobGenInformationAdCallBack var3) {
        if (var1 != null && !var1.isDestroy() && var2 != null && var3 != null) {
            YouDaoNative var4 = new YouDaoNative(var1.getActivity(), var2.getNativeId(), new YouDaoNativeNetworkInformationListener(this.map, var3,var1,var2));
            RequestParameters var5 = (new RequestParameters.Builder()).location((Location)null).build();
            var4.makeRequest(var5);
            this.map.put(var3.hashCode() + "", var4);
            return true;
        } else {
            return false;
        }
    }

    public void destroyAd() {
        try {
            YouDaoNative var3;
            for(Iterator var1 = this.map.entrySet().iterator(); var1.hasNext(); var3 = null) {
                Map.Entry var2 = (Map.Entry)var1.next();
                var3 = (YouDaoNative)var2.getValue();
                var3.destroy();
            }

            this.map.clear();
        } catch (Exception var4) {
            ;
        }

    }
}

