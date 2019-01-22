package com.tianpeng.tianpengaddemo;

import android.app.Application;

import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;


/**
 * @author : ciba
 * @date : 2018/7/11
 * @description : replace your description
 */

public class MyApplication extends Application {

    private String APP_ID = "123";
    private boolean debug = false;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2018/10/22 修改为自己的appId 测试id = 123
        // TODO: debug参数 默认传入false.
        TPADMobSDK.instance().initSdk(getApplicationContext(), APP_ID, debug);

    }

}
