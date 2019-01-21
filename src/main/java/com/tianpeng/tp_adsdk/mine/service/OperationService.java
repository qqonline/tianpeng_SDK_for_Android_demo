package com.tianpeng.tp_adsdk.mine.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.mine.bean.Status;
import com.tianpeng.tp_adsdk.mine.business.LogClickAgent;
import com.tianpeng.tp_adsdk.mine.business.OperationBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.mine.http.Constant;
import com.tianpeng.tp_adsdk.mine.http.HttpCilent;
import com.tianpeng.tp_adsdk.mine.utils.JsonUtil;
import com.tianpeng.tp_adsdk.mine.utils.MyLog;
import com.tianpeng.tp_adsdk.mine.utils.TimeUtils;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class OperationService extends Service {
    private Timer mTimer;
    private Map<String, Status> runningAppMap = new ConcurrentHashMap();

    public OperationService() {
    }

    @Nullable
    public IBinder onBind(Intent var1) {
        return null;
    }

    public int onStartCommand(Intent var1, int var2, int var3) {
        return super.onStartCommand(var1, var2, var3);
    }

    public void onCreate() {
        super.onCreate();
        this.mTimer = new Timer();
        this.mTimer.schedule(new TimerTask() {
            public void run() {
                LogAnalysisConfig.getInstance().getPagedata();
                OperationService.this.upLoadClickData();
            }
        }, 5000L, 5000L);
    }

    private void getRunningApps() {
        List var1 = AndroidProcesses.getRunningAppProcesses();
        if (var1 != null && !var1.isEmpty()) {
            try {
                Iterator var2 = var1.iterator();

                while(var2.hasNext()) {
                    AndroidAppProcess var3 = (AndroidAppProcess)var2.next();
                    if (var3 != null && var3.statm() != null && !TextUtils.isEmpty(var3.getPackageName())) {
                        PackageInfo var4 = var3.getPackageInfo(this.getApplicationContext(), 0);
                        if (var4 != null && var4.applicationInfo != null && (var4.applicationInfo.flags & 1) == 0) {
                            String var5 = var3.getPackageName();
                            String var6 = var5 + var3.pid;
                            Status var7 = (Status)this.runningAppMap.get(var6);
                            if (var7 == null) {
                                var7 = new Status();
                                this.runningAppMap.put(var6, var7);
                            }

                            boolean needUpload = var7.isUploadOver(var3.foreground, var3.statm().lastModified(), var3.statm().getSize());
                            if (needUpload) {
                                ;
                            }

                            MyLog.e("TAG", var6 + " this progress size is : " + var7.getProgressSize());
                            MyLog.e("TAG", " need upload data : " + needUpload);
                        }
                    }
                }
            } catch (Exception var9) {
                ;
            }

        }
    }

    private void upLoadClickData() {
        if (LogClickAgent.mList != null && LogClickAgent.mList.size() != 0) {
            String var1 = JsonUtil.operatelistToString(LogClickAgent.mList);
            LogClickAgent.mList.clear();
            ByteArrayEntity var2 = null;

            try {
                var2 = new ByteArrayEntity(var1.getBytes("UTF-8"));
                var2.setContentType(new BasicHeader("Content-Type", "application/json"));
            } catch (UnsupportedEncodingException var4) {
                ;
            }

            MyLog.i("fdrwewttr", "json" + var1);
            AsyncHttpResponseHandler var3 = new AsyncHttpResponseHandler() {
                public void onSuccess(String var1) {
                    super.onSuccess(var1);
                }

                public void onFailure(Throwable var1, String var2) {
                    super.onFailure(var1, var2);
                }
            };
            HttpCilent.postHttp(this, (new Constant()).clickDataUrl, var2, var3);
        }
    }

    public void getPagedata(Context var1) {
        ActivityManager var2 = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo var3 = (ActivityManager.RunningTaskInfo)var2.getRunningTasks(1).get(0);
        String var4 = var3.topActivity.getPackageName();
        String var5 = var3.topActivity.getClassName();
        String var6 = var3.baseActivity.getClassName();
        OperationBean var7 = new OperationBean();
        var7.setOperationType("OPEN");
        var7.setScheme(var5);
        var7.setStartTime(TimeUtils.getCurrentTime());
        var7.setEndTime(TimeUtils.getCurrentTime());
        LogClickAgent.operation(this, var7);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }

        this.startService(new Intent(this.getApplicationContext(), OperationService.class));
    }
}

