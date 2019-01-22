package com.tianpeng.tianpengaddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tianpeng.tianpengaddemo.banner.BannerActivity;
import com.tianpeng.tianpengaddemo.information.InformationActivity;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.insert.ADMobGenInsertView;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobGenInsertitailAdListener;


public class MainActivity extends AppCompatActivity {
    private ADMobGenInsertView adMobGenInsertView;
    private static final String TAG = "ADMobGen_Log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.tvBanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerActivity.jumpHere(MainActivity.this);
            }
        });
        findViewById(R.id.tvInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adMobGenInsertView = new ADMobGenInsertView(MainActivity.this);
                adMobGenInsertView.setListener(new ADMobGenInsertitailAdListener() {
                    @Override
                    public void onADExposure() {
                        Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
                    }

                    @Override
                    public void onADOpen() {
                        Log.e(TAG, "广告打开成功了 ::::: ");
                    }

                    @Override
                    public void onADLeftApplication() {
                        Log.e(TAG, "广告onADLeftApplication ::::: ");
                    }

                    @Override
                    public void onADFailed(String s) {
                        Log.e(TAG, "广告获取失败了 ::::: " + s);
                    }

                    @Override
                    public void onADReceiv() {
                        Log.e(TAG, "广告获取成功了 ::::: ");
                    }

                    @Override
                    public void onADClick() {
                        Log.e(TAG, "广告被点击了 ::::: ");
                    }

                    @Override
                    public void onAdClose() {
                        Log.e(TAG, "广告被关闭了，改回调不一定会有 ::::: ");
                    }
                });
                adMobGenInsertView.loadAd();
            }
        });
        findViewById(R.id.tvInformation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.jumpHere(MainActivity.this, InformationAdType.NORMAL);
            }
        });
        findViewById(R.id.tvInformationImageOnly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.jumpHere(MainActivity.this, InformationAdType.ONLY_IMAGE);
            }
        });
        findViewById(R.id.tvInformationRightImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.jumpHere(MainActivity.this, InformationAdType.RIGHT_IMAGE);
            }
        });
        findViewById(R.id.tvInformationLeftImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.jumpHere(MainActivity.this, InformationAdType.LEFT_IMAGE);
            }
        });
        findViewById(R.id.tvInformationBottomImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.jumpHere(MainActivity.this, InformationAdType.BOTTOM_IMAGE);
            }
        });
        findViewById(R.id.tvInformationvertric).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.jumpHere(MainActivity.this, InformationAdType.VERTICALPICFLOW);
            }
        });
    }
}
