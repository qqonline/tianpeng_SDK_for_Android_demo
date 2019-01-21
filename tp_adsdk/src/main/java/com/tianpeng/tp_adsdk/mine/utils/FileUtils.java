package com.tianpeng.tp_adsdk.mine.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class FileUtils {
    private static long lastModified = 0L;
    private static long fileNum = 0L;

    private FileUtils() {
    }

    public static long getFileLastModified(String var0) {
        lastModified = 0L;
        fileNum = 0L;
        if (var0 == null) {
            return lastModified;
        } else {
            if (!var0.startsWith("/")) {
                var0 = "/" + var0;
            }

            String var1 = Environment.getExternalStorageDirectory().getAbsolutePath();
            File var2 = new File(var1 + var0);
            if (var2 != null && var2.exists()) {
                try {
                    getFileLastModified(var2);
                    MyLog.e("TTAG", "fileNum : " + fileNum);
                    return lastModified;
                } catch (Exception var4) {
                    return 0L;
                }
            } else {
                return lastModified;
            }
        }
    }

    private static void getFileLastModified(File var0) {
        ++fileNum;
        File[] var1 = var0.listFiles();
        if (var1 == null) {
            lastModified = Math.max(var0.lastModified(), lastModified);
        } else {
            File[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                File var5 = var2[var4];
                getFileLastModified(var5);
            }

            var1 = null;
        }
    }
}
