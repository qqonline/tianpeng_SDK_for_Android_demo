package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.tianpeng.tp_adsdk.mine.bean.MachinesBean;
import com.tianpeng.tp_adsdk.mine.config.DataUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class MachinesUtils {
    public MachinesUtils() {
    }

    public static MachinesBean getAdvertBean(Context var0, String var1, LocationUtil.CustomLocation var2) {
        if (var0 == null) {
            return null;
        } else {
            MachinesBean var3 = new MachinesBean();
            DisplayTool var4 = new DisplayTool(var0);
            NetTool var5 = new NetTool();
            PhoneStateUtil.CustomPhoneState var6 = PhoneStateUtil.getPhoneState(var0);
            var3.g(var4.getwScreen());
            var3.h(var4.gethScreen());
            var3.i(1);
            var3.u(var1);
            if (Build.VERSION.SDK_INT >= 23) {
                var3.v(getMacAddr());
            } else {
                var3.v(getMacAddress(var0));
            }

            var3.t(var6.getImsi());
            var3.y(var6.getImei());
            var3.r(var6.getMeid());
            var3.w(var5.getCurrentNetType(var0, "未获取到"));
            var3.x(var4.getDensityDpi() + "");
            var3.z(Build.VERSION.RELEASE);
            var3.A(Build.MANUFACTURER);
            var3.B(Build.MODEL);
            var3.C(getAndroidId(var0));
            var3.D("");
            var3.E("");
            var3.F(DataUtil.getLat(var0));
            var3.G(DataUtil.getLng(var0));
            var3.H(getHostIP());
            var3.s(getCid());
            var3.a(isPad(var0) ? 5 : 4);
            var3.c(AdvertisingUtil.getGoogleAdId(var0));
            var3.d("");
            var3.e(Locale.getDefault().getLanguage());
            var3.b(getBatteryLevel(var0));
            var3.f(BlueToothUtil.getMac(var0));
            var3.g(getUniquePsuedoID());
            var3.h(CpuUtil.getCpuHardware());
            var3.i(var6.getIccid());
            var3.j(var2.getCountry());
            var3.c(var2.getCoordinateType());
            var3.a((double)var2.getAccuracy());
            var3.b(var2.getTime());
            var3.n(var6.getMcc());
            WifiUtil.CustomWifiInfo var7 = WifiUtil.getWifiInfo(var0);
            var3.l(var7.getBssid());
            var3.o(var7.getNetworkId());
            var3.p(var7.getSsid());
            var3.d(var7.getLinkSpeed());
            var3.e(var7.getRssi());
            var3.f(var6.getIsNetworkRoaming());
            return var3;
        }
    }

    private static String getUniquePsuedoID() {
        String var0 = null;
        String var1 = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;

        try {
            var0 = Build.class.getField("SERIAL").get((Object)null).toString();
            return (new UUID((long)var1.hashCode(), (long)var0.hashCode())).toString();
        } catch (Exception var3) {
            var0 = "serial";
            return (new UUID((long)var1.hashCode(), (long)var0.hashCode())).toString();
        }
    }

    private static int getBatteryLevel(Context var0) {
        int var1 = 100;

        try {
            BatteryManager var2 = (BatteryManager)var0.getSystemService(Context.BATTERY_SERVICE);
            if (Build.VERSION.SDK_INT >= 21) {
                var1 = var2.getIntProperty(4);
            }
        } catch (Exception var3) {
            ;
        }

        return var1;
    }

    private static boolean isPad(Context var0) {
        return (var0.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    private static String getCid() {
        String var0 = null;

        try {
            FileReader var1 = new FileReader("/sys/block/mmcblk0/device/type");
            Boolean var4 = (new BufferedReader((Reader)var1)).readLine().toLowerCase().contentEquals("mmc");
            if (var4 != null) {
                var0 = "/sys/block/mmcblk0/device/";
            }

            var1 = new FileReader(var0 + "cid");
            var0 = (new BufferedReader((Reader)var1)).readLine();
        } catch (Exception var3) {
            ;
        }

        return var0;
    }

    private static String getHostIP() {
        String var0 = "";

        try {
            Enumeration var1 = NetworkInterface.getNetworkInterfaces();
            InetAddress var2 = null;

            while(true) {
                while(var1.hasMoreElements()) {
                    NetworkInterface var3 = (NetworkInterface)var1.nextElement();
                    Enumeration var4 = var3.getInetAddresses();

                    while(var4.hasMoreElements()) {
                        var2 = (InetAddress)var4.nextElement();
                        if (!(var2 instanceof Inet6Address)) {
                            String var5 = var2.getHostAddress();
                            if (!"127.0.0.1".equals(var5)) {
                                var0 = var2.getHostAddress();
                                break;
                            }
                        }
                    }
                }

                return var0;
            }
        } catch (SocketException var6) {
            MyLog.i("yao", "SocketException");
            return var0;
        }
    }

    private static String getAndroidId(Context var0) {
        String var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
        return var1;
    }

    public static String getMacAddress(Context var0) {
        WifiManager var1 = (WifiManager)var0.getSystemService(Context.WIFI_SERVICE);
        if (var1 == null) {
            return "02:00:00:00:00:00";
        } else {
            WifiInfo var2 = var1.getConnectionInfo();
            if (var2 == null) {
                return "02:00:00:00:00:00";
            } else {
                String var3 = var2.getMacAddress();
                if (TextUtils.isEmpty(var3)) {
                    var3 = "02:00:00:00:00:00";
                }

                return var3;
            }
        }
    }

    public static String getMacAddr() {
        try {
            ArrayList var0 = Collections.list(NetworkInterface.getNetworkInterfaces());
            Iterator var1 = var0.iterator();

            while(var1.hasNext()) {
                NetworkInterface var2 = (NetworkInterface)var1.next();
                if (var2.getName().equalsIgnoreCase("wlan0")) {
                    byte[] var3 = var2.getHardwareAddress();
                    if (var3 == null) {
                        return "";
                    }

                    StringBuilder var4 = new StringBuilder();
                    byte[] var5 = var3;
                    int var6 = var3.length;

                    for(int var7 = 0; var7 < var6; ++var7) {
                        byte var8 = var5[var7];
                        var4.append(String.format("%02X:", var8));
                    }

                    if (var4.length() > 0) {
                        var4.deleteCharAt(var4.length() - 1);
                    }

                    return var4.toString();
                }
            }
        } catch (Exception var9) {
            ;
        }

        return "02:00:00:00:00:00";
    }

    private static String bytesToString(byte[] var0) {
        if (var0 != null && var0.length != 0) {
            StringBuilder var1 = new StringBuilder();
            byte[] var2 = var0;
            int var3 = var0.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte var5 = var2[var4];
                var1.append(String.format("%02X:", var5));
            }

            if (var1.length() > 0) {
                var1.deleteCharAt(var1.length() - 1);
            }

            return var1.toString();
        } else {
            return null;
        }
    }
}

