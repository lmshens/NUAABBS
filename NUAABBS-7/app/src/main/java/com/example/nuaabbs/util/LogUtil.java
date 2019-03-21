package com.example.nuaabbs.util;

import android.util.Log;

import com.example.nuaabbs.common.Constant;

public class LogUtil {

    public static final String TAG = Constant.APP_LOG_TAG + " MyLog ";

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int level = VERBOSE;

    public static void v(String tag, String msg){
        if(level <= VERBOSE) Log.v(TAG + tag, msg);
    }

    public static void d(String tag, String msg){
        if(level <= DEBUG) Log.d(TAG + tag, msg);
    }

    public static void d(String msg){
        if(level <= DEBUG) Log.d(TAG + "Default", msg);
    }

    public static void i(String tag, String msg){
        if(level <= INFO) Log.i(TAG + tag, msg);
    }

    public static void w(String tag, String msg){
        if(level <= WARN) Log.w(TAG + tag, msg);
    }

    public static void e(String tag, String msg){
        if(level <= ERROR) Log.e(TAG + tag, msg);
    }
}
