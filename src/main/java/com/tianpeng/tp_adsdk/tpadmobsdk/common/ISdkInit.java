package com.tianpeng.tp_adsdk.tpadmobsdk.common;

import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public interface ISdkInit {
    void init(IADMobGenConfiguration var1);

    String getPlatform();
}
