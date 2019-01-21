package com.tianpeng.tp_adsdk.admobhttp;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AsyncHttpResponseHandler {
    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;
    private Handler handler;

    public AsyncHttpResponseHandler() {
        if (Looper.myLooper() != null) {
            this.handler = new Handler() {
                public void handleMessage(Message var1) {
                    AsyncHttpResponseHandler.this.handleMessage(var1);
                }
            };
        }

    }

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onSuccess(String var1) {
    }

    public void onSuccess(int var1, String var2) {
        this.onSuccess(var2);
    }

    public void onFailure(Throwable var1) {
    }

    public void onFailure(Throwable var1, String var2) {
        this.onFailure(var1);
    }

    protected void sendSuccessMessage(int var1, String var2) {
        this.sendMessage(this.obtainMessage(0, new Object[]{new Integer(var1), var2}));
    }

    protected void sendFailureMessage(Throwable var1, String var2) {
        this.sendMessage(this.obtainMessage(1, new Object[]{var1, var2}));
    }

    protected void sendFailureMessage(Throwable var1, byte[] var2) {
        this.sendMessage(this.obtainMessage(1, new Object[]{var1, var2}));
    }

    protected void sendStartMessage() {
        this.sendMessage(this.obtainMessage(2, (Object)null));
    }

    protected void sendFinishMessage() {
        this.sendMessage(this.obtainMessage(3, (Object)null));
    }

    protected void handleSuccessMessage(int var1, String var2) {
        this.onSuccess(var1, var2);
    }

    protected void handleFailureMessage(Throwable var1, String var2) {
        this.onFailure(var1, var2);
    }

    protected void handleMessage(Message var1) {
        Object[] var2;
        switch(var1.what) {
            case 0:
                var2 = (Object[])((Object[])var1.obj);
                this.handleSuccessMessage((Integer)var2[0], (String)var2[1]);
                break;
            case 1:
                var2 = (Object[])((Object[])var1.obj);
                this.handleFailureMessage((Throwable)var2[0], (String)var2[1]);
                break;
            case 2:
                this.onStart();
                break;
            case 3:
                this.onFinish();
        }

    }

    protected void sendMessage(Message var1) {
        if (this.handler != null) {
            this.handler.sendMessage(var1);
        } else {
            this.handleMessage(var1);
        }

    }

    protected Message obtainMessage(int var1, Object var2) {
        Message var3 = null;
        if (this.handler != null) {
            var3 = this.handler.obtainMessage(var1, var2);
        } else {
            var3 = new Message();
            var3.what = var1;
            var3.obj = var2;
        }

        return var3;
    }

    void sendResponseMessage(HttpResponse var1) {
        StatusLine var2 = var1.getStatusLine();
        String var3 = null;

        try {
            BufferedHttpEntity var4 = null;
            HttpEntity var5 = var1.getEntity();
            if (var5 != null) {
                var4 = new BufferedHttpEntity(var5);
                var3 = EntityUtils.toString(var4, "UTF-8");
            }
        } catch (IOException var6) {
            this.sendFailureMessage(var6, (String)((String)null));
        }

        if (var2.getStatusCode() >= 300) {
            this.sendFailureMessage(new HttpResponseException(var2.getStatusCode(), var2.getReasonPhrase()), (String)var3);
        } else {
            this.sendSuccessMessage(var2.getStatusCode(), var3);
        }

    }
}
