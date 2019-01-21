package com.tianpeng.tp_adsdk.mine.exception;

import android.content.Context;

import com.tianpeng.tp_adsdk.mine.utils.LogSharedPreferencesUtil;
import com.tianpeng.tp_adsdk.mine.utils.MyLog;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static MyUncaughtExceptionHandler handler = new MyUncaughtExceptionHandler();
    private Context context;
    private Thread.UncaughtExceptionHandler mHandler;

    private MyUncaughtExceptionHandler() {
    }

    public static MyUncaughtExceptionHandler instance() {
        return handler;
    }

    public void init(Context var1) {
        this.context = var1;
        this.mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread var1, Throwable var2) {
        if (!this.crashlog(var2) && this.mHandler != null) {
            this.mHandler.uncaughtException(var1, var2);
        }

    }

    private boolean crashlog(Throwable var1) {
        if (var1 == null) {
            return false;
        } else {
            LogSharedPreferencesUtil.putBooleanPreference(this.context, "crashlog", true);
            this.crashlogtext(var1);
            return false;
        }
    }

    private void crashlogtext(Throwable var1) {
        StringWriter var2 = new StringWriter();
        PrintWriter var3 = new PrintWriter(var2);
        var1.printStackTrace(var3);

        for(Throwable var4 = var1.getCause(); var4 != null; var4 = var4.getCause()) {
            var4.printStackTrace(var3);
        }

        var3.close();
        String var5 = var2.toString();
        if (var5 != null) {
            MyLog.e("tttttttttt", "log :" + var5);
            LogSharedPreferencesUtil.putStringPreference(this.context, "crashlogtext", var5);
        }

    }
}
