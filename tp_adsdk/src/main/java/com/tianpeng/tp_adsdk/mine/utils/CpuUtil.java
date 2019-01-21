package com.tianpeng.tp_adsdk.mine.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class CpuUtil {
    public CpuUtil() {
    }

    public static String getCpuHardware() {
        String var0 = "";

        try {
            FileReader var1 = new FileReader("/proc/cpuinfo");
            BufferedReader var2 = new BufferedReader(var1, 8192);

            for(int var3 = 1; var3 < 100; ++var3) {
                String var4 = var2.readLine();
                Log.e("TAG", "getCpuInfo: " + var4);
                if (var4 == null) {
                    break;
                }

                if (var4.contains("Hardware")) {
                    String[] var5 = var4.split(":");
                    var0 = var5[1].trim();
                    break;
                }
            }
        } catch (Exception var6) {
            ;
        }

        return var0;
    }
}

