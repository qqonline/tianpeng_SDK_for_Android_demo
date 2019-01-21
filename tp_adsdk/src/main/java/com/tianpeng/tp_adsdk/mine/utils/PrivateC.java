package com.tianpeng.tp_adsdk.mine.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PrivateC {
    String privatekey = "MIGfMA0GCSqGSIb3";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public PrivateC() {
    }

    public String decrypt(String var1) {
        try {
            Cipher var2 = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec var3 = new SecretKeySpec(this.privatekey.getBytes(), "AES");
            var2.init(2, var3);
            byte[] var4 = var2.doFinal(decryptBASE64(var1));
            return new String(var4, "utf-8");
        } catch (Exception var5) {
            return null;
        }
    }

    public static byte[] decryptBASE64(String var0) {
        byte[] var1 = Base64.decode(var0.trim(), 0);
        return var1;
    }
}

