package com.tianpeng.tp_adsdk.youdao;

import com.tianpeng.tp_adsdk.tpadmobsdk.common.ISdkInit;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.youdao.sdk.common.YoudaoSDK;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class SdkInitImp implements ISdkInit {
    public SdkInitImp() {
    }

    public void init(IADMobGenConfiguration var1) {
        if (var1 != null) {
            YoudaoSDK.init(TPADMobSDK.instance().getAdMobSdkContext());
        }
    }

    public String getPlatform() {
        return "youdao";
    }
}
