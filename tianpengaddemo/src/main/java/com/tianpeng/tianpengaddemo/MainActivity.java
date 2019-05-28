package com.tianpeng.tianpengaddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tianpeng.tianpengaddemo.banner.BannerActivity;
import com.tianpeng.tianpengaddemo.information.InformationActivity;
import com.tianpeng.tianpengaddemo.interstitial.InterstitialActivity;
import com.tianpeng.tianpengaddemo.reward.RewardVideoActivity;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType;


public class MainActivity extends AppCompatActivity {

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
                InterstitialActivity.jumpHere(MainActivity.this);
            }
        });
        findViewById(R.id.tvReward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewardVideoActivity.jumpHere(MainActivity.this);
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
