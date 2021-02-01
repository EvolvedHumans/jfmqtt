package com.example.myapplication;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 07日 11时 44分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"你好",Toast.LENGTH_LONG).show();
    }
}
