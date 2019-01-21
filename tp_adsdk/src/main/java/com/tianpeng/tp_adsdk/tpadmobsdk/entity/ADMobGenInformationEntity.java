package com.tianpeng.tp_adsdk.tpadmobsdk.entity;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class ADMobGenInformationEntity {
    private String title;
    private String subTitle;
    private String imageUrl;
    private boolean isDownLoad;

    public ADMobGenInformationEntity(String title, String subTitle, String imageUrl, boolean isDownLoad) {
        this.title = title;
        this.subTitle = subTitle;
        this.imageUrl = imageUrl;
        this.isDownLoad = isDownLoad;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String var1) {
        this.title = var1;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String var1) {
        this.subTitle = var1;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String var1) {
        this.imageUrl = var1;
    }

    public boolean isDownload() {
        return this.isDownLoad;
    }

    public void setDownload(boolean var1) {
        this.isDownLoad = var1;
    }
}
