package com.tianpeng.tp_adsdk.admobhttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PersistentCookieStore implements CookieStore {
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private final ConcurrentHashMap<String, Cookie> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore(Context var1) {
        this.cookiePrefs = var1.getSharedPreferences("CookiePrefsFile", 0);
        this.cookies = new ConcurrentHashMap();
        String var2 = this.cookiePrefs.getString("names", (String)null);
        if (var2 != null) {
            String[] var3 = TextUtils.split(var2, ",");
            String[] var4 = var3;
            int var5 = var3.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String var7 = var4[var6];
                String var8 = this.cookiePrefs.getString("cookie_" + var7, (String)null);
                if (var8 != null) {
                    Cookie var9 = this.decodeCookie(var8);
                    if (var9 != null) {
                        this.cookies.put(var7, var9);
                    }
                }
            }

            this.clearExpired(new Date());
        }

    }

    public void addCookie(Cookie var1) {
        String var2 = var1.getName();
        if (!var1.isExpired(new Date())) {
            this.cookies.put(var2, var1);
        } else {
            this.cookies.remove(var2);
        }

        SharedPreferences.Editor var3 = this.cookiePrefs.edit();
        var3.putString("names", TextUtils.join(",", this.cookies.keySet()));
        var3.putString("cookie_" + var2, this.encodeCookie(new SerializableCookie(var1)));
        var3.commit();
    }

    public void clear() {
        this.cookies.clear();
        SharedPreferences.Editor var1 = this.cookiePrefs.edit();
        Iterator var2 = this.cookies.keySet().iterator();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.remove("cookie_" + var3);
        }

        var1.remove("names");
        var1.commit();
    }

    public boolean clearExpired(Date var1) {
        boolean var2 = false;
        SharedPreferences.Editor var3 = this.cookiePrefs.edit();
        Iterator var4 = this.cookies.entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry var5 = (Map.Entry)var4.next();
            String var6 = (String)var5.getKey();
            Cookie var7 = (Cookie)var5.getValue();
            if (var7.isExpired(var1)) {
                this.cookies.remove(var6);
                var3.remove("cookie_" + var6);
                var2 = true;
            }
        }

        if (var2) {
            var3.putString("names", TextUtils.join(",", this.cookies.keySet()));
        }

        var3.commit();
        return var2;
    }

    public List<Cookie> getCookies() {
        return new ArrayList(this.cookies.values());
    }

    protected String encodeCookie(SerializableCookie var1) {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();

        try {
            ObjectOutputStream var3 = new ObjectOutputStream(var2);
            var3.writeObject(var1);
        } catch (Exception var4) {
            return null;
        }

        return this.byteArrayToHexString(var2.toByteArray());
    }

    protected Cookie decodeCookie(String var1) {
        byte[] var2 = this.hexStringToByteArray(var1);
        ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
        Cookie var4 = null;

        try {
            ObjectInputStream var5 = new ObjectInputStream(var3);
            var4 = ((SerializableCookie)var5.readObject()).getCookie();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return var4;
    }

    protected String byteArrayToHexString(byte[] var1) {
        StringBuffer var2 = new StringBuffer(var1.length * 2);
        byte[] var3 = var1;
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            byte var6 = var3[var5];
            int var7 = var6 & 255;
            if (var7 < 16) {
                var2.append('0');
            }

            var2.append(Integer.toHexString(var7));
        }

        return var2.toString().toUpperCase();
    }

    protected byte[] hexStringToByteArray(String var1) {
        int var2 = var1.length();
        byte[] var3 = new byte[var2 / 2];

        for(int var4 = 0; var4 < var2; var4 += 2) {
            var3[var4 / 2] = (byte)((Character.digit(var1.charAt(var4), 16) << 4) + Character.digit(var1.charAt(var4 + 1), 16));
        }

        return var3;
    }
}

