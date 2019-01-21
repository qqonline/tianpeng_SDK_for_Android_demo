package com.tianpeng.tp_adsdk.toutiao.base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class ToastUtil {
    private static Toast toast;

    public ToastUtil() {
    }

    public static void showToast(Context var0, String var1, int var2) {
        Toast var3 = showToast(var0);
        if (var3 != null) {
            var3.setDuration(var2);
            var3.setText(String.valueOf(var1));
            var3.show();
        } else {
            Log.i("TToast", "toast msg: " + var1);
        }

    }

    private static Toast showToast(Context var0) {
        if (var0 == null) {
            return toast;
        } else {
            if (toast == null) {
                Class var1 = ToastUtil.class;
                synchronized(ToastUtil.class) {
                    if (toast == null) {
                        toast = Toast.makeText(var0.getApplicationContext(), "", Toast.LENGTH_SHORT);
                    }
                }
            }

            return toast;
        }
    }
}

