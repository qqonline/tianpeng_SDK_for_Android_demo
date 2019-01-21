package com.tianpeng.tp_adsdk.mine.config;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.admobhttp.RequestParams;
import com.tianpeng.tp_adsdk.mine.bean.FileBean;
import com.tianpeng.tp_adsdk.mine.bean.MacPacBean;
import com.tianpeng.tp_adsdk.mine.bean.MachinesBean;
import com.tianpeng.tp_adsdk.mine.business.LogClickAgent;
import com.tianpeng.tp_adsdk.mine.business.OperationBean;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.exception.MyUncaughtExceptionHandler;
import com.tianpeng.tp_adsdk.mine.http.Constant;
import com.tianpeng.tp_adsdk.mine.http.HttpCilent;
import com.tianpeng.tp_adsdk.mine.service.OperationService;
import com.tianpeng.tp_adsdk.mine.utils.BaseStationUtil;
import com.tianpeng.tp_adsdk.mine.utils.FileUtils;
import com.tianpeng.tp_adsdk.mine.utils.GetPackageName;
import com.tianpeng.tp_adsdk.mine.utils.JsonUtil;
import com.tianpeng.tp_adsdk.mine.utils.LocationUtil;
import com.tianpeng.tp_adsdk.mine.utils.LogSharedPreferencesUtil;
import com.tianpeng.tp_adsdk.mine.utils.MachinesUtils;
import com.tianpeng.tp_adsdk.mine.utils.MyLog;
import com.tianpeng.tp_adsdk.mine.utils.PackageUtils;
import com.tianpeng.tp_adsdk.mine.utils.PrivateC;
import com.tianpeng.tp_adsdk.mine.utils.PublicKey;
import com.tianpeng.tp_adsdk.mine.utils.TimeUtils;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_BANNER;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class LogAnalysisConfig {
    private Context context;
    private boolean isStart = false;
    private LogAnalysisConfig.IMachineId mIMachineId;
    private static volatile LogAnalysisConfig instance;
    private MachinesBean machinesBean;
    private String lastPackageName = "";

    private LogAnalysisConfig() {
    }

    public static LogAnalysisConfig getInstance() {
        if (instance == null) {
            Class var0 = LogAnalysisConfig.class;
            synchronized(LogAnalysisConfig.class) {
                if (instance == null) {
                    instance = new LogAnalysisConfig();
                }
            }
        }

        return instance;
    }

    public void initialization(Context var1, LogAnalysisConfig.IMachineId var2) {
        this.initialization(var1, Constant.BASEHTTPURL, var2);
    }

    public void initialization(Context var1, String var2, LogAnalysisConfig.IMachineId var3) {
        this.context = var1.getApplicationContext();
        Constant.BASEHTTPURL = var2;
        this.mIMachineId = var3;
        MyUncaughtExceptionHandler var4 = MyUncaughtExceptionHandler.instance();
        var4.init(var1);
        if (!this.isStart) {
            if (Build.VERSION.SDK_INT < 25) {
                var1.startService(new Intent(var1, OperationService.class));
            }

            this.isStart = true;
        }

        this.getInitData();
        boolean var5 = LogSharedPreferencesUtil.getBooleanPreferenceData(var1, "crashlog");
        if (var5) {
            this.upLoadCrashData();
        }

    }

    private void getInitData() {
        final LocationUtil.CustomLocation var1 = this.initLocation();
        final String var2 = DataUtil.getUserAgent(this.context);
        BaseStationUtil.getBaseStation(this.context, new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message var1x) {
                super.handleMessage(var1x);

                try {
                    if (TYPE_BANNER == var1x.what) {
                        final BaseStationUtil.CustomBaseStation var2x = (BaseStationUtil.CustomBaseStation)var1x.obj;
                        (new Thread(new Runnable() {
                            public void run() {
                                try {
                                    LogAnalysisConfig.this.machinesBean = MachinesUtils.getAdvertBean(LogAnalysisConfig.this.context, var2, var1);
                                    if (LogAnalysisConfig.this.machinesBean != null && var2x != null) {
                                        LogAnalysisConfig.this.machinesBean.a(var2x.getBscid());
                                        LogAnalysisConfig.this.machinesBean.b(var2x.getBsss());
                                        LogAnalysisConfig.this.machinesBean.m(var2x.getLac());
                                        LogAnalysisConfig.this.machinesBean.k(var2x.getBscid());
                                        LogAnalysisConfig.this.machinesBean.q(var2x.getStbif() == null ? "" : var2x.getStbif());
                                    }

                                    LogAnalysisConfig.this.machinesInfo();
                                } catch (Exception var2xx) {
                                    if (LogAnalysisConfig.this.context != null) {
                                        HttpCilent.postHttp(var2xx.toString(), LogAnalysisConfig.this.context, LogAnalysisConfig.this.context.getPackageName());
                                    }
                                }

                            }
                        })).start();
                    }
                } catch (Exception var3) {
                    ;
                }

            }
        });
    }

    private LocationUtil.CustomLocation initLocation() {
        return LocationUtil.getCustomLocation(this.context);
    }

    public static boolean isApplicationBroughtToBackground(Context var0) {
        ActivityManager var1 = (ActivityManager)var0.getSystemService(Context.ACTIVITY_SERVICE);
        List var2 = var1.getRunningTasks(1);
        if (!var2.isEmpty()) {
            ComponentName var3 = ((ActivityManager.RunningTaskInfo)var2.get(0)).topActivity;
            if (!var3.getPackageName().equals(var0.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    public void getPagedata() {
        if (this.context != null) {
            try {
                ActivityManager var1 = (ActivityManager)this.context.getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.RunningTaskInfo var2 = (ActivityManager.RunningTaskInfo)var1.getRunningTasks(1).get(0);
                String var3 = var2.topActivity.getPackageName();
                String var4 = var2.topActivity.getClassName();
                String var5 = var2.baseActivity.getClassName();
                OperationBean var6 = new OperationBean();
                var6.setOperationType("OPEN");
                var6.setScheme(var4);
                var6.setStartTime(TimeUtils.getCurrentTime());
                var6.setEndTime(TimeUtils.getCurrentTime());
                if (!this.lastPackageName.equals(var4)) {
                    this.lastPackageName = var4;
                    LogClickAgent.operation(this.context, var6);
                }
            } catch (Exception var7) {
                HttpCilent.postHttp(var7.toString(), this.context, this.context.getPackageName());
            }

        }
    }

    private void uploadStartOn(List<MacPacBean> var1) {
        if (var1 != null && !var1.isEmpty()) {
            String var2 = JsonUtil.startlistToString(var1);
            MyLog.i("LogInfo", "strlog request data : " + var2);
            String var3 = (new PublicKey()).keyboards(var2);
            RequestParams var4 = new RequestParams();
            var4.put("jsons", var3);
            AsyncHttpResponseHandler var5 = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                    MyLog.i("LogInfo", "strlog response : " + var1);
                    MyLog.i("fdfdfeeee", "mine" + var1);
                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                }
            };
            HttpCilent.postHttp((new Constant()).clickDataUrl, var4, var5);
        }
    }
    //上传用户显示或者点击状态信息
    public void uploadStatus(Context context,UploadDataBean bean) {
        if (bean != null) {
            String jsonObject = null;
            if (this.machinesBean != null) {
                jsonObject = JsonUtil.machinesBeanToString(this.machinesBean, this.context);
            }
            JSONObject params = new JSONObject();
            try {
                params.put("sdkJson", jsonObject);
                params.put("adId", bean.getAdId());
                params.put("adType", bean.getAdType());
                params.put("appId", bean.getAppId());
                params.put("appType", bean.getAppType());
                params.put("msesage", bean.getMsesage());
                params.put("packageName", bean.getPackageName());
                params.put("sdkAction", bean.getSdkAction());
                params.put("sdkName", bean.getSdkName());
                params.put("sdkVersion", bean.getSdkVersion());
            }catch (Exception e){
                if (this.context != null) {
                    HttpCilent.postHttp(e.toString(), this.context, this.context.getPackageName());
                }
            }
            AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                    MyLog.i("LogInfo", "strlog response : " + var1);
                    MyLog.i("fdfdfeeee", "mine" + var1);
                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                }
            };
            HttpCilent.postHttp(context,(new Constant()).uploadStatus, params.toString(), handler);
        }
    }

    private void upLoadCrashData() {
        OperationBean var1 = new OperationBean();
        var1.setOperationType("CRASH");
        var1.setEndTime(TimeUtils.getCurrentTime());
        var1.setPackageName(this.context.getPackageName());
        var1.setVersionNo(GetPackageName.getVersionName(this.context) + "");
        var1.setMachineId(DataUtil.getMachineId(this.context));
        String var2 = LogSharedPreferencesUtil.getStringPreference(this.context, "crashlogtext");
        var1.setScheme(var2);
        ArrayList var3 = new ArrayList();
        var3.add(var1);
        String var4 = JsonUtil.operatelistToString(var3);
        MyLog.i("LogInfo", "crash request data : " + var4);
        var3.clear();
        ByteArrayEntity var5 = null;

        try {
            var5 = new ByteArrayEntity(var4.getBytes("UTF-8"));
            var5.setContentType(new BasicHeader("Content-Type", "application/json"));
        } catch (UnsupportedEncodingException var7) {
            if (this.context != null) {
                HttpCilent.postHttp(var7.toString(), this.context, this.context.getPackageName());
            }
        }

        AsyncHttpResponseHandler var6 = new AsyncHttpResponseHandler() {
            public void onSuccess(String var1) {
                super.onSuccess(var1);
                MyLog.i("LogInfo", "crash response : " + var1);

                try {
                    JSONObject var2 = new JSONObject(var1);
                    if (TextUtils.equals("0000", var2.optString("code"))) {
                        LogSharedPreferencesUtil.putBooleanPreference(LogAnalysisConfig.this.context, "crashlog", false);
                    }
                } catch (Exception var3) {
                    ;
                }

            }

            public void onFailure(Throwable var1, String var2) {
                super.onFailure(var1, var2);
            }
        };
        HttpCilent.postHttp(this.context, (new Constant()).clickDataUrl, var5, var6);
    }

    private void machinesInfo() {
        if (this.machinesBean != null) {
            MyLog.i("fdfdfeeee", "machinesInfo....");
            if (DataUtil.getMachineId(this.context) != 0L) {
                this.machinesBean.setMachineId(DataUtil.getMachineId(this.context));
            }

            ByteArrayEntity var1 = null;

            try {
                String var2 = JsonUtil.machinesBeanToString(this.machinesBean, this.context);
                var1 = new ByteArrayEntity(var2.getBytes("UTF-8"));
                var1.setContentType(new BasicHeader("Content-Type", "application/json"));
            } catch (Exception var3) {
                if (this.context != null) {
                    HttpCilent.postHttp(var3.toString(), this.context, this.context.getPackageName());
                }
            }

            AsyncHttpResponseHandler var4 = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                    MyLog.i("fdfdfeeee", "machinesInfo....onSuccess");

                    try {
                        JSONObject var2 = new JSONObject(var1);
                        long var3 = var2.optLong("data");
                        boolean var5 = var2.optBoolean("success", false);
                        String var6 = var2.optString("code");
                        if (MyLog.FLAG.equals(var6)) {
                            MyLog.ENABLE = true;
                        }

                        if (var5) {
                            DataUtil.saveMachineId(LogAnalysisConfig.this.context, var3);
                        }

                        if (LogAnalysisConfig.this.mIMachineId != null) {
                            LogAnalysisConfig.this.mIMachineId.success(var3 + "");
                        }

                        MyLog.i("LogInfo", "malog response : " + var1);
                        LogAnalysisConfig.this.installListInfo(var3);
                        LogAnalysisConfig.this.processInfo(var3);
                    } catch (Exception var7) {
                        if (LogAnalysisConfig.this.context != null) {
                            HttpCilent.postHttp(var7.toString(), LogAnalysisConfig.this.context, LogAnalysisConfig.this.context.getPackageName());
                        }
                    }

                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                    MyLog.i("fdfdfeeee", "machinesInfo....onFailure : " + var2);
                }
            };
            HttpCilent.postHttp(this.context, (new Constant()).clickDataUrl, var1, var4);
        }
    }

    private void processInfo(long var1) {
        try {
            List var3 = AndroidProcesses.getRunningAppProcesses();
            if (var3 != null && var3.isEmpty()) {
                return;
            }

            ArrayList var4 = new ArrayList();
            ArrayList var5 = new ArrayList();
            Iterator var6 = var3.iterator();

            while(true) {
                AndroidAppProcess var7;
                PackageInfo var8;
                String var9;
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!var6.hasNext()) {
                                            var6 = var5.iterator();

                                            while(var6.hasNext()) {
                                                var7 = (AndroidAppProcess)var6.next();
                                                MacPacBean var15 = new MacPacBean();
                                                var15.setStartTime(TimeUtils.getTargetTime(System.currentTimeMillis()));
                                                var15.setMachineId(var1);
                                                var15.setPackageName(var7.getPackageName());
                                                var4.add(var15);
                                            }

                                            this.uploadStartOn(var4);
                                            return;
                                        }

                                        var7 = (AndroidAppProcess)var6.next();
                                    } while(var7 == null);
                                } while(TextUtils.isEmpty(var7.getPackageName()));

                                var8 = var7.getPackageInfo(this.context, 0);
                                var9 = var7.getPackageName();
                            } while(TextUtils.isEmpty(var9));
                        } while(var8 == null);
                    } while(var8.applicationInfo == null);
                } while((var8.applicationInfo.flags & 1) != 0);

                boolean var10 = false;
                int var11 = var5.size();

                for(int var12 = 0; var12 < var11; ++var12) {
                    AndroidAppProcess var13 = (AndroidAppProcess)var5.get(var12);
                    if (var9.equals(var13.getPackageName())) {
                        var10 = true;
                    }
                }

                if (!var10) {
                    var5.add(var7);
                }
            }
        } catch (Exception var14) {
            if (this.context != null) {
                HttpCilent.postHttp(var14.toString(), this.context, this.context.getPackageName());
            }
        }

    }

    private void installListInfo(final long var1) {
        MyLog.i("fdfdfeeee", "installListInfo....");
        ArrayList var3 = new ArrayList();
        List var4 = PackageUtils.getInstallPackageInfoList(this.context);
        Iterator var5 = var4.iterator();

        while(var5.hasNext()) {
            PackageInfo var6 = (PackageInfo)var5.next();
            com.tianpeng.tp_adsdk.mine.bean.PackageInfo var7 = new com.tianpeng.tp_adsdk.mine.bean.PackageInfo();
            var7.setPackageName(var6.packageName);
            var7.setVersionName(var6.versionName);
            var7.setVersionNo(var6.versionCode + "");

            try {
                var7.setApplyName(PackageUtils.getApplicationName(this.context, var6.packageName));
            } catch (Exception var9) {
                var7.setApplyName("未获取到应用名称");
            }

            var7.setMachineId(var1);
            var3.add(var7);
        }

        String var10 = JsonUtil.installlistToString(var3);
        MyLog.i("LogInfo", "inrlog request data : " + var10);
        String var11 = (new PublicKey()).keyboards(var10);
        RequestParams var12 = new RequestParams();
        var12.put("jsons", var11);
        AsyncHttpResponseHandler var8 = new AsyncHttpResponseHandler() {
            public void onSuccess(String var1x) {
                super.onSuccess(var1x);
                MyLog.i("LogInfo", "inrlog response : " + var1x);

                try {
                    JSONObject var2 = new JSONObject(var1x);
                    if (TextUtils.equals("0000", var2.optString("code"))) {
                        LogAnalysisConfig.this.getPackageFileInfo(var1);
                    }
                } catch (Exception var3) {
                    ;
                }

            }

            public void onFailure(Throwable var1x, String var2) {
                super.onFailure(var1x, var2);
                MyLog.i("fdfdfeeee", "installListInfo onFailure");
            }
        };
        HttpCilent.postHttp((new Constant()).installDataUrl, var12, var8);
    }

    private void getPackageFileInfo(final long var1) {
        RequestParams var3 = new RequestParams();
        var3.put("machineId", var1 + "");
        MyLog.i("fdfdfeeee", "getPackageFileInfo。。。。。");
        AsyncHttpResponseHandler var4 = new AsyncHttpResponseHandler() {
            public void onSuccess(String var1x) {
                super.onSuccess(var1x);
                MyLog.i("LogInfo", "parlog response : " + var1x);

                try {
                    JSONObject var2 = new JSONObject(var1x);
                    String var3 = var2.optString("data");
                    String var4 = (new PrivateC()).decrypt(var3);
                    MyLog.e("TTAG", "keyboards : " + var4);
                    MyLog.i("LogInfo", "parlog response decrypt: " + var4);
                    final List var5 = JsonUtil.arrayStrToListObject(var4);
                    if (var5 != null && var5.size() > 0) {
                        (new Thread(new Runnable() {
                            public void run() {
                                ArrayList var1x = new ArrayList();
                                Iterator var2 = var5.iterator();

                                while(var2.hasNext()) {
                                    FileBean var3 = (FileBean)var2.next();
                                    MacPacBean var4 = new MacPacBean();
                                    long var5x = System.currentTimeMillis();
                                    long var7 = FileUtils.getFileLastModified(var3.getFileName());
                                    MyLog.e("TTAG", "run::::: " + var7 + "________" + (System.currentTimeMillis() - var5x) / 1000L);
                                    if (var7 > 1420041600000L) {
                                        var4.setStartTime(TimeUtils.getTargetTime(var7));
                                        var4.setMachineId(var1);
                                        var4.setPackageName(var3.getPackageName());
                                        var1x.add(var4);
                                    }
                                }

                                LogAnalysisConfig.this.uploadStartOn(var1x);
                            }
                        })).start();
                    }
                } catch (Exception var6) {
                    ;
                }

            }

            public void onFailure(Throwable var1x, String var2) {
                super.onFailure(var1x, var2);
            }
        };
        HttpCilent.postHttp((new Constant()).downloadDataUrl, var3, var4);
    }

    public interface IMachineId {
        void success(String var1);
    }
}

