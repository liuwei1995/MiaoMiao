package com.zhaoyao.miaomiao.http.util;


import com.alibaba.fastjson.JSON;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 *  Created by liuwei on 2017/5/25 09:00
 *  <li>这个需要根据自己的业务需求来写{@link HttpCallback#onCallbackResult(Boolean, String)}</li>
 * @param <T>
 */
public abstract class HttpCallback<T extends Object> implements ICallback {

    private static final String TAG = "HttpCallback";

    @Override
    public void onCallbackResult(Boolean isSuccess, String s) {
        LogUtils.d(TAG,"isSuccess=="+isSuccess+"\n====="+ s);
        if (!isSuccess){
            onChildCallbackResult(isSuccess, (T) s);
        }else {
            Class<?> aClass = analysisClazzInfo(this);
            if (aClass == null){
                onChildCallbackResult(isSuccess, (T) s);
            }else {
                T t = parseObject(s, (Class<T>) aClass);
                onChildCallbackResult(true,t);
            }
        }
    }

    public abstract void onChildCallbackResult(Boolean isSuccess, T result);


    private T parseObject(String text, Class<T> clazz){
        T t = null;
        try {
            t = JSON.parseObject(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    private static Class<?> analysisClazzInfo(Object object){
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0){
                return (Class<?>) actualTypeArguments[0];
            }
        }
        return null;
    }
}
