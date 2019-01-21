package com.tianpeng.tp_adsdk.toutiao.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.bytedance.sdk.openadsdk.TTGlobalAppDownloadController;
import com.tianpeng.tp_adsdk.toutiao.SdkInitImp;


/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class AppDownloadService extends Service {
    public AppDownloadService() {
    }

    public IBinder onBind(Intent var1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent var1, int var2, int var3) {
        this.startIntent(var1);
        return super.onStartCommand(var1, var2, var3);
    }

    private void startIntent(Intent var1) {
        if (var1 != null) {
            int var2 = var1.getIntExtra("action", 0);
            long var3 = var1.getLongExtra("id", -1L);
            int var5 = var1.getIntExtra("internalStatusKey", -1);
            TTGlobalAppDownloadController var6 = SdkInitImp.getInstance(this.getApplicationContext()).getGlobalAppDownloadController(this.getApplicationContext());
            switch(var2) {
                case 0:
                    this.cancelNotify(var3);
                    break;
                case 1:
                case 2:
                    var6.changeDownloadStatus(var5, var3);
                    break;
                case 3:
                    this.cancelNotify(var3);
                    var6.changeDownloadStatus(var5, var3);
                    break;
                case 4:
                    var6.removeDownloadTask(var3);
                    this.cancelNotify(var3);
            }

        }
    }

    private void cancelNotify(long var1) {
        NotificationManager var3 = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        var3.cancel((int)var1);
    }
}

