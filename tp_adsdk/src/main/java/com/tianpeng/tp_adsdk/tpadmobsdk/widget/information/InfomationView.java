package com.tianpeng.tp_adsdk.tpadmobsdk.widget.information;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class InfomationView extends RelativeLayout {
    public InfomationView(Context var1) {
        super(var1);
    }

    protected void onMeasure(int var1, int var2) {
        this.setMeasuredDimension(getDefaultSize(0, var1), getDefaultSize(0, var2));
        int var3 = this.getMeasuredWidth();
        var1 = MeasureSpec.makeMeasureSpec(var3, MeasureSpec.EXACTLY);
        var2 = MeasureSpec.makeMeasureSpec((int)((float)var3 * 0.75F), MeasureSpec.EXACTLY);
        super.onMeasure(var1, var2);
    }
}
