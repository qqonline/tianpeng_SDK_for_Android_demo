package com.tianpeng.tp_adsdk.mine.business;

/**
 * Created by YuHong on 2019/1/17 0017.
 */
public class UploadDataBean {

    private String sdkName;
    private String sdkVersion;
    private String appType;//appid
    private String adType;//原生(native),(Banner) banner ,(开屏)open,(插屏)flow,(视频)mv
    private String appId;
    private String adId;
    private String packageName;
    private String msesage;
    private String result;
    private String sdkAction;//show  click down install

//    private JSONObject json;

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMsesage() {
        return msesage;
    }

    public void setMsesage(String msesage) {
        this.msesage = msesage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSdkAction() {
        return sdkAction;
    }

    public void setSdkAction(String sdkAction) {
        this.sdkAction = sdkAction;
    }

    public String getSdkName() {
        return sdkName;
    }

    public void setSdkName(String sdkName) {
        this.sdkName = sdkName;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

}
