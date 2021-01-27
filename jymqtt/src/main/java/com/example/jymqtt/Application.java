package com.example.jymqtt;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 12日 18时 12分
 * @Data： 应用数据共享界面
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class Application extends android.app.Application {

    /**
     * 程序创建时执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setApplication(this);
    }

    /**
     * 程序终止时执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 低内存时执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 程序在内存清理时执行
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


}
