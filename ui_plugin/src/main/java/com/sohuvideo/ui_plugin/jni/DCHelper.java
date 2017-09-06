package com.sohuvideo.ui_plugin.jni;

public class DCHelper {

    static {
        System.loadLibrary("securities");
    }

    /**
     * 获取Tkey，用于DC后天统计，区分唯一UID
     *
     * @param uid
     * @param plat
     * @param poid
     * @param appVer
     * @param partnerNo
     * @return
     */
    public static native String nativeGetKey(int plat, int poid, String appVer, String partnerNo, String uid);
    //public static native String nativeGetKey(int plat, int poid, String appVer, String partnerNo, String uid,
    //                                         Context context);

    /**
     * 获取UidMD5加密串，用于服务器检验上行数据是否为安全可靠
     *
     * @return
     */
    //public static native String nativeGetUidMD5String(String aid, String imei, String imsi, String cpusn, String hwsn,
    //                                                  String mac, String uid);
}
