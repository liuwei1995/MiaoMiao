package com.zhaoyao.miaomiao;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.sohuvideo.ui_plugin.fragment.SohuVideoFragment;
import com.zhaoyao.miaomiao.activity.AdActivity;
import com.zhaoyao.miaomiao.activity.BaseNewActivity;
import com.zhaoyao.miaomiao.activity.GoogleAdActivity;
import com.zhaoyao.miaomiao.activity.ImageRecognitionActivity;
import com.zhaoyao.miaomiao.adapter.TabFragmentAdapter;
import com.zhaoyao.miaomiao.fragment.CartoonRecommendFragment;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseNewActivity implements View.OnClickListener,TaskHandler<MainActivity> {

    private CartoonRecommendFragment mContent;
    /**
     * 广告
     */
    private TextView mTvAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        DrawerLayout mDrawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        initSlidingMenu(savedInstanceState);
    }

    private List<Fragment> fragments;
    private List<String> lists = null;
    private TabFragmentAdapter fragmentAdapter;
    private ViewPager viewPager;

    public void initView() {
        fragments = new ArrayList<>();
        lists = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        lists.add("漫画");
        fragments.add(CartoonRecommendFragment.newInstance());


        lists.add("视频");
        SohuVideoFragment sohuVideoFragment = SohuVideoFragment.newInstance(true);
        fragments.add(sohuVideoFragment);

        if (fragments.size() != lists.size() || lists.size() == 0) {
            return;
        }

        fragmentAdapter = new TabFragmentAdapter(fragments, lists, getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setOffscreenPageLimit(fragments.size());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        mTvAd = (TextView) findViewById(R.id.tv_ad);
        mTvAd.setOnClickListener(this);
        findViewById(R.id.tv_google_ad).setOnClickListener(this);
        findViewById(R.id.tv_image_recognition).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu(Bundle savedInstanceState) {
        // 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
        if (savedInstanceState != null) {
            mContent = (CartoonRecommendFragment) getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }

        if (mContent == null) {
            mContent = CartoonRecommendFragment.newInstance();
        }
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_frame, mContent).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ad:
                startActivityForResult(new Intent(this,AdActivity.class),100);
                break;
            case R.id.tv_google_ad:
                startActivity(new Intent(this,GoogleAdActivity.class));
                break;
            case R.id.tv_image_recognition:
                startActivity(new Intent(this,ImageRecognitionActivity.class));
                break;
        }
    }

    private Handler mHandler = new TaskHandlerImpl<>(this);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 100){
            mHandler.removeMessages(100);
            mHandler.sendEmptyMessageDelayed(100,2*1000);
        }
    }

    @Override
    public void handleMessage(WeakReference<MainActivity> weakReference, Message msg) {
        if(msg.what == 100){
            Intent intent = new Intent(this, AdActivity.class);
            intent.putExtra(AdActivity.START_AD_ACTIVITY_KEY,true);
            startActivityForResult(intent,100);
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
