package com.zhaoyao.miaomiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sohuvideo.ui_plugin.fragment.SohuVideoFragment;
import com.zhaoyao.miaomiao.activity.AdActivity;
import com.zhaoyao.miaomiao.activity.BaseNewActivity;
import com.zhaoyao.miaomiao.activity.GoogleAdActivity;
import com.zhaoyao.miaomiao.adapter.TabFragmentAdapter;
import com.zhaoyao.miaomiao.fragment.CartoonRecommendFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseNewActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ad:
                startActivity(new Intent(this,AdActivity.class));
                break;
            case R.id.tv_google_ad:
                startActivity(new Intent(this,GoogleAdActivity.class));
                break;
        }
    }
}
