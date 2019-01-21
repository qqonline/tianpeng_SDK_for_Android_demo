package com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity;

import java.util.List;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class EntityList {
    private List<String> bannerEntity;
    private List<String> splashEntity;
    private List<String> insertEntity;
    private List<String> informationEntity;
    private List<String> nativeEntity;
    private List<String> bigPicEntity;
    private List<String> rightPicEntity;
    private List<String> bottomPicEntity;
    private List<String> leftPicEntity;
    private List<String> topPicEntity;

    public List<String> getInsertEntity() {
        return insertEntity;
    }

    public void setInsertEntity(List<String> insertEntity) {
        this.insertEntity = insertEntity;
    }

    public List<String> getTopPicEntity() {
        return topPicEntity;
    }

    public void setTopPicEntity(List<String> topPicEntity) {
        this.topPicEntity = topPicEntity;
    }

    public EntityList() {
    }

    public List<String> getNativeEntity() {
        return this.nativeEntity;
    }

    public void setNativePicEntity(List<String> var1) {
        this.nativeEntity = var1;
    }

    public List<String> getBannerEntity() {
        return this.bannerEntity;
    }

    public void setBannerEntity(List<String> var1) {
        this.bannerEntity = var1;
    }

    public List<String> getSplashEntity() {
        return this.splashEntity;
    }

    public void setSplashEntity(List<String> var1) {
        this.splashEntity = var1;
    }

    public List<String> getInformationEntity() {
        return this.informationEntity;
    }

    public void setInformationEntity(List<String> var1) {
        this.informationEntity = var1;
    }

    public List<String> getBigPicEntity() {
        return this.bigPicEntity;
    }

    public void setBigNativePicEntity(List<String> var1) {
        this.bigPicEntity = var1;
    }

    public List<String> getRightPicEntity() {
        return this.rightPicEntity;
    }

    public void setRightPicEntity(List<String> var1) {
        this.rightPicEntity = var1;
    }

    public List<String> getBottomPicEntity() {
        return this.bottomPicEntity;
    }

    public void setBottomPicEntity(List<String> var1) {
        this.bottomPicEntity = var1;
    }

    public List<String> getLeftPicEntity() {
        return this.leftPicEntity;
    }

    public void setLeftPicEntity(List<String> var1) {
        this.leftPicEntity = var1;
    }
}
