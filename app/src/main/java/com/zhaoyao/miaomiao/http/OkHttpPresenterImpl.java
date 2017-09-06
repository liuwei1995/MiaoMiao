package com.zhaoyao.miaomiao.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.zhaoyao.miaomiao.http.util.ICallback;
import com.zhaoyao.miaomiao.http.util.IHttpPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


/**
 * Created by liuwei on 2017/5/25 09:33
 */

public final class OkHttpPresenterImpl implements IHttpPresenter {


//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
//    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
//    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
//    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
//    public static final MediaType FORM = MediaType.parse("multipart/form-data");

    /**
     * 取消请求的时候用
     */
    public static final Map<Object, Call> map = new HashMap<>();

    private Context mContext;

    private OkHttpPresenterImpl() {

    }

    public OkHttpPresenterImpl init(Context context){
        if (context != null && context.getApplicationContext() != null){
            this.mContext = context.getApplicationContext();
        }else {
            this.mContext = context;
        }
        return this;
    }

    private static OkHttpPresenterImpl okHttpUtils = null;
    private static Handler mainHandler;
    public static OkHttpPresenterImpl newInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpPresenterImpl.class){
                if(okHttpUtils == null){
                    okHttpUtils = new OkHttpPresenterImpl();
                    //更新UI线程
                    mainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return  okHttpUtils;
    }

    @Override
    public void get(String url) {
        get(url,null,null,null,null);
    }

    @Override
    public void get(String url, Long timeout) {
        get(url,null,timeout,null,null);
    }

    @Override
    public void get(String url, ICallback callback) {
        get(url,null,null,null,callback);
    }
    @Override
    public void get(String url, Long timeout, ICallback callback) {
        get(url,null,timeout,null,callback);
    }

    @Override
    public void get(String url, Object tag, ICallback callback) {
        get(url,null,null,tag,callback);
    }
    @Override
    public void get(String url, Long timeout, Object tag, ICallback callback) {
        get(url,null,timeout,tag,callback);
    }

    @Override
    public void get(String url, Map<String, String> params, ICallback callback) {
        get(url,params,null,callback);
    }
    @Override
    public void get(String url, Map<String, String> params, Long timeout, ICallback callback) {
        get(url, params,timeout,null,callback);
    }

    @Override
    public void get(String url, Map<String, String> params, final Object tag, final ICallback callback) {
        get(url, params,null,tag, callback);
    }
    @Override
    public void get(String url, Map<String, String> params, Long timeout, final Object tag, final ICallback callback) {
        if(params != null && !params.isEmpty()){
            if(url.trim().endsWith("/")){
                url = url.trim().substring(0, url.length()-1)+"?";
            }else {
                url = url.trim()+"?";
            }
            for (String key : params.keySet()) {
                Object value = params.get(key);
                url += key+"="+(value == null?null:value.toString())+"&";
            }
//            url += "token="+App.token;
//            url = url.substring(0, url.length()-1);
        }else {
            if(url.trim().endsWith("/")){
                url = url.trim().substring(0, url.length()-1)+"?";
            }else {
                url = url.trim()+"?";
            }
//            url += "token="+App.token;
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        Request request = new Builder().url(url).headers(headersBuilder.build()).build();

        Builder newBuilder = request.newBuilder();


        OkHttpClient.Builder b = new OkHttpClient.Builder();
        if (timeout != null && timeout > 1){
            b.connectTimeout(timeout,TimeUnit.SECONDS);
        }
        if (mContext != null){
            PersistentCookieStore persistentCookieStore = new PersistentCookieStore(mContext);
            List<Cookie> cookies = persistentCookieStore.getCookies();
            if (!cookies.isEmpty()) {
                newBuilder.header("Cookie", cookieHeader(cookies));
            }
            b.cookieJar(new CookiesManager(mContext));
        }
        OkHttpClient client = b.build();

        Call call = client.newCall(request);
        if(tag != null){
            addTag(tag,call);
        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                remove(tag);
                if (call.isCanceled())return;
                if(callback == null)return;
                final String string = response.body().string();
                if(response.isSuccessful()){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onCallbackResult(true,string);
                        }
                    });
                }else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onCallbackResult(false,string);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled())return;
                remove(tag);
                if(callback == null)return;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCallbackResult(false,null);
                    }
                });
            }
        });
    }
    @Override
    public void post(String url) {
        post(url, null,null,null, null);
    }
    @Override
    public void post(String url, Long timeout) {
        post(url, null,timeout,null, null);
    }

    @Override
    public void post(String url, ICallback callback) {
        post(url, null,null,null, callback);
    }

    @Override
    public void post(String url, Long timeout, ICallback callback) {
        post(url, null,timeout,null, callback);
    }

    @Override
    public void post(String url, Object tag, ICallback callback) {
        post(url,null,null, tag, callback);
    }

    @Override
    public void post(String url, Long timeout, Object tag, ICallback callback) {
        post(url,null,timeout, tag, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback callback) {
        post(url, null,null,null, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, Long timeout, ICallback callback) {
        post(url, params, timeout,null,callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, final Object tag, final ICallback callback) {
        post(url, params,null,tag, callback);
    }
    @Override
    public void post(String url, Map<String, Object> params, Long timeout, final Object tag, final ICallback callback) {
        FormBody.Builder builder = null;
        if(params != null){
            builder = getFormBodyBuilder();
            for (String key : params.keySet()) {
                builder.add(key,params.get(key) != null ? params.get(key).toString():"");
            }
//            if (!TextUtils.isEmpty(App.token))
//            builder.add("token",App.token);
        }else {
//            if (!TextUtils.isEmpty(App.token)) {
//                builder = getFormBodyBuilder();
////                builder.add("token", App.token);
//            }
        }
        Builder requestBuilder;
        if(builder != null){
            requestBuilder = new Builder().url(url).post(builder.build());
        }else {
            requestBuilder = new Builder().url(url);
        }

        Headers.Builder headersBuilder = new Headers.Builder();
//        headersBuilder.add("X-NAPI-APP-ID", DeviceUtils.getUDID());
        requestBuilder = requestBuilder.headers(headersBuilder.build());

        Request request = requestBuilder.build();

        Builder newBuilder = request.newBuilder();

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        if (timeout != null && timeout > 1){
            b.connectTimeout(timeout,TimeUnit.SECONDS);
        }
        if (mContext != null){
            PersistentCookieStore persistentCookieStore = new PersistentCookieStore(mContext);
            List<Cookie> cookies = persistentCookieStore.getCookies();
            if (!cookies.isEmpty()) {
                newBuilder.header("Cookie", cookieHeader(cookies));
            }
            b.cookieJar(new CookiesManager(mContext));
        }

        OkHttpClient client = b.build();

        Call call = client.newCall(request);
        if(tag != null){
            addTag(tag,call);
        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                remove(tag);
                if (call.isCanceled())return;
                if(callback == null)return;
                    final String string = response.body().string();
                    if(response.isSuccessful()){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onCallbackResult(true,string);
                            }
                        });
                    }else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onCallbackResult(false,string);
                            }
                        });
                    }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                remove(tag);
                if (call.isCanceled())return;
                if(callback == null)return;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCallbackResult(false,null);
                    }
                });
            }
        });
    }

    /**
     * 添加数据
     * @return Builder  add("key", "value")
     */
    private FormBody.Builder getFormBodyBuilder() {
        return new FormBody.Builder();
    }
    public static String cookieHeader(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        for (int i = 0, size = cookies.size(); i < size; i++) {
            if (i > 0) {
                cookieHeader.append("; ");
            }
            Cookie cookie = cookies.get(i);
            cookieHeader.append(cookie.name()).append('=').append(cookie.value());
        }
        return cookieHeader.toString();
    }

    private static void addTag(Object key, Call value){
        synchronized (map){
            map.put(key,value);
        }
    }
    @Override
    public  void cancel(Object tag) {
        synchronized (map){
            Call call = map.remove(tag);
            if(call != null){
                call.cancel();
            }
        }
    }
    private  static void remove(Object tag){
        synchronized (map){
            if(tag != null)
                map.remove(tag);
        }
    }

}
