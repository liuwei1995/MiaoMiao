package com.zhaoyao.miaomiao.db.impl;

import android.content.Context;

import com.tgb.lk.ahibernate.dao.impl.BaseDaoImpl;
import com.zhaoyao.miaomiao.db.DBHelper;
import com.zhaoyao.miaomiao.entity.GdtAdExposureClickEntity;

import java.util.List;

/**
 * Created by liuwei on 2018/1/12 17:53
 */

public class GdtAdExposureClickEntityImpl extends BaseDaoImpl<GdtAdExposureClickEntity> {

    public GdtAdExposureClickEntityImpl(Context context) {
        super(new DBHelper(context), GdtAdExposureClickEntity.class);
    }

    public synchronized int updateExposureNumber(Integer id) {
        if (id == null || id < 0)return -1;
        return update("UPDATE " + GdtAdExposureClickEntity.class.getSimpleName() + "  SET exposureNumber = (exposureNumber + 1) WHERE id = " + id);
    }

    public synchronized int updateclickNumber(Integer id) {
        if (id == null || id < 0)return -1;
        return update("UPDATE " + GdtAdExposureClickEntity.class.getSimpleName() + " SET clickNumber = (clickNumber + 1) WHERE id = " + id);
    }

    public List<GdtAdExposureClickEntity> find(){
        return findBySql2List("SELECT * FROM " + GdtAdExposureClickEntity.class.getSimpleName());
    }

    public GdtAdExposureClickEntity findByCreateTime(String createTime){
        return findBySql("SELECT * FROM " + GdtAdExposureClickEntity.class.getSimpleName() + " WHERE createTime = ?", createTime);
    }

}