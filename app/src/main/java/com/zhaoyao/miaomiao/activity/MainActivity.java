package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.fragment.CartoonRecommendFragment;
import com.zhaoyao.miaomiao.fragment.LeftFragment;

public class MainActivity extends SlidingFragmentActivity {

    private CartoonRecommendFragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
//	        .setScrimColor(Color.BLUE)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(true)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
//	        .addListener(new SwipeListener() {//滑动监听
//
//	            @Override
//	            public void onScroll(float percent, int px) {//滑动的百分比与距离
//	            }
//
//	            @Override
//	            public void onEdgeTouch() {//当开始滑动
//	            }
//
//	            @Override
//	            public void onScrollToClose() {//当滑动关闭
//	            }
//	        })
        ;
        initSlidingMenu(savedInstanceState);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_frame, mContent).commit();
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

        // 设置左侧滑动菜单
        setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, LeftFragment.newInstance()).commit();

        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.5f);
        // 设置触摸屏幕的模式,这里设置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.5f);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     */
    public void switchConent(android.support.v4.app.Fragment fragment, String title) {
//		getSlidingMenu().showContent();
        if ("积分墙".equals(title)) {
//			Intent intent = new Intent();
//			intent.setClass(this, Integral.class);
//			startActivity(intent);
        }
        else if ("login".equals(title)) {
        }else if ("搜索".equals(title)) {
        }else if ("笑话".equals(title)) {
        }else if ("设置收入账号".equals(title)) {
        }
        else if ("拼图".equals(title)) {
//            Intent intent = new Intent();
//            intent.setClass(this, GameActivity.class);
//            startActivity(intent);
        }
        getSlidingMenu().showMenu();
    }

}
