package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

import android.os.Handler;
import android.webkit.JavascriptInterface;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class AndroidJs {
    private final Handler mHandler;
    private AndroidJsCallBack androidJsCallBack;

    public AndroidJs(Handler var1) {
        this.mHandler = var1;
    }

    @JavascriptInterface
    public void adClick() {
        if (this.mHandler != null && this.androidJsCallBack != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    AndroidJs.this.androidJsCallBack.onAdClick();
                }
            });
        }

    }

    @JavascriptInterface
    public void adCloseClick() {
        if (this.mHandler != null && this.androidJsCallBack != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    AndroidJs.this.androidJsCallBack.onAdCloseClick();
                }
            });
        }

    }

    public void setAndroidJsCallBack(AndroidJsCallBack var1) {
        this.androidJsCallBack = var1;
    }
}

