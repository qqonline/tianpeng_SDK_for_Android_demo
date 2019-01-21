package com.tianpeng.tp_adsdk.toutiao.listener;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.toutiao.base.ToastUtil;
import com.tianpeng.tp_adsdk.toutiao.view.ADMobGenInformationView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.information.ADMobGenInformationImp;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdCallBack;

import java.util.List;

/**
 * Created by YuHong on 2019/1/9 0009.
 */
public class TTFeedAdListener implements TTAdNative.FeedAdListener {
    private IADMobGenInformationAdCallBack callBack;
    private IADMobGenAd iadMobGenAd;
    private boolean downloadStart = false;
    private boolean downloadFinish = false;
    private IADMobGenConfiguration configuration;
    private UploadDataBean bean;

    public TTFeedAdListener(IADMobGenAd var1, IADMobGenInformationAdCallBack var2, IADMobGenConfiguration configuration) {
        this.callBack = var2;
        this.iadMobGenAd = var1;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("native");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("toutiao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(iadMobGenAd.getApplicationContext().getPackageName());
    }

    public void onError(int var1, String var2) {
        if (this.checkNotNull()) {
            this.callBack.onADFailed(var2);
        }

    }

    public void onFeedAdLoad(List<TTFeedAd> var1) {
        if (var1 != null && !var1.isEmpty()) {
            TTFeedAd var2 = (TTFeedAd)var1.get(0);
            if (var2 == null) {
                this.callBack.onADFailed("on FeedAdLoaded: ad is null!");
            } else {
                var2.setVideoAdListener(new TTFeedAd.VideoAdListener() {
                    public void onVideoLoad(TTFeedAd var1) {
                    }

                    public void onVideoError(int var1, int var2) {
                    }

                    public void onVideoAdStartPlay(TTFeedAd var1) {
                    }

                    public void onVideoAdPaused(TTFeedAd var1) {
                    }

                    public void onVideoAdContinuePlay(TTFeedAd var1) {
                    }
                });
                if (var2.getInteractionType() == 4) {
                    this.onDownListener(var2);
                }
                if (this.callBack.getIadMobGenInformation() instanceof ADMobGenInformationImp) {
                    ADMobGenInformationImp var3 = (ADMobGenInformationImp)this.callBack.getIadMobGenInformation();
                    var3.setInformationAdType(var2.getInteractionType());
                }

                ADMobGenInformationView var4 = new ADMobGenInformationView(var2, this.callBack);
                this.callBack.onADReceiv(var4);
                bean.setSdkAction("show");
                LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
            }
        } else {
            this.callBack.onADFailed("on FeedAdLoaded: ad is null!");
        }
    }

    private void onDownListener(TTFeedAd var1) {
        var1.setActivityForDownloadApp(this.iadMobGenAd.getActivity());
        var1.setDownloadListener(new TTAppDownloadListener() {
            public void onIdle() {
            }

            public void onDownloadActive(long var1, long var3, String var5, String var6) {
                if (!TTFeedAdListener.this.downloadStart) {
                    TTFeedAdListener.this.downloadStart = true;
                    if (TTFeedAdListener.this.iadMobGenAd != null && TTFeedAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                        ToastUtil.showToast(TTFeedAdListener.this.iadMobGenAd.getApplicationContext(), "开始下载,点击图片暂停", 0);
                    }
                    bean.setSdkAction("down");
                    LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                }

            }

            public void onDownloadPaused(long var1, long var3, String var5, String var6) {
                if (TTFeedAdListener.this.iadMobGenAd != null && TTFeedAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                    ToastUtil.showToast(TTFeedAdListener.this.iadMobGenAd.getApplicationContext(), "下载暂停，点击图片继续", 0);
                }

            }

            public void onDownloadFailed(long var1, long var3, String var5, String var6) {
            }

            public void onInstalled(String var1, String var2) {
                bean.setSdkAction("install");
                LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
            }

            public void onDownloadFinished(long var1, String var3, String var4) {
                if (!TTFeedAdListener.this.downloadFinish) {
                    TTFeedAdListener.this.downloadFinish = true;
                    if (TTFeedAdListener.this.iadMobGenAd != null && TTFeedAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                        ToastUtil.showToast(TTFeedAdListener.this.iadMobGenAd.getApplicationContext(), "下载成功,点击图片安装", 0);
                    }
                }

            }
        });
    }

    public boolean checkNotNull() {
        return this.callBack != null && this.callBack.getIadMobGenInformation() != null && !this.callBack.getIadMobGenInformation().isDestroy();
    }
}

