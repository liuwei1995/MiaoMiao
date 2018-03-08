package com.zhaoyao.miaomiao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zhaoyao.miaomiao.service.InstallerAccessibilityService;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;

/**
 * Created by liuwei on 2018/3/6 16:41
 */

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (intent.getData() == null)return;
        if(Intent.ACTION_PACKAGE_ADDED.equals(action)){
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "添加成功"+packageName, Toast.LENGTH_LONG).show();
            LogUtils.d(packageName);
            context.sendBroadcast(new Intent(InstallerAccessibilityService.ACTION_PACKAGE_ADDED));
//            LocalBroadcastManager.getInstance(context).sendBroadcast();
        }
        else if(Intent.ACTION_PACKAGE_REMOVED.equals(action)){
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "卸载成功"+packageName, Toast.LENGTH_LONG).show();
            LogUtils.d(packageName);
        }
        else if(Intent.ACTION_PACKAGE_REPLACED.equals(action)){
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "替换成功"+packageName, Toast.LENGTH_LONG).show();
            LogUtils.d(packageName);
        }

    }
}
