package com.tianpeng.tp_adsdk.tpadmobsdk.http.imp;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenConfiguration;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class IADMobGenConfigurationImp implements IADMobGenConfiguration {
    private String sdkName;
    private String appId;
    private Configuration configuration;
    private int flowAdType = 0;
    private int turnType;

    public IADMobGenConfigurationImp() {
    }

    public void setSdkName(String var1) {
        this.sdkName = var1;
    }

    public void setAppId(String var1) {
        this.appId = var1;
    }

    public void setConfiguration(Configuration var1) {
        this.configuration = var1;
    }

    public static String debugOrRelease(boolean debug) {
        return debug ? "ADMobGenConfigurationDebug" : "ADMobGenConfigurationRelease";
    }

    public int getTurn() {
        return this.turnType;
    }

    public void setTurnType(int var1) {
        this.turnType = var1;
    }

    public String getSdkName() {
        return this.sdkName;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getSplashId() {
        Configuration var1 = this.getConfiguration();
        return var1 == null ? "" : var1.getSplashId();
    }

    public String getBannerId() {
        Configuration var1 = this.getConfiguration();
        return var1 == null ? "" : var1.getBannerId();
    }

    public String getNativeId() {
        Configuration var1 = this.getConfiguration();
        if (var1 == null) {
            return "";
        } else {
            switch(this.flowAdType) {
                case InformationAdType.BOTTOM_IMAGE:
                    return var1.getNativeBottom();
                case 2:
                default:
                    return var1.getNativeNormal();
                case InformationAdType.LEFT_IMAGE:
                    return var1.getNativeLeft();
                case InformationAdType.RIGHT_IMAGE:
                    return var1.getNativeRight();
                case InformationAdType.VERTICALPICFLOW:
                    return var1.getNativeVertical();
                case InformationAdType.ONLY_IMAGE:
                    return var1.getNativeOnlyImage();
            }
        }
    }

    @Override
    public String getInsertId() {
        Configuration var1 = this.getConfiguration();
        return var1 == null ? "" : var1.getInsertId();
    }

    public void setFlowAdType(int var1) {
        this.flowAdType = var1;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static class Configuration {
        private String bannerId;
        private String insertId;
        private String nativeNormal;
        private String nativeOnlyImage;
        private String splashId;
        private String nativeVertical;
        private String nativeRight;
        private String nativeBottom;
        private String nativeLeft;

        public Configuration(String bannerId, String insertId,String nativeNormal, String nativeOnlyImage, String splashId, String nativeVertical, String nativeRight, String nativeBottom, String nativeLeft) {
            this.bannerId = bannerId;
            this.insertId = insertId;
            this.nativeNormal = nativeNormal;
            this.nativeOnlyImage = nativeOnlyImage;
            this.splashId = splashId;
            this.nativeVertical = nativeVertical;
            this.nativeRight = nativeRight;
            this.nativeBottom = nativeBottom;
            this.nativeLeft = nativeLeft;
        }

        public String getInsertId() {
            return insertId;
        }

        public String getNativeOnlyImage() {
            return this.nativeOnlyImage;
        }

        public String getBannerId() {
            return this.bannerId;
        }

        public String getNativeNormal() {
            return this.nativeNormal;
        }

        public String getSplashId() {
            return this.splashId;
        }

        public String getNativeVertical() {
            return this.nativeVertical;
        }

        public String getNativeRight() {
            return this.nativeRight;
        }

        public String getNativeBottom() {
            return this.nativeBottom;
        }

        public String getNativeLeft() {
            return this.nativeLeft;
        }
    }
}

