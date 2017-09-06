package com.sohuvideo.ui_plugin.manager;

import com.sohu.lib.net.request.RequestManager;


public class NetRequestManager {

    private static final RequestManager mRequestManager = new RequestManager();

    public static RequestManager getRequestManager() {
        return mRequestManager;
    }


    public static void cancleAllRequest() {
        if (mRequestManager != null)
            mRequestManager.cancelAllRequest();
    }

}
