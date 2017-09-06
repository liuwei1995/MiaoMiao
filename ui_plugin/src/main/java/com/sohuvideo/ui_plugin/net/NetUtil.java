package com.sohuvideo.ui_plugin.net;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;


public class NetUtil {

    /**
     * 检查网络状态
     *
     * @return true 网络正常 false 网络异常
     */
    public static boolean checkNetState(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) { // wifi可用
            return true;
        } else {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(
                    Service.TELEPHONY_SERVICE);
            if (mTelephonyManager.getSimState() != TelephonyManager.SIM_STATE_READY) // SIM卡没有就绪
            {
                return false;
            } else {
                ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    // 能联网
                    return true;
                } else {
                    // do something
                    // 不能联网
                    return false;
                }
            }
        }
    }
}
