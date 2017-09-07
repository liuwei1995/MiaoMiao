package com.zhaoyao.miaomiao.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.adapter.ReadComicActivityAdapter;
import com.zhaoyao.miaomiao.entity.ComicChapterDetailsEntity;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.NetWorkUtils;
import com.zhaoyao.miaomiao.util.image.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看漫画
 * Created by liuwei on 2017/8/29 10:57
 */

public class ReadComicActivity extends BaseNewActivity {

    private static final String TAG = "ReadComicActivity";

    public static final String CHAPTER_SOURCE_KEY = "CHAPTER_SOURCE_KEY";
    private RecyclerView mRecyclerView;

    public static void startActivity(Context context, ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean.ChapterSourceBean chapter_source) {
        if (chapter_source == null) return;
        Intent intent = new Intent(context, ReadComicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAPTER_SOURCE_KEY, chapter_source);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        registerNetReceiver();
        initView();
        boolean wifiConnected = NetWorkUtils.isWifiConnected(this.getApplicationContext());
        if (!wifiConnected){
//            NetWorkActivity.startActivityForResult(this,100);
            showAlertDialog(null);
        }else {
            getData();
        }
    }
    private AlertDialog  mDialog;
    private TextView tvDialogTitle;

    private synchronized void showAlertDialog(String title) {
        if (mDialog == null){
            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.hint_network, null);
            tvDialogTitle = (TextView) view.findViewById(R.id.tv_title);
            if (!TextUtils.isEmpty(title)){
                tvDialogTitle.setText(title);
            }
            view.findViewById(R.id.tv_cancel).setOnClickListener(this);
            view.findViewById(R.id.tv_continue).setOnClickListener(this);
            mAlertDialog.setView(view);
            mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    finish();
                    return false;
                }
            });
            mDialog = mAlertDialog.show();
        }else {
            if (!mDialog.isShowing()){
                if (!TextUtils.isEmpty(title)){
                    tvDialogTitle.setText(title);
                }
                mDialog.show();
            }
        }
    }

    private void getData() {
        ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean.ChapterSourceBean chapter_source = getIntent().getParcelableExtra(CHAPTER_SOURCE_KEY);
        if (chapter_source == null) {
            finish();
            return;
        }
//        ViewGroup.LayoutParams layoutParams = itemViewHolder.itemView.getLayoutParams();
//        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int start_num = chapter_source.getStart_num();
        int end_num = chapter_source.getEnd_num();

        String url = "http://mhpic.";
        String chapter_domain = chapter_source.getChapter_domain();
        if (TextUtils.isEmpty(chapter_domain))chapter_domain = "samanlehua.com";
        url += chapter_domain+"/";
        String rule = chapter_source.getRule();
        url += rule;
        String suffix = "";
        if ("zymkcdn.com".equals(chapter_domain)){
            suffix = "-mht.middle";
        }else if ("samanlehua.com".equals(chapter_domain)){
            suffix += "";
        }
        LogUtils.d(TAG, chapter_source.toString());
        List<String> list = new ArrayList<>(end_num);
        for (int i = start_num; i <= end_num; i++) {
            String s  = url.replace("$$",i+"") + suffix;
            list.add(s);
        }
        setAdapter(list);
    }

    private void setAdapter(List<String> list) {
        if (list == null || list.size() == 0){
            finish();
            return;
        }
        ReadComicActivityAdapter readComicActivityAdapter = new ReadComicActivityAdapter(list, R.layout.item_activity_read_comic);
        mRecyclerView.setAdapter(readComicActivityAdapter);
    }
    LinearLayout llAd = null;
    private BannerView mBannerView;
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_ReadComicActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        llAd = (LinearLayout) findViewById(R.id.ll_ad);
        doRefreshBanner();
    }

    private void initBanner() {
        mBannerView = new BannerView(this, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
        mBannerView.setRefresh(30);
        llAd.removeAllViews();
        mBannerView.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {
                LogUtils.a(adError);
            }

            @Override
            public void onADReceiv() {
                LogUtils.a("onADReceiv");
            }
        });
        llAd.addView(mBannerView);
    }

    private void doRefreshBanner() {
        if (mBannerView == null) {
            initBanner();
        }
        mBannerView.loadAD();
    }

    private void doCloseBanner() {
        llAd.removeAllViews();
        if (mBannerView != null) {
            mBannerView.destroy();
            mBannerView = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            if (resultCode == Activity.RESULT_OK){
                getData();
            }else {
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mDialog != null)mDialog.dismiss();
        if (v.getId() == R.id.tv_cancel){
            finish();
        }else if (v.getId() == R.id.tv_continue){
            GlideUtils.newInstance().resume();
            getData();
        }
    }
    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;

    public void registerNetReceiver(){
        IntentFilter filter = new IntentFilter();
        //监听wifi连接（手机与路由器之间的连接）
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        //监听互联网连通性（也就是是否已经可以上网了），当然只是指wifi网络的范畴
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        //这个是监听网络状态的，包括了wifi和移动网络。
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver(), filter);
    }

    public class NetworkConnectChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
           if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                NetworkInfo extra_network_info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (extra_network_info != null){
                    NetworkInfo.State state = extra_network_info.getState();
                    LogUtils.d(state);
                    int type = extra_network_info.getType();
                    String typeName = extra_network_info.getTypeName();
                    if ("MOBILE".equals(typeName) && type == 0){
//                        showAlertDialog(null);
                        if ("CONNECTED".equals(state.toString())){
                            GlideUtils.newInstance().stop();
                            showAlertDialog("当前处于移动数据流量是否继续浏览");
                        }else if ("DISCONNECTED".equals(state.toString())){
                            GlideUtils.newInstance().stop();
                        }
                    }else if ("WIFI".equals(typeName) && type == 1){
                        if ("CONNECTED".equals(state.toString())){
                            GlideUtils.newInstance().resume();
                        }else if ("DISCONNECTED".equals(state.toString())){
                            GlideUtils.newInstance().stop();
                        }
                    }
                    LogUtils.d(typeName);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        doCloseBanner();
        GlideUtils.newInstance().resume();
        if (mDialog != null)mDialog.dismiss();
        if (mNetworkConnectChangedReceiver != null)
        unregisterReceiver(mNetworkConnectChangedReceiver);
        super.onDestroy();
    }
}
