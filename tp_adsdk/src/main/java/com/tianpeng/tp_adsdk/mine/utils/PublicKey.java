package com.tianpeng.tp_adsdk.mine.utils;

import android.util.Base64;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PublicKey {
    String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9AnMx0xJR5Oy/7k0MPedEsYLv3U3iRue/+GyqBEH4rQB6rKp54NeKr8B5kZWx0KvRjlnEyz44pMc495ZTsr2gJwjPRPIUVfmLQuB6qXOngf5O2E5X9YpXPKURi2UWzpVabHiD1nD7tJoyE8HMYCa7zQOaG45oJOXLBOPpFdppPQIDAQAB";
    public static final String KEY_ALGORITHM = "RSA";

    public PublicKey() {
    }

    public String keyboards(String var1) {
        try {
            AES var2 = new AES();
            SecretKey var3 = var2.geneKey();
            byte[] var4 = var3.getEncoded();
            String var5 = encryptBASE64(var4);
            String var6 = var2.aesCipher(var1, var3);
            byte[] var7 = encryptByPublicKey(var5, this.publickey);
            String var8 = encryptBASE64(var7);
            JSONObject var9 = new JSONObject();
            var9.put("key", var8);
            var9.put("content", var6);
            String var10 = var9.toString();
            return encryptBASE64(var10.getBytes());
        } catch (Exception var11) {
            return "";
        }
    }

    public static byte[] encryptByPublicKey(String var0, String var1) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] var2 = decryptBASE64(var1);
        X509EncodedKeySpec var3 = new X509EncodedKeySpec(var2);
        KeyFactory var4 = KeyFactory.getInstance("RSA");
        java.security.PublicKey var5 = var4.generatePublic(var3);
        Cipher var6 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        var6.init(1, var5);
        return var6.doFinal(var0.getBytes());
    }

    public static byte[] decryptBASE64(String var0) {
        byte[] var1 = Base64.decode(var0.trim(), 0);
        return var1;
    }

    public static String encryptBASE64(byte[] var0) {
        String var1 = Base64.encodeToString(var0, 0);
        return var1;
    }
}

