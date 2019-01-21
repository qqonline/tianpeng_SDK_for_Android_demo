package com.tianpeng.tp_adsdk.tpadmobsdk.http;

import android.support.annotation.Nullable;

import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.admobhttp.RequestParams;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.SDKUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.ADMobGenCommon;


/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class RequestParam {
    public static void request(boolean var0, AsyncHttpResponseHandler var1) {
        RequestParams var2 = new RequestParams();
        var2.put("method", "sdkinfo");
        var2.put("appid", TPADMobSDK.instance().getAppId());
        var2.put("os", "1");
        var2.put("debug", var0 ? "1" : "0");
        var2.put("v", "2");
        var2.put("device", SDKUtil.getInstance().getAndroidId());
        var2.put("packageName", SDKUtil.getInstance().getPackageName());
        var2.put("version", ADMobGenCommon.getSdkVersion());
        HttpClient.getInstance().requestParam(BaseUrl.getBaseUrl()+"/"+TPADMobSDK.instance().getAppId(), var2, var1);
//        try {
//            HttpClient.getInstance().requestParam(BaseUrl.getBaseUrl(),  JSON.toJSONString(var2), var1);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    public static void request(@Nullable String var0,@Nullable String var1,@Nullable int var2,@Nullable String var3,@Nullable String var4) {
    }
}
