package com.tianpeng.tianpengaddemo.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tianpeng.tianpengaddemo.MySmartRefreshLayout;
import com.tianpeng.tianpengaddemo.R;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.constant.InformationAdType;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.ADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.information.IADMobGenInformation;
import com.tianpeng.tp_adsdk.tpadmobsdk.ad.listener.SimpleADMobGenInformationAdListener;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : ciba
 * @date : 2018/6/23
 * @description : 信息流广告demo
 */

public class InformationActivity extends Activity implements OnRefreshLoadMoreListener {
    private static final String TAG = "ADMobGen_Log";
    private RecyclerView mRecyclerView;
    private List<Object> mDataList = new ArrayList<>();
    private CustomAdapter mAdapter;
    private MySmartRefreshLayout refreshLayout;
    private ADMobGenInformation adMobGenInformation;
    private LinearLayoutManager linearLayoutManager;
    private int loadType;

    public static void jumpHere(Context context,int type) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        int adType = getIntent().getIntExtra("type", InformationAdType.NORMAL);
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.lvList);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        默认信息流样式为上图下文
//        adMobGenInformation = new ADMobGenInformation(this);
        adMobGenInformation = new ADMobGenInformation(this,adType);
        adMobGenInformation.setListener(new SimpleADMobGenInformationAdListener() {
            @Override
            public void onADExposure(IADMobGenInformation adMobGenInformation) {
                Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 	::::: ");
            }

            @Override
            public void onADReceiv(IADMobGenInformation adMobGenInformation) {
                Log.e(TAG, "广告获取成功 ::::: ");
                finishLoad(adMobGenInformation);
            }

            @Override
            public void onADClick(IADMobGenInformation adMobGenInformation) {
                Log.e(TAG, "广告被点击 ::::: ");
            }

            @Override
            public void onADFailed(String error) {
                Log.e(TAG, "广告数据获取失败时回调 ::::: " + error);
                finishLoad(null);
            }
        });
        mAdapter = new CustomAdapter(mDataList);
        mRecyclerView.setAdapter(mAdapter);

        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.autoRefresh();


    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        loadType = MySmartRefreshLayout.TYPE_LOAD_MORE;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        loadType = MySmartRefreshLayout.TYPE_FRESH;
        clearData();
        loadData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 广告视图资源释放
        clearData();

        // 广告资源释放
        if (adMobGenInformation != null) {
            adMobGenInformation.destroy();
        }
    }

    /**
     * 获取结束
     *
     * @param adMobGenInformation ：广告对象
     */
    private void finishLoad(IADMobGenInformation adMobGenInformation) {
        refreshLayout.finish(loadType, adMobGenInformation != null, false);
        if (adMobGenInformation != null) {
            mDataList.add(adMobGenInformation);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 模拟获取数据
     */
    private void loadData() {
        for (int i = 0; i < 20; ++i) {
            mDataList.add("No." + i + " Normal Data");
        }
        adMobGenInformation.loadAd();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, " InformationActivity onResume::::: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, " InformationActivity onPause::::: ");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, " InformationActivity onStop::::: ");

    }

    /**
     * 释放广告视图
     */
    private void clearData() {
        for (int i = 0; i < mDataList.size(); i++) {
            Object o = mDataList.get(i);
            if (o != null && o instanceof IADMobGenInformation) {
                ((IADMobGenInformation) o).destroy();
            }
        }
        mDataList.clear();
        mAdapter.notifyDataSetChanged();
    }
}
