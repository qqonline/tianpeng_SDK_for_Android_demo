package com.tianpeng.tp_adsdk.tpadmobsdk.controller.viewcontrol;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.ADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.ControllerImpTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.PremissTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.Config;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.information.ADMobGenInformationImp;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.imp.IADMobGenInformationAdCallBackIml;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ADUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.ProxyUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.SDKUtil;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms.PLAFORM_GDT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_BOTTOMPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_LEFTPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_ONLY_IMAGE;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_RIGHTPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_VERTICALPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType.BOTTOM_IMAGE;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType.LEFT_IMAGE;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType.ONLY_IMAGE;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType.RIGHT_IMAGE;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType.VERTICALPICFLOW;

//import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_TOPPICFLOW;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class InformationConfig extends Config<ADMobGenInformation> {
    private ADMobGenInformation information;
    private Map<String, IADMobGenInformationAdController> map = new ConcurrentHashMap();
    private List<String> list;

    public InformationConfig(IADMobGenConfiguration var1) {
        super(var1);
        this.initMap();
    }

    public void setView(ADMobGenInformation var1) {
        this.information = var1;
    }

    public void adDestroy() {
        try {
            this.getListForType();
        } catch (Exception var2) {
            if (this.information.getListener() != null) {
                this.information.getListener().onADFailed("get ad error");
            }
        }

    }

    private void initMap() {
        String[] var1 = TPADMobSDK.instance().getPlatforms();
        if (var1 != null && var1.length > 0) {
            String[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String var5 = var2[var4];
                IADMobGenInformationAdController var6 = (IADMobGenInformationAdController) ControllerImpTool.getClassInstance(ControllerImpTool.getInformationAdControllerImp(var5));
                if (var6 != null) {
                    this.map.put(var5, var6);
                }
            }
        }

    }

    private void getListForType() {
        if (this.information != null && !this.information.isDestroy()) {
            if (this.list != null) {
                this.list = null;
            }

            switch(this.information.getInformationAdType()) {
                case BOTTOM_IMAGE:
                    this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INFORMATION_BOTTOMPICFLOW);
                    break;
                case 2:
                default:
                    this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INFORMATION);
                    break;
                case LEFT_IMAGE:
                    this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INFORMATION_LEFTPICFLOW);
                    break;
                case RIGHT_IMAGE:
                    this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INFORMATION_RIGHTPICFLOW);
                    break;
                case VERTICALPICFLOW:
                    this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INFORMATION_VERTICALPICFLOW);
                    break;
                case ONLY_IMAGE:
                    this.list = SDKUtil.getInstance().getEntity().getEntityList(TYPE_INFORMATION_ONLY_IMAGE);
            }

            if (this.list != null && this.list.size() != 0) {
                this.loadADConfig(0);
            } else {
                if (this.information.getListener() != null) {
                    this.information.getListener().onADFailed("get platform is empty");
                }

            }
        } else {
            throw new RuntimeException("ADMobGenInformation is null or be destroyed!!");
        }
    }

    private void loadADConfig(final int var1) {
        final String var2 = (String)this.list.get(var1);
        if (var2.equals(PLAFORM_GDT) && !PremissTool.checkPermission(TPADMobSDK.instance().getAdMobSdkContext())) {
            if (var1 + 1 < this.list.size()) {
                this.loadADConfig(var1 + 1);
                return;
            }

            if (this.information.getListener() != null) {
                this.information.getListener().onADFailed("no permission");
                return;
            }
        }

        IADMobGenConfiguration var3 = SDKUtil.getInstance().getConfig(var2);
        if (var3 == null) {
            if (this.information.getListener() != null) {
                this.information.getListener().onADFailed("getConfiguration is empty");
            }

        } else {
            var3.setFlowAdType(this.information.getInformationAdType());
            final IADMobGenInformationAdController var4 = (IADMobGenInformationAdController)this.map.get(var2);
            if (var4 == null) {
                if (this.information.getListener() != null) {
                    this.information.getListener().onADFailed(var2 + "'s controller is empty");
                }

            } else {
                LogTool.show("InformationAdLoadHelper_createInformation_loadAd..." + var3.getSdkName() + "_______" + var3.getNativeId());
                final ADMobGenInformationImp var5 = new ADMobGenInformationImp();
                RelativeLayout var6 = new RelativeLayout(this.information.getActivity());
                var6.setBackgroundColor(-1);
                int var7 = this.information.getOnlyImageHeight();
                ViewGroup.LayoutParams var8;
                if (this.information.getInformationAdType() == TYPE_INFORMATION_ONLY_IMAGE && var7 > 0) {
                    var8 = new ViewGroup.LayoutParams(var7 * 750 / 422, -2);
                    var6.setLayoutParams(var8);
                } else {
                    var8 = new ViewGroup.LayoutParams(-1, -2);
                    var6.setLayoutParams(var8);
                }

                var6.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View var1) {
                        LogTool.show("AdUtil_getSplashContainer click...");
                    }
                });
                var5.setInformationAdView(var6);
                IADMobGenInformationAdCallBackIml var10 = new IADMobGenInformationAdCallBackIml(this.information, var5, true, var3) {
                    public void onADFailed(String var1x) {
                        LogTool.show(var2 + "'information filed :" + var1x);
                        if (var1 + 1 < InformationConfig.this.list.size()) {
                            if (var4 != null) {
                                var4.destroyAd();
                            }

                            if (var5 != null) {
                                var5.destroy();
                            }

                            InformationConfig.this.loadADConfig(var1 + 1);
                        } else {
                            super.onADFailed(var1x);
                        }
                    }

                    public void onADReceiv(IADMobGenInformationView var1x) {
                        super.onADReceiv(var1x);
                    }
                };
                boolean var9 = var4.loadAd(this.information, var3, var10);
                if (var1 == 0) {
                    this.load();
                }

                if (!var9 && this.information.getListener() != null) {
                    this.information.getListener().onADFailed("information is error");
                }

            }
        }
    }

    private void load() {
        if (ProxyUtil.isSleep()) {
            if (this.information != null && this.information.getApplicationContext() != null) {
                ADUtil.getInstance().loadAdMobShowAd(this.information.getApplicationContext(), TYPE_INFORMATION, STR_TYPE_INFORMATION);
            }

        }
    }

    protected void destroy() {
        try {
            Iterator var1 = this.map.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry var2 = (Map.Entry)var1.next();
                IADMobGenInformationAdController var3 = (IADMobGenInformationAdController)var2.getValue();
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

