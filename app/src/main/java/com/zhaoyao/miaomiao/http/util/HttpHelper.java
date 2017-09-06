package com.zhaoyao.miaomiao.http.util;

import java.util.Map;

/**
 * Created by liuwei on 2017/5/25 09:12
 */

public class HttpHelper implements IHttpPresenter {

    private static IHttpPresenter mIHttpPresenter;

    public void init(IHttpPresenter iHttpPresenter) {
        if (mIHttpPresenter == null) {
            synchronized (HttpHelper.class) {
                if (mIHttpPresenter == null)
                    mIHttpPresenter = iHttpPresenter;
            }
        }
    }

    private static HttpHelper mHttpHelper;

    private HttpHelper() {
    }

    public static HttpHelper newInstance() {
//        if (mIHttpPresenter == null) throw new NullPointerException("You must call " + HttpHelper.class.getName() + "#init(IHttpPresenter iHttpPresenter) ");
        if (mHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (mHttpHelper == null)
                    mHttpHelper = new HttpHelper();
            }
        }
        return mHttpHelper;
    }

    @Override
    public void get(String url) {
        mIHttpPresenter.get(url);
    }

    @Override
    public void get(String url, Long timeout) {
        mIHttpPresenter.get(url, timeout);
    }

    @Override
    public void get(String url, ICallback callback) {
        mIHttpPresenter.get(url, callback);
    }

    @Override
    public void get(String url, Long timeout, ICallback callback) {
        mIHttpPresenter.get(url, timeout, callback);
    }

    @Override
    public void get(String url, Object tag, ICallback callback) {
        mIHttpPresenter.get(url, tag, callback);
    }

    @Override
    public void get(String url, Long timeout, Object tag, ICallback callback) {
        mIHttpPresenter.get(url, timeout, tag, callback);
    }

    @Override
    public void get(String url, Map<String, String> params, ICallback callback) {
        mIHttpPresenter.get(url, params, callback);
    }

    @Override
    public void get(String url, Map<String, String> params, Long timeout, ICallback callback) {
        mIHttpPresenter.get(url, params, timeout, callback);
    }

    @Override
    public void get(String url, Map<String, String> params, Object tag, ICallback callback) {
        mIHttpPresenter.get(url, params, tag, callback);
    }

    @Override
    public void get(String url, Map<String, String> params, Long timeout, Object tag, ICallback callback) {
        mIHttpPresenter.get(url, params, timeout, tag, callback);
    }

    @Override
    public void post(String url) {
        mIHttpPresenter.post(url);
    }

    @Override
    public void post(String url, Long timeout) {
        mIHttpPresenter.post(url, timeout);
    }

    @Override
    public void post(String url, ICallback callback) {
        mIHttpPresenter.post(url, callback);
    }

    @Override
    public void post(String url, Long timeout, ICallback callback) {
        mIHttpPresenter.post(url, timeout, callback);
    }

    @Override
    public void post(String url, Object tag, ICallback callback) {
        mIHttpPresenter.post(url, tag, callback);
    }

    @Override
    public void post(String url, Long timeout, Object tag, ICallback callback) {
        mIHttpPresenter.post(url, timeout, tag, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {
        mIHttpPresenter.post(url, params, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, Long timeout, ICallback callback) {
        mIHttpPresenter.post(url, params, timeout, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, Object tag, ICallback callback) {
        mIHttpPresenter.post(url, params, tag, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, Long timeout, Object tag, ICallback callback) {
        mIHttpPresenter.post(url, params, timeout, tag, callback);
    }

    @Override
    public void cancel(Object tag) {
        mIHttpPresenter.cancel(tag);
    }

}
