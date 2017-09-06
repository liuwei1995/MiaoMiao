package com.sohuvideo.ui_plugin.utils;

import android.util.Log;

/**
 * 日志辅助类
 */
public class LogManager {
    private final static boolean DEBUG = true;
    private static String TAG = "SOHU_LogManager";
    private static String TAGNOR = "SOHU_";
    private static boolean SHOW_LOG = true;

    public static boolean canShow() {
        return DEBUG;
    }
    public static void printStackTrace(Throwable e) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (e != null) {
                	LogManager.e(TAG, e.toString());
                }
            } catch (IllegalArgumentException ex) {
            	LogManager.e(TAG, e.toString());
            } catch (Exception ex) {
            	LogManager.e(TAG, e.toString());
            } catch (Error ex) {
            	LogManager.e(TAG, e.toString());
            }
        }
    }
    public static void v(String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                Log.v(TAG, msg);
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void i(String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                Log.i(TAG, msg);
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void d(String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                Log.d(TAG, msg);
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void w(String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void e(String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                Log.e(TAG, msg);
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void v(String tag, String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.v(TAGNOR + tag, msg);
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void i(String tag, String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.i(TAGNOR + tag, msg);
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void i(String tag, Object msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.i(TAGNOR + tag, msg + "");
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void d(String tag, String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.d(TAGNOR + tag, msg);
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void w(String tag, String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.w(TAGNOR + tag, msg);
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void e(String tag, String msg) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.e(TAGNOR + tag, msg);
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
    public static void e(String tag, String msg, Throwable th) {
        SHOW_LOG = canShow();
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.e(TAGNOR + tag, msg, th);
                }
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            } catch (Error e) {
                LogManager.printStackTrace(e);
            }
        }
    }
}
