package com.zhaoyao.miaomiao.service;


import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.qq.e.ads.ADActivity;
import com.zhaoyao.miaomiao.BaseApplication;
import com.zhaoyao.miaomiao.activity.AdGdtClickActivity;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;
import com.zhaoyao.miaomiao.util.ToastUtil;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class InstallerAccessibilityService extends AccessibilityService implements TaskHandler {

    private String packageinstaller = "com.android.packageinstaller";

    private String launcher = "com.android.launcher";

    private String browser = "com.android.browser";

    private String nexuslauncher = "com.google.android.apps.nexuslauncher";


    public static String ACTION_START_ACTIVITY = "com.zhaoyao.miaomiao.service.InstallerAccessibilityService.action.START_ACTIVITY";
    public static String ACTION_GLOBAL_ACTION_HOME = "com.zhaoyao.miaomiao.service.InstallerAccessibilityService.action.GLOBAL_ACTION_HOME";
    public static String ACTION_PACKAGE_ADDED = "com.zhaoyao.miaomiao.service.InstallerAccessibilityService.action.PACKAGE_ADDED";
    public static String ACTION_TO_AD_ACTIVITY = "com.zhaoyao.miaomiao.service.InstallerAccessibilityService.action.TO_AD_ACTIVITY";
    public static String ACTION_ON_ACTIVITY_PAUSED = "com.zhaoyao.miaomiao.service.InstallerAccessibilityService.action.ON_ACTIVITY_PAUSED";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (packageinstaller.equals(event.getPackageName())) {
            switch (event.getEventType()) {
                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    mHandler.removeMessages(MSG_CLICK_XIA);
                    mHandler.sendEmptyMessageDelayed(MSG_CLICK_XIA, 500);
                    break;
                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                    break;
                default:
                    break;
            }
        } else if (browser.equals(event.getPackageName())) {
            switch (event.getEventType()) {
                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    ToastUtil.toastSome(this, "现在在浏览器里  5 秒之后启动  AdGdtClickActivity 界面");
                    LogUtils.d("现在在浏览器里  5 秒之后启动  AdGdtClickActivity 界面");
                    mHandler.removeMessages(MSG_ACTION_HOME);
                    mHandler.sendEmptyMessageDelayed(MSG_ACTION_HOME, 5 * 1000);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mMyReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_START_ACTIVITY);
        filter.addAction(ACTION_GLOBAL_ACTION_HOME);
        filter.addAction(ACTION_PACKAGE_ADDED);
        filter.addAction(ACTION_TO_AD_ACTIVITY);
        filter.addAction(ACTION_ON_ACTIVITY_PAUSED);
        registerReceiver(mMyReceiver, filter);
    }

    @Override
    public void onDestroy() {
        if (mMyReceiver != null) {
            unregisterReceiver(mMyReceiver);
        }
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onInterrupt() {

    }

    public static synchronized void startActivity(Context context) {
        Intent intent = new Intent(ACTION_START_ACTIVITY);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static synchronized void performHome(Context context) {
        Intent intent = new Intent(ACTION_GLOBAL_ACTION_HOME);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private Handler mHandler = new TaskHandlerImpl(this);

    private final int MSG_ACTION_HOME = 9;
    private final int MSG_START_ADGDT_CLICK_ACTIVITY = 10;
    private final int MSG_CLICK_XIA = 11;
    private final int MSG_CLICK_AN = 12;
    private final int MSG_ACTION_TO_AD_ACTIVITY = 13;
    private final int MSG_ACTION_ON_ACTIVITY_PAUSED = 14;

    @Override
    public void handleMessage(WeakReference weakReference, Message msg) {
        if (msg.what == MSG_ACTION_HOME) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                mHandler.removeMessages(MSG_START_ADGDT_CLICK_ACTIVITY);
                mHandler.sendEmptyMessageDelayed(MSG_START_ADGDT_CLICK_ACTIVITY, 1000);
            }
        } else if (msg.what == MSG_START_ADGDT_CLICK_ACTIVITY) {
            Intent intent = new Intent();
            ComponentName cn = new ComponentName(getPackageName(), AdGdtClickActivity.class.getName());
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (msg.what == MSG_CLICK_XIA) {
            AccessibilityNodeInfo rootInActiveWindow = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rootInActiveWindow = this.getRootInActiveWindow();
            }
            if (rootInActiveWindow != null) {
                List<AccessibilityNodeInfo> 下一步 = rootInActiveWindow.findAccessibilityNodeInfosByText("下一步");
                if (下一步 != null && 下一步.size() > 0) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : 下一步) {
                        if (!accessibilityNodeInfo.isClickable()) {
                            continue;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            boolean performAction = accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            LogUtils.d("performAction:\t点击下一步按钮\t" + performAction);
                            mHandler.removeMessages(MSG_CLICK_XIA);
                            mHandler.sendEmptyMessageDelayed(MSG_CLICK_XIA, 500);
                        }
                    }
                } else {
                    mHandler.removeMessages(MSG_CLICK_AN);
                    mHandler.sendEmptyMessageDelayed(MSG_CLICK_AN, 500);
                }
            }
        } else if (msg.what == MSG_CLICK_AN) {
            AccessibilityNodeInfo rootInActiveWindow = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rootInActiveWindow = this.getRootInActiveWindow();
            }
            if (rootInActiveWindow != null) {
                List<AccessibilityNodeInfo> 安装 = rootInActiveWindow.findAccessibilityNodeInfosByText("安装");
                if (安装 != null && 安装.size() > 0) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : 安装) {
                        if (!accessibilityNodeInfo.isClickable()) {
                            continue;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            boolean performAction = accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            LogUtils.d("performAction:\t点击安装按钮\t" + performAction);
                        }
                        break;
                    }
                }
            }
        } else if (MSG_ACTION_TO_AD_ACTIVITY == msg.what) {
            BaseApplication.finish(ADActivity.class.getName());
        } else if (MSG_ACTION_ON_ACTIVITY_PAUSED == msg.what) {
            if (homeName == null) {
                List<String> homes = getHomes();
                if (homes.size() != 0) {
                    homeName = homes.get(0);
                }
            }
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
                String packageName = cn.getPackageName();
                if (!this.getPackageName().equals(packageName) && !"com.android.packageinstaller".equals(packageName) && !packageName.equals(homeName)) {
                    ToastUtil.toastSome(this, "重复点击  4秒后返回");
                    LogUtils.d("MSG_ACTION_ON_ACTIVITY_PAUSED", packageName);
                    mHandler.removeMessages(MSG_ACTION_HOME);
                    mHandler.sendEmptyMessageDelayed(MSG_ACTION_HOME, 4 * 1000);
                } else {
                    LogUtils.d("MSG_ACTION_ON_ACTIVITY_PAUSED", packageName);
                }
            }
        }
    }

    private String homeName = null;

    private synchronized String getHome() {
        if (homeName == null) {
            List<String> homes = getHomes();
            if (homes.size() != 0) {
                homeName = homes.get(0);
            }
        }
        return homeName;
    }
    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<>();
        PackageManager packageManager = this.getPackageManager();
        //属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }


    private MyReceiver mMyReceiver;

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent data) {
            if (ACTION_START_ACTIVITY.equals(data.getAction())) {
                Intent intent = new Intent();
                ComponentName cn = new ComponentName(getPackageName(), AdGdtClickActivity.class.getName());
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (ACTION_GLOBAL_ACTION_HOME.equals(data.getAction())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                }
            } else if (ACTION_PACKAGE_ADDED.equals(data.getAction())) {
                LogUtils.d("安装成功回到首页  5 秒之后启动  AdGdtClickActivity 界面");
                mHandler.removeMessages(MSG_ACTION_HOME);
                mHandler.sendEmptyMessageDelayed(MSG_ACTION_HOME, 4 * 1000);
            } else if (ACTION_TO_AD_ACTIVITY.equals(data.getAction())) {
                ToastUtil.toastSome(context, "10 秒后返回广告界面");
                mHandler.removeMessages(MSG_ACTION_TO_AD_ACTIVITY);
                mHandler.sendEmptyMessageDelayed(MSG_ACTION_TO_AD_ACTIVITY, 10 * 1000);
            } else if (ACTION_ON_ACTIVITY_PAUSED.equals(data.getAction())) {
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                if (activityManager != null) {
                    ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
                    String packageName = cn.getPackageName();
                    if (packageName.equals(getHome())){
                        return;
                    }
                }
                mHandler.removeMessages(MSG_ACTION_ON_ACTIVITY_PAUSED);
                mHandler.sendEmptyMessageDelayed(MSG_ACTION_ON_ACTIVITY_PAUSED, 3 * 1000);
            }
        }
    }

    private static final String TAG = "InstallerAccessibilityS";

    /**
     * 检测辅助功能是否开启<br>
     * 方 法 名：isAccessibilitySettingsOn <br>
     * 创 建 人 <br>
     * 创建时间：2016-6-22 下午2:29:24 <br>
     * 修 改 人： <br>
     * 修改日期： <br>
     *
     * @param mContext
     * @return boolean
     */
    private static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        // TestService为对应的服务
        final String service = mContext.getPackageName() + "/" + InstallerAccessibilityService.class.getCanonicalName();
        Log.i(TAG, "service:" + service);
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            // com.z.buildingaccessibilityservices/com.z.buildingaccessibilityservices.TestService
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }

    public static boolean startActivitySettings(Context mContext) {
        if (!isAccessibilitySettingsOn(mContext)) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            mContext.startActivity(intent);
            return false;
        }
        return true;
    }

}
