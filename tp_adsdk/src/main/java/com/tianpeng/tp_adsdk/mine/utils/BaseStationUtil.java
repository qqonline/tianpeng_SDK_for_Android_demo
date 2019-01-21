package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class BaseStationUtil {
    public static final int MESSAGE_BASE_STATION = 1001;

    public BaseStationUtil() {
    }

    public static void getBaseStation(Context var0, final Handler var1) {
        final BaseStationUtil.CustomBaseStation var2 = new BaseStationUtil.CustomBaseStation();

        try {
            final TelephonyManager var3 = (TelephonyManager)var0.getSystemService("phone");
            var3.listen(new PhoneStateListener() {
                public void onSignalStrengthsChanged(SignalStrength var1x) {
                    super.onSignalStrengthsChanged(var1x);
                    var3.listen(this, 0);
                    var2.setBsss(BaseStationUtil.getBsss(var1x));
                    BaseStationUtil.sendMessage(var1, var2, 0);
                    Log.e("TAG", "onSignalStrengthsChanged::::::::: " + var1x.toString());
                }
            }, 256);
            int var4 = ContextCompat.checkSelfPermission(var0, "android.permission.ACCESS_COARSE_LOCATION");
            if (var4 == 0) {
                List var5 = var3.getNeighboringCellInfo();

                try {
                    var2.setStbif(JSONObject.toJSONString(var5));
                } catch (Exception var10) {
                    ;
                }

                CellLocation var6 = var3.getCellLocation();
                String var8;
                int var9;
                if (var6 instanceof CdmaCellLocation) {
                    CdmaCellLocation var7 = (CdmaCellLocation)var6;
                    var8 = var7.getBaseStationId() + "";
                    var9 = var7.getNetworkId();
                    var2.setLac(var9 + "");
                    var2.setBscid(var8);
                } else if (var6 instanceof GsmCellLocation) {
                    GsmCellLocation var12 = (GsmCellLocation)var6;
                    var8 = var12.getCid() + "";
                    var9 = var12.getLac();
                    var2.setLac(var9 + "");
                    var2.setBscid(var8);
                } else {
                    var2.setBscid("未获取到,可能没插sim卡");
                    var2.setLac("未获取到,可能没插sim卡");
                }
            } else {
                var2.setBscid("用户拒绝权限");
                var2.setLac("用户拒绝权限");
            }
        } catch (Exception var11) {
            ;
        }

        sendMessage(var1, var2, 1500);
    }

    private static String getBsss(SignalStrength var0) {
        if (var0 == null) {
            return "未获取到,可能没插sim卡";
        } else {
            String var1 = var0.toString();
            return TextUtils.isEmpty(var1) ? "未获取到,可能没插sim卡" : var1;
        }
    }

    private static void sendMessage(Handler var0, BaseStationUtil.CustomBaseStation var1, int var2) {
        if (var0 != null) {
            var0.removeMessages(MESSAGE_BASE_STATION);
            Message var3 = new Message();
            var3.what = MESSAGE_BASE_STATION;
            var3.obj = var1;
            var0.sendMessageDelayed(var3, (long)var2);
        }

    }

    public static class CustomBaseStation {
        private String bscid = "未获取到,可能没插sim卡";
        private String bsss = "";
        private String lac = "未获取到,可能没插sim卡";
        private String stbif;

        public CustomBaseStation() {
        }

        public String getStbif() {
            return this.stbif;
        }

        public void setStbif(String var1) {
            this.stbif = var1;
        }

        public String getLac() {
            return this.lac;
        }

        public void setLac(String var1) {
            this.lac = var1;
        }

        public String getBscid() {
            return this.bscid;
        }

        public void setBscid(String var1) {
            this.bscid = var1;
        }

        public String getBsss() {
            return this.bsss;
        }

        public void setBsss(String var1) {
            this.bsss = var1;
        }
    }
}

