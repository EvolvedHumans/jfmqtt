package com.yangf.pub_libs.log;

import android.util.Log;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 19日 23时 19分
 * @Data： 日志打印工具类，定制自己的日志工具
 * 在便谢谢一个比较庞大的项目，期间为了方便调试，在代码中加入了大量的日志，但如果项目完成了，如果还添加这些日志，
 * 就可能应用项目的运行速度，这时候就需要使用自定义的日志类来改善。
 *
 * 使用这种方法，刚才所说的那个问题也就不复存在零零，你只需要在开发阶段将level指定成VERBOSE，当项目正式上线的时候
 * 将level指定成ERROR就可以了
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class Log4j {
    private final static Integer VERBOSE = 1;
    private final static Integer DEBUG = 2;
    private final static Integer INFO = 3;
    private final static Integer WARN = 4;
    private final static Integer ERROR = 5;

    //项目未上线前，将日志级别调到最低，打印所有日志
    private final static Integer level = VERBOSE;

    public static void v(String tag,String msg){
        if(level<=VERBOSE){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if(level<=DEBUG){
            Log.d(tag,msg);
        }
    }

    public static void i(String tag,String msg){
        if(level<=INFO){
            Log.v(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if(level<=WARN){
            Log.w(tag,msg);
        }
    }

    public static void e(String tag,String msg){
        if(level<=ERROR){
            Log.e(tag,msg);
        }
    }

}
