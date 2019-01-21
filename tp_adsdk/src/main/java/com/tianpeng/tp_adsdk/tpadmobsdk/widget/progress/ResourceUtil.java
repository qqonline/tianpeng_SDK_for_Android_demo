package com.tianpeng.tp_adsdk.tpadmobsdk.widget.progress;

import android.content.res.Resources;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ResourceUtil {
    public static float densityFloat(Resources var0, float var1) {
        float var2 = var0.getDisplayMetrics().density;
        return var1 * var2 + 0.5F;
    }

    public static float scaledDensityFloat(Resources var0, float var1) {
        float var2 = var0.getDisplayMetrics().scaledDensity;
        return var1 * var2;
    }
}
