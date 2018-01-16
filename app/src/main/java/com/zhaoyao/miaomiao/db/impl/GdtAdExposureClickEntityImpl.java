package com.zhaoyao.miaomiao.db.impl;

import android.content.Context;

import com.tgb.lk.ahibernate.dao.impl.BaseDaoImpl;
import com.zhaoyao.miaomiao.db.DBHelper;
import com.zhaoyao.miaomiao.entity.GdtAdExposureClickEntity;

/**
 * Created by liuwei on 2018/1/12 17:53
 */

public class GdtAdExposureClickEntityImpl extends BaseDaoImpl<GdtAdExposureClickEntity> {

    public GdtAdExposureClickEntityImpl(Context context) {
        super(new DBHelper(context), GdtAdExposureClickEntity.class);
    }

    public int updateExposureNumber() {
        return update("UPDATE " + GdtAdExposureClickEntity.class.getSimpleName() + " SET messageState = " + 1 + " WHERE messageType = '" + 1 + "'");
//        return update("UPDATE " + GdtAdExposureClickEntity.class.getSimpleName() + " SET messageState = " + messageState + " WHERE messageType = '" + messageType + "'");
    }
}