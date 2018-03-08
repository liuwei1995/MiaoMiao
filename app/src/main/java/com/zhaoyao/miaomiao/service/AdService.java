package com.zhaoyao.miaomiao.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.zhaoyao.miaomiao.activity.AdActivity;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;
import com.zhaoyao.miaomiao.util.ToastUtil;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;
import com.zhaoyao.miaomiao.util.preferences.DataAccessSharedPreferencesUtils;

import java.lang.ref.WeakReference;
import java.util.Map;

public class AdService extends Service implements TaskHandler {

    public static void startService(Context context, Integer time) {
        if (context == null) return;
        Intent intent = new Intent(context, AdService.class);
        if (time != null && time > 1) {
            Bundle bundle = new Bundle();
            bundle.putInt(TIME_KEY, time);
            intent.putExtras(bundle);
        }
        context.startService(intent);
    }

    public static void stopService(Context context) {
        if (context == null) return;
        Intent intent = new Intent(context, AdService.class);
        context.stopService(intent);
    }

    public AdService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int time = 1;

    private static final String TIME_KEY = "TIME_KEY";

    @Override
    public void onCreate() {
        super.onCreate();
        mAdReceiver = new AdServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ON_CREATE);
        filter.addAction(ACTION_ON_DESTROY);
        filter.addAction(ACTION_RESTART_ACTIVITY);
        filter.addAction(ACTION_ON_KEY_DOWN);
        registerReceiver(mAdReceiver, filter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int anInt = bundle.getInt(TIME_KEY, time);
            time = anInt >= 1 ? anInt : 2;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void sendEmptyMessageDelayed() {
        handler.removeMessages(EMPTY_MESSAGE_DELAYED_2);
        handler.sendEmptyMessageDelayed(EMPTY_MESSAGE_DELAYED_2, time * 60 * 1000);
    }

    public static final int EMPTY_MESSAGE_DELAYED_2 = 2;

    private Handler handler = new TaskHandlerImpl(this);

    public static final String IS_START_ACTIVITY_10_KEY = "IS_START_ACTIVITY_10_KEY";
    public static final String IS_START_ACTIVITY_5_KEY = "IS_START_ACTIVITY_5_KEY";

    private static String AD_ACTIVITY_NAME = "AD_ACTIVITY_NAME";

    public synchronized static void saveData(Bundle extras) {
        if (extras != null) {
            ToastUtil.toastSome(DataAccessSharedPreferencesUtils.newInstance().getContext(), "saveData");
            SharedPreferences preferences = DataAccessSharedPreferencesUtils.newInstance().getSharedPreferences(AD_ACTIVITY_NAME);
            SharedPreferences.Editor edit = preferences.edit();
            for (String s : extras.keySet()) {
                Object o = extras.get(s);
                if (o != null) {
                    if (o instanceof Boolean) {
                        edit.putBoolean(s, Boolean.valueOf(o.toString()));
                    } else if (o instanceof String) {
                        edit.putString(s, o.toString());
                    }
                }
            }
            edit.commit();
        } else {
            LogUtils.file("extras == null");
        }
    }

    public synchronized static Bundle getData() {
        Bundle bundle = new Bundle();
        SharedPreferences preferences = DataAccessSharedPreferencesUtils.newInstance().getSharedPreferences(AD_ACTIVITY_NAME);
        Map<String, ?> all = preferences.getAll();
        if (all != null)
            for (String s : all.keySet()) {
                Object o = all.get(s);
                if (o == null) continue;
                if (o instanceof String) {
                    bundle.putString(s, (String) o);
                } else if (o instanceof Boolean) {
                    bundle.putBoolean(s, (Boolean) o);
                }
            }
        return bundle;
    }

    public synchronized static void removeData() {
        DataAccessSharedPreferencesUtils.newInstance().remove(AD_ACTIVITY_NAME);
    }

    @Override
    public void handleMessage(WeakReference weakReference, Message msg) {
        if (msg.what == EMPTY_MESSAGE_DELAYED_2) {
            if (!isCreate) {
                Bundle bundle = getData();
                if (bundle.getBoolean(IS_START_ACTIVITY_5_KEY, false)) {
                    Intent intent = new Intent(this, AdActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    if (bundle.getBoolean(IS_START_ACTIVITY_10_KEY, false)) {
                        Intent intent = new Intent(this, AdActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        ToastUtil.toastSome(DataAccessSharedPreferencesUtils.newInstance().getContext(), "removeData");
                        removeData();
                    }
                }
            } else {
                ToastUtil.toastSome(this, "已经启动");
            }
        }
    }

    private AdServiceReceiver mAdReceiver;

    @Override
    public void onDestroy() {
        if (mAdReceiver != null) {
            unregisterReceiver(mAdReceiver);
        }
        handler.removeCallbacksAndMessages(null);
        removeData();
        super.onDestroy();
    }

    public static String ACTION_ON_CREATE = "ACTION_ON_CREATE";
    public static String ACTION_ON_DESTROY = "ACTION_ON_DESTROY";
    public static String ACTION_RESTART_ACTIVITY = "ACTION_RESTART_ACTIVITY";
    public static String ACTION_ON_KEY_DOWN = "ACTION_ON_KEY_DOWN";


    private boolean isCreate = false;

    public class AdServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_ON_CREATE.equals(intent.getAction())) {
                isCreate = true;
                handler.removeMessages(EMPTY_MESSAGE_DELAYED_2);
                ToastUtil.toastSome(DataAccessSharedPreferencesUtils.newInstance().getContext(), "取消");
            } else if (ACTION_ON_DESTROY.equals(intent.getAction())) {
                isCreate = false;
                ToastUtil.toastSome(DataAccessSharedPreferencesUtils.newInstance().getContext(), "ACTION_ON_DESTROY");
                sendEmptyMessageDelayed();
            }else if (ACTION_ON_KEY_DOWN.equals(intent.getAction())){
                handler.removeMessages(EMPTY_MESSAGE_DELAYED_2);
                ToastUtil.toastSome(DataAccessSharedPreferencesUtils.newInstance().getContext(), "ACTION_ON_KEY_DOWN");
                removeData();
            }
        }
    }
}
