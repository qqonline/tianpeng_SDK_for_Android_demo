package com.tianpeng.tp_adsdk.admobhttp;

/**
 * Created by YuHong on 2019/1/4 0004.
 */

import android.content.Context;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.CookieStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;

public class AsyncHttpClient {
    private static final String VERSION = "1.4.1";
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static final int DEFAULT_MAX_RETRIES = 5;
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String ENCODING_GZIP = "gzip";
    private static int maxConnections = 10;
    private static int socketTimeout = 10000;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private ThreadPoolExecutor threadPool;
    private final Map<Context, List<WeakReference<Future<?>>>> requestMap;
    private final Map<String, String> clientHeaderMap;

    public AsyncHttpClient() {
        BasicHttpParams var1 = new BasicHttpParams();
        ConnManagerParams.setTimeout(var1, (long)socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute(var1, new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections(var1, 10);
        HttpConnectionParams.setSoTimeout(var1, socketTimeout);
        HttpConnectionParams.setConnectionTimeout(var1, socketTimeout);
        HttpConnectionParams.setTcpNoDelay(var1, true);
        HttpConnectionParams.setSocketBufferSize(var1, 8192);
        HttpProtocolParams.setVersion(var1, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(var1, String.format("android-async-http/%s (http://loopj.com/android-async-http)", "1.4.1"));
        SchemeRegistry var2 = new SchemeRegistry();
        var2.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        var2.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager var3 = new ThreadSafeClientConnManager(var1, var2);
        this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        this.httpClient = new DefaultHttpClient(var3, var1);
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest var1, HttpContext var2) {
                if (!var1.containsHeader("Accept")) {
                    var1.addHeader("Accept", "application/json");
                    var1.addHeader("Content-Type", "application/json");
                }

                Iterator var3 = AsyncHttpClient.this.clientHeaderMap.keySet().iterator();

                while(var3.hasNext()) {
                    String var4 = (String)var3.next();
                    var1.addHeader(var4, (String)AsyncHttpClient.this.clientHeaderMap.get(var4));
                }

            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse var1, HttpContext var2) {
                HttpEntity var3 = var1.getEntity();
                if (var3 != null) {
                    Header var4 = var3.getContentEncoding();
                    if (var4 != null) {
                        HeaderElement[] var5 = var4.getElements();
                        int var6 = var5.length;

                        for(int var7 = 0; var7 < var6; ++var7) {
                            HeaderElement var8 = var5[var7];
                            if (var8.getName().equalsIgnoreCase("gzip")) {
                                var1.setEntity(new AsyncHttpClient.InflatingEntity(var1.getEntity()));
                                break;
                            }
                        }
                    }

                }
            }
        });
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(5));
        this.threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        this.requestMap = new WeakHashMap();
        this.clientHeaderMap = new HashMap();
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public void setCookieStore(CookieStore var1) {
        this.httpContext.setAttribute("http.cookie-store", var1);
    }

    public void setThreadPool(ThreadPoolExecutor var1) {
        this.threadPool = var1;
    }

    public void setUserAgent(String var1) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), var1);
    }

    public void setTimeout(int var1) {
        HttpParams var2 = this.httpClient.getParams();
        ConnManagerParams.setTimeout(var2, (long)var1);
        HttpConnectionParams.setSoTimeout(var2, var1);
        HttpConnectionParams.setConnectionTimeout(var2, var1);
    }

    public void setSSLSocketFactory(SSLSocketFactory var1) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", var1, 443));
    }

    public void addHeader(String var1, String var2) {
        this.clientHeaderMap.put(var1, var2);
    }

    public void setBasicAuth(String var1, String var2) {
        AuthScope var3 = AuthScope.ANY;
        this.setBasicAuth(var1, var2, var3);
    }

    public void setBasicAuth(String var1, String var2, AuthScope var3) {
        UsernamePasswordCredentials var4 = new UsernamePasswordCredentials(var1, var2);
        this.httpClient.getCredentialsProvider().setCredentials(var3, var4);
    }

    public void cancelRequests(Context var1, boolean var2) {
        List var3 = (List)this.requestMap.get(var1);
        if (var3 != null) {
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
                WeakReference var5 = (WeakReference)var4.next();
                Future var6 = (Future)var5.get();
                if (var6 != null) {
                    var6.cancel(var2);
                }
            }
        }

        this.requestMap.remove(var1);
    }

    public void get(String var1, AsyncHttpResponseHandler var2) {
        this.get((Context)null, var1, (RequestParams)null, var2);
    }

    public void get(String var1, RequestParams var2, AsyncHttpResponseHandler var3) {
        this.get((Context)null, var1, var2, var3);
    }

    public void get(Context var1, String var2, AsyncHttpResponseHandler var3) {
        this.get(var1, var2, (RequestParams)null, var3);
    }

    public void get(Context var1, String var2, RequestParams var3, AsyncHttpResponseHandler var4) {
        this.sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(var2, var3)), (String)null, var4, var1);
    }

    public void get(Context var1, String var2, Header[] var3, RequestParams var4, AsyncHttpResponseHandler var5) {
        HttpGet var6 = new HttpGet(getUrlWithQueryString(var2, var4));
        if (var3 != null) {
            var6.setHeaders(var3);
        }

        this.sendRequest(this.httpClient, this.httpContext, var6, (String)null, var5, var1);
    }

    public void post(String var1, AsyncHttpResponseHandler var2) {
        this.post((Context)null, var1, (RequestParams)null, var2);
    }

    public void post(String var1, RequestParams var2, AsyncHttpResponseHandler var3) {
        this.post((Context)null, var1, var2, var3);
    }
    public void post(Context var1, String var2, RequestParams var3, AsyncHttpResponseHandler var4) {
        this.post(var1, var2, this.paramsToEntity(var3), "application/json", var4);
    }
    public void post(Context var1, String var2, String var3, AsyncHttpResponseHandler var4) throws UnsupportedEncodingException {
        this.post(var1, var2, this.paramsToEntity2(var3), "application/json", var4);
    }

    public void post(Context var1, String var2, HttpEntity var3, String var4, AsyncHttpResponseHandler var5) {
        this.sendRequest(this.httpClient, this.httpContext, this.addEntityToRequestBase(new HttpPost(var2), var3), var4, var5, var1);
    }
//    public void post2(Context var1, String var2, HttpEntity var3, String var4, AsyncHttpResponseHandler var5) {
//        this.sendRequest(this.httpClient, this.httpContext, var3, var4, var5, var1,var2);
//    }

    public void post(Context var1, String var2, Header[] var3, RequestParams var4, String var5, AsyncHttpResponseHandler var6) {
        HttpPost var7 = new HttpPost(var2);
        if (var4 != null) {
            var7.setEntity(this.paramsToEntity(var4));
        }

        if (var3 != null) {
            var7.setHeaders(var3);
        }

        this.sendRequest(this.httpClient, this.httpContext, var7, var5, var6, var1);
    }

    public void post(Context var1, String var2, Header[] var3, HttpEntity var4, String var5, AsyncHttpResponseHandler var6) {
        HttpEntityEnclosingRequestBase var7 = this.addEntityToRequestBase(new HttpPost(var2), var4);
        if (var3 != null) {
            var7.setHeaders(var3);
        }

        this.sendRequest(this.httpClient, this.httpContext, var7, var5, var6, var1);
    }

    public void put(String var1, AsyncHttpResponseHandler var2) {
        this.put((Context)null, var1, (RequestParams)null, var2);
    }

    public void put(String var1, RequestParams var2, AsyncHttpResponseHandler var3) {
        this.put((Context)null, var1, var2, var3);
    }

    public void put(Context var1, String var2, RequestParams var3, AsyncHttpResponseHandler var4) {
        this.put(var1, var2, this.paramsToEntity(var3), (String)null, var4);
    }

    public void put(Context var1, String var2, HttpEntity var3, String var4, AsyncHttpResponseHandler var5) {
        this.sendRequest(this.httpClient, this.httpContext, this.addEntityToRequestBase(new HttpPut(var2), var3), var4, var5, var1);
    }

    public void put(Context var1, String var2, Header[] var3, HttpEntity var4, String var5, AsyncHttpResponseHandler var6) {
        HttpEntityEnclosingRequestBase var7 = this.addEntityToRequestBase(new HttpPut(var2), var4);
        if (var3 != null) {
            var7.setHeaders(var3);
        }

        this.sendRequest(this.httpClient, this.httpContext, var7, var5, var6, var1);
    }

    public void delete(String var1, AsyncHttpResponseHandler var2) {
        this.delete((Context)null, var1, var2);
    }

    public void delete(Context var1, String var2, AsyncHttpResponseHandler var3) {
        HttpDelete var4 = new HttpDelete(var2);
        this.sendRequest(this.httpClient, this.httpContext, var4, (String)null, var3, var1);
    }

    public void delete(Context var1, String var2, Header[] var3, AsyncHttpResponseHandler var4) {
        HttpDelete var5 = new HttpDelete(var2);
        if (var3 != null) {
            var5.setHeaders(var3);
        }

        this.sendRequest(this.httpClient, this.httpContext, var5, (String)null, var4, var1);
    }

    protected void sendRequest(DefaultHttpClient var1, HttpContext var2, HttpUriRequest var3, String var4, AsyncHttpResponseHandler var5, Context var6) {
        if (var4 != null) {
            var3.addHeader("Content-Type", var4);
        }

        Future var7 = this.threadPool.submit(new AsyncHttpRequest(var1, var2, var3, var5));
        if (var6 != null) {
            Object var8 = (List)this.requestMap.get(var6);
            if (var8 == null) {
                var8 = new LinkedList();
                this.requestMap.put(var6, (List<WeakReference<Future<?>>>) var8);
            }

            ((List)var8).add(new WeakReference(var7));
        }

    }

    public static String getUrlWithQueryString(String var0, RequestParams var1) {
        if (var1 != null) {
            String var2 = var1.getParamString();
            if (var0.indexOf("?") == -1) {
                var0 = var0 + "?" + var2;
            } else {
                var0 = var0 + "&" + var2;
            }
        }

        return var0;
    }

    private HttpEntity paramsToEntity(RequestParams var1) {
        HttpEntity var2 = null;
        if (var1 != null) {
            var2 = var1.getEntity();
        }

        return var2;
    }
    private StringEntity paramsToEntity2(String json) throws UnsupportedEncodingException {
        StringEntity var2 = null;
        if (json != null) {
            var2 = new StringEntity(json);
        }

        return var2;
    }


    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase var1, HttpEntity var2) {
        if (var2 != null) {
            var1.setEntity(var2);
        }

        return var1;
    }

    private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity var1) {
            super(var1);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1L;
        }
    }
}
