package com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity;

import com.tianpeng.tp_adsdk.tpadmobsdk.controller.util.EntityUtil;

import java.util.List;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public abstract class Entity {
    protected Entity() {
    }

    public static Entity getEntity() {
        return EntityUtil.getInstance();
    }

    public abstract List<String> getEntityList(int var1);
}
