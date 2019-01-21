package com.tianpeng.tp_adsdk.tpadmobsdk.change;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class Md5Util {
    public static String md5(String var0) {
        try {
            MessageDigest var1 = MessageDigest.getInstance("md5");
            byte[] var2 = var1.digest(var0.getBytes());
            StringBuilder var3 = new StringBuilder();

            for(int var4 = 0; var4 < var2.length; ++var4) {
                String var5 = Integer.toHexString(var2[var4] & 255);
                if (var5.length() == 1) {
                    var5 = "0" + var5;
                }

                var3.append(var5);
            }

            return var3.toString();
        } catch (NoSuchAlgorithmException var6) {
            return null;
        }
    }
}
