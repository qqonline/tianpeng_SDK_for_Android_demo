package com.tianpeng.tp_adsdk.tpadmobsdk.change;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PremissTool {
    public static boolean checkPermission(Context var0) {
        String[] var1 = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        boolean var2 = true;
        String[] var3 = var1;
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            int var7 = ContextCompat.checkSelfPermission(var0, var6);
            if (0 != var7) {
                var2 = false;
            }
        }

        return var2;
    }
}
