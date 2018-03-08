package com.zhaoyao.miaomiao;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.qq.e.ads.ADActivity;
import com.sohuvideo.ui_plugin.api.UiPluginInit;
import com.zhaoyao.miaomiao.http.OkHttpPresenterImpl;
import com.zhaoyao.miaomiao.http.util.HttpHelper;
import com.zhaoyao.miaomiao.service.InstallerAccessibilityService;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.NetWorkUtils;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;
import com.zhaoyao.miaomiao.util.image.GildePresenterImpl;
import com.zhaoyao.miaomiao.util.image.util.ImageHelper;
import com.zhaoyao.miaomiao.util.preferences.DataAccessSharedPreferencesUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by liuwei on 2017/3/20
 */

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static final int SDK = Build.VERSION.SDK_INT;
    /**
     * 网络是否可用
     */
    public static boolean NETWORK_IS_AVAILABLE = false;

    public static boolean isWiFi = false;

    public static final Map<String, Integer> map = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtils.getInstance().init(this);
        NETWORK_IS_AVAILABLE = NetWorkUtils.isNetworkConnected(this);
        if (NETWORK_IS_AVAILABLE) {
            isWiFi = NetWorkUtils.isWifiConnected(this);
        }
        HttpHelper.newInstance().init(OkHttpPresenterImpl.newInstance().init(this));

        new LogUtils.Builder(this).setLogSwitch(true);
        ImageHelper.init(GildePresenterImpl.newInstance(this));
        UiPluginInit.init(this);
        DataAccessSharedPreferencesUtils.newInstance().init(this);
        for (int i = 1; i <= 30; i++) {
            try {
                Field bannerPosID = null;
                if (i == 1) {
                    bannerPosID = Constants.class.getDeclaredField("BannerPosID");
                } else {
                    bannerPosID = Constants.class.getDeclaredField("BannerPosID" + i);
                }
                bannerPosID.setAccessible(true);
                map.put(bannerPosID.get(null).toString(), (i - 1));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized void finish(String name){
        Activity activity = mapActivity.remove(name);
        if (null != activity){
            activity.finish();
        }
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ComponentName componentName = activity.getComponentName();
        if (ADActivity.class.getName().equals(componentName.getClassName())){
            mapActivity.put(activity.getComponentName().getClassName(), activity);
            LogUtils.d("onActivityStarted\tADActivity", activity.getComponentName().getClassName());
            activity.sendBroadcast(new Intent(InstallerAccessibilityService.ACTION_TO_AD_ACTIVITY));
        }else {
            LogUtils.d("onActivityStarted", activity.getComponentName().getClassName());
        }
    }

    private static Map<String, Activity> mapActivity = new HashMap<>();

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    public static boolean isOut = false;

    @Override
    public void onActivityPaused(Activity activity) {//退出
        if (!isOut) {
//            if (AdGdtClickActivity.class.getName().equals(activity.getComponentName().getClassName())){
//                activity.sendBroadcast(new Intent(InstallerAccessibilityService.ACTION_ON_ACTIVITY_PAUSED));
//            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (ADActivity.class.getName().equals(activity.getComponentName().getClassName())){
            mapActivity.remove(activity.getComponentName().getClassName());
        }
    }
}
