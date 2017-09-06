package com.sohuvideo.ui_plugin.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 接口类
 */
public class URLFactory {

    //1新上架  -1热度  2好评
    public static final int ORDER_HOT = -1;

    public static final String SOHU_DOMAIN = "http://open.mb.hd.sohu.com/v4/";
    public static final String TITLE = "category/pgc.json?api_key=";
    public static final String COLUMN_URL = "mobile/column/list.json?cate_code=";
    public static final String ALBUM_INFO = "album/info/";
    public static final String ALBUM_LIST = "album/videos/";

    private static String mPartner;
    private static String mApiKey;

    public static void init(Context context) {
        mPartner = getPartnerNo(context);
        mApiKey = getApiKey(context);
    }

    public static String getPartnerNo(Context context) {

        String mApiKey = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            mApiKey = "" + context.getPackageManager().getApplicationInfo(info.packageName, PackageManager.GET_META_DATA)
                    .metaData.getInt("SOHUVIDEO_CHANNEL");
        } catch (Exception e) {

        }
        return mApiKey;
    }

    public static final String getApiKey(Context context) {
        String mApiKey = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            mApiKey = context.getPackageManager().getApplicationInfo(info.packageName, PackageManager.GET_META_DATA)
                    .metaData.getString("SOHUVIDEO_KEY");
        } catch (Exception e) {

        }
        return mApiKey;
    }

    /**
     * 获取Ttitle接口
     *
     * @return
     */
    public static String getPgcTitle() {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append(TITLE).append(mApiKey).append("&partner=").append(mPartner);
        return sb.toString();
    }

    /**
     * 根据cid来获取具体某个频道页面的内容
     *
     * @param cid
     * @return
     */
    public static String getChannelContentByCid(int cid) {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append("category/channel/pgc/").append(cid).append(".json?api_key=").append(mApiKey);
        return sb.toString();
    }


    /**
     * 获取对应的Content
     *
     * @param cate_code 跳转码
     * @return
     */
    public static String getChannelPage(int cate_code) {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append(COLUMN_URL).append(cate_code).append("&api_key=").append(mApiKey);
        return sb.toString();
    }


    /**
     * 下拉刷新 获取更多接口
     *
     * @param cid              一级
     * @param second_cate_code 二级
     * @param page             当前页数
     * @return
     */
    public static String getRefreshPgcUrl(int cid, int second_cate_code, int page, int countByPage) {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append("category/channel/" + cid + ".json?api_key=").append(mApiKey).append("&cat=" + second_cate_code).append("&o=" + ORDER_HOT + "&area=&year=&page=" + page + "&page_size=" + countByPage + "&is_pgc=1");
        return sb.toString();
    }

    /**
     * 搜狐出品
     *
     * @param cid         9004
     * @param page        页数
     * @param countByPage 每页个数
     * @return
     */
    public static String getSoHUMakeUrl(int cid, int page, int countByPage) {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append("category/channel/" + cid + ".json?api_key=").append(mApiKey).append("&cat=&o=" + ORDER_HOT + "&area=&year=&page=" + page + "&page_size=" + countByPage);
        return sb.toString();
    }

    /**
     * 专辑详情
     *
     * @param aid
     * @return
     */
    public static String getAlbumInfoUrl(long aid) {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append(ALBUM_INFO).append(aid).append(".json?api_key=").append(mApiKey);
        return sb.toString();
    }

    /**
     * 专辑-分集列表
     *
     * @param aid
     * @param page
     * @param pageSize
     * @return
     */
    public static String getAlbumListUrl(long aid, int page, int pageSize) {
        StringBuffer sb = new StringBuffer(SOHU_DOMAIN);
        sb.append(ALBUM_LIST).append(aid).append(".json?api_key=").append(mApiKey);
        if (page > 0 && pageSize > 0) {
            sb.append("&page=" + page + "&page_size=" + pageSize);
        }
        return sb.toString();
    }


    /**
     * 出品人信息
     *
     * @return
     */
    public static String getProducerDaraUrl() {
        return "http://api.tv.sohu.com/v4/user/info/92382754.json?api_key=f351515304020cad28c92f70f002261c&_=1450752717708";
    }

    /**
     * 出品人专辑信息
     *
     * @param user_id
     * @return
     */
    public static String getProducerAlbumUrl(long user_id) {
        return "http://api.tv.sohu.com/v4/user/album.json?api_key=f351515304020cad28c92f70f002261c&page=1&page_size=30&user_id=" + user_id + "&with_fee_video=2";
    }

    /**
     * 出品人当前视频列表
     * @param user_id 出品人ID
     * @param page  页数
     * @param pageSize 每页条数
     * @return
     */
    public static String getProducerVideoUrl(long user_id, int page, int pageSize) {
        return "http://api.tv.sohu.com/v4/user/video.json?api_key=f351515304020cad28c92f70f002261c&page=" + page + "&page_size=" + pageSize + "&user_id=" + user_id + "&with_fee_video=2";
    }

}
