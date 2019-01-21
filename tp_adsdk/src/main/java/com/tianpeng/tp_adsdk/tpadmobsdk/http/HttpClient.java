package com.tianpeng.tp_adsdk.tpadmobsdk.http;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpClient;
import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.admobhttp.RequestParams;
import com.tianpeng.tp_adsdk.mine.business.OperationBean;
import com.tianpeng.tp_adsdk.mine.config.DataUtil;
import com.tianpeng.tp_adsdk.mine.http.Constant;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class HttpClient {
    private static HttpClient client;
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private HttpClient() {
        this.asyncHttpClient.setTimeout(10000);
    }
    public static HttpClient getInstance() {
        if (client == null) {
            Class clientClass = HttpClient.class;
            synchronized(HttpClient.class) {
                if (client == null) {
                    client = new HttpClient();
                }
            }
        }

        return client;
    }

    public void requestParam(String var1, RequestParams var2, AsyncHttpResponseHandler var3) {
        if (var2 == null) {
            var2 = new RequestParams();
        }

        this.asyncHttpClient.get(TPADMobSDK.instance().getAdMobSdkContext(), var1, var2, var3);
    }
    public void requestParam(String var1, String var2, AsyncHttpResponseHandler var3) throws UnsupportedEncodingException {
        if (var2 == null) {
            var2 = "";
        }

        this.asyncHttpClient.post(TPADMobSDK.instance().getAdMobSdkContext(), var1, var2, var3);
    }

    public void postLog(String var1, Context var2, boolean var3) {
        if (var3) {
            this.postLog(var1, var2, "com.tianpeng.tp_adsdk");
        } else {
            this.postLog(var1, var2, "cn.admob.loganalysis");
        }

    }

    private void postLog(String var1, Context var2, String var3) {
        try {
            OperationBean var4 = new OperationBean();
            var4.setOperationType("CRASH");
            var4.setPackageName(var3);
            var4.setMachineId(DataUtil.getMachineId(var2));
            var4.setScheme(var1);
            ArrayList var5 = new ArrayList();
            var5.add(var4);
            String var6 = JSONObject.toJSONString(var5);
            var5.clear();
            ByteArrayEntity var7 = null;
            var7 = new ByteArrayEntity(var6.getBytes("UTF-8"));
            var7.setContentType(new BasicHeader("Content-Type", "application/json"));
            AsyncHttpResponseHandler var8 = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                }
            };
            AsyncHttpClient var9 = new AsyncHttpClient();
            var9.setTimeout(5000);
            var9.post(var2, new Constant().crashDataUrl, var7, "application/json", var8);
        } catch (Exception var10) {
            ;
        }

    }
}
