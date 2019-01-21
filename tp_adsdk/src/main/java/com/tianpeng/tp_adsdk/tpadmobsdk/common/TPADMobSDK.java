package com.tianpeng.tp_adsdk.tpadmobsdk.common;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.tianpeng.tp_adsdk.mine.config.DataUtil;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.SDKUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenMachineId;
import com.tianpeng.tp_adsdk.tpadmobsdk.http.HttpClient;

import java.util.Iterator;
import java.util.List;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class TPADMobSDK {
    private static TPADMobSDK sdk = null;
    private Context mContext;
    private String appID;
    private String[] platforms;
    private boolean success = false;
//    private boolean isAdMob = false;

    private TPADMobSDK() {
    }

    public static TPADMobSDK instance() {
        if (sdk == null) {
            Class var0 = TPADMobSDK.class;
            synchronized(TPADMobSDK.class) {
                if (sdk == null) {
                    sdk = new TPADMobSDK();
                }
            }
        }

        return sdk;
    }

    public void initSdk(Context context,String appId,boolean isDebug){
        initSdk(context,appId, isDebug
                , ADMobGenAdPlaforms.PLAFORM_GDT
                , ADMobGenAdPlaforms.PLAFORM_BAIDU
                , ADMobGenAdPlaforms.PLAFORM_TOUTIAO
                , ADMobGenAdPlaforms.PLAFORM_YOUDAO
        );
    }

    private void initSdk(Context context, String appID, boolean isDebug, String... channels) {
        this.initSdk(context, appID, isDebug, (IADMobGenMachineId)null, channels);
    }

    private void initSdk(Context context, String appid, boolean isDebug, IADMobGenMachineId var4, String... channels) {
        if (context == null) {
            throw new RuntimeException("the context is null !!");
        } else if (TextUtils.isEmpty(appid)) {
            throw new RuntimeException("appid must not be empty !!");
        } else if (channels != null && channels.length > 0) {
//            String[] var6 = channels;
//            int var7 = channels.length;

//            for(int i = 0; i < var7; ++i) {
//                String var9 = var6[i];
//                if (var9.equals(PLAFORM_ADMOB)) {
//                    this.isAdMob = true;
//                }
//            }

//            if (!this.isAdMob) {
//                throw new RuntimeException("AdMob must be inited !!");
//            } else {
                platforms = channels;
                this.mContext = context.getApplicationContext();

                try {
                    if (context.getPackageName().equals(currProcessName(context))) {
                        LogTool.show(currProcessName(context));
                        this.success = true;
                        this.startInit(this.mContext);
                        this.init(appid, isDebug);
                    }
                } catch (Exception var10) {
                    LogTool.show("init crash" + var10.toString());
                    LogTool.show("init failed");
                    if (var10 != null && this.mContext != null) {
                       HttpClient.getInstance().postLog(var10.toString(), this.mContext, true);
                    }
                }

//            }
        } else {
            throw new RuntimeException("platforms must not be empty !!");
        }
    }

    private void startInit(Context context) {
        DataUtil.getUserAgent(context);
        (new Thread(new Runnable() {
            public void run() {
                if (TPADMobSDK.this.mContext != null) {
                    try {
                        LogAnalysisConfig.getInstance().initialization(TPADMobSDK.this.mContext, (LogAnalysisConfig.IMachineId)null);
                    } catch (Exception var2) {
                        HttpClient.getInstance().postLog(var2.toString(), TPADMobSDK.this.mContext, false);
                    }
                }

            }
        })).start();
    }

    private String currProcessName(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        if (list == null) {
            return null;
        } else {
            Iterator iterator = list.iterator();

            ActivityManager.RunningAppProcessInfo info;
            do {
                if (!iterator.hasNext()) {
                    return null;
                }

                info = (ActivityManager.RunningAppProcessInfo)iterator.next();
            } while(info.pid != Process.myPid() || info.processName == null);

            return info.processName;
        }
    }

    public Context getAdMobSdkContext() {
        if (this.mContext == null) {
            throw new RuntimeException("the context is null, please init sdk first !!");
        } else {
            return this.mContext;
        }
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public String getAppId() {
        return appID;
    }

    public String getSdkName() {
        return "com.tianpeng.tp_adsdk";
    }

    private void init(String var1, boolean var2) {
        appID = var1;
        SDKUtil.getInstance().init(var2, this.mContext);
    }

    public boolean isSamsungPhone() {
        String var1 = Build.MANUFACTURER;
        String var2 = Build.MODEL;
        if (var1 != null && var2 != null) {
            int var3 = Build.VERSION.SDK_INT;
            if (var3 <= 25) {
                return true;
            } else if (!var1.trim().contains("samsung")) {
                return false;
            } else {
                return !var2.trim().toLowerCase().contains("google") && !var2.trim().toLowerCase().contains("nexus");
            }
        } else {
            return false;
        }
    }

    public boolean isWifi() {
        NetworkInfo var1 = null;

        try {
            ConnectivityManager var2 = (ConnectivityManager)this.getAdMobSdkContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            var1 = var2.getActiveNetworkInfo();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var1 != null && 1 == var1.getType();
    }
}
