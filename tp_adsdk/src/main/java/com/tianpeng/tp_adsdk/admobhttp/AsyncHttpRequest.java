package com.tianpeng.tp_adsdk.admobhttp;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class AsyncHttpRequest implements Runnable {
    private  AbstractHttpClient client;
    private  HttpContext context;
    private  HttpUriRequest request;
    private  AsyncHttpResponseHandler responseHandler;
    private boolean isBinaryRequest;
    private int executionCount;

    public AsyncHttpRequest(AbstractHttpClient var1, HttpContext var2, HttpUriRequest var3, AsyncHttpResponseHandler var4) {
        this.client = var1;
        this.context = var2;
        this.request = var3;
        this.responseHandler = var4;
        if (var4 instanceof BinaryHttpResponseHandler) {
            this.isBinaryRequest = true;
        }

    }

    public void run() {
        try {
            if (this.responseHandler != null) {
                this.responseHandler.sendStartMessage();
            }

            this.makeRequestWithRetries();
            if (this.responseHandler != null) {
                this.responseHandler.sendFinishMessage();
            }
        } catch (IOException var2) {
            if (this.responseHandler != null) {
                this.responseHandler.sendFinishMessage();
                if (this.isBinaryRequest) {
                    this.responseHandler.sendFailureMessage(var2, (byte[])null);
                } else {
                    this.responseHandler.sendFailureMessage(var2, (String)null);
                }
            }
        }

    }

    private void makeRequest() throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            HttpResponse var1 = this.client.execute(this.request, this.context);
            if (!Thread.currentThread().isInterrupted() && this.responseHandler != null) {
                this.responseHandler.sendResponseMessage(var1);
            }
        }

    }

    private void makeRequestWithRetries() throws ConnectException {
        boolean var1 = true;
        IOException var2 = null;
        HttpRequestRetryHandler var3 = this.client.getHttpRequestRetryHandler();

        while(var1) {
            try {
                this.makeRequest();
                return;
            } catch (UnknownHostException var5) {
                if (this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(var5, "can't resolve host");
                }

                return;
            } catch (SocketException var6) {
                if (this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(var6, "can't resolve host");
                }

                return;
            } catch (SocketTimeoutException var7) {
                if (this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(var7, "socket time out");
                }

                return;
            } catch (IOException var8) {
                var2 = var8;
                var1 = var3.retryRequest(var8, ++this.executionCount, this.context);
            } catch (NullPointerException var9) {
                var2 = new IOException("NPE in HttpClient" + var9.getMessage());
                var1 = var3.retryRequest(var2, ++this.executionCount, this.context);
            }
        }

        ConnectException var4 = new ConnectException();
        var4.initCause(var2);
        throw var4;
    }
}
