package com.tianpeng.tp_adsdk.tpadmobsdk.controller;

import com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol.BannerConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol.InformationConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol.InsertConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol.SplashConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_BANNER;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INSERT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_SPLASH;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class ChannelChange {
    public static Config chooseChannel(int var0, IADMobGenConfiguration var1) {
        if (TYPE_BANNER == var0) {
            return new BannerConfig(var1);
        } else if (TYPE_INFORMATION == var0) {
            return new InformationConfig(var1);
        } else if (TYPE_INSERT == var0) {
            return new InsertConfig(var1);
        } else {
            return TYPE_SPLASH == var0 ? new SplashConfig(var1) : null;
        }
    }
}
