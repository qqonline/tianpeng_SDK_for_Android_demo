package com.tianpeng.tp_adsdk.mine.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class GetDeviceId {
    private Context context;

    public GetDeviceId(Context var1) {
        this.context = var1;
    }

    private String getUUID() {
        String var1 = UUID.randomUUID().toString();
        return var1;
    }

    private String getMAC() {
        if (this.context == null) {
            return "";
        } else {
            try {
                WifiManager var1 = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo var2 = var1.getConnectionInfo();
                return var2.getMacAddress();
            } catch (Exception var3) {
                return null;
            }
        }
    }

    private String getIMEI() {
        if (this.context == null) {
            return "";
        } else {
            try {
                TelephonyManager tm = (TelephonyManager)this.context.getSystemService(Context.TELEPHONY_SERVICE);
                List<String> strings = new ArrayList<>();
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            for(int i=0;i<tm.getPhoneCount();i++){
                                strings.add( tm.getDeviceId(i));
                            }
                        }else {
                            strings.add(tm.getDeviceId());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(strings.size()==0) {
                    strings.add("0");
                    strings.add("0");
                }else if(strings.size()==1){
                    strings.add("0");
                }
                return strings.get(0);
            } catch (Exception var2) {
                return null;
            }
        }
    }

    private String getDeviceId() {
        if (this.context == null) {
            return "";
        } else {
            String var1 = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
            return !"9774d56d682e549c".equals(var1) ? var1 : null;
        }
    }

    public String getId() {
        String var1 = this.getIMEI();
        String var2 = this.getMAC();
        String var3 = this.getDeviceId();
        String var4 = this.getUUID();
        String var5;
        if (var2 != null) {
            if (var3 != null) {
                var5 = var2 + var3;
            } else {
                var5 = var2;
            }
        } else if (var1 != null) {
            if (var3 != null) {
                var5 = var1 + var3;
            } else {
                var5 = var1;
            }
        } else {
            var5 = var4;
        }

        var5 = this.md5(var5);
        return var5;
    }

    private String md5(String var1) {
        char[] var2 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest var3 = null;

        try {
            var3 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var10) {
            throw new RuntimeException(var10.getMessage());
        }

        var3.update(var1.getBytes());
        byte[] var4 = var3.digest();
        int var5 = var4.length;
        char[] var6 = new char[var5 * 2];
        int var7 = 0;

        for(int var8 = 0; var8 < var5; ++var8) {
            byte var9 = var4[var8];
            var6[var7++] = var2[var9 >> 4 & 15];
            var6[var7++] = var2[var9 & 15];
        }

        return new String(var6);
    }
}

