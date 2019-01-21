package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class BlueToothUtil {
    public BlueToothUtil() {
    }

    public static String getMac(Context var0) {
        String var1 = "";
        String var2 = "";

        try {
            Process var3 = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader var4 = new InputStreamReader(var3.getInputStream());
            LineNumberReader var5 = new LineNumberReader(var4);

            while(null != var1) {
                var1 = var5.readLine();
                if (var1 != null) {
                    var2 = var1.trim();
                    break;
                }
            }
        } catch (Exception var7) {
            ;
        }

        if (TextUtils.isEmpty(var2)) {
            try {
                var2 = loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception var6) {
                ;
            }
        }

        return TextUtils.isEmpty(var2) ? getMacFromSecure(var0) : var2;
    }

    private static String loadFileAsString(String var0) throws IOException {
        FileReader var1 = new FileReader(var0);
        String var2 = loadReaderAsString(var1);
        var1.close();
        return var2;
    }

    private static String loadReaderAsString(Reader var0) throws IOException {
        StringBuilder var1 = new StringBuilder();
        char[] var2 = new char[4096];

        for(int var3 = var0.read(var2); var3 >= 0; var3 = var0.read(var2)) {
            var1.append(var2, 0, var3);
        }

        return var1.toString();
    }

    private static String getMacFromSecure(Context var0) {
        try {
            return Settings.Secure.getString(var0.getContentResolver(), "bluetooth_address");
        } catch (Exception var2) {
            return "";
        }
    }
}

