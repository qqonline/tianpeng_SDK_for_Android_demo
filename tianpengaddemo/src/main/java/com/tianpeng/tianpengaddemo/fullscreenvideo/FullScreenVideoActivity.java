package com.tianpeng.tianpengaddemo.fullscreenvideo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tianpeng.tianpengaddemo.R;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.ADMobGenAdPlaforms;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.ADMobFullScreenVideoAdListener;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.show.ADMobFullScreenVideoView;

/**
 * Created by YuHong on 2019/1/25 0025.
 */
public class FullScreenVideoActivity extends AppCompatActivity {
    private ADMobFullScreenVideoView videoView;
    private static final String TAG = "ADMob_Log";
    private boolean isLoaded;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);

    }

    public void downloadAd(View v){
        showProgressDialog();
        videoView = new ADMobFullScreenVideoView(FullScreenVideoActivity.this);
        videoView.setListener(new ADMobFullScreenVideoAdListener() {

            @Override
            public void onVideoCached() {
                Log.e(TAG, "视频广告已经缓存成功 ::::: ");
                isLoaded = true;
                Toast.makeText(FullScreenVideoActivity.this,"video cached",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onADShow() {
                Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
            }

            @Override
            public void onSkippedVideo() {
                Log.e(TAG, "广告被跳过了 ::::: ");
            }


            @Override
            public void onVideoComplete() {
                Log.e(TAG, "视频播放完毕::::: ");
            }

            @Override
            public void onADFailed(String s) {
                dismissProgressDialog();
                Log.e(TAG, "广告获取失败了 ::::: " + s);
            }

            @Override
            public void onADReceiv() {
                dismissProgressDialog();
                Log.e(TAG, "广告获取成功了 ::::: ");
            }

            @Override
            public void onADClick() {
                Log.e(TAG, "广告被点击了 ::::: ");
            }

            @Override
            public void onAdClose() {
                Log.e(TAG, "广告被关闭了，该回调不一定会有 ::::: ");
            }
        });
        videoView.loadAd(ADMobGenAdPlaforms.PLAFORM_BAIDU,"6164562");
    }

    public void showAd(View view){
        if(isLoaded)
        videoView.show();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载全屏视频...");
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        // 释放广告资源
        if (videoView != null) {
            videoView.destroy();
        }
        super.onDestroy();
    }

    public static void jumpHere(Context context) {
        context.startActivity(new Intent(context, FullScreenVideoActivity.class));
    }
}
