package com.tianpeng.tp_adsdk.mine.utils;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class WebViewUtil {
    public WebViewUtil() {
    }

    public static void destroy(WebView var0) {
        if (var0 != null) {
            try {
                ViewParent var1 = var0.getParent();
                if (var1 != null && var1 instanceof ViewGroup) {
                    ((ViewGroup)var1).removeView(var0);
                }

                var0.stopLoading();
                var0.clearView();
                var0.removeAllViews();
                var0.destroy();
                var0 = null;
            } catch (Exception var2) {
                ;
            }

        }
    }

    public static void destroyAndClearHistory(WebView var0) {
        if (var0 != null) {
            try {
                ViewParent var1 = var0.getParent();
                if (var1 != null && var1 instanceof ViewGroup) {
                    ((ViewGroup)var1).removeView(var0);
                }

                var0.clearHistory();
                var0.stopLoading();
                var0.clearView();
                var0.removeAllViews();
                var0.destroy();
                var0 = null;
            } catch (Exception var2) {
                ;
            }

        }
    }

    public static void clearWebView(WebView var0) {
        if (var0 != null) {
            try {
                ViewParent var1 = var0.getParent();
                if (var1 != null && var1 instanceof ViewGroup) {
                    ((ViewGroup)var1).removeView(var0);
                }

                var0.stopLoading();
                var0.clearView();
                var0.removeAllViews();
            } catch (Exception var2) {
                ;
            }

        }
    }
}

