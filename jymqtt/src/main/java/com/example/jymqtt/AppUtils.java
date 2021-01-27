package com.example.jymqtt;

import android.content.Context;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 20日 15时 53分
 * @Data： 获取全局Context
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class AppUtils {

    private static Application application;

    public static void setApplication(Application application) {
        if (AppUtils.application != null) {
            throw new IllegalStateException("application already holded 'application'.");
        }
        AppUtils.application = application;
    }
    public static Context getContext() {
        return application.getApplicationContext();
    }
}