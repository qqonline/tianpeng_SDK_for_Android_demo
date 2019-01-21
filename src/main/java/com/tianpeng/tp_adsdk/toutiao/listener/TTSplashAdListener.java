package com.tianpeng.tp_adsdk.toutiao.listener;

import android.view.View;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.toutiao.base.ToastUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenSplashAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class TTSplashAdListener implements TTAdNative.SplashAdListener {
    private final ADMobGenSplashAdListener listener;
    RelativeLayout layout;
    IADMobGenAd iadMobGenAd;
    private boolean downloadStart = false;
    private boolean downloadFinish = false;
    private UploadDataBean bean;

    private IADMobGenConfiguration configuration;
    public TTSplashAdListener(IADMobGenAd var1, RelativeLayout var2, ADMobGenSplashAdListener var3, IADMobGenConfiguration configuration) {
        this.listener = var3;
        this.layout = var2;
        this.iadMobGenAd = var1;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("open");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("toutiao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(iadMobGenAd.getApplicationContext().getPackageName());
    }

    public boolean listenerNotNull() {
        return this.listener != null;
    }

    public void onError(int var1, String var2) {
        if (this.listenerNotNull()) {
            this.listener.onADFailed(var2);
        }

    }

    public void onTimeout() {
        if (this.listenerNotNull()) {
            this.listener.onADFailed("get ad time out");
        }

    }

    public void onSplashAdLoad(TTSplashAd var1) {
        if (this.listenerNotNull()) {
            this.listener.onADReceiv();
        }

        if (var1 == null) {
            if (this.listenerNotNull()) {
                this.listener.onADFailed("unKnow error");
            }

        } else {
            View var2 = var1.getSplashView();
            if (var2 == null) {
                if (this.listenerNotNull()) {
                    this.listener.onADFailed("no view error");
                }

            } else {
                this.layout.addView(var2);
                this.setDownloadListener(var1);
                var1.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    public void onAdClicked(View var1, int var2) {
                        if (TTSplashAdListener.this.listenerNotNull()) {
                            TTSplashAdListener.this.listener.onADClick();
                        }
                        bean.setSdkAction("click");
                        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                    }

                    public void onAdShow(View var1, int var2) {
                        if (TTSplashAdListener.this.listenerNotNull()) {
                            TTSplashAdListener.this.listener.onADExposure();
                        }
                        bean.setSdkAction("show");
                        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                    }

                    public void onAdSkip() {
                        if (TTSplashAdListener.this.listenerNotNull()) {
                            TTSplashAdListener.this.listener.onAdClose();
                        }

                    }

                    public void onAdTimeOver() {
                        if (TTSplashAdListener.this.listenerNotNull()) {
                            TTSplashAdListener.this.listener.onAdClose();
                        }

                    }
                });
            }
        }
    }

    private void setDownloadListener(TTSplashAd var1) {
        var1.setDownloadListener(new TTAppDownloadListener() {
            public void onIdle() {
            }

            public void onDownloadActive(long var1, long var3, String var5, String var6) {
                if (!TTSplashAdListener.this.downloadStart) {
                    TTSplashAdListener.this.downloadStart = true;
                    if (TTSplashAdListener.this.iadMobGenAd != null && TTSplashAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                        ToastUtil.showToast(TTSplashAdListener.this.iadMobGenAd.getApplicationContext(), "开始下载,点击图片暂停", 0);
                    }
                    bean.setSdkAction("down");
                    LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                }
            }

            public void onDownloadPaused(long var1, long var3, String var5, String var6) {
                if (TTSplashAdListener.this.iadMobGenAd != null && TTSplashAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                    ToastUtil.showToast(TTSplashAdListener.this.iadMobGenAd.getApplicationContext(), "下载暂停，点击图片继续", 0);
                }

            }

            public void onDownloadFailed(long var1, long var3, String var5, String var6) {
            }

            public void onInstalled(String var1, String var2) {
                bean.setSdkAction("install");
                LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
            }

            public void onDownloadFinished(long var1, String var3, String var4) {
                if (!TTSplashAdListener.this.downloadFinish) {
                    TTSplashAdListener.this.downloadFinish = true;
                    if (TTSplashAdListener.this.iadMobGenAd != null && TTSplashAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                        ;
                    }
                }

            }
        });
    }
}

