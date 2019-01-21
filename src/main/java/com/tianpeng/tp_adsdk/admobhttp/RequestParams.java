package com.tianpeng.tp_adsdk.admobhttp;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class RequestParams {
    private static String ENCODING = "UTF-8";
    protected ConcurrentHashMap<String, String> urlParams;
    protected ConcurrentHashMap<String, RequestParams.FileWrapper> fileParams;
    protected ConcurrentHashMap<String, ArrayList<String>> urlParamsWithArray;

    public RequestParams() {
        this.init();
    }

    public RequestParams(Map<String, String> var1) {
        this.init();
        Iterator var2 = var1.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry var3 = (Map.Entry)var2.next();
            this.put((String)var3.getKey(), (String)var3.getValue());
        }

    }

    public RequestParams(String var1, String var2) {
        this.init();
        this.put(var1, var2);
    }

    public RequestParams(Object... var1) {
        this.init();
        int var2 = var1.length;
        if (var2 % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        } else {
            for(int var3 = 0; var3 < var2; var3 += 2) {
                String var4 = String.valueOf(var1[var3]);
                String var5 = String.valueOf(var1[var3 + 1]);
                this.put(var4, var5);
            }

        }
    }

    public void put(String var1, String var2) {
        if (var1 != null && var2 != null) {
            this.urlParams.put(var1, var2);
        }

    }

    public void put(String var1, File var2) throws FileNotFoundException {
        this.put(var1, new FileInputStream(var2), var2.getName());
    }

    public void put(String var1, ArrayList<String> var2) {
        if (var1 != null && var2 != null) {
            this.urlParamsWithArray.put(var1, var2);
        }

    }

    public void put(String var1, InputStream var2) {
        this.put(var1, var2, (String)null);
    }

    public void put(String var1, InputStream var2, String var3) {
        this.put(var1, var2, var3, (String)null);
    }

    public void put(String var1, InputStream var2, String var3, String var4) {
        if (var1 != null && var2 != null) {
            this.fileParams.put(var1, new RequestParams.FileWrapper(var2, var3, var4));
        }

    }

    public void remove(String var1) {
        this.urlParams.remove(var1);
        this.fileParams.remove(var1);
        this.urlParamsWithArray.remove(var1);
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder();
        Iterator var2 = this.urlParams.entrySet().iterator();

        Map.Entry var3;
        while(var2.hasNext()) {
            var3 = (Map.Entry)var2.next();
            if (var1.length() > 0) {
                var1.append("&");
            }

            var1.append((String)var3.getKey());
            var1.append("=");
            var1.append((String)var3.getValue());
        }

        var2 = this.fileParams.entrySet().iterator();

        while(var2.hasNext()) {
            var3 = (Map.Entry)var2.next();
            if (var1.length() > 0) {
                var1.append("&");
            }

            var1.append((String)var3.getKey());
            var1.append("=");
            var1.append("FILE");
        }

        var2 = this.urlParamsWithArray.entrySet().iterator();

        while(var2.hasNext()) {
            var3 = (Map.Entry)var2.next();
            if (var1.length() > 0) {
                var1.append("&");
            }

            ArrayList var4 = (ArrayList)var3.getValue();
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                String var6 = (String)var5.next();
                if (var4.indexOf(var6) != 0) {
                    var1.append("&");
                }

                var1.append((String)var3.getKey());
                var1.append("=");
                var1.append(var6);
            }
        }

        return var1.toString();
    }

    public HttpEntity getEntity() {
        Object var1 = null;
        if (!this.fileParams.isEmpty()) {
            SimpleMultipartEntity var2 = new SimpleMultipartEntity();
            Iterator var3 = this.urlParams.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry var4 = (Map.Entry)var3.next();
                var2.addPart((String)var4.getKey(), (String)var4.getValue());
            }

            int var11 = 0;
            int var12 = this.fileParams.entrySet().size() - 1;

            Iterator var5;
            Map.Entry var6;
            for(var5 = this.fileParams.entrySet().iterator(); var5.hasNext(); ++var11) {
                var6 = (Map.Entry)var5.next();
                RequestParams.FileWrapper var7 = (RequestParams.FileWrapper)var6.getValue();
                if (var7.inputStream != null) {
                    boolean var8 = var11 == var12;
                    if (var7.contentType != null) {
                        var2.addPart((String)var6.getKey(), var7.getFileName(), var7.inputStream, var7.contentType, var8);
                    } else {
                        var2.addPart((String)var6.getKey(), var7.getFileName(), var7.inputStream, var8);
                    }
                }
            }

            var5 = this.urlParamsWithArray.entrySet().iterator();

            while(var5.hasNext()) {
                var6 = (Map.Entry)var5.next();
                ArrayList var13 = (ArrayList)var6.getValue();
                Iterator var14 = var13.iterator();

                while(var14.hasNext()) {
                    String var9 = (String)var14.next();
                    var2.addPart((String)var6.getKey(), var9);
                }
            }

            var1 = var2;
        } else {
            try {
                var1 = new UrlEncodedFormEntity(this.getParamsList(), ENCODING);
            } catch (UnsupportedEncodingException var10) {
                var10.printStackTrace();
            }
        }

        return (HttpEntity)var1;
    }

    private void init() {
        this.urlParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
        this.urlParamsWithArray = new ConcurrentHashMap();
    }

    protected List<BasicNameValuePair> getParamsList() {
        LinkedList var1 = new LinkedList();
        Iterator var2 = this.urlParams.entrySet().iterator();

        Map.Entry var3;
        while(var2.hasNext()) {
            var3 = (Map.Entry)var2.next();
            var1.add(new BasicNameValuePair((String)var3.getKey(), (String)var3.getValue()));
        }

        var2 = this.urlParamsWithArray.entrySet().iterator();

        while(var2.hasNext()) {
            var3 = (Map.Entry)var2.next();
            ArrayList var4 = (ArrayList)var3.getValue();
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                String var6 = (String)var5.next();
                var1.add(new BasicNameValuePair((String)var3.getKey(), var6));
            }
        }

        return var1;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(this.getParamsList(), ENCODING);
    }

    private static class FileWrapper {
        public InputStream inputStream;
        public String fileName;
        public String contentType;

        public FileWrapper(InputStream var1, String var2, String var3) {
            this.inputStream = var1;
            this.fileName = var2;
            this.contentType = var3;
        }

        public String getFileName() {
            return this.fileName != null ? this.fileName : "nofilename";
        }
    }
}
