package com.tianpeng.tp_adsdk.tpadmobsdk.widget;

import android.content.Context;
import android.view.View;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public class BaseSplashView extends View {
    public BaseSplashView(Context var1) {
        super(var1);
    }

    protected void onMeasure(int var1, int var2) {
        int var3 = (int)(this.getResources().getDisplayMetrics().density * 5.0F);
        this.setMeasuredDimension(getDefaultSize(var3, var1), getDefaultSize(var3, var2));
        int var4 = this.getMeasuredWidth();
        int var5 = this.getMeasuredHeight();
        var1 = MeasureSpec.makeMeasureSpec(var4, MeasureSpec.EXACTLY);//1073741824
        var2 = MeasureSpec.makeMeasureSpec(var5, MeasureSpec.EXACTLY);
        super.onMeasure(var1, var2);
    }
}

