package com.tianpeng.tp_adsdk.mine.http;

import android.content.Context;

import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpClient;
import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.admobhttp.RequestParams;
import com.tianpeng.tp_adsdk.mine.business.OperationBean;
import com.tianpeng.tp_adsdk.mine.config.DataUtil;
import com.tianpeng.tp_adsdk.mine.utils.JsonUtil;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;


/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class HttpCilent {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void postHttp(Context var0, String var1, HttpEntity var2, AsyncHttpResponseHandler var3) {
        try {
            client.setTimeout(5000);
            client.post(var0, var1, var2, "application/json", var3);
        } catch (Exception var5) {
            ;
        }

    }

    public static void postHttp(String var0, RequestParams var1, AsyncHttpResponseHandler var2) {
        try {
            client.setTimeout(5000);
            client.post(var0, var1, var2);
        } catch (Exception var4) {
            ;
        }

    }
    public static void postHttp(Context context,String var0, RequestParams var1, AsyncHttpResponseHandler var2) {
        try {
            client.setTimeout(5000);
            client.post(context,var0, var1, var2);
        } catch (Exception var4) {
            ;
        }

    }
    public static void postHttp(Context context,String var0, String var1, AsyncHttpResponseHandler var2) {
        try {
            client.setTimeout(5000);
            client.post(context,var0, var1, var2);
        } catch (Exception var4) {
            ;
        }

    }

    public static void postHttp(String var0, AsyncHttpResponseHandler var1) {
        try {
            client.setTimeout(5000);
            client.post(var0, var1);
        } catch (Exception var3) {
            ;
        }

    }

    public static void postHttp(String var0, Context var1, String var2) {
        try {
            OperationBean var3 = new OperationBean();
            var3.setOperationType("CRASH");
            var3.setPackageName(var2);
            var3.setMachineId(DataUtil.getMachineId(var1));
            var3.setScheme(var0);
            ArrayList var4 = new ArrayList();
            var4.add(var3);
            String var5 = JsonUtil.operatelistToString(var4);
            var4.clear();
            ByteArrayEntity var6 = null;
            var6 = new ByteArrayEntity(var5.getBytes("UTF-8"));
            var6.setContentType(new BasicHeader("Content-Type", "application/json"));
            AsyncHttpResponseHandler var7 = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                }
            };
            AsyncHttpClient var8 = new AsyncHttpClient();
            var8.setTimeout(5000);
            var8.post(var1, new Constant().crashDataUrl, var6, "application/json", var7);
        } catch (Exception var9) {
            ;
        }

    }
    public static void uploadInfo(String var0, Context var1, String var2) {
        try {
            OperationBean var3 = new OperationBean();
            var3.setOperationType("CRASH");
            var3.setPackageName(var2);
            var3.setMachineId(DataUtil.getMachineId(var1));
            var3.setScheme(var0);
            ArrayList var4 = new ArrayList();
            var4.add(var3);
            String var5 = JsonUtil.operatelistToString(var4);
            var4.clear();
            ByteArrayEntity var6 = null;
            var6 = new ByteArrayEntity(var5.getBytes("UTF-8"));
            var6.setContentType(new BasicHeader("Content-Type", "application/json"));
            AsyncHttpResponseHandler var7 = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                }
            };
            AsyncHttpClient var8 = new AsyncHttpClient();
            var8.setTimeout(5000);
            var8.post(var1, new Constant().crashDataUrl, var6, "application/json", var7);
        } catch (Exception var9) {
            ;
        }

    }
}
