package com.sohuvideo.ui_plugin.api;

import android.content.Context;

import com.android.sohu.sdk.common.toolbox.PackageUtils;
import com.sohu.lib.net.request.RequestManager;
import com.sohuvideo.api.SohuPlayerSDK;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.utils.LayoutConstants;


public class UiPluginInit {

    public static void init(Context cn) {
        if (null == cn) {
            return;
        }

        String curProcessName = PackageUtils.getCurProcessName(cn);
        String packageName = cn.getPackageName();

        if (packageName.equals(curProcessName)) {
            /**
             * 初始化网络
             */
            RequestManager.initiate(cn);

            LayoutConstants.reInitLayoutConstants(cn);

            URLFactory.init(cn);
            /**
             * 初始化SDK播放器
             */
            SohuPlayerSDK.init(cn);

        }
    }


    public static void close(){
        SohuPlayerSDK.close();
    }
}
