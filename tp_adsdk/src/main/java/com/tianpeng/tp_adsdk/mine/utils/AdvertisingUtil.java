package com.tianpeng.tp_adsdk.mine.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class AdvertisingUtil {
    public AdvertisingUtil() {
    }

    public static String getGoogleAdId(Context var0) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            return "Cannot call in the main thread, You must call in the other thread";
        } else {
            try {
                PackageManager var1 = var0.getPackageManager();
                var1.getPackageInfo("com.android.vending", 0);
                AdvertisingUtil.AdvertisingConnection var2 = new AdvertisingUtil.AdvertisingConnection();
                Intent var3 = new Intent("com.google.android.gms.ads.identifier.service.START");
                var3.setPackage("com.google.android.gms");
                if (var0.bindService(var3, var2, Context.BIND_AUTO_CREATE)) {
                    String var5;
                    try {
                        AdvertisingUtil.AdvertisingInterface var4 = new AdvertisingUtil.AdvertisingInterface(var2.getBinder());
                        var5 = var4.getId();
                    } finally {
                        var0.unbindService(var2);
                    }

                    return var5;
                }
            } catch (Exception var10) {
                ;
            }

            return "";
        }
    }

    private static final class AdvertisingInterface implements IInterface {
        private IBinder binder;

        public AdvertisingInterface(IBinder var1) {
            this.binder = var1;
        }

        public IBinder asBinder() {
            return this.binder;
        }

        public String getId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            String var3;
            try {
                var1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.binder.transact(1, var1, var2, 0);
                var2.readException();
                var3 = var2.readString();
            } finally {
                var2.recycle();
                var1.recycle();
            }

            return var3;
        }
    }

    private static final class AdvertisingConnection implements ServiceConnection {
        boolean retrieved;
        private final LinkedBlockingQueue<IBinder> queue;

        private AdvertisingConnection() {
            this.retrieved = false;
            this.queue = new LinkedBlockingQueue(1);
        }

        public void onServiceConnected(ComponentName var1, IBinder var2) {
            try {
                this.queue.put(var2);
            } catch (InterruptedException var4) {
                ;
            }

        }

        public void onServiceDisconnected(ComponentName var1) {
        }

        public IBinder getBinder() throws InterruptedException {
            if (this.retrieved) {
                throw new IllegalStateException();
            } else {
                this.retrieved = true;
                return (IBinder)this.queue.take();
            }
        }
    }
}

