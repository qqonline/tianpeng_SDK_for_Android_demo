package com.tianpeng.tp_adsdk.admobhttp;

import android.os.SystemClock;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;

import javax.net.ssl.SSLHandshakeException;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
class RetryHandler implements HttpRequestRetryHandler {
    private static final int RETRY_SLEEP_TIME_MILLIS = 1500;
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet();
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet();
    private  int maxRetries;

    public RetryHandler(int var1) {
        this.maxRetries = var1;
    }

    public boolean retryRequest(IOException var1, int var2, HttpContext var3) {
        boolean var4 = true;
        Boolean var5 = (Boolean)var3.getAttribute("http.request_sent");
        boolean var6 = var5 != null && var5;
        if (var2 > this.maxRetries) {
            var4 = false;
        } else if (this.isInList(exceptionBlacklist, var1)) {
            var4 = false;
        } else if (this.isInList(exceptionWhitelist, var1)) {
            var4 = true;
        } else if (!var6) {
            var4 = true;
        }

        if (var4) {
            HttpUriRequest var7 = (HttpUriRequest)var3.getAttribute("http.request");
            String var8 = var7.getMethod();
            var4 = !var8.equals("POST");
        }

        if (var4) {
            SystemClock.sleep(1500L);
        } else {
            var1.printStackTrace();
        }

        return var4;
    }

    protected boolean isInList(HashSet<Class<?>> var1, Throwable var2) {
        Iterator var3 = var1.iterator();

        do {
            if (!var3.hasNext()) {
                return false;
            }
        } while(!((Class)var3.next()).isInstance(var2));

        return true;
    }

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLHandshakeException.class);
    }
}

