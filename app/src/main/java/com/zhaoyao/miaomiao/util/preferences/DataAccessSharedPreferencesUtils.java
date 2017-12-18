package com.zhaoyao.miaomiao.util.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 数据存取
 * Created by liuwei on 2017/11/22 09:26
 * @see #init(Context)
 */

public class DataAccessSharedPreferencesUtils {

    private static DataAccessSharedPreferencesUtils mDataAccessSharedPreferencesUtils;

    public static DataAccessSharedPreferencesUtils newInstance() {
        if (mDataAccessSharedPreferencesUtils == null){
            synchronized (DataAccessSharedPreferencesUtils.class){
                if (mDataAccessSharedPreferencesUtils == null){
                    mDataAccessSharedPreferencesUtils = new DataAccessSharedPreferencesUtils();
                }
            }
        }
        return mDataAccessSharedPreferencesUtils;
    }
    private Context mContext;

    public synchronized Context getContext(){
        return mContext;
    }
    public synchronized SharedPreferences getSharedPreferences(Context con,String name){
        if (con == null)throw new NullPointerException("Context == null");
        if (con.getApplicationContext() != null)return con.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return con.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
    public synchronized SharedPreferences getSharedPreferences(String name){
        if (mContext == null)throw new NullPointerException("Context == null  see DataAccessSharedPreferencesUtils.init()");
        return mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void init(Context con){
        if (mContext == null){
            synchronized (DataAccessSharedPreferencesUtils.class){
                if (mContext == null){
                    if (con == null)throw new NullPointerException("Context == null");
                    if (con.getApplicationContext() != null){
                        this.mContext = con.getApplicationContext();
                    }else {
                        this.mContext = con;
                    }
                }
            }
        }
    }

    public synchronized void putString(String name, String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(name);
        sharedPreferences.edit().putString(key,value).commit();
    }

    public synchronized void putInt(String name, String key, Integer value){
        SharedPreferences sharedPreferences = getSharedPreferences(name);
        sharedPreferences.edit().putInt(key,value).apply();
    }

    public synchronized void putBoolean(String name, String key, boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences(name);
        sharedPreferences.edit().putBoolean(key,value).apply();
    }

    public synchronized void putLong(String name, String key, Long value){
        SharedPreferences sharedPreferences = getSharedPreferences(name);
        sharedPreferences.edit().putLong(key,value).apply();
    }

    public synchronized void putFloat(String name, String key, Float value){
        SharedPreferences sharedPreferences = getSharedPreferences(name);
        sharedPreferences.edit().putFloat(key,value).apply();
    }
    public synchronized String getString(String name, String key){
        return getString(name, key, null);
    }

    public synchronized String getString(String name, String key, String defValue){
        return getSharedPreferences(name).getString(key, defValue);
    }

    /**
     * 获取 int
     * @param name
     * @param key
     * @return 默认返回 -1
     */
    public synchronized Integer getInt(String name, String key){
        return getSharedPreferences(name).getInt(key, -1);
    }

    public synchronized Integer getInt(String name, String key, Integer defValue){
        return getSharedPreferences(name).getInt(key, defValue);
    }

    /**
     *
     * @param name
     * @param key
     * @return 默认返回 false
     */
    public synchronized boolean getBoolean(String name, String key){
        return getSharedPreferences(name).getBoolean(key, false);
    }

    public synchronized boolean getBoolean(String name, String key, boolean defValue){
        return getSharedPreferences(name).getBoolean(key, defValue);
    }


    public synchronized void remove(String name){
        getSharedPreferences(name).edit().clear().apply();
    }

    public synchronized void remove(String name, String key){
        getSharedPreferences(name).edit().remove(key).apply();
    }



    /**
     * 保存用户信息
     * @param data json  对应 {@link #getUserInformation()}
     */
    public synchronized void saveUserInformation(Object data){
        if (data == null)return;
        putString("saveUserInformation", "saveUserInformation", data.toString());
    }

    /**
     *
     * @return s   有就返回String数据   没有 就是 null {@link #saveUserInformation(Object)}
     */
    public synchronized String getUserInformation(){
        return getString("saveUserInformation", "saveUserInformation");
    }

    /**
     * 移除用户信息  see {@link #saveUserInformation(Object)}
     */
    public synchronized void removeUserInformation(){
        remove("saveUserInformation", "saveUserInformation");
    }


    private static final String STORE_VERSION_NAME = "STORE_VERSION_NAME";

    private static final String STORE_VERSION_KEY = "STORE_VERSION_KEY";

    /**
     * 保存获取的版本号信息
     * @param data d
     */
    public synchronized void saveStoreVersion(Object data){
        if (data != null)
        putString(STORE_VERSION_NAME, STORE_VERSION_KEY, data.toString());
    }

    /**
     *  获取保存的版本号信息
     * @return s
     */
    public synchronized String getStoreVersion(){
        return getString(STORE_VERSION_NAME, STORE_VERSION_KEY, null);
    }

    public synchronized void removeStoreVersion(){
        remove(STORE_VERSION_NAME, STORE_VERSION_KEY);
    }

    private static final String STORE_VERSION_STATE_NAME = "STORE_VERSION_STATE_NAME";

    private static final String STORE_VERSION_STATE_KEY = "STORE_VERSION_STATE_KEY";

    /**
     * 是否请求过版本号
     * @param isSave is
     */
    public synchronized void saveStoreVersionState(boolean isSave){//状态
        putBoolean(STORE_VERSION_STATE_NAME, STORE_VERSION_STATE_KEY, isSave);
    }

    public synchronized boolean getStoreVersionState(){
        return getBoolean(STORE_VERSION_STATE_NAME, STORE_VERSION_STATE_KEY, false);
    }


    public synchronized void removeStoreVersionState(){
        remove(STORE_VERSION_STATE_NAME, STORE_VERSION_STATE_KEY);
    }

    /**
     * 保存是不是第一次运行程序
     * @param isFirst i
     */
    public synchronized void saveTheFirst(boolean isFirst){
        putBoolean("saveTheFirst", "saveTheFirst", isFirst);
    }

    public synchronized boolean getTheFirst(){
        return getBoolean("saveTheFirst", "saveTheFirst", false);
    }

    public synchronized void removeTheFirst(){
        remove("saveTheFirst", "saveTheFirst");
    }


    private static final String USER_KEY = "USER_KEY";

    private static final String USER_KEY_NAME = "USER_KEY_NAME";

    private static final String USER_KEY_PASSWORD = "USER_KEY_PASSWORD";

    /**
     * 获取用户名
     * @return 用户名
     */
    public synchronized String getUserName(){
        return getString(USER_KEY,USER_KEY_NAME);
    }

    /***
     * 获取用户密码
     * @return 密码
     */
    public synchronized String getPassword(){
        return getString(USER_KEY,USER_KEY_PASSWORD);
    }

    /***
     * 保存登录的用户名和密码
     * @param userName 用户名
     * @param password 密码
     */
    public synchronized void saveUserNameAndPassword(String userName, String password){
        SharedPreferences.Editor editor = getSharedPreferences(USER_KEY).edit();

        editor.putString(USER_KEY_NAME,userName);
        editor.putString(USER_KEY_PASSWORD,password);

        editor.apply();
    }

    /**
     * 移除保存的登录的用户名和密码
     */
    public synchronized void removeUserNameAndPassword(){
        remove(USER_KEY);
    }

    private static final String RECORD_PASSWORD_NAME = "RECORD_PASSWORD_NAME";
    private static final String RECORD_PASSWORD_KEY = "RECORD_PASSWORD_KEY";

    /**
     * 保存是否记录密码的状态
     * @see #isRecordPassword(boolean)
     * @param isRecord 是否记录
     */
    public synchronized void recordPassword(boolean isRecord){
        putBoolean(RECORD_PASSWORD_NAME, RECORD_PASSWORD_KEY, isRecord);
    }

    public synchronized boolean isRecordPassword(boolean defValue){
       return getBoolean(RECORD_PASSWORD_NAME, RECORD_PASSWORD_KEY, defValue);
    }

}
