package com.tianpeng.tp_adsdk.admobhttp;

import android.content.Context;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public abstract class SyncHttpClient extends AsyncHttpClient {
    private int responseCode;
    private String result;
    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        void sendResponseMessage(HttpResponse var1) {
            SyncHttpClient.this.responseCode = var1.getStatusLine().getStatusCode();
            super.sendResponseMessage(var1);
        }

        protected void sendMessage(Message var1) {
            this.handleMessage(var1);
        }

        public void onSuccess(String var1) {
            SyncHttpClient.this.result = var1;
        }

        public void onFailure(Throwable var1, String var2) {
            SyncHttpClient.this.result = SyncHttpClient.this.onRequestFailed(var1, var2);
        }
    };

    public SyncHttpClient() {
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    protected void sendRequest(DefaultHttpClient var1, HttpContext var2, HttpUriRequest var3, String var4, AsyncHttpResponseHandler var5, Context var6) {
        if (var4 != null) {
            var3.addHeader("Content-Type", var4);
        }

        (new AsyncHttpRequest(var1, var2, var3, var5)).run();
    }

    public abstract String onRequestFailed(Throwable var1, String var2);

    public void delete(String var1, RequestParams var2, AsyncHttpResponseHandler var3) {
        this.delete(var1, var3);
    }

    public String get(String var1, RequestParams var2) {
        this.get(var1, var2, this.responseHandler);
        return this.result;
    }

    public String get(String var1) {
        this.get(var1, (RequestParams)null, this.responseHandler);
        return this.result;
    }

    public String put(String var1, RequestParams var2) {
        this.put(var1, var2, this.responseHandler);
        return this.result;
    }

    public String put(String var1) {
        this.put(var1, (RequestParams)null, this.responseHandler);
        return this.result;
    }

    public String post(String var1, RequestParams var2) {
        this.post(var1, var2, this.responseHandler);
        return this.result;
    }

    public String post(String var1) {
        this.post(var1, (RequestParams)null, this.responseHandler);
        return this.result;
    }

    public String delete(String var1, RequestParams var2) {
        this.delete(var1, var2, this.responseHandler);
        return this.result;
    }

    public String delete(String var1) {
        this.delete(var1, (RequestParams)null, this.responseHandler);
        return this.result;
    }
}

