package com.sohuvideo.ui_plugin.manager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.sohuvideo.ui_plugin.api.StatisticConstants;
import com.sohuvideo.ui_plugin.fragment.ChannelFragment;
import com.sohuvideo.ui_plugin.fragment.SohuMakeChannelFragment;
import com.sohuvideo.ui_plugin.fragment.SohuStreamFragment;
import com.sohuvideo.ui_plugin.fragment.SohuVideoFragment;
import com.sohuvideo.ui_plugin.model.Channel;

import java.util.ArrayList;
import java.util.List;


public class FragmentSwitchManager {

    private static FragmentSwitchManager mInstance;
    private Context mContext;
    private List<Channel> mTitleList;
    private ViewPager pager;
    private TabLayout indicator;
    private ViewPagerAdapter mViewPagerAdapter;
    private SohuVideoFragment videoFragment;

    private FragmentSwitchManager(SohuVideoFragment videoFragment, Context context) {
        this.videoFragment = videoFragment;
        this.mContext = context;
        mTitleList = new ArrayList<Channel>();
    }

    public static FragmentSwitchManager getInstance(SohuVideoFragment videoFragment, Context context) {
        if (mInstance == null) {
            synchronized (FragmentSwitchManager.class) {
                if (mInstance == null) {
                    mInstance = new FragmentSwitchManager(videoFragment, context);
                }
            }
        }
        return mInstance;
    }


    /**
     * 初始化pager
     *
     * @param pager
     * @param indicator
     */
    public void initViewPager(ViewPager pager, TabLayout indicator) {
        this.pager = pager;
        this.indicator = indicator;
        mViewPagerAdapter = new ViewPagerAdapter(videoFragment.getChildFragmentManager());
        this.pager.setAdapter(mViewPagerAdapter);
        pager.addOnPageChangeListener(new ViewPagerSelectListener());
        indicator.setupWithViewPager(this.pager);
    }

    /**
     * Clear Fragment
     */
    public void clearFragment() {
        pager.removeAllViews();
        mViewPagerAdapter = null;
        mInstance = null;
    }

    /**
     * 刷新标题
     *
     * @param channels
     */
    public void notifyAdapter(List<Channel> channels) {
        pager.removeAllViews();
        mTitleList = channels;
        mViewPagerAdapter.notifyDataSetChanged();
//        indicator.notifyDataSetChanged();
//        indicator.invalidate();
        pager.requestLayout();
    }

    /**
     * ViewPager Adapter
     */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return getSize();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position).getCate_name();
        }

        @Override
        public Fragment getItem(int position) {
            int type = mTitleList.get(position).getType();
            switch (type) {
                case StatisticConstants.FragmentType.CHANNEL:
                    return ChannelFragment.newInstance(mTitleList.get(position));
                case StatisticConstants.FragmentType.PRODUCER:
                    return new SohuStreamFragment();
                case StatisticConstants.FragmentType.SOHUMAKE:
                    return new SohuMakeChannelFragment();
            }
            return null;
        }
    }

    /**
     * ViewPager Listener
     */
    private class ViewPagerSelectListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * size
     *
     * @return
     */
    public int getSize() {
        return mTitleList.size();
    }
}