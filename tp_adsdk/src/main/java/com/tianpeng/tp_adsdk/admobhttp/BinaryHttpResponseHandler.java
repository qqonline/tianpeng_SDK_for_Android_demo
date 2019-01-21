package com.tianpeng.tp_adsdk.admobhttp;

import android.os.Message;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class BinaryHttpResponseHandler extends AsyncHttpResponseHandler {
    private static String[] mAllowedContentTypes = new String[]{"image/jpeg", "image/png"};

    public BinaryHttpResponseHandler() {
    }

    public BinaryHttpResponseHandler(String[] var1) {
        this();
        mAllowedContentTypes = var1;
    }

    public void onSuccess(byte[] var1) {
    }

    public void onSuccess(int var1, byte[] var2) {
        this.onSuccess(var2);
    }

    public void onFailure(Throwable var1, byte[] var2) {
        this.onFailure(var1);
    }

    protected void sendSuccessMessage(int var1, byte[] var2) {
        this.sendMessage(this.obtainMessage(0, new Object[]{var1, var2}));
    }

    protected void sendFailureMessage(Throwable var1, byte[] var2) {
        this.sendMessage(this.obtainMessage(1, new Object[]{var1, var2}));
    }

    protected void handleSuccessMessage(int var1, byte[] var2) {
        this.onSuccess(var1, var2);
    }

    protected void handleFailureMessage(Throwable var1, byte[] var2) {
        this.onFailure(var1, var2);
    }

    protected void handleMessage(Message var1) {
        Object[] var2;
        switch(var1.what) {
            case 0:
                var2 = (Object[])((Object[])var1.obj);
                this.handleSuccessMessage((Integer)var2[0], (byte[])((byte[])var2[1]));
                break;
            case 1:
                var2 = (Object[])((Object[])var1.obj);
                this.handleFailureMessage((Throwable)var2[0], (byte[])((byte[])var2[1]));
                break;
            default:
                super.handleMessage(var1);
        }

    }

    void sendResponseMessage(HttpResponse var1) {
        StatusLine var2 = var1.getStatusLine();
        Header[] var3 = var1.getHeaders("Content-Type");
        byte[] var4 = null;
        if (var3.length != 1) {
            this.sendFailureMessage(new HttpResponseException(var2.getStatusCode(), "None, or more than one, Content-Type Header found!"), var4);
        } else {
            Header var5 = var3[0];
            boolean var6 = false;
            String[] var7 = mAllowedContentTypes;
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String var10 = var7[var9];
                if (var10.equals(var5.getValue())) {
                    var6 = true;
                }
            }

            if (!var6) {
                this.sendFailureMessage(new HttpResponseException(var2.getStatusCode(), "Content-Type not allowed!"), var4);
            } else {
                try {
                    BufferedHttpEntity var12 = null;
                    HttpEntity var13 = var1.getEntity();
                    if (var13 != null) {
                        var12 = new BufferedHttpEntity(var13);
                    }

                    var4 = EntityUtils.toByteArray(var12);
                } catch (IOException var11) {
                    this.sendFailureMessage(var11, (byte[])null);
                }

                if (var2.getStatusCode() >= 300) {
                    this.sendFailureMessage(new HttpResponseException(var2.getStatusCode(), var2.getReasonPhrase()), var4);
                } else {
                    this.sendSuccessMessage(var2.getStatusCode(), var4);
                }

            }
        }
    }
}