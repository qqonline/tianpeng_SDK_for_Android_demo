package com.tianpeng.tp_adsdk.toutiao.service;

import android.content.Context;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAppDownloadInfo;
import com.bytedance.sdk.openadsdk.TTGlobalAppDownloadListener;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class AppDownloadStatusListener implements TTGlobalAppDownloadListener {
    public static final int DOWNLOAD_STATUS_ACTIVE = 1;
    public static final int DOWNLOAD_STATUS_WATING = 2;
    public static final int DOWNLOAD_STATUS_FINISH = 3;
    public static final int DOWNLOAD_STATUS_DELETE = 4;
    public static final int DOWNLOAD_STATUS_FAILED = 5;
    private Context context;

    public AppDownloadStatusListener(Context var1) {
        this.context = var1.getApplicationContext();
    }

    public void onDownloadActive(TTAppDownloadInfo var1) {
        Log.d("TTGlobalDownload", "下载中----" + var1.getAppName() + "---" + this.a(var1) + "%");
    }

    public void onDownloadPaused(TTAppDownloadInfo var1) {
        Log.d("TTGlobalDownload", "暂停----" + var1.getAppName() + "---" + this.a(var1) + "%");
    }

    public void onDownloadFinished(TTAppDownloadInfo var1) {
        Log.d("TTGlobalDownload", "下载完成----" + var1.getAppName() + "---" + this.a(var1) + "%  file: " + var1.getFileName());
    }

    public void onInstalled(String var1, String var2, long var3, int var5) {
        Log.d("TTGlobalDownload", "安装完成----pkgName: " + var1);
    }

    public void onDownloadFailed(TTAppDownloadInfo var1) {
    }

    private int a(TTAppDownloadInfo var1) {
        if (var1 == null) {
            return 0;
        } else {
            double var2 = 0.0D;

            try {
                var2 = (double)var1.getCurrBytes() / (double)var1.getTotalBytes();
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            int var4 = (int)(var2 * 100.0D);
            if (var4 < 0) {
                var4 = 0;
            }

            return var4;
        }
    }
}

