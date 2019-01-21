package com.tianpeng.tp_adsdk.tpadmobsdk.controller.entity.information;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.IADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.entity.IADMobGenInformationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class ADMobGenInformationImp implements ViewTreeObserver.OnScrollChangedListener, IADMobGenInformation {
    private View a;
    private Rect b = new Rect();
    private List<IADMobGenInformationView> c = new ArrayList();
    private long d;
    private boolean e;
    private Map<Integer, Integer> f = new HashMap();
    private int g;

    public ADMobGenInformationImp() {
    }

    public void setInformationAdView(View var1) {
        this.a = var1;
    }

    public View getInformationAdView() {
        return this.a;
    }

    public void render() {
        try {
            if (this.e) {
                return;
            }

            if (this.f.size() < this.c.size()) {
                this.b();
                this.a();
            }

            for(int var1 = 0; var1 < this.c.size(); ++var1) {
                IADMobGenInformationView var2 = (IADMobGenInformationView)this.c.get(var1);
                if (var2 != null) {
                    var2.render();
                }
            }

            this.onExposured();
        } catch (Exception var3) {
            ;
        }

    }

    public void onExposured() {
        try {
            if (this.a == null || this.e) {
                return;
            }

            long var1 = System.currentTimeMillis();
            if (var1 - this.d < 100L) {
                return;
            }

            this.d = var1;
            this.b.set(0, 0, 0, 0);
            this.a.getLocalVisibleRect(this.b);
            int var3 = this.a.getMeasuredWidth();
            int var4 = this.a.getMeasuredHeight();
            if (var3 <= 0 || var4 <= 0) {
                return;
            }

            int var5 = this.b.right - this.b.left;
            int var6 = this.b.bottom - this.b.top;
            if (this.b.left == 0 && var5 >= var3 / 2 && this.b.top == 0 && var6 >= var4 / 2) {
                this.b();

                for(int var7 = 0; var7 < this.c.size(); ++var7) {
                    IADMobGenInformationView var8 = (IADMobGenInformationView)this.c.get(var7);
                    if (var8 != null) {
                        this.f.put(var8.hashCode(), var8.hashCode());
                        var8.onExposured();
                    }
                }
            }
        } catch (Exception var9) {
            ;
        }

    }

    public void destroy() {
        try {
            for(int var1 = 0; var1 < this.c.size(); ++var1) {
                IADMobGenInformationView var2 = (IADMobGenInformationView)this.c.get(var1);
                if (var2 != null) {
                    var2.destroy();
                    if (var2.getInformationAdView() != null && var2.getInformationAdView() instanceof ViewGroup) {
                        ((ViewGroup)var2.getInformationAdView()).removeAllViews();
                    }
                }
            }

            this.c.clear();
            this.b();
            if (this.a != null && this.a instanceof ViewGroup) {
                ((ViewGroup)this.a).removeAllViews();
                this.a = null;
            }

            this.e = true;
        } catch (Exception var3) {
            ;
        }

    }

    public int getInformationAdType() {
        return this.g;
    }

    public void setInformationAdType(int var1) {
        this.g = var1;
    }

    public boolean isDestroy() {
        return this.e;
    }

    public void addIADMobGenInformationView(IADMobGenInformationView var1) {
        if (var1 != null) {
            this.c.add(var1);
            this.onExposured();
        }

    }

    public void onScrollChanged() {
        this.onExposured();
    }

    private void a() {
        if (this.a != null && this.a.getViewTreeObserver() != null) {
            this.a.getViewTreeObserver().addOnScrollChangedListener(this);
        }

    }

    private void b() {
        if (this.a != null && this.a.getViewTreeObserver() != null) {
            this.a.getViewTreeObserver().removeOnScrollChangedListener(this);
        }

    }
}
