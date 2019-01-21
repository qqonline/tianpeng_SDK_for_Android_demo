package com.tianpeng.tp_adsdk.toutiao.listener;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.tianpeng.tp_adsdk.BuildConfig;
import com.tianpeng.tp_adsdk.mine.business.UploadDataBean;
import com.tianpeng.tp_adsdk.mine.config.LogAnalysisConfig;
import com.tianpeng.tp_adsdk.toutiao.base.ToastUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.IADMobGenAd;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class TTInsertAdListener implements TTAdNative.InteractionAdListener {
    private final ADMobGenInsertitailAdListener listener;
    IADMobGenAd iadMobGenAd;
    private boolean downloadStart = false;
    private boolean downloadFinish = false;
    private UploadDataBean bean;
    private IADMobGenConfiguration configuration;
    public TTInsertAdListener( IADMobGenAd iadMobGenAd,ADMobGenInsertitailAdListener listener,IADMobGenConfiguration configuration) {
        this.iadMobGenAd = iadMobGenAd;
        this.listener = listener;
        this.configuration = configuration;
        bean = new UploadDataBean();
        bean.setAdId(configuration.getBannerId());
        bean.setAdType("flow");
        bean.setAppId(configuration.getAppId());
        bean.setAppType(TPADMobSDK.instance().getAppId());
        bean.setSdkName("toutiao");
        bean.setSdkVersion(BuildConfig.VERSION_NAME);
        bean.setPackageName(iadMobGenAd.getApplicationContext().getPackageName());
    }

    @Override
    public void onError(int i, String s) {
        if (this.listenerNotNull()) {
            this.listener.onADFailed(s);
        }
    }

    @Override
    public void onInteractionAdLoad(TTInteractionAd ttInteractionAd) {
        if (this.listenerNotNull()) {
            this.listener.onADReceiv();
        }
        if (ttInteractionAd == null) {
            if (this.listenerNotNull()) {
                this.listener.onADFailed("unKnow error");
            }
        }else {
            ttInteractionAd.setAdInteractionListener(new TTInteractionAd.AdInteractionListener() {
                @Override
                public void onAdClicked() {
                    if (listenerNotNull()) {
                        listener.onADClick();
                    }
                    bean.setSdkAction("click");
                    LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                }

                @Override
                public void onAdShow() {
                    if (listenerNotNull()) {
                        listener.onADExposure();
                    }
                    bean.setSdkAction("show");
                    LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                }

                @Override
                public void onAdDismiss() {
                    if (listenerNotNull()) {
                        listener.onAdClose();
                    }
                }
            });
            //如果是下载类型的广告，可以注册下载状态回调监听
            if (ttInteractionAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                ttInteractionAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!downloadStart) {
                            downloadStart = true;
                            if (iadMobGenAd != null && iadMobGenAd.getApplicationContext() != null) {
                                ToastUtil.showToast(TTInsertAdListener.this.iadMobGenAd.getApplicationContext(), "开始下载,点击图片暂停", 0);
                            }
                            bean.setSdkAction("down");
                            LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        if (TTInsertAdListener.this.iadMobGenAd != null && TTInsertAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                            ToastUtil.showToast(TTInsertAdListener.this.iadMobGenAd.getApplicationContext(), "下载暂停，点击图片继续", 0);
                        }
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        if (!TTInsertAdListener.this.downloadFinish) {
                            TTInsertAdListener.this.downloadFinish = true;
                            if (TTInsertAdListener.this.iadMobGenAd != null && TTInsertAdListener.this.iadMobGenAd.getApplicationContext() != null) {
                                ;
                            }
                        }
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        bean.setSdkAction("install");
                        LogAnalysisConfig.getInstance().uploadStatus(iadMobGenAd.getApplicationContext(),bean);
                    }
                });
                //弹出插屏广告
                ttInteractionAd.showInteractionAd(iadMobGenAd.getActivity());
            }
        }
    }

    public boolean listenerNotNull() {
        return this.listener != null;
    }
}
