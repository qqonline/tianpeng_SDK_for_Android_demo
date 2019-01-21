package com.tianpeng.tp_adsdk.toutiao.view;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class TTCommView extends RelativeLayout {
    private int width;
    private int height;

    public TTCommView(Context var1) {
        super(var1);
    }

    public TTCommView(Context var1, int var2, int var3) {
        super(var1);
        this.width = var2;
        this.height = var3;
    }

    protected void onMeasure(int var1, int var2) {
        this.setMeasuredDimension(getDefaultSize(0, var1), getDefaultSize(0, var2));
        int var3 = this.getMeasuredWidth();
        boolean var4 = false;
        int var5;
        if (this.height > 0 && this.width > 0) {
            var5 = var3 * this.height / this.width;
        } else {
            var5 = var3 * 5 / 32;
        }

        var1 = MeasureSpec.makeMeasureSpec(var3, MeasureSpec.EXACTLY);
        var2 = MeasureSpec.makeMeasureSpec(var5, MeasureSpec.EXACTLY);
        super.onMeasure(var1, var2);
    }
}

