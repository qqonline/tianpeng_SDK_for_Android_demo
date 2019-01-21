package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;

import com.tianpeng.tp_adsdk.mine.bean.FileBean;
import com.tianpeng.tp_adsdk.mine.bean.MacPacBean;
import com.tianpeng.tp_adsdk.mine.bean.MachinesBean;
import com.tianpeng.tp_adsdk.mine.bean.PackageInfo;
import com.tianpeng.tp_adsdk.mine.business.OperationBean;
import com.tianpeng.tp_adsdk.mine.http.HttpCilent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class JsonUtil {
    public JsonUtil() {
    }

    public static String machinesBeanToString(MachinesBean bean, Context context) {
        JSONObject var2 = new JSONObject();

        try {
            var2.put("machineId", bean.getMachineId());
            var2.put("screenWidth", bean.F());
            var2.put("screenHeight", bean.G());
            var2.put("imsi", bean.H());
            var2.put("machineType", bean.I());
            var2.put("ua", bean.J());
            var2.put("networkAddress", bean.K());
            var2.put("networkType", bean.L());
            var2.put("sdScreendpi", bean.M());
            var2.put("imei", bean.N());
            var2.put("osVersion", bean.O());
            var2.put("vendor", bean.P());
            var2.put("modelNo", bean.Q());
            var2.put("androidId", bean.R());
            var2.put("idfa", bean.S());
            var2.put("openUdid", bean.T());
            var2.put("lat", bean.U());
            var2.put("lng", bean.V());
            var2.put("ip", bean.W());
            var2.put("meid", bean.D());
            var2.put("cid", bean.E());
            var2.put("bscid", bean.b());
            var2.put("bsss", bean.c());
            var2.put("deviceType", bean.d());
            var2.put("advertisingId", bean.e());
            var2.put("idfv", bean.f());
            var2.put("language", bean.g());
            var2.put("battery", bean.h());
            var2.put("isroot", bean.i());
            var2.put("btmac", bean.j());
            var2.put("pdunid", bean.k());
            var2.put("cputy", bean.l());
            var2.put("iccid", bean.m());
            var2.put("country", bean.n());
            var2.put("coordinateType", bean.o());
            var2.put("locaAccuracy", bean.p());
            var2.put("coordTime", bean.q());
            var2.put("cellularId", bean.r());
            var2.put("bssId", bean.s());
            var2.put("lac", bean.t());
            var2.put("mcc", bean.u());
            var2.put("netwkId", bean.v());
            var2.put("ssid", bean.w());
            var2.put("lksd", bean.x());
            var2.put("rssi", bean.y());
            var2.put("roaming", bean.z());
            var2.put("stbif", bean.A());
            var2.put("cpuType", bean.B());
            var2.put("cpuSubtype", bean.C());
        } catch (JSONException var4) {
            var4.printStackTrace();
            if (context != null) {
                HttpCilent.postHttp(var4.toString(), context, context.getPackageName());
            }
        }

        return var2.toString();
    }
    public static String startlistToString(List<MacPacBean> list) {
        JSONArray var1 = new JSONArray();

        try {
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                MacPacBean var3 = (MacPacBean)var2.next();
                JSONObject var4 = new JSONObject();
                var4.put("machineId", var3.getMachineId());
                var4.put("packageName", var3.getPackageName());
                var4.put("startTime", var3.getStartTime());
                var1.put(var4);
            }
        } catch (JSONException var5) {
            return "";
        }

        return var1.toString();
    }

    public static String operatelistToString(List<OperationBean> list) {
        JSONArray var1 = new JSONArray();

        try {
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                OperationBean var3 = (OperationBean)var2.next();
                JSONObject var4 = new JSONObject();
                var4.put("operationType", var3.getOperationType());
                var4.put("machineType", var3.getMachineType());
                var4.put("scheme", var3.getScheme());
                var4.put("startCooX", var3.getStartCooX());
                var4.put("endCooX", var3.getEndCooX());
                var4.put("startCooY", var3.getStartCooY());
                var4.put("endCooY", var3.getEndCooY());
                var4.put("startTime", var3.getStartTime());
                var4.put("endTime", var3.getEndTime());
                var4.put("packageName", var3.getPackageName());
                var4.put("versionNo", var3.getVersionNo());
                var4.put("machineId", var3.getMachineId());
                var1.put(var4);
            }
        } catch (Exception var5) {
            return "";
        }

        return var1.toString();
    }

    public static String installlistToString(List<PackageInfo> list) {
        JSONArray var1 = new JSONArray();

        try {
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                PackageInfo var3 = (PackageInfo)var2.next();
                JSONObject var4 = new JSONObject();
                var4.put("packageName", var3.getPackageName());
                var4.put("versionNo", var3.getVersionNo());
                var4.put("machineId", var3.getMachineId());
                var4.put("applyName", var3.getApplyName());
                var4.put("versionName", var3.getVersionName());
                var1.put(var4);
            }
        } catch (Exception var5) {
            return "";
        }

        return var1.toString();
    }

    public static List<FileBean> arrayStrToListObject(String str) {
        try {
            JSONArray var1 = new JSONArray(str);
            ArrayList var2 = new ArrayList();

            for(int var3 = 0; var3 < var1.length(); ++var3) {
                FileBean var4 = new FileBean();
                JSONObject var5 = var1.getJSONObject(var3);
                if (var5.has("packageName")) {
                    var4.setPackageName(var5.getString("packageName"));
                }

                if (var5.has("fileName")) {
                    var4.setFileName(var5.getString("fileName"));
                }

                if (var5.has("filePath")) {
                    var4.setFilePath(var5.getString("filePath"));
                }

                var2.add(var4);
            }

            return var2;
        } catch (JSONException var6) {
            return new ArrayList();
        }
    }
}

