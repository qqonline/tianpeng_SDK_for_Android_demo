package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class NetTool {
    public NetTool() {
    }

    public boolean networkAvaliable(Context var1) {
        if (var1 == null) {
            return false;
        } else {
            ConnectivityManager var2 = (ConnectivityManager)var1.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo var3 = var2.getActiveNetworkInfo();
            return null == var3 ? false : var3.isAvailable();
        }
    }

    public boolean IsStartWifi(Context var1) {
        if (var1 == null) {
            return false;
        } else {
            try {
                WifiManager var2 = (WifiManager)var1.getSystemService(Context.WIFI_SERVICE);
                return var2 != null && WifiManager.WIFI_STATE_ENABLED == var2.getWifiState();
            } catch (Exception var3) {
                return false;
            }
        }
    }

    public String getCurrentNetType(Context var1) {
        return this.getCurrentNetType(var1, "");
    }

    public String getCurrentNetType(Context var1, String var2) {
        if (var1 == null) {
            return TextUtils.isEmpty(var2) ? "" : var2;
        } else {
            String var3 = TextUtils.isEmpty(var2) ? "" : var2;
            ConnectivityManager var4 = (ConnectivityManager)var1.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo var5 = var4.getActiveNetworkInfo();
            if (var5 == null) {
                var3 = "null";
            } else if (var5.getType() == 1) {
                var3 = "WIFI";
            } else if (var5.getType() == 0) {
                int var6 = var5.getSubtype();
                if (var6 != 4 && var6 != 1 && var6 != 2) {
                    if (var6 != 3 && var6 != 8 && var6 != 6 && var6 != 5 && var6 != 12) {
                        if (var6 == 13) {
                            var3 = "4G";
                        }
                    } else {
                        var3 = "3G";
                    }
                } else {
                    var3 = "2G";
                }
            }

            return var3;
        }
    }
}

