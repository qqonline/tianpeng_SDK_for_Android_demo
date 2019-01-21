package com.tianpeng.tp_adsdk.toutiao.listener;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenBannerAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class TTBannerAdListener implements TTAdNative.BannerAdListener {
    private final ADMobGenBannerAdListener listener;
    private RelativeLayout layout;
    private IADMobGenAd iadMobGenAd;
    private boolean isDowload = false;
    private boolean isDowloadFinsh = false;
    private IADMobGenConfiguration configuration;
    private UploadDataBean bean;

    public TTBannerAdListener(IADMobGenAd var1, RelativeLayout var2, ADMobGenBannerAdListener var3, IADMobGenConfiguration configuration) {
        this.listener = var3;
        this.layout = var2;
        this.iadMobGenAd = var1;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("banner");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("toutiao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(iadMobGenAd.getApplicationContext().getPackageName());
    }

    public void onError(int var1, String var2) {
        if (this.listener != null) {
            this.listener.onADFailed(var2);
        }

    }

    public void onBannerAdLoad(TTBannerAd var1) {
        if (var1 == null) {
            if (this.listener != null) {
                this.listener.onADFailed("unKnow error");
            }

        } else {
            View var2 = var1.getBannerView();
            if (var2 == null) {
                if (this.listener != null) {
                    this.listener.onADFailed("no view data");
                }

            } else {
                ViewGroup var3 = (ViewGroup)var2;
                if (var3.getChildCount() > 2) {
                    var3.getChildAt(2).setVisibility(View.GONE);
                }

                RelativeLayout.LayoutParams var4 = new RelativeLayout.LayoutParams(-1, -1);
                var2.setLayoutParams(var4);
                this.layout.addView(var2);
                if (this.listener != null) {
                    this.listener.onADReceiv();
                }

                var1.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    public void onAdClicked(View var1, int var2) {
                        if (TTBannerAdListener.this.listener != null) {
                            TTBannerAdListener.this.listener.onADClick();
                        }
                        bean.setSdkAction("click");
                        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                    }

                    public void onAdShow(View var1, int var2) {
                        if (TTBannerAdListener.this.listener != null) {
                            TTBannerAdListener.this.listener.onADExposure();
                        }
                        bean.setSdkAction("show");
                        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                    }
                });
                this.setDownloadListener(var1);
                var1.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
                    public void onSelected(int var1, String var2) {
                        if (TTBannerAdListener.this.listener != null) {
                            TTBannerAdListener.this.listener.onAdClose();
                        }

                    }

                    public void onCancel() {
                    }
                });
            }
        }
    }

    private void setDownloadListener(TTBannerAd var1) {
        var1.setDownloadListener(new TTAppDownloadListener() {
            public void onIdle() {
            }

            public void onDownloadActive(long var1, long var3, String var5, String var6) {
                if (!TTBannerAdListener.this.isDowload) {
                    TTBannerAdListener.this.isDowload = true;
                    if (TTBannerAdListener.this.iadMobGenAd != null && TTBannerAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                        ;
                    }
                    bean.setSdkAction("down");
                    LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                }

            }

            public void onDownloadPaused(long var1, long var3, String var5, String var6) {
                if (TTBannerAdListener.this.iadMobGenAd != null && TTBannerAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                    ;
                }

            }

            public void onDownloadFailed(long var1, long var3, String var5, String var6) {
            }

            public void onInstalled(String var1, String var2) {
                bean.setSdkAction("install");
                LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
            }

            public void onDownloadFinished(long var1, String var3, String var4) {
                if (!TTBannerAdListener.this.isDowloadFinsh) {
                    TTBannerAdListener.this.isDowloadFinsh = true;
                    if (TTBannerAdListener.this.iadMobGenAd != null && TTBannerAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                        ;
                    }
                }

            }
        });
    }
}

