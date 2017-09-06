package com.zhaoyao.miaomiao.http.util;

import java.util.Map;

/**
 * Created by liuwei on 2017/5/25 09:10
 */

public interface IHttpPresenter {

    void get(String url);

    void get(String url, Long timeout);

    void get(String url, ICallback callback);

    void get(String url, Long timeout, ICallback callback);

    void get(String url, Object tag, ICallback callback);

    void get(String url, Long timeout, Object tag, ICallback callback);

    void get(String url, Map<String, String> params, ICallback callback);

    void get(String url, Map<String, String> params, Long timeout, ICallback callback);

    void get(String url, Map<String, String> params, Object tag, ICallback callback);

    void get(String url, Map<String, String> params, Long timeout, Object tag, ICallback callback);

    void post(String url);

    void post(String url, Long timeout);

    void post(String url, ICallback callback);

    void post(String url, Long timeout, ICallback callback);

    void post(String url, Object tag, ICallback callback);

    void post(String url, Long timeout, Object tag, ICallback callback);

    void post(String url, Map<String, Object> params, ICallback callback);

    void post(String url, Map<String, Object> params, Long timeout, ICallback callback);

    void post(String url, Map<String, Object> params, Object tag, ICallback callback);

    void post(String url, Map<String, Object> params, Long timeout, Object tag, ICallback callback);

    void cancel(Object tag);
}
