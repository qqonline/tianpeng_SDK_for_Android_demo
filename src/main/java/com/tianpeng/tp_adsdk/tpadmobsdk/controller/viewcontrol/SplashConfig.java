package com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol;

import android.os.Handler;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.splash.ADMobGenSplashView;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.ControllerImpTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.Config;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp.ADMobGenSplashAdListenerImp;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ADUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ProxyUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.SDKUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenSplashAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.widget.BaseSplashView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_SPLASH;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_SPLASH;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class SplashConfig extends Config<ADMobGenSplashView> {
    private ADMobGenSplashView splashView;
    private BaseSplashView baseSplashView;
    private Handler handler = new Handler();
    private Map<String, IADMobGenSplashAdController> mobGenSplashAdControllerMap = new ConcurrentHashMap();
    private List<String> list;
    private Long currentTime;

    public SplashConfig(IADMobGenConfiguration var1) {
        super(var1);
    }

    public void setView(ADMobGenSplashView var1) {
        this.splashView = var1;
    }

    public void adDestroy() {
        try {
            if (this.splashView == null || this.splashView.isDestroy()) {
                this.splashView.getListener().onADFailed("ADMobGenSplashView is destroy");
                return;
            }

            this.destroryAll();
            this.initMap();
            this.currentTime = System.currentTimeMillis();
            this.load(0);
        } catch (Exception var2) {
            if (this.splashView.getListener() != null) {
                this.splashView.getListener().onADFailed("get ad error：");
            }
        }

    }

    private void initSpalshView() {
        if (this.baseSplashView == null) {
            this.baseSplashView = new BaseSplashView(this.splashView.getApplicationContext());
        } else {
            ViewParent var1 = this.baseSplashView.getParent();
            if (var1 != null && var1 instanceof ViewGroup) {
                ((ViewGroup)var1).removeView(this.baseSplashView);
            }
        }

        this.splashView.addView(this.baseSplashView);
        ViewGroup.LayoutParams var4 = this.baseSplashView.getLayoutParams();
        if (var4 != null && var4 instanceof android.widget.RelativeLayout.LayoutParams) {
            int var2 = (int)(this.splashView.getApplicationContext().getResources().getDisplayMetrics().density * 15.0F);
            android.widget.RelativeLayout.LayoutParams var3 = (android.widget.RelativeLayout.LayoutParams)var4;
            var3.addRule(11);
            var3.topMargin = var2;
            var3.rightMargin = var2;
        }

    }

    private void initMap() {
        String[] var1 = TPADMobSDK.instance().getPlatforms();
        if (var1 != null && var1.length > 0) {
            String[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String var5 = var2[var4];
                IADMobGenSplashAdController var6 = (IADMobGenSplashAdController) ControllerImpTool.getClassInstance(ControllerImpTool.getSplashAdControllerImp(var5));
                if (var6 != null) {
                    this.mobGenSplashAdControllerMap.put(var5, var6);
                }
            }
        }

    }

    private void load(final int var1) {
        if (System.currentTimeMillis() - this.currentTime > 3000L) {
            if (this.splashView.getListener() != null) {
                this.splashView.getListener().onADFailed("get ad time out");
            }

        } else {
            this.initSpalshView();
            this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_SPLASH);
            if (this.list != null && this.list.size() != 0) {
                if (var1 >= this.list.size()) {
                    if (this.splashView.getListener() != null) {
                        this.splashView.getListener().onADFailed("no ad error");
                    }

                } else {
                    String var2 = (String)this.list.get(var1);
                    final IADMobGenConfiguration configuration = SDKUtil.getInstance().getConfig(var2);
                    if (configuration == null) {
                        if (this.splashView.getListener() != null) {
                            this.splashView.getListener().onADFailed("configuration is empty");
                        }

                    } else {
                        final IADMobGenSplashAdController controller = (IADMobGenSplashAdController)this.mobGenSplashAdControllerMap.get(var2);
                        if (controller == null) {
                            if (this.splashView.getListener() != null) {
                                this.splashView.getListener().onADFailed(var2 + "'s controller is empty");
                            }

                        } else {
                            final RelativeLayout relativeLayout = controller.createSplashContainer(this.splashView, true);
                            if (relativeLayout == null) {
                                if (this.splashView.getListener() != null) {
                                    this.splashView.getListener().onADFailed("create splash error");
                                }

                            } else {
                                this.splashView.addView(relativeLayout);
                                relativeLayout.post(new Runnable() {
                                    public void run() {
                                        if (SplashConfig.this.handler != null) {
                                            SplashConfig.this.handler.removeCallbacksAndMessages((Object)null);
                                        }

                                        boolean var1x = controller.loadAd(SplashConfig.this.splashView, relativeLayout, configuration, new ADMobGenSplashAdListenerImp(SplashConfig.this.splashView, configuration, false) {
                                            public void onADFailed(String failMsg) {
                                                LogTool.show(this.sdkName + "'startup filed :" + failMsg);
                                                if (var1 + 1 >= SplashConfig.this.list.size()) {
                                                    super.onADFailed(failMsg);
                                                } else {
                                                    if (SplashConfig.this.splashView != null) {
                                                        SplashConfig.this.splashView.removeAllViews();
                                                    }

                                                    if (controller != null) {
                                                        controller.destroyAd();
                                                    }

                                                    SplashConfig.this.load(var1 + 1);
                                                }
                                            }
                                        }, SplashConfig.this.baseSplashView);
                                        if (var1 == 0) {
                                            SplashConfig.this.loadAdMobShowAd(configuration);
                                        }

                                        if (!var1x && SplashConfig.this.splashView.getListener() != null) {
                                            SplashConfig.this.splashView.getListener().onADFailed("load splash ad failed");
                                        }

                                    }
                                });
                                this.handler.postDelayed(new Runnable() {
                                    public void run() {
                                        if (SplashConfig.this.splashView != null && !SplashConfig.this.splashView.isDestroy() && SplashConfig.this.splashView.getListener() != null) {
                                            SplashConfig.this.splashView.getListener().onADFailed("view post error");
                                        }

                                    }
                                }, 4000L);
                            }
                        }
                    }
                }
            } else {
                if (this.splashView.getListener() != null) {
                    this.splashView.getListener().onADFailed("platform is empty");
                }

            }
        }
    }

    private void loadAdMobShowAd(IADMobGenConfiguration var1) {
        if (var1 != null && ProxyUtil.isSleep()) {
            ADUtil.getInstance().loadAdMobShowAd(this.splashView.getContext(), TYPE_SPLASH, STR_TYPE_SPLASH);
        }
    }

    protected void destroy() {
        this.destroryAll();
    }

    private void destroryAll() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages((Object)null);
        }

        this.destroryView();
    }

    private void destroryView() {
        try {
            Iterator var1 = this.mobGenSplashAdControllerMap.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry var2 = (Map.Entry)var1.next();
                IADMobGenSplashAdController var3 = (IADMobGenSplashAdController)var2.getValue();
                if (var3 != null) {
                    var3.destroyAd();
                }
            }

            this.mobGenSplashAdControllerMap.clear();
        } catch (Exception var4) {
            ;
        }

    }
}
