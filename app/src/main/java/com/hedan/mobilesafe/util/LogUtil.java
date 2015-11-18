package com.hedan.mobilesafe.util;

import android.util.Log;

/**
 * 自定义Log类，指定Level可以方便地管理显示的日志
 * Created by Administrator on 2015/10/27.
 */
public class LogUtil {
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    private static final int LEVEL = VERBOSE;

    public static void v(String tag ,String msg){
        if(LEVEL <= VERBOSE){
            Log.v(tag,msg);
        }
    }
    public static void d(String tag ,String msg){
        if(LEVEL <= DEBUG){
            Log.d(tag, msg);
        }
    }
    public static void i(String tag ,String msg){
        if(LEVEL <= INFO){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag ,String msg){
        if(LEVEL <= WARN){
            Log.w(tag, msg);
        }
    }
    public static void w(String tag ,String msg,Exception e){
        if(LEVEL <= WARN){
            Log.w(tag,msg,e);
        }
    }
    public static void e(String tag ,String msg,Exception e){
        if(LEVEL <= ERROR){
            Log.e(tag,msg,e);
        }
    }
    public static void e(String tag ,String msg){
        if(LEVEL <= ERROR){
            Log.e(tag,msg);
        }
    }
}
