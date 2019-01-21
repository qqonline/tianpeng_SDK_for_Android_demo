package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class WifiUtil {
    public WifiUtil() {
    }

    public static WifiUtil.CustomWifiInfo getWifiInfo(Context var0) {
        WifiUtil.CustomWifiInfo var1 = new WifiUtil.CustomWifiInfo();

        try {
            WifiManager var2 = (WifiManager)var0.getSystemService("wifi");
            WifiInfo var3 = var2.getConnectionInfo();
            String var4 = var3.getBSSID();
            var1.setBssid(var4);
            String var5 = var3.getNetworkId() + "";
            var1.setNetworkId(var5);
            var1.setLinkSpeed(var3.getLinkSpeed());
            var1.setRssi(var3.getRssi());
            String var6 = var3.getSSID();
            if (var6.startsWith("\"")) {
                var6 = var6.replaceFirst("\"", "");
                if (var6.endsWith("\"")) {
                    var6 = var6.substring(0, var6.length() - 1);
                }
            }

            var1.setSsid(var6);
        } catch (Exception var7) {
            ;
        }

        return var1;
    }

    public static class CustomWifiInfo {
        private String ssid = "";
        private String bssid = "";
        private String networkId = "";
        private int linkSpeed;
        private int rssi;

        public CustomWifiInfo() {
        }

        public int getLinkSpeed() {
            return this.linkSpeed;
        }

        public void setLinkSpeed(int var1) {
            this.linkSpeed = var1;
        }

        public int getRssi() {
            return this.rssi;
        }

        public void setRssi(int var1) {
            this.rssi = var1;
        }

        public String getNetworkId() {
            return this.networkId;
        }

        public void setNetworkId(String var1) {
            this.networkId = var1;
        }

        public String getBssid() {
            return this.bssid;
        }

        public void setBssid(String var1) {
            this.bssid = var1;
        }

        public String getSsid() {
            return this.ssid;
        }

        public void setSsid(String var1) {
            this.ssid = var1;
        }
    }
}

