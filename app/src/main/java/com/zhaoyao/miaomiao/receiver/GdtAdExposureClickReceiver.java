package com.zhaoyao.miaomiao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhaoyao.miaomiao.activity.AdActivity;
import com.zhaoyao.miaomiao.db.impl.GdtAdExposureClickEntityImpl;
import com.zhaoyao.miaomiao.entity.GdtAdExposureClickEntity;
import com.zhaoyao.miaomiao.util.DateUtils;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuwei on 2018/1/16 10:36
 */

public class GdtAdExposureClickReceiver extends BroadcastReceiver {

    private static final String TAG = "GdtAdExposureClickRecei";

    public static final String ACTION_UPDATE_EXPOSURE_NUMBER = "miaomiao.receiver.action.UPDATE_EXPOSURE_NUMBER";

    public static final String ACTION_UPDATE_CLICK_NUMBER = "miaomiao.receiver.action.UPDATE_CLICK_NUMBER";

    private GdtAdExposureClickEntityImpl mGdtAdExposureClickEntityImpl;

    private String format_data;

    private GdtAdExposureClickEntity byCreateTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mGdtAdExposureClickEntityImpl == null)
            synchronized (this) {
                if (mGdtAdExposureClickEntityImpl == null)
                    mGdtAdExposureClickEntityImpl = new GdtAdExposureClickEntityImpl(context);
            }
        SimpleDateFormat simpleDateFormat = DateUtils.dateFormat.get();
        format_data = simpleDateFormat.format(new Date());

        if (byCreateTime == null) {
            synchronized (this) {
                if (byCreateTime == null) {
                    byCreateTime = mGdtAdExposureClickEntityImpl.findByCreateTime(format_data);
                    LogUtils.d(TAG, byCreateTime);
                }
            }
        }
        if (byCreateTime == null || !byCreateTime.getCreateTime().equals(format_data)) {
            synchronized (this) {
                if (byCreateTime == null || !byCreateTime.getCreateTime().equals(format_data)) {
                    GdtAdExposureClickEntity gdtAdExposureClickEntity = new GdtAdExposureClickEntity();
                    gdtAdExposureClickEntity.setCreateTime(format_data);
                    long insert = mGdtAdExposureClickEntityImpl.insert(gdtAdExposureClickEntity);
                    if (insert >= 0) {
                        byCreateTime = gdtAdExposureClickEntity;
                    } else {
                        byCreateTime = null;
                    }
                }
            }
        }
        if (byCreateTime == null) {
            LogUtils.d(TAG, "byCreateTime == null");
            return;
        }

        if (ACTION_UPDATE_EXPOSURE_NUMBER.equals(intent.getAction())) {
            int i = mGdtAdExposureClickEntityImpl.updateExposureNumber(byCreateTime.getId());
            if (i >= 0) {
                LogUtils.d(TAG, ACTION_UPDATE_EXPOSURE_NUMBER + "\t成功");
                Intent intent1 = new Intent(AdActivity.ACTION_GDT_AD_EXPOSURE_CLICK_ENTITY);
                intent1.putExtra("exposureNumber", byCreateTime.getExposureNumber() + "");
                intent1.putExtra("clickNumber", byCreateTime.getClickNumber() + "");
                context.sendBroadcast(intent1);
            } else {
                LogUtils.d(TAG, ACTION_UPDATE_EXPOSURE_NUMBER + "\t失败");
            }
        } else if (ACTION_UPDATE_CLICK_NUMBER.equals(intent.getAction())) {
            int i = mGdtAdExposureClickEntityImpl.updateclickNumber(byCreateTime.getId());
            if (i >= 0) {
                LogUtils.d(TAG, ACTION_UPDATE_CLICK_NUMBER + "\t成功");
            } else {
                LogUtils.d(TAG, ACTION_UPDATE_CLICK_NUMBER + "\t失败");
            }
        }
    }
}
