package com.tianpeng.tp_adsdk.mine.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.tianpeng.tp_adsdk.admobhttp.AsyncHttpResponseHandler;
import com.tianpeng.tp_adsdk.mine.config.DataUtil;
import com.tianpeng.tp_adsdk.mine.http.HttpCilent;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import static com.tianpeng.tp_adsdk.mine.http.Constant.BDLOACATION;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class LocationUtil {
    private static LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location var1) {
        }

        public void onStatusChanged(String var1, int var2, Bundle var3) {
        }

        public void onProviderEnabled(String var1) {
        }

        public void onProviderDisabled(String var1) {
        }
    };

    public LocationUtil() {
    }

    public static LocationUtil.CustomLocation getCustomLocation(Context var0) {
        String var1 = "CN";
        LocationUtil.CustomLocation var2 = new LocationUtil.CustomLocation(0.0D, 0.0D, var1, System.currentTimeMillis(), 0.0F);

        try {
            int var3 = ContextCompat.checkSelfPermission(var0, "android.permission.ACCESS_COARSE_LOCATION");
            int var4 = ContextCompat.checkSelfPermission(var0, "android.permission.ACCESS_FINE_LOCATION");
            if (var3 == 0 && var4 == 0) {
                LocationManager var5 = (LocationManager)var0.getSystemService(Context.LOCATION_SERVICE);
                Location var6;
                if (var5.isProviderEnabled("gps")) {
                    var6 = var5.getLastKnownLocation("gps");
                    if (var6 != null) {
                        getLocationInfo(var6, var2);
                    } else {
                        var5.requestLocationUpdates("network", 1000L, 0.0F, locationListener);
                        Location var7 = var5.getLastKnownLocation("network");
                        getLocationInfo(var7, var2);
                    }
                } else {
                    var5.requestLocationUpdates("network", 1000L, 0.0F, locationListener);
                    var6 = var5.getLastKnownLocation("network");
                    getLocationInfo(var6, var2);
                }

                if (0.0D != var2.getLat() && 0.0D != var2.getLng()) {
                    DataUtil.saveLngAndLat(var0, var2.getLng() + "", var2.getLat() + "");
                } else {
                    var2.setCoordinateType(3);
                    var2.setTime(System.currentTimeMillis());
                    getServerLocation(var0);
                }
            } else {
                var2.setCoordinateType(3);
                var2.setTime(System.currentTimeMillis());
                getServerLocation(var0);
            }

            Geocoder var9 = new Geocoder(var0, Locale.getDefault());
            List var10 = var9.getFromLocation(var2.getLat(), var2.getLng(), 1);
            Address var11 = (Address)var10.get(0);
            var1 = var11.getCountryName() + "_" + var11.getCountryCode();
            var2.setCountry(var1);
        } catch (Exception var8) {
            ;
        }

        return var2;
    }

    private static void getLocationInfo(Location var0, LocationUtil.CustomLocation var1) {
        if (var0 != null) {
            var1.setLat(var0.getLatitude());
            var1.setLng(var0.getLongitude());
            var1.setTime(var0.getTime());
            var1.setAccuracy(var0.getAccuracy());
        }

    }

    private static void getServerLocation(final Context var0) {
        AsyncHttpResponseHandler var1 = new AsyncHttpResponseHandler() {
            public void onSuccess(String var1) {
                super.onSuccess(var1);

                try {
                    JSONObject var2 = new JSONObject(var1);
                    JSONObject var3 = var2.optJSONObject("content");
                    JSONObject var4 = var3.optJSONObject("point");
                    String var5 = var4.optString("x");
                    String var6 = var4.optString("y");
                    DataUtil.saveLngAndLat(var0, var5, var6);
                } catch (Exception var7) {
                    ;
                }

            }
        };
        HttpCilent.postHttp(BDLOACATION, var1);
    }

    public static class CustomLocation {
        private double lat;
        private double lng;
        private String country;
        private long time;
        private float accuracy;
        private int coordinateType = 1;

        public CustomLocation(double var1, double var3, String var5, long var6, float var8) {
            this.lat = var1;
            this.lng = var3;
            this.country = var5;
            this.time = var6;
            this.accuracy = var8;
        }

        public int getCoordinateType() {
            return this.coordinateType;
        }

        public void setCoordinateType(int var1) {
            this.coordinateType = var1;
        }

        public float getAccuracy() {
            return this.accuracy;
        }

        public void setAccuracy(float var1) {
            this.accuracy = var1;
        }

        public long getTime() {
            return this.time;
        }

        public void setTime(long var1) {
            this.time = var1;
        }

        public double getLat() {
            return this.lat;
        }

        public void setLat(double var1) {
            this.lat = var1;
        }

        public double getLng() {
            return this.lng;
        }

        public void setLng(double var1) {
            this.lng = var1;
        }

        public String getCountry() {
            return this.country;
        }

        public void setCountry(String var1) {
            this.country = var1;
        }
    }
}

