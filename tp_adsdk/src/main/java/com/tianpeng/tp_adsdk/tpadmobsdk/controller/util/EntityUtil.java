package com.tianpeng.tp_adsdk.tpadmobsdk.controller.util;

import com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.Entity;

import java.util.List;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class EntityUtil extends Entity {
    private static EntityUtil entityUtil = null;

    public EntityUtil() {
    }

    public static EntityUtil getInstance() {
        if (entityUtil == null) {
            Class var0 = EntityUtil.class;
            synchronized(EntityUtil.class) {
                if (entityUtil == null) {
                    entityUtil = new EntityUtil();
                }
            }
        }

        return entityUtil;
    }

    public List<String> getEntityList(int var1) {
        return SDKUtil.getInstance().getDisplayList(var1);
    }

    public List<String> getShowList(int var1) {
        return SDKUtil.getInstance().getShowList(var1);
    }
}
