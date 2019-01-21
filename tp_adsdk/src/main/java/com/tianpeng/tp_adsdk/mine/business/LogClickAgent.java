package com.tianpeng.tp_adsdk.mine.business;

import android.content.Context;

import com.tianpeng.tp_adsdk.mine.config.DataUtil;
import com.tianpeng.tp_adsdk.mine.utils.GetPackageName;
import com.tianpeng.tp_adsdk.mine.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class LogClickAgent {
    public static List<OperationBean> mList = new ArrayList();

    public LogClickAgent() {
    }

    public static void operation(Context var0, OperationBean var1) {
        var1.setPackageName(var0.getPackageName());
        var1.setVersionNo(GetPackageName.getVersionName(var0) + "");
        var1.setMachineId(DataUtil.getMachineId(var0));
        var1.setMachineType(1);
        MyLog.i("fdfsdreerrrrr", var1.toString());
        mList.add(var1);
    }
}

