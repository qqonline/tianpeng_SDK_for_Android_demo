package com.tianpeng.tp_adsdk.tpadmobsdk.controller.util;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.ControllerImpTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.change.LogTool;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.ISdkInit;
import com.tianpeng.tp_adsdk.tpadmobsdk.common.TPADMobSDK;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.Entity;
import com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.EntityList;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenShowAdController;
import com.tianpeng.tp_adsdk.tpadmobsdk.http.RequestParam;
import com.tianpeng.tp_adsdk.tpadmobsdk.http.imp.IADMobGenConfigurationImp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_BANNER;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION_BIG;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION_LEFT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION_RIGHT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION_SMALL;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INFORMATION_UP;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_INSERT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.STR_TYPE_SPLASH;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_BANNER;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_BOTTOMPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_LEFTPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_ONLY_IMAGE;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_RIGHTPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INFORMATION_VERTICALPICFLOW;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_INSERT;
import static com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdType.TYPE_SPLASH;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class SDKUtil {
        private static SDKUtil sdkUtil = null;
        private Map<String, IADMobGenConfiguration> configurationMap = new HashMap();
        private Map<String, ISdkInit> iSdkInitMap = new HashMap();
        private Entity entity;
        private EntityList displayList;
        private EntityList showList;
        private int flag = 0;
        private int turnType = 0;
        private IADMobGenShowAdController controller;
        private String androidId;
        private String packageName;

        private SDKUtil() {
        }

        public static SDKUtil getInstance() {
            if (sdkUtil == null) {
                Class var0 = SDKUtil.class;
                synchronized(SDKUtil.class) {
                    if (sdkUtil == null) {
                        sdkUtil = new SDKUtil();
                    }
                }
            }

            return sdkUtil;
        }

        public void init(boolean debug, Context context) {
            this.androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
            this.packageName = context.getPackageName();
            this.init(debug);
        }

        public void init(final boolean debug) {
            LogTool.show("AdMobSdk init");
            String[] var2 = TPADMobSDK.instance().getPlatforms();
            if (var2 != null) {
                String[] var3 = var2;
                int var4 = var2.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String var6 = var3[var5];
                    ISdkInit var7 = (ISdkInit) ControllerImpTool.getClassInstance(ControllerImpTool.getSdkInitImp(var6));
                    if (var7 == null) {
                        LogTool.show(var6 + "'s sdk is not compile");
                    } else {
                        this.iSdkInitMap.put(var7.getPlatform(), var7);
                    }
                }

//                this.controller = (IADMobGenShowAdController)ControllerImpTool.getClassInstance(ControllerImpTool.getADMobSDKImp());
                this.commitData(SharedPreferencesUtil.getInstance().getValue(IADMobGenConfigurationImp.debugOrRelease(debug)), false, debug);
                RequestParam.request(debug, new AsyncHttpResponseHandler() {
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        SDKUtil.this.commitData(data, true, debug);
                    }

                    public void onFailure(Throwable var1x, String var2) {
                        super.onFailure(var1x, var2);
                        LogTool.show("ADMobGenAd", "onFailure: " + var2);
                    }
                });
            }
        }

        private void commitData(String data, boolean var2, boolean var3) {
            if (!TextUtils.isEmpty(data)) {
                try {
//                    String json = (new StringBuilder(data)).reverse().toString();
                    JSONObject jsonObject = new JSONObject(this.formart(data));
                    this.displayList = this.getEntityList(jsonObject.getJSONObject("sdkDisplay"));
                    this.showList = this.getEntityList(jsonObject.getJSONObject("sdkShow"));
//                    this.turnType = jsonObject.getInt("turn");
                    this.configurationMap.clear();
                    JSONArray sdkList = jsonObject.getJSONArray("sdkList");
                    if (sdkList != null) {
                        for(int i = 0; i < sdkList.length(); ++i) {
                            IADMobGenConfigurationImp iadMobGenConfigurationImp = this.getConfig(sdkList.getJSONObject(i));
                            if (iadMobGenConfigurationImp != null) {
                                this.configurationMap.put(iadMobGenConfigurationImp.getSdkName(), iadMobGenConfigurationImp);
                                ISdkInit iSdkInit = (ISdkInit)this.iSdkInitMap.get(iadMobGenConfigurationImp.getSdkName());
                                if (iSdkInit != null) {
                                    iSdkInit.init(iadMobGenConfigurationImp);
                                    if (var2) {
                                        LogTool.show(iadMobGenConfigurationImp.getSdkName() + "'platform init success");
                                    }
                                }
                            }
                        }
                    } else {
                        LogTool.show("sdk init failed : not ad platform");
                    }

                    if (var2) {
                        try {
                            this.flag = jsonObject.getInt("flag");
                        } catch (Exception var10) {
                            ;
                        }

                        SharedPreferencesUtil.getInstance().commitValue(IADMobGenConfigurationImp.debugOrRelease(var3), data);
                    }
                } catch (Exception var11) {
                    ;
                }

            }
        }

        private String formart(String var1) {
            if (var1 != null && !var1.equals("")) {
                try {
                    var1 = var1.replace(" ", "");
                    byte[] var2 = new byte[var1.length() / 2];

                    for(int var3 = 0; var3 < var2.length; ++var3) {
                        var2[var3] = (byte)(255 & Integer.parseInt(var1.substring(var3 * 2, var3 * 2 + 2), 16));
                    }

                    var1 = new String(var2, "UTF-8");
                } catch (Exception var4) {
                    ;
                }

                return var1;
            } else {
                return null;
            }
        }

        private IADMobGenConfigurationImp getConfig(JSONObject var1) {
            try {
                String sdkName = var1.getString("sdkName").trim();
                String var3 = var1.getString("appId");
                String var4 = "";
                String var5 = "";
                String var6 = "";
                String var7 = "";
                String var8 = "";
                String var9 = "";
                String var10 = "";
                String var11 = "";
                String insert = "";
                if (var1.has("posidList")) {
                    JSONObject var12 = var1.getJSONObject("posidList");
                    if (var12.has(STR_TYPE_BANNER)) {
                        var4 = var12.getString(STR_TYPE_BANNER);
                    }

                    if (var12.has(STR_TYPE_INSERT)) {
                        insert = var12.getString(STR_TYPE_INSERT);
                    }

                    if (var12.has(STR_TYPE_SPLASH)) {
                        var5 = var12.getString(STR_TYPE_SPLASH);
                    }

                    if (var12.has(STR_TYPE_INFORMATION)) {
                        var6 = var12.getString(STR_TYPE_INFORMATION);
                    }

                    if (var12.has(STR_TYPE_INFORMATION_SMALL)) {
                        var7 = var12.getString(STR_TYPE_INFORMATION_SMALL);
                    }

                    if (var12.has(STR_TYPE_INFORMATION_RIGHT)) {
                        var9 = var12.getString(STR_TYPE_INFORMATION_RIGHT);
                    }

                    if (var12.has(STR_TYPE_INFORMATION_BIG)) {
                        var8 = var12.getString(STR_TYPE_INFORMATION_BIG);
                    }

                    if (var12.has(STR_TYPE_INFORMATION_LEFT)) {
                        var11 = var12.getString(STR_TYPE_INFORMATION_LEFT);
                    }

//                    if (var12.has("nativePicD")) {
//                        var10 = var12.getString("nativePicD");
//                    }
                    if (var12.has(STR_TYPE_INFORMATION_UP)) {
                        var10 = var12.getString(STR_TYPE_INFORMATION_UP);
                    }
                }

                IADMobGenConfigurationImp var14 = new IADMobGenConfigurationImp();
                var14.setAppId(var3);
                var14.setSdkName(sdkName);
                var14.setTurnType(this.turnType);
                var14.setConfiguration(new IADMobGenConfigurationImp.Configuration(var4,insert, var6, var7, var5, var8, var9, var10, var11));
                return var14;
            } catch (Exception var13) {
                return null;
            }
        }

        private EntityList getEntityList(JSONObject var1) {
            try {
                EntityList var2 = new EntityList();
                this.setConfig(var2, var1.getJSONArray(STR_TYPE_SPLASH), STR_TYPE_SPLASH);
                this.setConfig(var2, var1.getJSONArray(STR_TYPE_BANNER), STR_TYPE_BANNER);
                this.setConfig(var2, var1.getJSONArray(STR_TYPE_INSERT), STR_TYPE_INSERT);
                this.setConfig(var2, var1.getJSONArray(STR_TYPE_INFORMATION), STR_TYPE_INFORMATION);
                if (var1.has(STR_TYPE_INFORMATION_SMALL)) {
                    this.setConfig(var2, var1.getJSONArray(STR_TYPE_INFORMATION_SMALL), STR_TYPE_INFORMATION_SMALL);
                }

                if (var1.has(STR_TYPE_INFORMATION_RIGHT)) {
                    this.setConfig(var2, var1.getJSONArray(STR_TYPE_INFORMATION_RIGHT), STR_TYPE_INFORMATION_RIGHT);
                }

                if (var1.has(STR_TYPE_INFORMATION_BIG)) {
                    this.setConfig(var2, var1.getJSONArray(STR_TYPE_INFORMATION_BIG), STR_TYPE_INFORMATION_BIG);
                }

                if (var1.has(STR_TYPE_INFORMATION_LEFT)) {
                    this.setConfig(var2, var1.getJSONArray(STR_TYPE_INFORMATION_LEFT), STR_TYPE_INFORMATION_LEFT);
                }

//                if (var1.has("nativePicD")) {
//                    this.setConfig(var2, var1.getJSONArray("nativePicD"), "nativePicD");
//                }
                if (var1.has(STR_TYPE_INFORMATION_UP)) {
                    this.setConfig(var2, var1.getJSONArray(STR_TYPE_INFORMATION_UP), STR_TYPE_INFORMATION_UP);
                }

                return var2;
            } catch (Exception var3) {
                return null;
            }
        }

        private void setConfig(EntityList entityList, JSONArray jsonArray, String type) throws JSONException {
            String[] platforms = TPADMobSDK.instance().getPlatforms();
            if (entityList != null && jsonArray != null && jsonArray.length() > 0) {
                ArrayList arrayList = new ArrayList();

                for(int i = 0; i < jsonArray.length(); ++i) {
                    String json = jsonArray.getString(i);
                    if (!TextUtils.isEmpty(json)) {
                        json = json.trim();
                        if (platforms != null && platforms.length > 0) {
                            String[] platforms1 = platforms;
                            int size = platforms.length;

                            for(int j = 0; j < size; ++j) {
                                String platform = platforms1[j];
                                if (json.equalsIgnoreCase(platform)) {
                                    arrayList.add(json);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (STR_TYPE_SPLASH.equalsIgnoreCase(type)) {
                    entityList.setSplashEntity(arrayList);
                } else if (STR_TYPE_INFORMATION.equalsIgnoreCase(type)) {
                    entityList.setInformationEntity(arrayList);
                } else if (STR_TYPE_BANNER.equalsIgnoreCase(type)) {
                    entityList.setBannerEntity(arrayList);
                } else if (STR_TYPE_INSERT.equalsIgnoreCase(type)) {
                    entityList.setInsertEntity(arrayList);
                } else if (STR_TYPE_INFORMATION_SMALL.equalsIgnoreCase(type)) {
                    entityList.setNativePicEntity(arrayList);
                } else if (STR_TYPE_INFORMATION_RIGHT.equalsIgnoreCase(type)) {
                    entityList.setRightPicEntity(arrayList);
                } else if (STR_TYPE_INFORMATION_BIG.equalsIgnoreCase(type)) {
                    entityList.setBigNativePicEntity(arrayList);
                } else if (STR_TYPE_INFORMATION_LEFT.equalsIgnoreCase(type)) {
                    entityList.setLeftPicEntity(arrayList);
//                } else if ("nativePicD".equalsIgnoreCase(var3)) {
//                    var1.setBottomPicEntity(var5);
                }else if (STR_TYPE_INFORMATION_UP.equalsIgnoreCase(type)) {
                    entityList.setTopPicEntity(arrayList);
                }
            }

        }

        public IADMobGenConfiguration getConfig(String var1) {
            return (IADMobGenConfiguration)this.configurationMap.get(var1);
        }

        public Entity getEntity() {
            if (this.entity == null) {
                this.entity = Entity.getEntity();
            }

            return this.entity;
        }

        public List<String> getDisplayList(int var1) {
            return this.getEntityList(var1, this.displayList);
        }

        public List<String> getShowList(int var1) {
            return this.getEntityList(var1, this.showList);
        }

        private List<String> getEntityList(int var1, EntityList var2) {
            if (var2 == null) {
                return new ArrayList();
            } else if (TYPE_SPLASH == var1) {
                return (List)(var2.getSplashEntity() == null ? new ArrayList() : var2.getSplashEntity());
            } else if (TYPE_BANNER == var1) {
                return (List)(var2.getBannerEntity() == null ? new ArrayList() : var2.getBannerEntity());
            } else if (TYPE_INSERT == var1) {
                return (List)(var2.getInsertEntity() == null ? new ArrayList() : var2.getInsertEntity());
            } else if (TYPE_INFORMATION == var1) {
                return (List)(var2.getInformationEntity() == null ? new ArrayList() : var2.getInformationEntity());
            } else if (TYPE_INFORMATION_ONLY_IMAGE == var1) {
                return (List)(var2.getNativeEntity() == null ? new ArrayList() : var2.getNativeEntity());
            } else if (TYPE_INFORMATION_VERTICALPICFLOW == var1) {
                return (List)(var2.getBigPicEntity() == null ? new ArrayList() : var2.getBigPicEntity());
            } else if (TYPE_INFORMATION_RIGHTPICFLOW == var1) {
                return (List)(var2.getRightPicEntity() == null ? new ArrayList() : var2.getRightPicEntity());
            } else if (TYPE_INFORMATION_LEFTPICFLOW == var1) {
                return (List)(var2.getLeftPicEntity() == null ? new ArrayList() : var2.getLeftPicEntity());
            } else if (TYPE_INFORMATION_BOTTOMPICFLOW == var1) {
                return (List)(var2.getTopPicEntity() == null ? new ArrayList() : var2.getTopPicEntity());
//            } else if (TYPE_INFORMATION_TOPPICFLOW == var1) {
//                return (List)(var2.getTopPicEntity() == null ? new ArrayList() : var2.getTopPicEntity());
            } else {
                return new ArrayList();
            }
        }

        public int getFlag() {
            return this.flag;
        }

        public String getAndroidId() {
            return this.androidId;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public IADMobGenShowAdController getController() {
            return this.controller;
        }
}
