package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PhoneStateUtil {
    public PhoneStateUtil() {
    }

    public static PhoneStateUtil.CustomPhoneState getPhoneState(Context var0) {
        PhoneStateUtil.CustomPhoneState var1 = new PhoneStateUtil.CustomPhoneState();

        try {
            int var2 = ContextCompat.checkSelfPermission(var0, "android.permission.READ_PHONE_STATE");
            if (var2 == 0) {
                TelephonyManager var3 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
                String var4 = var3.getSubscriberId();
                String var5 = var3.getSimSerialNumber();
                String var6 = var3.getDeviceId();
                boolean var7 = var3.isNetworkRoaming();
                var1.setImei(TextUtils.isEmpty(var6) ? "未获取到,可能没插sim卡" : var6);
                var1.setIccid(TextUtils.isEmpty(var5) ? "未获取到,可能没插sim卡" : var5);
                var1.setImsi(TextUtils.isEmpty(var4) ? "未获取到,可能没插sim卡" : var4);
                var1.setIsNetworkRoaming(var7 ? 1 : 0);
            } else {
                var1.setImei("用户拒绝权限");
                var1.setIccid("用户拒绝权限");
                var1.setImsi("用户拒绝权限");
            }

            getImeiAndMeid(var0, var1);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return var1;
    }

    private static void getImeiAndMeid(Context var0, PhoneStateUtil.CustomPhoneState var1) {
        TelephonyManager var2 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Method var3 = var2.getClass().getMethod("getDeviceId", Integer.TYPE);
            String var4 = var1.getImei();
            if (var4 != null && !"用户拒绝权限".equals(var4) && !"未获取到,可能没插sim卡".equals(var4)) {
                if (var4.length() == 14) {
                    var1.setMeid(var4);
                } else {
                    var1.setImei1(var4);
                }
            }

            String var5 = (String)var3.invoke(var2, 1);
            if (var5.length() == 14) {
                var1.setMeid(var5);
            } else {
                var1.setImei1(var5);
            }

            String var6 = (String)var3.invoke(var2, 2);
            if (var6.length() == 14) {
                var1.setMeid(var6);
            } else if (!var6.equals(var1.getImei1())) {
                var1.setImei2(var6);
            }
        } catch (Exception var7) {
            ;
        }

    }

    public static class CustomPhoneState {
        private String iccid = "";
        private String imsi = "";
        private String imei1 = "";
        private String imei2 = "";
        private String meid = "";
        private String imei = "";
        private String mcc = "";
        private int isNetworkRoaming;

        public CustomPhoneState() {
        }

        public int getIsNetworkRoaming() {
            return this.isNetworkRoaming;
        }

        public void setIsNetworkRoaming(int var1) {
            this.isNetworkRoaming = var1;
        }

        public String getMcc() {
            if (!"用户拒绝权限".equals(this.imsi) && !"未获取到,可能没插sim卡".equals(this.imsi)) {
                if (this.imsi != null && this.imsi.length() > 3) {
                    this.mcc = this.imsi.substring(0, 3);
                }
            } else {
                this.mcc = this.imsi;
            }

            return this.mcc;
        }

        public String getIccid() {
            return this.iccid;
        }

        public void setIccid(String var1) {
            this.iccid = var1;
        }

        public String getImsi() {
            return this.imsi;
        }

        public void setImsi(String var1) {
            this.imsi = var1;
        }

        public String getImei1() {
            return this.imei1;
        }

        public void setImei1(String var1) {
            this.imei1 = var1;
        }

        public String getImei2() {
            return this.imei2;
        }

        public void setImei2(String var1) {
            this.imei2 = var1;
        }

        public String getMeid() {
            return this.meid;
        }

        public void setMeid(String var1) {
            this.meid = var1;
        }

        public String getImei() {
            if (!"用户拒绝权限".equals(this.imei1) && !"未获取到,可能没插sim卡".equals(this.imei1) && !"用户拒绝权限".equals(this.imei2) && !"未获取到,可能没插sim卡".equals(this.imei2)) {
                if (!TextUtils.isEmpty(this.imei1) && !TextUtils.isEmpty(this.imei2)) {
                    return this.imei1 + "," + this.imei2;
                }

                if (!TextUtils.isEmpty(this.imei1)) {
                    return this.imei1;
                }
            }

            return this.imei;
        }

        public void setImei(String var1) {
            this.imei = var1;
        }
    }
}

