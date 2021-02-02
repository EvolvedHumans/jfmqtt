package com.example.graphicsloading;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.graphicsloading
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 02月 01日 20时 20分
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /******************************
         * //universal-image-loader框架
         * //创建默认的ImageLoader的配置参数
         * ImageLoaderConfiguration是图片加载器ImageLoader的配置参数，使用了建造者模式。
         * 这里是直接使用了createDefault()方法创建一个默认的ImageLoaderConfiguration，
         * 当然我们还可以自己设置ImageLoaderConfiguration
         * 如下是基本的初始化参数：
         */
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
//        ImageLoader.getInstance().init(configuration);

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
