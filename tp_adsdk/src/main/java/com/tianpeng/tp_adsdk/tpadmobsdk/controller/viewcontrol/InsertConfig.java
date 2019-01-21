package com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol;

import android.os.Handler;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.insert.ADMobGenInsertView;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.ControllerImpTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.Config;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp.ADMobGenInsertAdListenerImp;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ADUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ProxyUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.SDKUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInsertitailAdController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INSERT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INSERT;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class InsertConfig extends Config<ADMobGenInsertView> {
    private ADMobGenInsertView view;
    private Handler handler = new Handler();
    private Map<String, IADMobGenInsertitailAdController> controllerMap = new ConcurrentHashMap();
    private List<String> list;
    private Long currentTime;

    public InsertConfig(IADMobGenConfiguration var1) {
        super(var1);
    }

    public void setView(ADMobGenInsertView var1) {
        this.view = var1;
    }

    public void adDestroy() {
        try {
            if (view == null || this.view.isDestroy()) {
                view.getListener().onADFailed("ADMobGenSplashView is destroy");
                return;
            }

            this.destroryAll();
            this.initMap();
            this.currentTime = System.currentTimeMillis();
            this.load(0);
        } catch (Exception var2) {
            if (view.getListener() != null) {
                view.getListener().onADFailed("get ad error：");
            }
        }

    }

    private void initMap() {
        String[] var1 = TPADMobSDK.instance().getPlatforms();
        if (var1 != null && var1.length > 0) {
            String[] var2 = var1;
            int var3 = var1.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String var5 = var2[var4];
                IADMobGenInsertitailAdController var6 = ControllerImpTool.getClassInstance(ControllerImpTool.getInsertAdControllerImp(var5));
                if (var6 != null) {
                    this.controllerMap.put(var5, var6);
                }
            }
        }

    }

    private void load(final int var1) {
        if (System.currentTimeMillis() - this.currentTime > 3000L) {
            if (view.getListener() != null) {
                view.getListener().onADFailed("get ad time out");
            }

        } else {
            this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INSERT);
            if (this.list != null && this.list.size() != 0) {
                if (var1 >= this.list.size()) {
                    if (view.getListener() != null) {
                        view.getListener().onADFailed("no ad error");
                    }

                } else {
                    String var2 = (String) this.list.get(var1);
                    final IADMobGenConfiguration configuration = SDKUtil.getInstance().getConfig(var2);
                    if (configuration == null) {
                        if (view.getListener() != null) {
                            view.getListener().onADFailed("configuration is empty");
                        }

                    } else {
                        final IADMobGenInsertitailAdController controller = this.controllerMap.get(var2);
                        if (controller == null) {
                            if (view.getListener() != null) {
                                view.getListener().onADFailed(var2 + "'s controller is empty");
                            }

                        } else {
                            boolean var1x = controller.loadAd(InsertConfig.this.view, configuration,false, new ADMobGenInsertAdListenerImp(InsertConfig.this.view, configuration, false) {
                                public void onADFailed(String failMsg) {
                                    LogTool.show(this.sdkName + "'insert filed :" + failMsg);
                                    if (var1 + 1 >= InsertConfig.this.list.size()) {
                                        super.onADFailed(failMsg);
                                    } else {
                                        if (InsertConfig.this.view != null) {
                                            InsertConfig.this.view.removeAllViews();
                                        }

                                        if (controller != null) {
                                            controller.destroyAd();
                                        }

                                        InsertConfig.this.load(var1 + 1);
                                    }
                                }
                            });
//                            if(var1x){
//                                controller.showAD();
//                            }
                            if (var1 == 0) {
                                InsertConfig.this.loadAdMobShowAd(configuration);
                            }

                            if (!var1x && InsertConfig.this.view.getListener() != null) {
                                InsertConfig.this.view.getListener().onADFailed("load splash ad failed");
                            }

                            this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (InsertConfig.this.view != null && !InsertConfig.this.view.isDestroy()
                                            && InsertConfig.this.view.getListener() != null) {
                                        InsertConfig.this.view.getListener().onADFailed("view post error");
                                    }

                                }
                            }, 4000L);
                        }
                    }
                }
            }
        }
}

    private void loadAdMobShowAd(IADMobGenConfiguration var1) {
        if (var1 != null && ProxyUtil.isSleep()) {
            ADUtil.getInstance().loadAdMobShowAd(view.getContext(), TYPE_INSERT, STR_TYPE_INSERT);
        }
    }

    protected void destroy() {
        this.destroryAll();
    }

    private void destroryAll() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages((Object) null);
        }

        this.destroryView();
    }

    private void destroryView() {
        try {
            Iterator var1 = this.controllerMap.entrySet().iterator();

            while (var1.hasNext()) {
                Map.Entry var2 = (Map.Entry) var1.next();
                IADMobGenInsertitailAdController var3 = (IADMobGenInsertitailAdController) var2.getValue();
                if (var3 != null) {
                    var3.destroyAd();
                }
            }

            this.controllerMap.clear();
        } catch (Exception var4) {
            ;
        }

    }
}
