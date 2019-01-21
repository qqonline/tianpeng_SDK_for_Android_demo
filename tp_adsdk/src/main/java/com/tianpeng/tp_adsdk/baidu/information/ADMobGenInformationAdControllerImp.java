package com.tianpeng.tp_adsdk.baidu.information;

import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.feeds.RequestParameters;
import com.tianpeng.tp_adsdk.baidu.listener.InformationLoadListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdController;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ADMobGenInformationAdControllerImp implements IADMobGenInformationAdController {
    private Map<String,BaiduNative> map = new ConcurrentHashMap();

    public ADMobGenInformationAdControllerImp() {
    }

    public boolean loadAd(IADMobGenAd var1, IADMobGenConfiguration var2, IADMobGenInformationAdCallBack var3) {
        if (var1 != null && !var1.isDestroy() && var2 != null && var3 != null) {
            BaiduNative var4 = new BaiduNative(var1.getActivity(), var2.getNativeId(), new InformationLoadListener(var3, this.map,var1,var2));
            RequestParameters var5 = (new RequestParameters.Builder()).setWidth(1).build();
            var4.makeRequest(var5);
            this.map.put(var3.hashCode() + "", var4);
            return true;
        } else {
            return false;
        }
    }

    public void destroyAd() {
        try {
            BaiduNative var3;
            for(Iterator var1 = this.map.entrySet().iterator(); var1.hasNext(); var3 = null) {
                Map.Entry var2 = (Map.Entry)var1.next();
                var3 = (BaiduNative)var2.getValue();
                var3.destroy();
            }

            this.map.clear();
        } catch (Exception var4) {
            ;
        }

    }
}
