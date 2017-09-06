package com.zhaoyao.miaomiao.http;


import com.zhaoyao.miaomiao.http.util.HttpHelper;
import com.zhaoyao.miaomiao.http.util.ICallback;

import java.util.Map;

/**
 * Created by liuwei on 2017/3/22
 */

public final class HttpUtils implements HttpPathConstant{


    private static HttpHelper getPresenter(){
        return HttpHelper.newInstance();
    }

    private HttpUtils() {
        throw new NullPointerException("private  final class");
    }

//https://api.yyhao.com/app_api/v2/getrecommend/?client-channel=huawei&client-version=1.6.2&client-type=android

    /**
     * 看漫画获取推荐
     * https://api.yyhao.com/app_api/v2/getrecommend/?
     * client-channel=huawei&client-version=1.6.2&client-type=android
     * @param map client-channel=huawei&client-version=1.6.2&client-type=android
     * @param mICallback h
     */
    public static void getRecommend(Map<String,String> map,Object tag, ICallback mICallback){
        getPresenter().get(IP_API_YYHAO_COM+HTTP_API_YYHAO_COM_APP_API_V2_GETRECOMMEND,map,tag,mICallback);
    }

    /**
     *
     * @param map  comic_id=25934
     * @param tag t
     * @param mICallback h
     */
    public static void getComicInfo(Map<String,String> map,Object tag, ICallback mICallback){
        getPresenter().get(IP_API_YYHAO_COM+HTTP_API_YYHAO_COM_APP_API_V2_GETCOMICINFO,map,tag,mICallback);
    }


    public static void cancel(Object tag){
        getPresenter().cancel(tag);
    }

}
