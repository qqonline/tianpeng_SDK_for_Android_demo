package com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.banner.ADMobGenBannerView;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.ControllerImpTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.Config;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp.ADMobGenBannerAdListenerImp;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ADUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ProxyUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.SDKUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenBannerAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_BANNER;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_BANNER;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.MobADSize.AUTO_HEIGHT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.MobADSize.FULL_WIDTH;

/**
 * Created by YuHong on 2019/1/5 0005.
 */
public class BannerConfig extends Config<ADMobGenBannerView> {
    private ADMobGenBannerView bannerView;
    private Map<String, IADMobGenBannerAdController> map = new ConcurrentHashMap();
    private List<String> list;

    public BannerConfig(IADMobGenConfiguration var1) {
        super(var1);
    }

    public void setView(ADMobGenBannerView var1) {
        this.bannerView = var1;
    }

    public void adDestroy() {
        try {
            if (this.bannerView == null || this.bannerView.isDestroy()) {
                return;
            }

            this.destroyView();
            this.addAllController();
            this.loadADConfig(0);
        } catch (Exception var2) {
            if (this.bannerView.getListener() != null) {
                this.bannerView.getListener().onADFailed("get ad error");
            }
        }

    }

    private void loadADConfig(final int var1) {
        this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_BANNER);
        if ((this.list == null || this.list.size() == 0 || var1 >= this.list.size()) && this.bannerView.getListener() != null) {
            this.bannerView.getListener().onADFailed("platform is empty");
        } else {
            if (this.bannerView != null) {
                this.bannerView.removeAllViews();
            }

            String var2 = (String) this.list.get(var1);
            IADMobGenConfiguration var3 = SDKUtil.getInstance().getConfig(var2);
            if (var3 == null && this.bannerView.getListener() != null) {
                this.bannerView.getListener().onADFailed("getConfiguration is empty");
            } else {
                final IADMobGenBannerAdController var4 = (IADMobGenBannerAdController) this.map.get(var2);
                if (var4 == null) {
                    if (this.bannerView.getListener() != null) {
                        this.bannerView.getListener().onADFailed(var2 + "'s controller is empty");
                    }

                } else {
                    RelativeLayout var5 = var4.createBannerContainer(this.bannerView);
                    ViewGroup.LayoutParams var6 = var5.getLayoutParams();
                    if (var6 != null) {
                        var6.height = AUTO_HEIGHT;
                        var6.width = FULL_WIDTH;
                        var5.setLayoutParams(var6);
                    }

                    boolean var7 = var4.loadAd(this.bannerView, var5, var3, true, new ADMobGenBannerAdListenerImp(this.bannerView, var3, false) {
                        public void onADFailed(String var1x) {
                            LogTool.show(this.sdkName + "'banner filed :" + var1x);
                            if (var1 + 1 >= BannerConfig.this.list.size()) {
                                super.onADFailed(var1x);
                            } else {
                                if (var4 != null) {
                                    var4.destroyAd();
                                }

                                if (this.configuration != null) {
                                    this.configuration = null;
                                }

                                if (BannerConfig.this.bannerView != null) {
                                    BannerConfig.this.bannerView.removeAllViews();
                                }

                                BannerConfig.this.loadADConfig(var1 + 1);
                            }
                        }
                    });
                    if (var1 == 0) {
                        this.load(var3);
                    }

                    if (!var7 && this.bannerView.getListener() != null) {
                        this.bannerView.getListener().onADFailed("load banner ad failed");
                    }

                }
            }
        }
    }

    protected void destroy() {
        this.destroyView();
    }

    private void addAllController() {
        String[] var1 = TPADMobSDK.instance().getPlatforms();
        if (var1 != null && var1.length > 0) {
            String[] platforms = var1;
            int size = var1.length;

            for (int i = 0; i < size; ++i) {
                String platform = platforms[i];
                IADMobGenBannerAdController controller = (IADMobGenBannerAdController) ControllerImpTool.getClassInstance(ControllerImpTool.getBannerAdControllerImp(platform));
                if (controller != null) {
                    this.map.put(platform, controller);
                }
            }
        }

    }

    private void load(IADMobGenConfiguration var1) {
        if (var1 != null && ProxyUtil.isSleep()) {
            ADUtil.getInstance().loadAdMobShowAd(this.bannerView.getContext(), TYPE_BANNER, STR_TYPE_BANNER);
        }
    }

    private void destroyView() {
        try {
            Iterator var1 = this.map.entrySet().iterator();

            while (var1.hasNext()) {
                Map.Entry var2 = (Map.Entry) var1.next();
                IADMobGenBannerAdController var3 = (IADMobGenBannerAdController) var2.getValue();
                if (var3 != null) {
                    var3.destroyAd();
                }
            }

            this.map.clear();
        } catch (Exception var4) {
            ;
        }

    }
}
