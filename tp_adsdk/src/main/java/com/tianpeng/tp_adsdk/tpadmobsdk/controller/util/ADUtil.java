package com.tianpeng.tp_adsdk.tpadmobsdk.controller.util;

import android.content.Context;

import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;

import java.util.Iterator;
import java.util.List;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ADUtil {
    private static ADUtil instance;

    private ADUtil() {
    }

    public static ADUtil getInstance() {
        if (instance == null) {
            Class var0 = ADUtil.class;
            synchronized(ADUtil.class) {
                if (instance == null) {
                    instance = new ADUtil();
                }
            }
        }

        return instance;
    }

    public void loadAdMobShowAd(Context var1, int var2, String var3) {
        List var4 = EntityUtil.getInstance().getShowList(var2);
        if (var4 != null && var4.size() != 0) {
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                String var6 = (String)var5.next();
                LogTool.show("show platform :" + var6);
            }

//            IADMobGenShowAdController var7 = SDKUtil.getInstance().getController();
//            if (var4.contains(PLAFORM_ADMOB) && var7 != null) {
//                LogTool.show("load admob show");
//                var7.loadAdMobShowAd(var1, var3);
//            }

        }
    }
}
