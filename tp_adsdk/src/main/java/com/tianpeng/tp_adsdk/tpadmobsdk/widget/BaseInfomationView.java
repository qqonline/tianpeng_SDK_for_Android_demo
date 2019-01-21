package com.tianpeng.tp_adsdk.tpadmobsdk.widget;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.CSSStyleChange;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.ADMobGenInformationEntity;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_ADMOB;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_BAIDU;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_TOUTIAO;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.MobADSize.FULL_WIDTH;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public abstract class BaseInfomationView<T, E extends IADMobGenAd, R extends ADMobGenAdListener> extends RelativeLayout {
    private T data;
    private E mobad;
    private R listener;
    protected WebView webView;
    protected View view;
    protected boolean isWeb;
    protected int adType = 0;
    protected String platfromstr = "";

    public BaseInfomationView(Context var1, boolean isWeb, boolean var3) {
        super(var1);
        this.isWeb = isWeb;
        this.showData(isWeb, var3);
    }

    private void showData(boolean isWeb, boolean var2) {
        if (isWeb) {
            this.webView = new WebView(this.getContext().getApplicationContext());
            this.webView.setHorizontalScrollBarEnabled(false);
            this.webView.setVerticalScrollBarEnabled(false);
            this.webView.setScrollBarStyle(View.ACCESSIBILITY_LIVE_REGION_NONE);
            WebSettings var3 = this.webView.getSettings();
            var3.setJavaScriptEnabled(true);
            var3.setCacheMode(WebSettings.MENU_ITEM_NONE);
            var3.setDomStorageEnabled(true);
            var3.setDefaultTextEncodingName("UTF-8");
            var3.setSupportZoom(false);
            var3.setBuiltInZoomControls(false);
            var3.setLoadsImagesAutomatically(true);
            if (Build.VERSION.SDK_INT >= 7) {
                var3.setDomStorageEnabled(true);
            }

            if (Build.VERSION.SDK_INT >= 21) {
                this.webView.getSettings().setMixedContentMode(2);
            }

            RelativeLayout.LayoutParams var4 = new LayoutParams(FULL_WIDTH, FULL_WIDTH);
            this.webView.setLayoutParams(var4);
            this.webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView var1, String var2) {
                    super.onPageFinished(var1, var2);
                    BaseInfomationView.this.onRenderFinish();
                }

                public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                    BaseInfomationView.this.loadWebView();
                }
            });
            this.addView(this.webView);
        }

        if (var2) {
            this.view = new View(this.getContext());
            this.view.setLayoutParams(new LayoutParams(FULL_WIDTH, FULL_WIDTH));
            this.addView(this.view);
        }

    }

    public void setInfromationAdType(int var1) {
        this.adType = var1;
    }

    public void setPlatfromstr(String var1) {
        this.platfromstr = var1;
    }

    private void loadWebView() {
        if (this.webView != null && this.mobad != null && !this.mobad.isDestroy()) {
            this.webView.clearCache(true);
            this.webView.loadDataWithBaseURL((String)null, "<html xmlns=\"http://www.w3.org/1999/xhtml\"> <body style=\"margin: 0; padding: 0\" ><text width=\"1px\" height=\"1px\"/></body></html>", "text/html", "utf-8", (String)null);
        }

    }

    public void onRenderFinish() {
    }

    protected void loadWebView(String var1) {
        if (this.webView != null) {
            this.webView.clearCache(true);
            this.webView.loadDataWithBaseURL((String)null, "<html xmlns=\"http://www.w3.org/1999/xhtml\"> <body style=\"margin: 0; padding: 0\" ><img style=\"object-fit:cover\" width=\"100%\" height=\"100%\" src=\"" + var1 + "\"/></body></html>", "text/html", "utf-8", (String)null);
        }
    }

    protected void addLogo(ADMobGenInformationEntity var1) {
        if (this.webView != null && var1 != null) {
            this.webView.clearCache(true);
            String var2 = "";
            if (this.platfromstr.equals(PLAFORM_ADMOB)) {
                var2 = CSSStyleChange.getADMobLogo();
            } else if (this.platfromstr.equals(PLAFORM_TOUTIAO)) {
                var2 = CSSStyleChange.getPangolinLogo();
            } else if (this.platfromstr.equals(PLAFORM_BAIDU)) {
                var2 = CSSStyleChange.getBDADLogo();
            }

            switch(this.adType) {
                case 1:
                    this.loadDataWithBaseURL(CSSStyleChange.b(var2, var1.getImageUrl(), var1.getSubTitle(), var1.getTitle()));
                    break;
                case 2:
                default:
                    this.loadDataWithBaseURL(CSSStyleChange.a(var2, var1.getImageUrl(), var1.getSubTitle(), var1.getTitle()));
                    break;
                case 3:
                    this.loadDataWithBaseURL(CSSStyleChange.c(var2, var1.getImageUrl(), var1.getSubTitle(), var1.getTitle()));
                    break;
                case 4:
                    this.loadDataWithBaseURL(CSSStyleChange.d(var2, var1.getImageUrl(), var1.getSubTitle(), var1.getTitle()));
                    break;
                case 5:
                    this.loadDataWithBaseURL(CSSStyleChange.e(var2, var1.getImageUrl(), var1.getSubTitle(), var1.getTitle()));
                    break;
                case 6:
                    this.loadDataWithBaseURL(CSSStyleChange.f(var2, var1.getImageUrl(), var1.getSubTitle(), var1.getTitle()));
            }

        }
    }

    private void loadDataWithBaseURL(String var1) {
        this.webView.loadDataWithBaseURL((String)null, var1, "text/html", "utf-8", (String)null);
    }

    public void destroy() {
        try {
            if (this.webView != null) {
                ViewParent var1 = this.webView.getParent();
                if (var1 != null && var1 instanceof ViewGroup) {
                    ((ViewGroup)var1).removeAllViews();
                }

                this.webView.setVisibility(GONE);
                this.webView.clearHistory();
                this.webView.clearView();
                this.webView.removeAllViews();
                this.webView.destroy();
                this.webView = null;
            }

            this.data = null;
            this.mobad = null;
            this.listener = null;
        } catch (Exception var2) {
            ;
        }

    }

    public void setAdMobGenAd(E var1) {
        this.mobad = var1;
    }

    public void setAdMobGenAdListener(R var1) {
        this.listener = var1;
    }

    public void setData(T var1) {
        this.data = var1;
    }

    protected T getData() {
        return this.data;
    }

    protected E getAdMobGenAd() {
        return this.mobad;
    }

    protected R getAdMobGenAdListener() {
        return this.listener;
    }

    public WebView getWebView() {
        return this.webView;
    }

    public View getTopClickView() {
        return this.view;
    }

    protected abstract String getLogTag();

    public abstract void showAd(T var1);

    public abstract String showImage(T var1);

    public abstract boolean clickAdImp(T var1, View var2);
}

