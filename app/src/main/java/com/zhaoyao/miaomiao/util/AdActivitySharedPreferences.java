package com.zhaoyao.miaomiao.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhaoyao.miaomiao.activity.AdActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuwei on 2017/11/10 10:08
 */

public class AdActivitySharedPreferences {

    private static AdActivitySharedPreferences mAdActivitySharedPreferences;

    public static AdActivitySharedPreferences newInstance() {
        if (mAdActivitySharedPreferences == null){
            synchronized (AdActivitySharedPreferences.class){
                if (mAdActivitySharedPreferences == null){
                    mAdActivitySharedPreferences = new AdActivitySharedPreferences();
                }
            }
        }
        return mAdActivitySharedPreferences;
    }

//    public synchronized void apply(Context mContext, Intent data){
//        SharedPreferences preferences = mContext.getSharedPreferences(AdActivity.class.getSimpleName(), Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = preferences.edit();
//        Bundle extras = null;
//        if (data != null && (extras  = data.getExtras()) != null){
//            for (String s : extras.keySet()) {
//                Object o = extras.get(s);
//                if (o != null){
//                    if (o instanceof Boolean){
//                        edit.putBoolean(s,Boolean.valueOf(o.toString()));
//                    }else if (o instanceof String){
//                        edit.putString(s,o.toString());
//                    }
//                }
//            }
//        }
//        edit.putBoolean("isJump",true);
//        edit.apply();
//    }

    /**
     *
     * @param mContext m
     * @return  Map<String,Object>   map  isJump
     */
    public synchronized  Map<String,Object> getAll(Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AdActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        Boolean isJump = sharedPreferences.getBoolean("isJump", false);
        Map<String,Object> map = new HashMap<>();
        map.put("isJump",isJump);
        Map<String, ?> all = sharedPreferences.getAll();
        if (all != null){
            for (String s : all.keySet()) {
                map.put(s,all.get(s));
            }
        }
        return  map;
    }
    public synchronized void clear(Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AdActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}
