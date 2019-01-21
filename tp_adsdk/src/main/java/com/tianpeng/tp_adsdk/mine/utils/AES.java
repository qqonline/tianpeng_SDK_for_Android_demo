package com.tianpeng.tp_adsdk.mine.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class AES {
    String ALGORITHM = "AES/ECB/PKCS5Padding";

    public AES() {
    }

    public SecretKey geneKey() throws NoSuchAlgorithmException {
        KeyGenerator var1 = KeyGenerator.getInstance("AES");
        var1.init(128);
        SecretKey var2 = var1.generateKey();
        return var2;
    }

    public String aesCipher(String var1, SecretKey var2) {
        try {
            Cipher var3 = Cipher.getInstance(this.ALGORITHM);
            var3.init(1, var2);
            byte[] var4 = var3.doFinal(var1.getBytes("UTF-8"));
            return PublicKey.encryptBASE64(var4);
        } catch (Exception var5) {
            return null;
        }
    }

    public SecretKey geneKey(String var1) throws NoSuchAlgorithmException {
        KeyGenerator var2 = KeyGenerator.getInstance("AES");
        SecureRandom var3 = SecureRandom.getInstance("SHA1PRNG");
        var3.setSeed(PrivateC.decryptBASE64(var1));
        var2.init(128, var3);
        SecretKey var4 = var2.generateKey();
        return new SecretKeySpec(var4.getEncoded(), "AES");
    }
}

