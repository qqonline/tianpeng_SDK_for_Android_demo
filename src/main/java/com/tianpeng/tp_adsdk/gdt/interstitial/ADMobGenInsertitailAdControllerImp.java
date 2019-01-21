package com.tianpeng.tp_adsdk.gdt.interstitial;

import com.qq.e.ads.interstitial.InterstitialAD;
import com.tianpeng.tp_adsdk.gdt.listener.GDTInsertADListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInsertitailAdController;

/**
 * Created by YuHong on 2019/1/16 0016.
 */
public class ADMobGenInsertitailAdControllerImp implements IADMobGenInsertitailAdController {
    InterstitialAD ad;
    @Override
    public boolean loadAd(IADMobGenAd iadMobGenAd, IADMobGenConfiguration configuration, boolean var4, ADMobGenInsertitailAdListener listener) {
        if (iadMobGenAd != null && !iadMobGenAd.isDestroy() && configuration != null) {
            ad = new InterstitialAD(iadMobGenAd.getActivity(),  configuration.getAppId(), configuration.getInsertId());
            ad.setADListener(new GDTInsertADListener(listener,ad,configuration,iadMobGenAd));
            ad.loadAD();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroyAd() {
        ad = null;
    }

//    @Override
//    public void showAD() {
//        if(ad!=null)
//        ad.show();
//    }
}
